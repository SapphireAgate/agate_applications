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
package org.unitedinternet.cosmo.model.ormlite;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.DiscriminatorType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MapKeyClass;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.persistence.Index;
//import javax.persistence.Version;
//import javax.validation.constraints.NotNull;

//import org.hibernate.annotations.BatchSize;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//import org.hibernate.annotations.NaturalId;
//import org.hibernate.annotations.Type;
//import org.hibernate.validator.constraints.Length;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.AttributeTombstone;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionItemDetails;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.NoteItem;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.Stamp;
import org.unitedinternet.cosmo.model.StampTombstone;
import org.unitedinternet.cosmo.model.Ticket;
import org.unitedinternet.cosmo.model.Tombstone;
import org.unitedinternet.cosmo.model.User;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


///**
// * Hibernate persistent Item.
// */
//@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//
//
//@Table(name = "item",
//        indexes={@Index(name = "idx_itemtype",columnList = "itemtype" ),
//                 @Index(name = "idx_itemuid",columnList = "uid" ),
//                 @Index(name = "idx_itemname",columnList = "itemname" ),
//        }
//)
//@DiscriminatorColumn(
//        name="itemtype",
//        discriminatorType=DiscriminatorType.STRING,
//        length=16)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

// ORMlite persisted Item object

@DatabaseTable(tableName = "item")
public class OrmliteItem implements Item { //extends HibAuditableObject implements Item {

	
	// All fields
	@DatabaseField(columnName = "ITEMTYPE")
	private String itemtype;
	
    @DatabaseField(generatedIdSequence = "SEQ", columnName = "ID")
    private Long id;
    
    @DatabaseField(columnName = "CREATEDATE")
	private Long createDate;
    
    @DatabaseField(columnName = "ETAG")
    private String etag;
	
    @DatabaseField(columnName = "MODIFYDATE")
    private Long modifyDate;
    
    @DatabaseField(columnName = "CLIENTCREATEDATE")
    private Long clientCreationDate;

    @DatabaseField(columnName = "CLIENTMODIFIEDDATE")
    private Long clientModifiedDate;

	@DatabaseField(columnName = "DISPLAYNAME")
	private String displayName;
    
	@DatabaseField(columnName = "ITEMNAME", canBeNull = false)
    private String name;

    @DatabaseField(columnName = "UID", canBeNull = false)
    private String uid;

	//@DatabaseField(columnName = "VERSION", canBeNull = false)
    @DatabaseField(columnName = "VERSION")
	private Integer version;

	@DatabaseField(columnName = "LASTMODIFICATION")
    private Integer lastModification = null;
    
    @DatabaseField(columnName = "LASTMODIFIEDBY")
    private String lastModifiedBy = null;
    
    @DatabaseField(columnName = "NEEDSREPLY")
    private Boolean needsReply = null;

    @DatabaseField(columnName = "SENT")
    private Boolean sent = null;

    @DatabaseField(columnName = "ISAUTOTRIAGE") 
    private Boolean autoTriage = null;
    
    @DatabaseField(columnName = "TRIAGESTATUSCODE")
    private Integer code = null;
    
    @DatabaseField(columnName = "TRIAGESTATUSRANK", dataType = DataType.BIG_DECIMAL_NUMERIC)
    private BigDecimal rank = null;
    
    @DatabaseField(columnName = "ICALUID")
    private String icalUid = null;

    @DatabaseField(columnName = "CONTENTENCODING")
    private String contentEncoding = null;

    @DatabaseField(columnName = "CONTENTLANGUAGE")
    private String contentLanguage = null;

    @DatabaseField(columnName = "CONTENTLENGTH")
    private Long contentLength = null;
    
    @DatabaseField(columnName = "CONTENTTYPE")
    private String contentType = null;

    @DatabaseField(columnName = "HASMODIFICATIONS")
    private boolean hasModifications = false;
    
    
    // All foreign fields
    @DatabaseField(columnName = "OWNERID", foreign = true, canBeNull = false)
    private OrmliteUser owner;
    
    @DatabaseField(columnName = "MODIFIESITEMID", foreign = true)
    private OrmliteItem modifies = null;
    
    // All foreign collections
    @ForeignCollectionField(eager = false)
    ForeignCollection<OrmliteItem> modifications;

    @ForeignCollectionField(eager = false)
    ForeignCollection<OrmliteAttribute> attributes;
    
    @ForeignCollectionField(eager = false)
    ForeignCollection<OrmliteStamp> stamps;
    
    @ForeignCollectionField(eager = false)
    ForeignCollection<OrmliteTombstone> tombstones;

    @ForeignCollectionField(eager = false,  foreignFieldName = "itemid")
    ForeignCollection<OrmliteCollectionItemDetails> parentDetails;
    
    @ForeignCollectionField(eager = false, foreignFieldName = "collectionid")
    ForeignCollection<OrmliteCollectionItemDetails> childDetails;
    
    // Other
	private transient Boolean isActive = Boolean.TRUE;
    private transient Map<String, Stamp> stampMap = null;
    private transient Set<CollectionItem> parents = null;

    public OrmliteItem() {
    	uid = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getStamps()
     */
    public Set<Stamp> getStamps() {
    	Set<Stamp> s = new HashSet<Stamp>();
    	
    	for (Stamp st : stamps) {
    		s.add(st);
    	}
    	
        return Collections.unmodifiableSet(s);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getStampMap()
     */
    public Map<String, Stamp> getStampMap() {
        if(stampMap==null) {
            stampMap = new HashMap<String, Stamp>();
            for(Stamp stamp : stamps) {
                stampMap.put(stamp.getType(), stamp);
            }
        }

        return stampMap;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#addStamp(org.unitedinternet.cosmo.model.Stamp)
     */
    public void addStamp(Stamp stamp) {
        if (stamp == null) {
            throw new IllegalArgumentException("stamp cannot be null");
        }

        // remove old tombstone if exists
        for(Tombstone ts : tombstones) {
            if(ts instanceof StampTombstone && ((StampTombstone) ts).getStampType().equals(stamp.getType())) {
                tombstones.remove(ts);
            }
        }

        stamp.setItem(this);
        stamps.add((OrmliteStamp) stamp);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#removeStamp(org.unitedinternet.cosmo.model.Stamp)
     */
    public void removeStamp(Stamp stamp) {
        // only remove stamps that belong to item
        if(!stamps.contains(stamp)) {
            return;
        }

        stamps.remove(stamp);
        
        OrmliteItemWrapper itemw = new OrmliteItemWrapper();
        itemw.setPersistedItem(this);
        OrmliteStampTombstoneWrapper t = new OrmliteStampTombstoneWrapper(itemw, stamp);

        // add tombstone for tracking purposes
        tombstones.add(t.getPersistedTombstone());
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getStamp(java.lang.String)
     */
    public Stamp getStamp(String type) {
        for(Stamp stamp : stamps) {
            // only return stamp if it matches class and is active
            if(stamp.getType().equals(type)) {
                return stamp;
            }
        }

        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getStamp(java.lang.Class)
     */
    public Stamp getStamp(Class<?> clazz) {
    	System.out.println("[AGATE] stamps = " + stamps + "; for clazz = " + clazz);
        for(Stamp stamp : stamps) {
            // only return stamp if it is an instance of the specified class
            if(clazz.isInstance(stamp)) {
                return stamp;
            }
        }

        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getAttributes()
     */
    public Map<QName, Attribute> getAttributes() {
    	
    	Map<QName, Attribute> a = new HashMap<QName, Attribute>();
    	
    	for (Attribute att : this.attributes) {
    		a.put(att.getQName(), att);
    	}
    	
        return Collections.unmodifiableMap(a);
    }

//    /* (non-Javadoc)
//     * @see org.unitedinternet.cosmo.model.Item#addTicket(org.unitedinternet.cosmo.model.Ticket)
//     */
//    public void addTicket(Ticket ticket) {
//        ticket.setItem(this);
//        tickets.add(ticket);
//    }

//    /* (non-Javadoc)
//     * @see org.unitedinternet.cosmo.model.Item#removeTicket(org.unitedinternet.cosmo.model.Ticket)
//     */
//    public void removeTicket(Ticket ticket) {
//        tickets.remove(ticket);
//    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#addAttribute(org.unitedinternet.cosmo.model.Attribute)
     */
    public void addAttribute(Attribute attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("attribute cannot be null");
        }

        // remove old tombstone if exists
        for(Tombstone ts : tombstones) {
            if(ts instanceof AttributeTombstone && 
               ((AttributeTombstone) ts).getQName().equals(attribute.getQName())) {
                tombstones.remove(ts);
            }
        }

        //((OrmliteAttribute) attribute).validate();
        attribute.setItem(this);
        attributes.add((OrmliteAttribute) attribute);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {
        removeAttribute(new OrmliteQName(name));
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#removeAttribute(org.unitedinternet.cosmo.model.QName)
     */
    public void removeAttribute(QName qname) {
    	for (Attribute a : attributes) {
    		if (a.getQName().equals(qname)) {
    			attributes.remove((OrmliteAttribute) a);
    		}
        }
        tombstones.add(new OrmliteAttributeTombstone(this, qname));
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#removeAttributes(java.lang.String)
     */
    public void removeAttributes(String namespace) {
        ArrayList<QName> toRemove = new ArrayList<QName>();
        for (Attribute a: attributes) {
            if (a.getQName().getNamespace().equals(namespace)) {
                toRemove.add(a.getQName());
            }
        }

        for(QName qname: toRemove) {
            removeAttribute(qname);
        }
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getAttribute(java.lang.String)
     */
    public Attribute getAttribute(String name) {
        return getAttribute(new OrmliteQName(name));
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getAttribute(org.unitedinternet.cosmo.model.QName)
     */
    public Attribute getAttribute(QName qname) {
    	for (Attribute a : attributes) {
    		if (a.getQName().equals(qname))
    			return a;
    	}
    	return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getAttributeValue(java.lang.String)
     */
    public Object getAttributeValue(String name) {
        return getAttributeValue(new OrmliteQName(name));
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getAttributeValue(org.unitedinternet.cosmo.model.QName)
     */
    public Object getAttributeValue(QName qname) {
        Attribute attr = getAttribute(qname);
        if (attr == null) {
            return attr;
        }
        return attr.getValue();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object value) {
        setAttribute(new OrmliteQName(name),value);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setAttribute(org.unitedinternet.cosmo.model.QName, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public void setAttribute(QName key, Object value) {
        OrmliteAttribute attr = (OrmliteAttribute) getAttribute(key);

        if(attr!=null) {
            attr.setValue(value);
            attr.validate();
        }
        else {
            throw new IllegalArgumentException("attribute " + key + " not found");
        }
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getAttributes(java.lang.String)
     */
    public Map<String, Attribute> getAttributes(String namespace) {
        HashMap<String, Attribute> attrs = new HashMap<String, Attribute>();
        for(Attribute a: attributes) {
            if(a.getQName().getNamespace().equals(namespace)) {
                attrs.put(a.getQName().getLocalName(), a);
            }
        }

        return attrs;
    }


    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getClientCreationDate()
     */
    public Date getClientCreationDate() {
        return new Date((Long) clientCreationDate);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setClientCreationDate(java.util.Date)
     */
    public void setClientCreationDate(Date clientCreationDate) {
        this.clientCreationDate = Long.valueOf(clientCreationDate.getTime());
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getClientModifiedDate()
     */
    public Date getClientModifiedDate() {
        return new Date((Long) clientModifiedDate);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setClientModifiedDate(java.util.Date)
     */
    public void setClientModifiedDate(Date clientModifiedDate) {
        this.clientModifiedDate = Long.valueOf(clientModifiedDate.getTime());
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getDisplayName()
     */
    public String getDisplayName() {
        return displayName;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setDisplayName(java.lang.String)
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getOwner()
     */
    public User getOwner() {
        return owner;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setOwner(org.unitedinternet.cosmo.model.User)
     */
    public void setOwner(User owner) {
        this.owner = (OrmliteUser) owner;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getUid()
     */
    public String getUid() {
        return uid;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setUid(java.lang.String)
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getVersion()
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param parent collection to add item to
     */
    public void addParent(OrmliteItem parent) {
        parentDetails.add(new OrmliteCollectionItemDetails(parent,this));

        // clear cached parents
        parents = null;
    }

    public void removeParent(CollectionItem parent) {
        CollectionItemDetails cid = getParentDetails(parent);
        if(cid!=null) {
            parentDetails.remove(cid);
            // clear cached parents
            parents = null;
        }
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getParents()
     */
    public Set<CollectionItem> getParents() {
        if(parents!=null) {
            return parents;
        }

        parents = new HashSet<CollectionItem>();
        for(CollectionItemDetails cid: parentDetails) {
            parents.add(cid.getCollection());
        }

        parents = Collections.unmodifiableSet(parents);

        return parents;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getParent()
     */
    public CollectionItem getParent() {
        if(getParents().size()==0) {
            return null;
        }

        return getParents().iterator().next();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getParentDetails(org.unitedinternet.cosmo.model.CollectionItem)
     */
    public CollectionItemDetails getParentDetails(CollectionItem parent) {
        for(CollectionItemDetails cid: parentDetails) {
            if(cid.getCollection().equals(parent)) {
                return cid;
            }
        }
        
        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getIsActive()
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#setIsActive(java.lang.Boolean)
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

//    /* (non-Javadoc)
//     * @see org.unitedinternet.cosmo.model.Item#getTickets()
//     */
//    public Set<Ticket> getTickets() {
//        return Collections.unmodifiableSet(tickets);
//    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#getTombstones()
     */
    public Set<Tombstone> getTombstones() {
    	Set<Tombstone> s = new HashSet<Tombstone>();
        for(OrmliteTombstone t: tombstones) {
           s.add(t);
        }
    	
        return Collections.unmodifiableSet(s);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#addTombstone(org.unitedinternet.cosmo.model.Tombstone)
     */
    public void addTombstone(Tombstone tombstone) {
        tombstone.setItem(this);
        tombstones.add((OrmliteTombstone)tombstone);
    }


    /**
     * Item uid determines equality 
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null || uid==null) {
            return false;
        }
        if( ! (obj instanceof Item)) {
            return false;
        }

        return uid.equals(((Item) obj).getUid());
    }

    @Override
    public int hashCode() {
        if(uid==null) {
            return super.hashCode();
        }
        else {
            return uid.hashCode();
        }
    }

    @Override
    public Date getCreationDate() {
        return new Date(createDate);
    }
    
    @Override
    public Date getModifiedDate() {
    	return new Date(modifyDate);
    }
    
//    @Override
//    public String calculateEntityTag() {
//        String uid = getUid() != null ? getUid() : "-";
//        String modTime = getModifiedDate() != null ?
//                Long.valueOf(getModifiedDate().getTime()).toString() : "-";
//                String etag = uid + ":" + modTime;
//                return encodeEntityTag(etag.getBytes(Charset.forName("UTF-8")));
//    }

    protected void copyToItem(Item item) {
        item.setOwner(getOwner());
        item.setDisplayName(getDisplayName());

        // copy attributes
        for(Attribute a: attributes) {
            item.addAttribute(a.copy());
        }

        // copy stamps
        for(Stamp stamp: stamps) {
            item.addStamp(stamp.copy());
        }
    }
    
    
	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public Integer getLastModification() {
		return lastModification;
	}

	public void setLastModification(Integer lastModification) {
		this.lastModification = lastModification;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Boolean getNeedsReply() {
		return needsReply;
	}

	public void setNeedsReply(Boolean needsReply) {
		this.needsReply = needsReply;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	public Boolean getAutoTriage() {
		return autoTriage;
	}

	public void setAutoTriage(Boolean autoTriage) {
		this.autoTriage = autoTriage;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public BigDecimal getRank() {
		return rank;
	}

	public void setRank(BigDecimal rank) {
		this.rank = rank;
	}

	public String getIcalUid() {
		return icalUid;
	}

	public void setIcalUid(String icalUid) {
		this.icalUid = icalUid;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getContentLanguage() {
		return contentLanguage;
	}

	public void setContentLanguage(String contentLanguage) {
		this.contentLanguage = contentLanguage;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isHasModifications() {
		return hasModifications;
	}

	public void setHasModifications(boolean hasModifications) {
		this.hasModifications = hasModifications;
	}

	public OrmliteItem getModifies() {
		return modifies;
	}

	public void setModifies(OrmliteItem modifies) {
		this.modifies = modifies;
	}

	public ForeignCollection<OrmliteItem> getModifications() {
		return modifications;
	}

	public void setModifications(ForeignCollection<OrmliteItem> modifications) {
		this.modifications = modifications;
	}

	public ForeignCollection<OrmliteCollectionItemDetails> getParentDetails() {
		return parentDetails;
	}

	public void setParentDetails(
			ForeignCollection<OrmliteCollectionItemDetails> parentDetails) {
		this.parentDetails = parentDetails;
	}

	public void setClientCreationDate(Long clientCreationDate) {
		this.clientCreationDate = clientCreationDate;
	}

	public void setClientModifiedDate(Long clientModifiedDate) {
		this.clientModifiedDate = clientModifiedDate;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setOwner(OrmliteUser owner) {
		this.owner = owner;
	}

	public void setAttributes(ForeignCollection<OrmliteAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setStamps(ForeignCollection<OrmliteStamp> stamps) {
		this.stamps = stamps;
	}

	public void setTombstones(ForeignCollection<OrmliteTombstone> tombstones) {
		this.tombstones = tombstones;
	}

	public void setStampMap(Map<String, Stamp> stampMap) {
		this.stampMap = stampMap;
	}

	public void setParents(Set<CollectionItem> parents) {
		this.parents = parents;
	}
	

    public ForeignCollection<OrmliteCollectionItemDetails> getChildDetails() {
		return childDetails;
	}

	public void setChildDetails(
			ForeignCollection<OrmliteCollectionItemDetails> childDetails) {
		this.childDetails = childDetails;
	}

	@Override
	public Item copy() {
		return null;
	}
}
