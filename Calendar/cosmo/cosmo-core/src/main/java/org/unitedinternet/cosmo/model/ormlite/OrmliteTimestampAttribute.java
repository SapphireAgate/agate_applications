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

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//
//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.TimestampAttribute;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent TimestampAtttribute.
// */
//@Entity
//@DiscriminatorValue("timestamp")

@DatabaseTable(tableName = "attribute")
public class OrmliteTimestampAttribute extends OrmliteAttribute implements TimestampAttribute {

    private static final long serialVersionUID = 5263977785074085449L;
    
    @DatabaseField(columnName = "attributetype", defaultValue = "timestamp")
    private String attributetype;
    
    
    //@Column(name = "intvalue")
    //@Type(type="long_timestamp")
    @DatabaseField(columnName = "intvalue")
    private Long value;

    /** default constructor */
    public OrmliteTimestampAttribute() {
    }

    public OrmliteTimestampAttribute(QName qname, Date value) {
        setQName(qname);
        this.value = Long.valueOf(value.getTime());
    }

    // Property accessors
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getValue()
     */
    public Date getValue() {
        return new Date((Long)value);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.TimestampAttribute#setValue(java.util.Date)
     */
    public void setValue(Date value) {
        this.value = Long.valueOf(value.getTime());;
    }
    
    public void setValue(Object value) {
        if (value != null && !(value instanceof Date)) {
            throw new ModelValidationException(
                    "attempted to set non Date value on attribute");
        }
        setValue((Date) value);
    }
    
    /**
     * Convienence method for returning a Date value on a TimestampAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch TextAttribute from
     * @param qname QName of attribute
     * @return Date value of TextAttribute
     */
    public static Date getValue(Item item, QName qname) {
        TimestampAttribute ta = (TimestampAttribute) item.getAttribute(qname);
        if(ta==null) {
            return null;
        }
        else {
            return ta.getValue();
        }
    }
    
    /**
     * Convienence method for setting a Date value on a TimestampAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch TimestampAttribute from
     * @param qname QName of attribute
     * @param value value to set on TextAttribute
     */
    public static void setValue(Item item, QName qname, Date value) {
        TimestampAttribute attr = (TimestampAttribute) item.getAttribute(qname);
        if(attr==null && value!=null) {
            attr = new OrmliteTimestampAttribute(qname,value);
            item.addAttribute(attr);
            return;
        }
        if(value==null) {
            item.removeAttribute(qname);
        }
        else {
            attr.setValue(value);
        }
    }
    
    public Attribute copy() {
        TimestampAttribute attr = new OrmliteTimestampAttribute();
        attr.setQName(getQName().copyQName());
        attr.setValue(value);
        return attr;
    }

    @Override
    public void validate() {
        
    }

    @Override
    public String calculateEntityTag() {
        return "";
    }

}
