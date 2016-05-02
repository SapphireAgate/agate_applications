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

import org.unitedinternet.cosmo.model.ICalendarItem;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

import com.j256.ormlite.field.DatabaseField;

import net.fortuna.ical4j.model.Calendar;

///**
// * Hibernate persistent ICalendarItem.
// */
//@Entity
//@DiscriminatorValue("icalendar")

public abstract class OrmliteICalendarItemWrapper extends OrmliteContentItemWrapper implements ICalendarItem {

    public static final QName ATTR_ICALENDAR = new OrmliteQName(
            ICalendarItem.class, "icalendar");
    
    public OrmliteICalendarItemWrapper() {
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ICalendarItem#getIcalUid()
     */
    public String getIcalUid() {
        return getPersistedItem().getIcalUid();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ICalendarItem#setIcalUid(java.lang.String)
     */
    public void setIcalUid(String icalUid) {
    	getPersistedItem().setIcalUid(icalUid);
    }
    
    /**
     * Return the Calendar object containing a calendar component.
     * Used by sublcasses to store specific components.
     * @return calendar
     */
    protected Calendar getCalendar() {
        // calendar stored as ICalendarAttribute on Item
        return OrmliteICalendarAttributeWrapper.getValue(getPersistedItem(), ATTR_ICALENDAR);
    }
    
    /**
     * Set the Calendar object containing a calendar component.
     * Used by sublcasses to store specific components.
     * @param calendar
     */
    protected void setCalendar(Calendar calendar) {
        // calendar stored as ICalendarAttribute on Item
        OrmliteICalendarAttributeWrapper.setValue(getPersistedItem(), ATTR_ICALENDAR, calendar);
    }
    
//    @Override
//    protected void copyToItem(Item item) {
//        
//        if(!(item instanceof ICalendarItem)) {
//            return;
//        }
//        
//        super.copyToItem(item);
//        
//        // copy icalUid
//        ICalendarItem icalItem = (ICalendarItem) item;
//        icalItem.setIcalUid(getIcalUid());
//    }
}
