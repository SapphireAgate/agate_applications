/*
 * Copyright 2008 Open Source Applications Foundation
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
package org.unitedinternet.cosmo.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
import org.unitedinternet.cosmo.dao.EventLogDao;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.ContentItem;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.ItemChangeRecord;
import org.unitedinternet.cosmo.model.NoteItem;
import org.unitedinternet.cosmo.model.event.EventLogEntry;
import org.unitedinternet.cosmo.model.event.ItemAddedEntry;
import org.unitedinternet.cosmo.model.event.ItemEntry;
import org.unitedinternet.cosmo.model.event.ItemRemovedEntry;
import org.unitedinternet.cosmo.model.event.ItemUpdatedEntry;
import org.unitedinternet.cosmo.model.ormlite.OrmliteEventLogEntry;
import org.unitedinternet.cosmo.model.ormlite.OrmliteItem;
//import org.unitedinternet.cosmo.model.hibernate.HibTicket;
import org.unitedinternet.cosmo.model.ormlite.OrmliteUser;
//import org.springframework.orm.hibernate4.SessionFactoryUtils;


/**
 * Implementation of EventLogDao using Hibernate persistence objects.
 */
public class EventLogDaoImpl implements EventLogDao {

    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(EventLogDaoImpl.class);


    public void addEventLogEntry(EventLogEntry entry) {
//        try {
//            addEventLogEntryInternal(entry);
//            getSession().flush();
//        } catch (HibernateException e) {
//            getSession().clear();
//            throw e;
//            //throw SessionFactoryUtils.convertHibernateAccessException(e);
//        }
       	System.out.println("[AGATE] ContentDaoImpl not implemented addEventLogEntry");
    	throw new NotImplementedException();
    }

    public void addEventLogEntries(List<EventLogEntry> entries) {

//        try {
//            for (EventLogEntry entry : entries) {
//                addEventLogEntryInternal(entry);
//            }
//
//            getSession().flush();
//        } catch (HibernateException e) {
//            getSession().clear();
//            throw e;
//            //throw SessionFactoryUtils.convertHibernateAccessException(e);
//        }
       	System.out.println("[AGATE] ContentDaoImpl not implemented addEventLogEntries");
    	throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public List<ItemChangeRecord> findChangesForCollection(
            CollectionItem collection, Date start, Date end) {
//        try {
//            Query hibQuery = getSession().getNamedQuery("logEntry.by.collection.date");
//            hibQuery.setParameter("parentId", ((HibItem) collection).getId());
//            hibQuery.setParameter("startDate", start);
//            hibQuery.setParameter("endDate", end);
//            List<HibEventLogEntry> results = hibQuery.list();
//
//            ArrayList<ItemChangeRecord> changeRecords = new ArrayList<ItemChangeRecord>();
//
//            for (HibEventLogEntry result : results) {
//                changeRecords.add(convertToItemChangeRecord(result));
//            }
//
//            return changeRecords;
//
//        } catch (HibernateException e) {
//            getSession().clear();
//            throw e;
//            //throw SessionFactoryUtils.convertHibernateAccessException(e);
//        }
       	System.out.println("[AGATE] ContentDaoImpl not implemented findChangesForCollection");
    	throw new NotImplementedException();
    }


    public void destroy() {

    }

    public void init() {

    }

    private ItemChangeRecord convertToItemChangeRecord(OrmliteEventLogEntry entry) {
        ItemChangeRecord record = new ItemChangeRecord();
        record.setAction(ItemChangeRecord.toAction(entry.getType()));
        record.setDate(entry.getDate());
        record.setItemUuid(entry.getUid1());
        record.setItemDisplayName(entry.getStrval1());
        record.setModifiedBy(entry.getStrval2());

        return record;
    }

    private void addEventLogEntryInternal(EventLogEntry entry) {

        if (entry instanceof ItemAddedEntry) {
            addItemAddedEntry((ItemAddedEntry) entry);
        } else if (entry instanceof ItemRemovedEntry) {
            addItemRemovedEntry((ItemRemovedEntry) entry);
        } else if (entry instanceof ItemUpdatedEntry) {
            addItemUpdatedEntry((ItemUpdatedEntry) entry);
        }

    }

    /**
     * translate ItemAddedEntry to HibEventLogEntry
     *
     * @param entry item added.
     */
    private void addItemAddedEntry(ItemAddedEntry entry) {
        OrmliteEventLogEntry ormEntry = createBaseHibEntry(entry);
        ormEntry.setType("ItemAdded");
        setBaseItemEntryAttributes(ormEntry, entry);
    }

    /**
     * translate ItemRevmoedEntry to HibEventLogEntry
     *
     * @param entry
     */
    private void addItemRemovedEntry(ItemRemovedEntry entry) {
        OrmliteEventLogEntry ormEntry = createBaseHibEntry(entry);
        ormEntry.setType("ItemRemoved");
        setBaseItemEntryAttributes(ormEntry, entry);
    }

    /**
     * translate ItemUpdatedEntry to HibEventLogEntry(s)
     *
     * @param entry
     */
    private void addItemUpdatedEntry(ItemUpdatedEntry entry) {
        OrmliteEventLogEntry ormEntry = createBaseHibEntry(entry);
        ormEntry.setType("ItemUpdated");
        setBaseItemEntryAttributes(ormEntry, entry);
    }

    private OrmliteEventLogEntry createBaseHibEntry(EventLogEntry entry) {
        OrmliteEventLogEntry ormEntry = new OrmliteEventLogEntry();

        if (entry.getDate() != null) {
            ormEntry.setDate(entry.getDate());
        }

        if (entry.getUser() != null) {
            ormEntry.setAuthType("user");
            ormEntry.setAuthId(((OrmliteUser) entry.getUser()).getId());
        } else {
            //hibEntry.setAuthType("ticket");
            //hibEntry.setAuthId(((HibTicket) entry.getTicket()).getId());
        }

        return ormEntry;
    }

    private void setBaseItemEntryAttributes(OrmliteEventLogEntry ormEntry, ItemEntry entry) {
//        hibEntry.setId1(((HibItem) entry.getCollection()).getId());
//        hibEntry.setId2(((HibItem) entry.getItem()).getId());
//        hibEntry.setUid1(entry.getItem().getUid());
//        updateDisplayName(hibEntry, entry);
//        updateLastModifiedBy(hibEntry, entry);
//        getSession().save(hibEntry);
       	System.out.println("[AGATE] ContentDaoImpl not implemented setBaseItemEntryAttributes");
    	throw new NotImplementedException();
    }

    private void updateLastModifiedBy(OrmliteEventLogEntry ormEntry, ItemEntry entry) {
        Item item = entry.getItem();
        if (item instanceof ContentItem) {
            ormEntry.setStrval2(((ContentItem) item).getLastModifiedBy());
        }

        if (ormEntry.getStrval2() == null) {
            if (entry.getUser() != null) {
                ormEntry.setStrval2(entry.getUser().getEmail());
            } else {
                ormEntry.setStrval2("ticket: anonymous");
            }
        }
    }

    private void updateDisplayName(OrmliteEventLogEntry hibEntry, ItemEntry entry) {
        Item item = entry.getItem();
        String displayName = item.getDisplayName();

        // handle case of "missing" displayName
        if (displayName == null && item instanceof NoteItem) {
            NoteItem note = (NoteItem) item;
            if (note.getModifies() != null) {
                displayName = note.getModifies().getDisplayName();
            }
        }

        // limit to 255 chars
        hibEntry.setStrval1(StringUtils.substring(displayName, 0, 255));
    }
}
