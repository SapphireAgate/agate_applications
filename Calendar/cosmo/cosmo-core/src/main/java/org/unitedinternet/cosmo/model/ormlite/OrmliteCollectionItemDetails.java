/*
 * Copyright 2008 Open Source Applications Foundation
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
package org.unitedinternet.cosmo.model.ormlite;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionItemDetails;
import org.unitedinternet.cosmo.model.Item;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Hibernate persistent CollectionItemDetails, which is
 * used to store extra attributes in the many-to-many
 * association of collection<-->item.  Extra information
 * that is stored include the date the item was added
 * to the collection.
 */

@DatabaseTable(tableName = "collection_item")
public class OrmliteCollectionItemDetails implements CollectionItemDetails {

	@DatabaseField(columnName = "CREATEDATE")
	private Long createDate;
	
    @DatabaseField(columnName = "ITEMID", foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private OrmliteItem itemid;
	
	@DatabaseField(columnName = "COLLECTIONID", foreign = true, canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private OrmliteItem collectionid;
 
    public OrmliteCollectionItemDetails() {
    	this.createDate = System.currentTimeMillis();
    }
    
    public OrmliteCollectionItemDetails(Item collection,
            Item item) {
        this.collectionid = (OrmliteItem) collection;
        //this.itemid = ((OrmliteItemWrapper) item).getPersistedItem();
        this.itemid = (OrmliteItem)item;
        this.createDate = System.currentTimeMillis();
    }
    
    public void setCollection(CollectionItem collection) {
        this.collectionid = ((OrmliteCollectionItemWrapper) collection).getPersistedItem();
    }
    
    public CollectionItem getCollection() {
    	OrmliteCollectionItemWrapper item = new OrmliteCollectionItemWrapper();
    	item.setPersistedItem(this.collectionid);
        return item;
    }

    public void  setItem(Item item) {
        this.itemid = ((OrmliteItemWrapper) item).getPersistedItem();
    }
    
    public Item getItem() {
    	OrmliteItemWrapper itemw = new OrmliteItemWrapper();
    	itemw.setPersistedItem(this.itemid);
    	return itemw;
    }

    public Date getTimestamp() {
        return new Date(this.createDate);
    }

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public OrmliteItem getCollectionid() {
		return collectionid;
	}

	public void setCollectionid(OrmliteItem collectionid) {
		this.collectionid = collectionid;
	}

	public OrmliteItem getItemid() {
		return itemid;
	}

	public void setItemid(OrmliteItem itemid) {
		this.itemid = itemid;
	}

//    @Override
//    public boolean equals(Object obj) {
//        if(obj==null) {
//            return false;
//        }
//        if( ! (obj instanceof OrmliteCollectionItemDetails)) {
//            return false;
//        }
//        
//        OrmliteCollectionItemDetails cid = (OrmliteCollectionItemDetails) obj;
//        return this.collection.equals(cid.getCollection()) &&
//            this.item.equals(cid.getItem());
//    }
//
//    @Override
//    public int hashCode() {
//        return this.hashCode();
//    }

    /**
     * PrimaryKey of CollectionItemDetails consists of two
     * foreign keys, the collection, and the item.
     */
//    @Embeddable
//    private static class CollectionItemPK implements Serializable {
//        
//        private static final long serialVersionUID = -8144072492198688087L;
//
//        @ManyToOne(targetEntity = OrmliteCollectionItem.class, fetch = FetchType.EAGER)
//        @JoinColumn(name = "collectionid", nullable = false)
//        public CollectionItem collection;
//
//        @ManyToOne(targetEntity = OrmliteItem.class)
//        @JoinColumn(name = "itemid", nullable = false)
//        public Item item;
//        
//        public CollectionItemPK() {}
//
//        @Override
//        public boolean equals(Object obj) {
//            if(obj==null || item==null || collection==null) {
//                return false;
//            }
//            if( ! (obj instanceof CollectionItemPK)) {
//                return false;
//            }
//            
//            CollectionItemPK pk = (CollectionItemPK) obj;
//            return collection.equals(pk.collection) && item.equals(pk.item);
//        }
//
//        @Override
//        public int hashCode() {
//            return new HashCodeBuilder(13,73 ).appendSuper(item.hashCode())
//            .appendSuper(collection.hashCode()).toHashCode();
//        }
//    }
}
