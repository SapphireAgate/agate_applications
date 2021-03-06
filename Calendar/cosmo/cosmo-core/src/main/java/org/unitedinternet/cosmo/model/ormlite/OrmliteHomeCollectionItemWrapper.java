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

import org.unitedinternet.cosmo.model.HomeCollectionItem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


///**
// * Hibernate persistent HomeCollectionItem.
// */
//@Entity
//@DiscriminatorValue("homecollection")

public class OrmliteHomeCollectionItemWrapper extends OrmliteCollectionItemWrapper implements HomeCollectionItem {

    private static final long serialVersionUID = -4301319758735788800L;
    
    public OrmliteHomeCollectionItemWrapper() {
    	getPersistedItem().setItemtype("homecollection");
    }
    
    public void setName(String name) {
        // Prevent name changes to home collection
        if(getName()==null) {
            super.setName(name);
        }
    }
}
