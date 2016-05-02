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

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mf.javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.CosmoIOException;
import org.unitedinternet.cosmo.CosmoParseException;
import org.unitedinternet.cosmo.CosmoXMLStreamException;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.XmlAttribute;
import org.unitedinternet.cosmo.util.DomReader;
import org.unitedinternet.cosmo.util.DomWriter;

import org.w3c.dom.Element;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent XMLAttribute.
// */
//@Entity
//@DiscriminatorValue("xml")

@DatabaseTable(tableName = "attribute")
public class OrmliteXmlAttribute extends OrmliteAttribute implements  XmlAttribute {

    private static final long serialVersionUID = -6431240722450099152L;
    
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(XmlAttribute.class);

    @DatabaseField(columnName = "attributetype", defaultValue = "xml")
    private String attributetype;

    
//    @Column(name = "textvalue", length= 2147483647)
//    @Type(type="xml_clob")
//    private Element value;
    
    @DatabaseField(columnName = "textvalue", dataType = DataType.LONG_STRING)
    private String text;

    private Element fromString(String string) {
        Element element = null;
        try {
            element = (Element) DomReader.read(string);
        } catch (ParserConfigurationException e) {
            throw new CosmoParseException(e);
        } catch (XMLStreamException e) {
            throw new CosmoXMLStreamException(e);
        } catch (IOException e) {
            throw new CosmoIOException(e);
        }
        return element;
    }

    private String toString(Element el) {
        String elementString = null;
        try {
            elementString = DomWriter.write(el);
        } catch (XMLStreamException e) {
            throw new CosmoXMLStreamException(e);
        } catch (IOException e) {
            throw new CosmoIOException(e);
        }
        return  elementString;
    }
    
    
    public OrmliteXmlAttribute() {
    }

    public OrmliteXmlAttribute(QName qname,
                        Element el) {
        setQName(qname);
        this.text = this.toString(el);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getValue()
     */
    public Element getValue() {
        return this.fromString(this.text);
    }

    public Attribute copy() {
        XmlAttribute attr = new OrmliteXmlAttribute();
        attr.setQName(getQName().copyQName());
        Element clone = text != null ?
            (Element) fromString(this.text).cloneNode(true) : null;
        attr.setValue(clone);
        return attr;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.XmlAttribute#setValue(org.w3c.dom.Element)
     */
    public void setValue(Element el) {
        this.text = this.toString(el);
    }

    public void setValue(Object value) {
        if (value != null && ! (value instanceof Element)) {
            throw new ModelValidationException("attempted to set non-Element value");
        }
        setValue((Element) value);
    }

    /**
     * Convienence method for returning a Element value on an XmlAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch XmlAttribute from
     * @param qname QName of attribute
     * @return Long value of XmlAttribute
     */
    public static Element getValue(Item item,
                                QName qname) {
        XmlAttribute xa = (XmlAttribute) item.getAttribute(qname);
        if (xa == null) {
            return null;
        }
        else {
            return xa.getValue();
        }
    }

    /**
     * Convienence method for setting a Elementvalue on an XmlAttribute
     * with a given QName stored on the given item.
     * @param item item to fetch Xmlttribute from
     * @param qname QName of attribute
     * @param value value to set on XmlAttribute
     */
    public static void setValue(Item item,
                                QName qname,
                                Element value) {
        XmlAttribute attr = (XmlAttribute) item.getAttribute(qname);
        if (attr == null && value != null) {
            attr = new OrmliteXmlAttribute(qname, value);
            item.addAttribute(attr);
            return;
        }
        if (value == null) {
            item.removeAttribute(qname);
        }
        else {
            attr.setValue(value);
        }
    }

    @Override
    public void validate() {
        
    }

    @Override
    public String calculateEntityTag() {
        return "";
    }
}
