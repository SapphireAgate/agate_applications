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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Calendar;

import org.unitedinternet.cosmo.dao.external.UuidExternalGenerator;
import org.unitedinternet.cosmo.model.AvailabilityItem;
import org.unitedinternet.cosmo.model.BinaryAttribute;
import org.unitedinternet.cosmo.model.CalendarAttribute;
import org.unitedinternet.cosmo.model.CalendarCollectionStamp;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionSubscription;
import org.unitedinternet.cosmo.model.DecimalAttribute;
import org.unitedinternet.cosmo.model.EntityFactory;
import org.unitedinternet.cosmo.model.EventExceptionStamp;
import org.unitedinternet.cosmo.model.EventStamp;
import org.unitedinternet.cosmo.model.FileItem;
import org.unitedinternet.cosmo.model.FreeBusyItem;
import org.unitedinternet.cosmo.model.IntegerAttribute;
import org.unitedinternet.cosmo.model.MessageStamp;
import org.unitedinternet.cosmo.model.NoteItem;
import org.unitedinternet.cosmo.model.PasswordRecovery;
import org.unitedinternet.cosmo.model.Preference;
import org.unitedinternet.cosmo.model.QName;
import org.unitedinternet.cosmo.model.StringAttribute;
import org.unitedinternet.cosmo.model.TaskStamp;
import org.unitedinternet.cosmo.model.TextAttribute;
import org.unitedinternet.cosmo.model.Ticket;
import org.unitedinternet.cosmo.model.TicketType;
import org.unitedinternet.cosmo.model.TriageStatus;
import org.unitedinternet.cosmo.model.User;
import org.unitedinternet.cosmo.model.XmlAttribute;
import org.unitedinternet.cosmo.util.VersionFourGenerator;
import org.w3c.dom.Element;

/**
 * EntityFactory implementation that uses Hibernate persistent objects.
 */
public class OrmliteEntityFactory implements EntityFactory {

    private VersionFourGenerator idGenerator = new VersionFourGenerator();

    public CollectionItem createCollection() {
        return new OrmliteCollectionItemWrapper();
    }

    public CollectionItem createCollection(String targetUri) {
        CollectionItem createdCollection = new OrmliteCollectionItemWrapper();
        CalendarCollectionStamp colorStamp = createCalendarCollectionStamp(createdCollection);
        
        boolean isExternalUrl = targetUri != null;
        
        createdCollection.setUid(getCalendarUuid(isExternalUrl));
        createdCollection.setName(getCalendarUuid(isExternalUrl));        

        if (targetUri != null) {
            colorStamp.setTargetUri(targetUri);
        }
        createdCollection.addStamp(colorStamp);
        return createdCollection;
    }

    private String getCalendarUuid(boolean isExternalCalendar) {
        if (!isExternalCalendar) {
            return this.generateUid();
        }
        return UuidExternalGenerator.getNext();
    }

    public NoteItem createNote() {
        return new OrmliteNoteItemWrapper();
    }

    public AvailabilityItem createAvailability() {
        return new OrmliteAvailabilityItem();
    }

    public BinaryAttribute createBinaryAttribute(QName qname, byte[] bytes) {
        return new OrmliteBinaryAttributeWrapper(qname, bytes);
    }

    public BinaryAttribute createBinaryAttribute(QName qname, InputStream is) {
        return new OrmliteBinaryAttributeWrapper(qname, is);
    }

    public CalendarAttribute createCalendarAttribute(QName qname, Calendar cal) {
        return new OrmliteCalendarAttributeWrapper(qname, cal);
    }

    public CalendarCollectionStamp createCalendarCollectionStamp(CollectionItem col) {
        return new OrmliteCalendarCollectionStampWrapper(col);
    }

    public CollectionSubscription createCollectionSubscription() {
        return new OrmliteCollectionSubscription();
    }

    public DecimalAttribute createDecimalAttribute(QName qname, BigDecimal bd) {
        return new OrmliteDecimalAttributeWrapper(qname, bd);
    }

    public EventExceptionStamp createEventExceptionStamp(NoteItem note) {
        return new OrmliteEventExceptionStamp(note);
    }

    public EventStamp createEventStamp(NoteItem note) {
        return new OrmliteEventStamp(note);
    }

    public FileItem createFileItem() {
    	System.out.println("[AGATE] createFileItem not implemented");
    	return null;
        //return new HibFileItem();
    }

    public FreeBusyItem createFreeBusy() {
        return new OrmliteFreeBusyItemWrapper();
    }

    public IntegerAttribute createIntegerAttribute(QName qname, Long longVal) {
        return new OrmliteIntegerAttributeWrapper(qname, longVal);
    }

    public XmlAttribute createXMLAttribute(QName qname, Element e) {
        return new OrmliteXmlAttribute(qname, e);
    }

    public MessageStamp createMessageStamp() {
        return new OrmliteMessageStamp();
    }

    public PasswordRecovery createPasswordRecovery(User user, String key) {
        //return new HibPasswordRecovery(user, key);
    	System.out.println("[AGATE] no password recovery support");
    	return null;
    }

    public Preference createPreference() {
        return new OrmlitePreference();
    }

    public Preference createPreference(String key, String value) {
        return new OrmlitePreference(key, value);
    }

    public QName createQName(String namespace, String localname) {
        return new OrmliteQName(namespace, localname);
    }

    public StringAttribute createStringAttribute(QName qname, String str) {
        return new OrmliteStringAttributeWrapper(qname, str);
    }

    public TaskStamp createTaskStamp() {
        return new OrmliteTaskStamp();
    }

    public TextAttribute createTextAttribute(QName qname, Reader reader) {
        return new OrmliteTextAttributeWrapper(qname, reader);
    }

    public Ticket createTicket(TicketType type) {
        //return new HibTicket(type);
    	System.out.println("[AGATE] no ticketing support");
    	return null;
    }

    public TriageStatus createTriageStatus() {
        return new OrmliteTriageStatus();
    }

    public User createUser() {
        return new OrmliteUser();
    }

    public Ticket creatTicket() {
        //return new HibTicket();
    	System.out.println("[AGATE] no ticketing support");
    	return null;
    }

    public String generateUid() {
        return idGenerator.nextStringIdentifier();
    }

}
