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
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.Stamp;
import org.unitedinternet.cosmo.model.StampTombstone;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent StampTombstone.
// */
//@Entity
//@DiscriminatorValue("stamp")


public class OrmliteStampTombstoneWrapper extends OrmliteTombstoneWrapper implements StampTombstone {

    private static final long serialVersionUID = 4538628685498897273L;

    /**
     * Constructor.
     */
    public OrmliteStampTombstoneWrapper() {
    }
    
    public OrmliteStampTombstoneWrapper(Item item, Stamp stamp) {
    	getPersistedTombstone().setItem(((OrmliteItemWrapper)item).getPersistedItem());
    	getPersistedTombstone().setStamptype(stamp.getType());
    }
    
    public OrmliteStampTombstoneWrapper(Item item, String stampType) {
    	getPersistedTombstone().setItem(((OrmliteItemWrapper)item).getPersistedItem());
    	getPersistedTombstone().setStamptype(stampType);
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.StampTombstone#getStampType()
     */
    public String getStampType() {
        return getPersistedTombstone().getStamptype();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.StampTombstone#setStampType(java.lang.String)
     */
    public void setStampType(String stampType) {
    	getPersistedTombstone().setStamptype(stampType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StampTombstone)) {
            return false;
        }
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
        		getStampType(), ((StampTombstone) obj).getStampType()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(15, 25).appendSuper(super.hashCode())
                .append(getStampType().hashCode()).toHashCode();
    }
    
    
}
