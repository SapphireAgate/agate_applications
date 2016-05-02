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

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Embedded;
//import javax.persistence.Entity;

//import org.hibernate.annotations.Target;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionItemDetails;
import org.unitedinternet.cosmo.model.ContentItem;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.Stamp;
import org.unitedinternet.cosmo.model.Tombstone;
import org.unitedinternet.cosmo.model.TriageStatus;
import org.unitedinternet.cosmo.model.User;

///**
// * Hibernate persistent ContentItem.
// */
//@Entity
//@DiscriminatorValue("content")

public class OrmliteContentItemWrapper extends OrmliteItemWrapper implements ContentItem {

    private static final long serialVersionUID = 4904755977871771389L;
    
    public OrmliteContentItemWrapper() {
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#getLastModifiedBy()
     */
    public String getLastModifiedBy() {
        return getPersistedItem().getLastModifiedBy();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#setLastModifiedBy(java.lang.String)
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        getPersistedItem().setLastModifiedBy(lastModifiedBy);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#getLastModification()
     */
    public Integer getLastModification() {
        return getPersistedItem().getLastModification();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#setLastModification(java.lang.Integer)
     */
    public void setLastModification(Integer lastModification) {
    	getPersistedItem().setLastModification(lastModification);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#getTriageStatus()
     */
    public TriageStatus getTriageStatus() {
    	
    	TriageStatus t = new OrmliteTriageStatus();
    	t.setAutoTriage(getPersistedItem().getAutoTriage());
    	t.setCode(getPersistedItem().getCode());
    	t.setRank(getPersistedItem().getRank());
        return t;
    }
  
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#setTriageStatus(org.unitedinternet.cosmo.model.TriageStatus)
     */
    public void setTriageStatus(TriageStatus ts) {
    	getPersistedItem().setCode(ts.getCode());
    	getPersistedItem().setAutoTriage(ts.getAutoTriage());
    	getPersistedItem().setRank(ts.getRank());
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#getSent()
     */
    public Boolean getSent() {
        return getPersistedItem().getSent();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#setSent(java.lang.Boolean)
     */
    public void setSent(Boolean sent) {
    	getPersistedItem().setSent(sent);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#getNeedsReply()
     */
    public Boolean getNeedsReply() {
        return getPersistedItem().getNeedsReply();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ContentItem#setNeedsReply(java.lang.Boolean)
     */
    public void setNeedsReply(Boolean needsReply) {
    	getPersistedItem().setNeedsReply(needsReply);
    }
    
//    @Override
//    protected void copyToItem(Item item) {
//        if(!(item instanceof ContentItem)) {
//            return;
//        }
//        
//        super.copyToItem(item);
//        
//        ContentItem contentItem = (ContentItem) item;
//        
//        contentItem.setLastModifiedBy(getLastModifiedBy());
//        contentItem.setLastModification(getLastModification());
//        contentItem.setTriageStatus(getTriageStatus());
//        contentItem.setSent(getSent());
//        contentItem.setNeedsReply(getNeedsReply());
//    }
}
