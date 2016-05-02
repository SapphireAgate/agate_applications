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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

import org.apache.commons.io.IOUtils;
//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.CosmoIOException;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.BinaryAttribute;
import org.unitedinternet.cosmo.model.DataSizeException;
import org.unitedinternet.cosmo.model.QName;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent BinaryAtttribute.
// */
//@Entity
//@DiscriminatorValue("binary")

//@DatabaseTable(tableName = "attribute")
public class OrmliteBinaryAttributeWrapper extends OrmliteAttributeWrapper implements BinaryAttribute {

    private static final long serialVersionUID = 6296196539997344427L;
    
    public static final long MAX_BINARY_ATTR_SIZE = 100 * 1024 * 1024;

    /** default constructor */
    public OrmliteBinaryAttributeWrapper() {
    }

    public OrmliteBinaryAttributeWrapper(QName qname, byte[] value) {
        setQName(qname);
        getPersistedAttribute().setBinvalue(value);
    }
    
    /**
     * Construct BinaryAttribute and initialize data using
     * an InputStream
     * @param qname 
     * @param value
     */
    public OrmliteBinaryAttributeWrapper(QName qname, InputStream value) {
        setQName(qname);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(value, bos);
        } catch (IOException e) {
           throw new CosmoIOException("error reading input stream", e);
        }
        getPersistedAttribute().setBinvalue(bos.toByteArray());
    }

    // Property accessors
 
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getValue()
     */
    public byte[] getValue() {
        return getPersistedAttribute().getBinvalue();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.BinaryAttribute#setValue(byte[])
     */
    public void setValue(byte[] value) {
    	getPersistedAttribute().setBinvalue(value);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        if (value != null && !(value instanceof byte[])) {
            throw new ModelValidationException(
                    "attempted to set non binary value on attribute");
        }
        setValue((byte[]) value);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.BinaryAttribute#getLength()
     */
    public int getLength() {
        if(getValue()!=null) {
            return getValue().length;
        }
        else {
            return 0;
        }
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.BinaryAttribute#getInputStream()
     */
    public InputStream getInputStream() {
        if(getValue()!=null) {
            return new ByteArrayInputStream(getValue());
        }
        else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.unitedinternet.cosmo.model.Attribute#copy()
     */
    public Attribute copy() {
        BinaryAttribute attr = new OrmliteBinaryAttributeWrapper();
        attr.setQName(getQName().copyQName());
        if (getValue() != null) {
            attr.setValue(getValue().clone());
        }
        return attr;
    }
    
    //@Override
    public void validate() {
        if (getValue()!=null && getValue().length > MAX_BINARY_ATTR_SIZE) {
            throw new DataSizeException("Binary attribute " + getQName() + " too large");
        }
    }

    @Override
    public String calculateEntityTag() {
      return "";
    }

}
