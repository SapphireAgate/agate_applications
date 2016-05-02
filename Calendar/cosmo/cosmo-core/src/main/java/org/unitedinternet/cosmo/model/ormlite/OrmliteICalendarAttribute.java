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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;

//import org.hibernate.annotations.Type;
import org.unitedinternet.cosmo.CosmoException;
import org.unitedinternet.cosmo.CosmoIOException;
import org.unitedinternet.cosmo.CosmoParseException;
import org.unitedinternet.cosmo.CosmoValidationException;
import org.unitedinternet.cosmo.calendar.util.CalendarUtils;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.ICalendarAttribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent ICalendarAtttribute.
// */
//@Entity
//@DiscriminatorValue("icalendar")

@DatabaseTable(tableName = "attribute")
public class OrmliteICalendarAttribute extends OrmliteAttribute implements ICalendarAttribute {

    private static final long serialVersionUID = -4714651259305593990L;
    
    @DatabaseField(columnName = "attributetype", defaultValue = "icalendar")
    private String attributetype;
    
    
    //@Column(name = "textvalue", length= 2147483647)
    //@Type(type="calendar_clob")

    @DatabaseField(columnName = "textvalue")
    private String cal;

    /** default constructor */
    public OrmliteICalendarAttribute() {
    }

    /**
     * @param qname qualified name
     * @param value initial value
     */
    public OrmliteICalendarAttribute(QName qname, Calendar value) {
        setQName(qname);
        this.cal = toString(value);
    }
    
    /**
     * @param qname qualified name
     * @param value calendar
     */
    public OrmliteICalendarAttribute(QName qname, String value) {
        setQName(qname);
        setValue(value);
    }

    private Calendar fromString(String value) {
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = null;
        try {
            calendar = builder.build(new StringReader(value));
        } catch (IOException e) {
            throw new CosmoIOException("can not happen with StringReader", e);
        } catch (ParserException e) {
            throw new CosmoParseException(e);
        }
        return calendar;
    }

    private String toString(Calendar value) {
        String calendar = null;
        try {
            calendar = CalendarUtils.outputCalendar(value);
        } catch (ValidationException e) {
            throw new CosmoValidationException(e);
        } catch (IOException e) {
            throw new CosmoIOException(e);
        }
        return calendar;
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Attribute#getValue()
     */
    public Calendar getValue() {
        return fromString(this.cal);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ICalendarAttribute#setValue(net.fortuna.ical4j.model.Calendar)
     */
    public void setValue(Calendar value) {
        this.cal = toString(value);
    }
    
    public void setValue(Object value) {
        if (value != null && !(value instanceof Calendar)
                && !(value instanceof String) 
                && !(value instanceof InputStream)) {
            throw new ModelValidationException(
                    "attempted to set non Calendar value on attribute");
        }
        
        if(value instanceof Calendar) {
            setValue((Calendar) value);
        }
        else if(value instanceof InputStream) {
            setValue((InputStream) value);
        }
        else {
            setValue((String) value);
        }
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ICalendarAttribute#setValue(java.lang.String)
     */
    public void setValue(String value) {
        //try {
            //this.cal = CalendarUtils.parseCalendar(value);
        	this.cal = value;
        //} catch (ParserException e) {
        //    throw new ModelValidationException("invalid calendar: " + value);
        //} catch (IOException ioe) {
        //    throw new ModelValidationException("error parsing calendar");
        //}
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.ICalendarAttribute#setValue(java.io.InputStream)
     */
    public void setValue(InputStream is) {
        try {
            this.cal = toString(CalendarUtils.parseCalendar(is));
        } catch (ParserException e) {
            throw new ModelValidationException("invalid calendar: "
                    + e.getMessage());
        } catch (IOException ioe) {
            throw new ModelValidationException("error parsing calendar: "
                    + ioe.getMessage());
        }
    }
    
    public Attribute copy() {
        ICalendarAttribute attr = new OrmliteICalendarAttribute();
        attr.setQName(getQName().copyQName());
        try {
            attr.setValue(fromString(this.cal));
        } catch (Exception e) {
            throw new CosmoException("Error copying ICalendar attribute", e);
        }
        
        return attr;
    }
    
    /**
     * Convienence method for returning a Calendar value on 
     * a ICalendarAttribute with a given QName stored on the given item.
     * @param item item to fetch ICalendarAttribute from
     * @param qname QName of attribute
     * @return Date value of ICalendarAttribute
     */
    public static Calendar getValue(Item item, QName qname) {
        ICalendarAttribute attr = (ICalendarAttribute) item.getAttribute(qname);
        if(attr==null) {
            return null;
        }
        else {
            return attr.getValue();
        }
    }
    
    /**
     * Convienence method for setting a Calendar value on a 
     * ICalendarpAttribute with a given QName stored on the given item.
     * @param item item to fetch ICalendarpAttribute from
     * @param qname QName of attribute
     * @param value value to set on ICalendarpAttribute
     */
    public static void setValue(Item item, QName qname, Calendar value) {
        ICalendarAttribute attr = (ICalendarAttribute) item.getAttribute(qname);
        if(attr==null && value!=null) {
            attr = new OrmliteICalendarAttribute(qname,value);
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

    @Override
    public void validate() {
        
    }

    @Override
    public String calculateEntityTag() {
        return "";
    }
}
