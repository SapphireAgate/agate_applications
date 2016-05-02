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

//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

import java.util.Date;
import java.util.Set;

import net.fortuna.ical4j.model.Calendar;

//import org.unitedinternet.cosmo.hibernate.validator.Availability;
import org.unitedinternet.cosmo.model.AvailabilityItem;
import org.unitedinternet.cosmo.model.EntityFactory;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.Stamp;
import org.unitedinternet.cosmo.model.Ticket;
import org.unitedinternet.cosmo.model.Tombstone;

///**
// * Hibernate persistent AvailabilityItem.
// */
//@Entity
//@DiscriminatorValue("availability")


public class OrmliteAvailabilityItem extends OrmliteICalendarItemWrapper implements AvailabilityItem {

    private static final long serialVersionUID = 5907156118116983463L;


    public OrmliteAvailabilityItem() {
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Item#copy()
     */
    public Item copy() {
        AvailabilityItem copy = new OrmliteAvailabilityItem();
        //copyToItem(copy);
        return copy;
    }
    
   
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.AvailabilityItem#getAvailabilityCalendar()
     */
    //@Availability
    public Calendar getAvailabilityCalendar() {
        return getCalendar();
    }
    
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.AvailabilityItem#setAvailabilityCalendar(net.fortuna.ical4j.model.Calendar)
     */
    public void setAvailabilityCalendar(Calendar calendar) {
        setCalendar(calendar);
    }
}
