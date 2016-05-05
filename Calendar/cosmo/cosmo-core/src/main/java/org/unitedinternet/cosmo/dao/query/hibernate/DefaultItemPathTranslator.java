/*
 * Copyright 2006 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitedinternet.cosmo.dao.query.hibernate;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.apache.abdera.i18n.text.UrlEncoding;
import org.apache.commons.lang.NotImplementedException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.unitedinternet.cosmo.dao.hibernate.AbstractDaoImpl;
import org.unitedinternet.cosmo.BeansSimulator;
import org.unitedinternet.cosmo.dao.query.ItemPathTranslator;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.ormlite.OrmliteCollectionItemDetails;
import org.unitedinternet.cosmo.model.ormlite.OrmliteItem;
import org.unitedinternet.cosmo.model.ormlite.OrmliteUser;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Default implementation for ItempPathTranslator. This implementation expects
 * paths to be of the format: /username/parent1/parent2/itemname
 */
public class DefaultItemPathTranslator implements ItemPathTranslator {

    
   
    
    /*
     * (non-Javadoc)
     * 
     * @see org.unitedinternet.cosmo.dao.query.ItemPathTranslator#findItemByPath(org.hibernate.Session,
     *      java.lang.String)
     */

    /**
     * Finds item by path.
     *
     * @param path The given path.
     * @return item The expected item.
     */
    public Item findItemByPath(final String path) {
        return (Item) findItemByPath(BeansSimulator.getConnectionSource(), path);
       	//System.out.println("[AGATE] DefaultItemPathTranslator not implemented findItemByPath");
    	//throw new NotImplementedException();

    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.dao.query.ItemPathTranslator#findItemByPath(java.lang.String, org.unitedinternet.cosmo.model.CollectionItem)
     */

    /**
     * Finds item by the given path.
     *
     * @param path The given path.
     * @param root The collection item.
     * @return The expected item.
     */
    public Item findItemByPath(final String path, final CollectionItem root) {
        return (Item) findItemByPath(BeansSimulator.getConnectionSource(), path, root);
       	//System.out.println("[AGATE] DefaultItemPathTranslator not implemented findItemByPath");
    	//throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public Item findItemParent(String path) {
        if (path == null) {
            return null;
        }

        int lastIndex = path.lastIndexOf("/");
        if (lastIndex == -1) {
            return null;
        }

        if ((lastIndex + 1) >= path.length()) {
            return null;
        }

        String parentPath = path.substring(0, lastIndex);

        return findItemByPath(parentPath);
    }

    /**
     * {@inheritDoc}
     */
    public String getItemName(String path) {
        if (path == null) {
            return null;
        }

        int lastIndex = path.lastIndexOf("/");
        if (lastIndex == -1) {
            return null;
        }

        if ((lastIndex + 1) >= path.length()) {
            return null;
        }

        return path.substring(lastIndex + 1);
    }

    /**
     * Finds item by the given path.
     *
     * @param session The current session.
     * @param path    The given path.
     * @return The expected item.
     */
    protected Item findItemByPath(ConnectionSource c, String path) {

        if (path == null || "".equals(path)) {
            return null;
        }

        if (path.charAt(0) == '/') {
            path = path.substring(1, path.length());
        }

        String[] segments = path.split("/");

        if (segments.length == 0) {
            return null;
        }
        String username = decode(segments[0]);

        System.out.println("[AGATE] username = " + username);
        
        String rootName = username;
        Item rootItem = findRootItemByOwnerAndName(c, username,
                rootName);

        // If parent item doesn't exist don't go any further
        if (rootItem == null) {
            return null;
        }

        Item parentItem = rootItem;
        for (int i = 1; i < segments.length; i++) {
            Item nextItem = findItemByParentAndName(parentItem,
                    decode(segments[i]));
            parentItem = nextItem;
            // if any parent item doesn't exist then bail now
            if (parentItem == null) {
                return null;
            }
        }

        return parentItem;
    }

    /**
     * Finds item by path.
     *
     * @param session The current session.
     * @param path    The given path.
     * @param root    The collection root.
     * @return The expected item.
     */
    protected Item findItemByPath(ConnectionSource c, String path, CollectionItem root) {

        if (path == null || "".equals(path)) {
            return null;
        }

        if (path.charAt(0) == '/') {
            path = path.substring(1, path.length());
        }

        String[] segments = path.split("/");

        if (segments.length == 0) {
            return null;
        }

        Item parentItem = root;
        for (int i = 0; i < segments.length; i++) {
            Item nextItem = findItemByParentAndName(parentItem,
                    decode(segments[i]));
            parentItem = nextItem;
            // if any parent item doesn't exist then bail now
            if (parentItem == null) {
                return null;
            }
        }

        return parentItem;
    }

    protected Item findRootItemByOwnerAndName(ConnectionSource c, String username, String name) {
    	
    	Dao<OrmliteItem, String> itemsDao = BeansSimulator.getBaseItemDao();
    	Dao<OrmliteUser, String> usersDao = BeansSimulator.getBaseUserDao();
    	
    	QueryBuilder<OrmliteItem, String> itemsDaoQb = itemsDao.queryBuilder();
    	QueryBuilder<OrmliteUser, String> usersDaoQb = usersDao.queryBuilder();

    	try {
    		usersDaoQb.where().eq("USERNAME", username);
    		OrmliteItem item = itemsDaoQb.leftJoin(usersDaoQb).where().eq("ITEMNAME", name).queryForFirst();
    		return item;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
 
//        Query hibQuery = session.getNamedQuery(
//                "item.by.ownerName.name.nullParent").setParameter("username",
//                username).setParameter("name", name);
//
//        List<?> results = hibQuery.list();
//        if (results.size() > 0) {
//            return (Item) results.get(0);
//        } else {
//            return null;
//        }
    	//System.out.println("[AGATE] DefaultItemPathTranslator not implemented findRootItemByOwnerAndName");
    	//throw new NotImplementedException();
    }

    protected Item findItemByParentAndName(Item parent,
                                           String name) {

    	Dao<OrmliteItem, String> itemsDao = BeansSimulator.getBaseItemDao();
    	//Dao<OrmliteCollectionItemDetails, String> collectionItemDetailsDao = BeansSimulator.getBaseCollectionItemDetailsDao();

    	QueryBuilder<OrmliteItem, String> itemsDaoQb = itemsDao.queryBuilder();
    	//QueryBuilder<OrmliteCollectionItemDetails, String> collectionItemQb = collectionItemDetailsDao.queryBuilder();

    	try {
    		String query = "SELECT \"ID\" FROM \"ITEM\" " +
    				"LEFT JOIN \"COLLECTION_ITEM\" ON \"ITEM\".\"ID\" = \"COLLECTION_ITEM\".\"ITEMID\" " +
    				"WHERE \"ITEM\".\"ITEMNAME\" = \'" + name + "\' AND  \"COLLECTION_ITEM\".\"COLLECTIONID\" = " + ((OrmliteItem)parent).getId();

    		Long itemid = itemsDao.queryRawValue(query);
    		
    		itemsDaoQb.where().eq("ITEMNAME", name).and().eq("ID", itemid);
    		//collectionItemQb.where().eq("COLLECTIONID", parent);
    		//OrmliteItem item = itemsDaoQb.leftJoin(collectionItemQb).queryForFirst();
    		OrmliteItem item = itemsDaoQb.queryForFirst();
    		return item;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

//        Query hibQuery = session.getNamedQuery("item.by.parent.name")
//                .setParameter("parent", parent).setParameter("name", name);
//
//        List<?> results = hibQuery.list();
//        if (results.size() > 0) {
//            return (Item) results.get(0);
//        } else {
//            return null;
//        }
    	//System.out.println("[AGATE] DefaultItemPathTranslator not implemented findItemByParentAndName");
    	//throw new NotImplementedException();
    }
    private static String decode(String urlPath){
        try {
            return  UrlEncoding.decode(urlPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}