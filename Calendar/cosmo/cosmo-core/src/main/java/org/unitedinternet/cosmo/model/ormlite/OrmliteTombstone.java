/*
 * Copyright 2007 Open Source Applications Foundation
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

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.DiscriminatorType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.Tombstone;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Hibernate persistent Tombstone.
 */
//@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@Table(name="tombstones")
//@DiscriminatorColumn(
//        name="tombstonetype",
//        discriminatorType=DiscriminatorType.STRING,
//        length=16)

@DatabaseTable(tableName = "tombstones")
public class OrmliteTombstone implements Tombstone {
    
    private static final long serialVersionUID = -2053727391597612705L;

    @DatabaseField(columnName = "TOMBSTONETYPE")
    private String tombstonetype;
    
    @DatabaseField(id = true, columnName = "ID")
    private Long id;
    
    @DatabaseField(columnName = "REMOVEDATE")
    private Date removedate = null;
    
    @DatabaseField(columnName = "STAMPTYPE")
    private String stamptype;
    
    @DatabaseField(columnName = "LOCALNAME")
    private String localname;
    
    @DatabaseField(columnName = "NAMESPACE")
    private String namespace;
    
    @DatabaseField(columnName = "ITEMUID")
    private String itemuid;
    
    @DatabaseField(columnName = "ITEMNAME")
    private String itemname;
    
    // Foreign fields
    @DatabaseField(columnName = "ITEMID", foreign = true, foreignAutoCreate = true)
    private OrmliteItem item;
    
    /**
     * Constructor.
     */
    public OrmliteTombstone() {
    }
    
    public OrmliteTombstone(Item item) {
        this.item = (OrmliteItem) item;
        this.removedate = new Date(System.currentTimeMillis());
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Tombstone#getTimestamp()
     */
    public Date getTimestamp() {
        return removedate;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Tombstone#setTimestamp(java.util.Date)
     */
    public void setTimestamp(Date timestamp) {
        this.removedate = timestamp;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Tombstone#getItem()
     */
    public Item getItem() {
        return item;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Tombstone#setItem(org.unitedinternet.cosmo.model.Item)
     */
    public void setItem(Item item) {
        this.item = (OrmliteItem) item;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof Tombstone)) {
            return false;
        }
        return new EqualsBuilder().append(item, ((Tombstone) obj).getItem()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23).append(item).toHashCode();
    }

	public String getTombstonetype() {
		return tombstonetype;
	}

	public void setTombstonetype(String tombstonetype) {
		this.tombstonetype = tombstonetype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRemovedate() {
		return removedate;
	}

	public void setRemovedate(Date removedate) {
		this.removedate = removedate;
	}

	public String getStamptype() {
		return stamptype;
	}

	public void setStamptype(String stamptype) {
		this.stamptype = stamptype;
	}

	public String getLocalname() {
		return localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getItemUid() {
		return itemuid;
	}

	public void setItemUid(String itemuid) {
		this.itemuid = itemuid;
	}

	public String getItemName() {
		return itemname;
	}

	public void setItemName(String itemname) {
		this.itemname = itemname;
	}

	public void setItem(OrmliteItem item) {
		this.item = item;
	}
}
