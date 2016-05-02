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
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent Attribute.
// */
//@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//// Define indexes on discriminator and key fields
//@Table(
//        name="attribute",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames={"itemid", "namespace", "localname"})},
//        indexes={@Index(name="idx_attrtype", columnList="attributetype"),
//                 @Index(name="idx_attrname", columnList="localname"),
//                 @Index(name="idx_attrns", columnList="namespace")})
//@DiscriminatorColumn(
//        name="attributetype",
//        discriminatorType=DiscriminatorType.STRING,
//        length=16)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)


@DatabaseTable(tableName = "attribute")
public class OrmliteAttribute extends OrmliteQName implements Attribute {

    private static final long serialVersionUID = 3927093659569283339L;

    @DatabaseField(columnName = "ATTRIBUTETYPE")
    private String attributetype;

    @DatabaseField(id = true, columnName = "ID", unique = true)
    private Long id;
    
    @DatabaseField(columnName = "TEXTVALUE")
    private String textvalue;

    @DatabaseField(columnName = "BINVALUE", dataType = DataType.BYTE_ARRAY)
    private byte[] binvalue;

    @DatabaseField(columnName = "BOOLEANVALUE")
    private Boolean boolvalue;
    
    @DatabaseField(columnName = "STRINGVALUE")
    private String stringvalue;
    
    @DatabaseField(columnName = "INTVALUE")
    private Long intvalue;

    @DatabaseField(columnName = "DECVALUE", dataType = DataType.BIG_DECIMAL_NUMERIC)
    private BigDecimal decvalue;
    
    @DatabaseField(columnName = "DATEVALUE", dataType = DataType.DATE_LONG)
    private Date datevalue;

    @DatabaseField(columnName = "TZVALUE")
    private String tzvalue;
    
    // Foreign fields
    @DatabaseField(columnName = "ITEMID", foreign = true)
    private OrmliteItem item;

    // Constructors
    /** default constructor */
    public OrmliteAttribute() {
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getQName()
     */
    public QName getQName() {
        return this.copyQName();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#setQName(org.unitedinternet.cosmo.model.QName)
     */
    public void setQName(QName qname) {
        this.setLocalName(qname.getLocalName());
        this.setNamespace(qname.getNamespace());
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getName()
     */
    public String getName() {
        return this.getLocalName();
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getItem()
     */
    public Item getItem() {
        return item;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#setItem(org.unitedinternet.cosmo.model.Item)
     */
    public void setItem(Item item) {
        this.item = (OrmliteItem)item;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#copy()
     */
    public Attribute copy() {
    	throw new NotImplementedException();
    }

    /**
     * Return string representation
     */
    public String toString() {
        Object value = getValue();
        if(value==null) {
            return "null";
        }
        return value.toString();
    }

    public void validate() {
    	throw new NotImplementedException();
    }

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public String calculateEntityTag() {
		return null;
	}

	public String getAttributetype() {
		return attributetype;
	}

	public void setAttributetype(String attributetype) {
		this.attributetype = attributetype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTextvalue() {
		return textvalue;
	}

	public void setTextvalue(String textvalue) {
		this.textvalue = textvalue;
	}

	public byte[] getBinvalue() {
		return binvalue;
	}

	public void setBinvalue(byte[] binvalue) {
		this.binvalue = binvalue;
	}

	public Boolean getBoolvalue() {
		return boolvalue;
	}

	public void setBoolvalue(Boolean boolvalue) {
		this.boolvalue = boolvalue;
	}

	public String getStringvalue() {
		return stringvalue;
	}

	public void setStringvalue(String stringvalue) {
		this.stringvalue = stringvalue;
	}

	public Long getIntvalue() {
		return intvalue;
	}

	public void setIntvalue(Long intvalue) {
		this.intvalue = intvalue;
	}

	public BigDecimal getDecvalue() {
		return decvalue;
	}

	public void setDecvalue(BigDecimal decvalue) {
		this.decvalue = decvalue;
	}

	public Date getDatevalue() {
		return datevalue;
	}

	public void setDatevalue(Date datevalue) {
		this.datevalue = datevalue;
	}

	public String getTzvalue() {
		return tzvalue;
	}

	public void setTzvalue(String tzvalue) {
		this.tzvalue = tzvalue;
	}

	public void setItem(OrmliteItem item) {
		this.item = item;
	}
}
