package org.unitedinternet.cosmo.model.ormlite;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.unitedinternet.cosmo.BeansSimulator;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionItemDetails;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.Stamp;
import org.unitedinternet.cosmo.model.Tombstone;
import org.unitedinternet.cosmo.model.User;

import com.j256.ormlite.dao.Dao;

public class OrmliteItemWrapper implements Item {
	protected OrmliteItem persistedItem;

	public OrmliteItemWrapper() {
	}
	
	public OrmliteItem getPersistedItem() {
		if (persistedItem == null) {
			this.persistedItem = new OrmliteItem();
			
			Dao<OrmliteItem, String> itemDao = BeansSimulator.getBaseItemDao();
            try {
				itemDao.assignEmptyForeignCollection(this.persistedItem, "stamps");
				itemDao.assignEmptyForeignCollection(this.persistedItem, "modifications");
				itemDao.assignEmptyForeignCollection(this.persistedItem, "attributes");
				itemDao.assignEmptyForeignCollection(this.persistedItem, "tombstones");
				itemDao.assignEmptyForeignCollection(this.persistedItem, "parentDetails");
				itemDao.assignEmptyForeignCollection(this.persistedItem, "childDetails");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return persistedItem;
	}

	public void setPersistedItem(OrmliteItem persistedItem) {
		this.persistedItem = persistedItem;
	}

	@Override
	public Set<Stamp> getStamps() {
		return getPersistedItem().getStamps();
	}

	@Override
	public Map<String, Stamp> getStampMap() {
		return getPersistedItem().getStampMap();
	}

	@Override
	public void addStamp(Stamp stamp) {
		getPersistedItem().addStamp(((OrmliteStampWrapper)stamp).getPersistedStamp());
		
	}

	@Override
	public void removeStamp(Stamp stamp) {
		getPersistedItem().removeStamp(((OrmliteStampWrapper)stamp).getPersistedStamp());		
	}

	@Override
	public Stamp getStamp(String type) {
		return getPersistedItem().getStamp(type);
	}

	@Override
	public Stamp getStamp(Class<?> clazz) {
		return getPersistedItem().getStamp(clazz);
	}

	@Override
	public Map<QName, Attribute> getAttributes() {
		return getPersistedItem().getAttributes();
	}

	@Override
	public void addAttribute(Attribute attribute) {
		getPersistedItem().addAttribute(((OrmliteAttributeWrapper)attribute).getPersistedAttribute());		
	}

	@Override
	public void removeAttribute(String name) {
		getPersistedItem().removeAttribute(name);
	}

	@Override
	public void removeAttribute(QName qname) {
		getPersistedItem().removeAttribute(qname);	
	}

	@Override
	public void removeAttributes(String namespace) {
		getPersistedItem().removeAttributes(namespace);
	}

	@Override
	public Attribute getAttribute(String name) {
		return getPersistedItem().getAttribute(name);
	}

	@Override
	public Attribute getAttribute(QName qname) {
		return getPersistedItem().getAttribute(qname);
	}

	@Override
	public Object getAttributeValue(String name) {
		return getPersistedItem().getAttributeValue(name);
	}

	@Override
	public Object getAttributeValue(QName qname) {
		return getPersistedItem().getAttributeValue(qname);
	}

	@Override
	public void setAttribute(String name, Object value) {
		getPersistedItem().setAttribute(name, value);
	}

	@Override
	public void setAttribute(QName key, Object value) {
		getPersistedItem().setAttribute(key, value);
	}

	@Override
	public Map<String, Attribute> getAttributes(String namespace) {
		return getPersistedItem().getAttributes(namespace);
	}

	@Override
	public Date getClientCreationDate() {
		return getPersistedItem().getClientCreationDate();
	}

	@Override
	public void setClientCreationDate(Date clientCreationDate) {
		getPersistedItem().setClientCreationDate(clientCreationDate);
	}

	@Override
	public Date getCreationDate() {
		return getPersistedItem().getCreationDate();
	}

	@Override
	public Date getModifiedDate() {
		return getPersistedItem().getModifiedDate();
	}
	
	@Override
	public Date getClientModifiedDate() {
		return getPersistedItem().getClientModifiedDate();
	}

	@Override
	public void setClientModifiedDate(Date clientModifiedDate) {
		getPersistedItem().setClientModifiedDate(clientModifiedDate);
	}

	@Override
	public String getName() {
		return getPersistedItem().getName();
	}

	@Override
	public void setName(String name) {
		getPersistedItem().setName(name);
	}

	@Override
	public String getDisplayName() {
		return getPersistedItem().getDisplayName();
	}

	@Override
	public void setDisplayName(String displayName) {
		getPersistedItem().setName(displayName);
	}

	@Override
	public User getOwner() {
		return getPersistedItem().getOwner();
	}

	@Override
	public void setOwner(User owner) {
		 getPersistedItem().setOwner(owner);
	}

	@Override
	public String getUid() {
		return getPersistedItem().getUid();
	}

	@Override
	public void setUid(String uid) {
		getPersistedItem().setUid(uid);		
	}

	@Override
	public Set<CollectionItem> getParents() {
		return getPersistedItem().getParents();
	}

	@Override
	public CollectionItemDetails getParentDetails(CollectionItem parent) {
		return getPersistedItem().getParentDetails(parent);
	}

	@Override
	public CollectionItem getParent() {
		return getPersistedItem().getParent();
	}

	@Override
	public Boolean getIsActive() {
		return getPersistedItem().getIsActive();
	}

	@Override
	public void setIsActive(Boolean isActive) {
		getPersistedItem().setIsActive(isActive);
	}

	@Override
	public Set<Tombstone> getTombstones() {
		return getPersistedItem().getTombstones();
	}

	@Override
	public Item copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setItemType(String itemtype) {
		getPersistedItem().setItemtype(itemtype);
	}

	public Long getId() {
		return getPersistedItem().getId();
	}
	
	public void addParent(CollectionItem parent) {
		getPersistedItem().addParent(((OrmliteItemWrapper)parent).getPersistedItem());
	}
}
