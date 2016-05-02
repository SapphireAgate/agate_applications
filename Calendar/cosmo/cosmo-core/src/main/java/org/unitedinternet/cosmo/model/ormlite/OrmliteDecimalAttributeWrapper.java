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

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//
//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.DecimalAttribute;
import org.unitedinternet.cosmo.model.QName;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent DecimalAtttribute.
// */
//@Entity
//@DiscriminatorValue("decimal")

@DatabaseTable()
public class OrmliteDecimalAttributeWrapper extends OrmliteAttributeWrapper implements DecimalAttribute {

    private static final long serialVersionUID = 7830581788843520989L;
    
    /** default constructor */
    public OrmliteDecimalAttributeWrapper() {
    }

    public OrmliteDecimalAttributeWrapper(QName qname, BigDecimal value) {
        setQName(qname);
        getPersistedAttribute().setDecvalue(value);
    }

    // Property accessors
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getValue()
     */
    public BigDecimal getValue() {
        return getPersistedAttribute().getDecvalue();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.hibernate.HibAttribute#copy()
     */
    public Attribute copy() {
        DecimalAttribute attr = new OrmliteDecimalAttributeWrapper();
        attr.setQName(getQName().copyQName());
        if(getValue() !=null ) {
            attr.setValue(new BigDecimal(getValue().toString()));
        }
        return attr;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.DecimalAttribute#setValue(java.math.BigDecimal)
     */
    public void setValue(BigDecimal value) {
    	getPersistedAttribute().setDecvalue(value);;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        if (value != null && !(value instanceof BigDecimal)) {
            throw new ModelValidationException(
                    "attempted to set non BigDecimal value on attribute");
        }
        setValue((BigDecimal) value);
    }

    //@Override
    //public void validate() {
        
    //}

    @Override
    public String calculateEntityTag() {
        return "";
    }

}
