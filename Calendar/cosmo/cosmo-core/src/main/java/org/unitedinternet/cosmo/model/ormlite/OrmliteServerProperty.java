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
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//
//import org.hibernate.annotations.Index;
import org.unitedinternet.cosmo.model.ServerProperty;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent ServerProperty.
// */
//@Entity
//@Table(name="server_properties")

@DatabaseTable(tableName = "server_properties")
public class OrmliteServerProperty extends BaseModelObject implements ServerProperty {

    private static final long serialVersionUID = -4099057363051156531L;
    
    //@Column(name = "propertyname", unique=true, length=255)
    //@NotNull
    @DatabaseField(columnName = "propertyname")
    private String name;
    
    //@Column(name = "propertyvalue", length=2048)
    @DatabaseField(columnName = "propertyvalue")
    private String value;
  
    // Constructors

    /** default constructor */
    public OrmliteServerProperty() {
    }
    
    public OrmliteServerProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ServerProperty#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ServerProperty#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ServerProperty#getValue()
     */
    public String getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ServerProperty#setValue(java.lang.String)
     */
    public void setValue(String value) {
        this.value = value;
    }
}
