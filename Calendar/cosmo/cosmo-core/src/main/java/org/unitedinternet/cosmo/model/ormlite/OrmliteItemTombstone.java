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

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.ItemTombstone;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent ItemTombstone.
// */
//@Entity
//@DiscriminatorValue("item")

@DatabaseTable(tableName = "tombstones")
public class OrmliteItemTombstone extends OrmliteTombstone implements ItemTombstone {
    
    private static final long serialVersionUID = 6733626121229413324L;

    @DatabaseField(columnName = "tombstonetype", defaultValue = "item")
    private String itemtype;
    
    //@Column(name="itemuid", length=255)
    @DatabaseField(columnName = "itemuid")
    private String itemUid = null;
    
    //@Column(name="itemname", length=255)
    @DatabaseField(columnName = "itemname")
    private String itemName = null;
    
    /**
     * Constructor.
     */
    public OrmliteItemTombstone() {
    }
    
    public OrmliteItemTombstone(OrmliteItem parent, Item item) {
        super(parent);
        itemUid = item.getUid();
        itemName = item.getName();
    }
   
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ItemTombstone#getItemUid()
     */
    public String getItemUid() {
        return this.itemUid;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ItemTombstone#setItemUid(java.lang.String)
     */
    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ItemTombstone)) {
            return false;
        }
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
                itemUid, ((ItemTombstone) obj).getItemUid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 27).appendSuper(super.hashCode())
                .append(itemUid.hashCode()).toHashCode();
    }
}
