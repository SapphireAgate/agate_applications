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

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Index;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//import org.hibernate.annotations.NaturalId;
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.Length;
import org.unitedinternet.cosmo.dao.ModelValidationException;
import org.unitedinternet.cosmo.model.CollectionItem;
import org.unitedinternet.cosmo.model.CollectionSubscription;
import org.unitedinternet.cosmo.model.Preference;
import org.unitedinternet.cosmo.model.User;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Hibernate persistent User.
 */
//@Entity
//@Table(name="users")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@DatabaseTable(tableName = "users")
public class OrmliteUser implements User {//extends HibAuditableObject implements User {

    /**
     */
    private static final long serialVersionUID = -5401963358519490736L;
   
    /**
     */
    public static final int USERNAME_LEN_MIN = 3;
    /**
     */
    public static final int USERNAME_LEN_MAX = 64;
   
    /**
     */
    public static final int FIRSTNAME_LEN_MIN = 1;
    /**
     */
    public static final int FIRSTNAME_LEN_MAX = 128;
    /**
     */
    public static final int LASTNAME_LEN_MIN = 1;
    /**
     */
    public static final int LASTNAME_LEN_MAX = 128;
    /**
     */
    public static final int EMAIL_LEN_MIN = 1;
    /**
     */
    public static final int EMAIL_LEN_MAX = 128;

    @DatabaseField(id = true, columnName = "ID", unique = true)
    private Long id;

    //@Column(name = "uid", nullable=false, unique=true, length=255)
    //@NotNull
    //@Length(min=1, max=255)
    @DatabaseField(columnName = "UID", unique = true)
    private String uid;
    
    //@Column(name = "username", nullable=false)
//    @Index(name="idx_username")
    @DatabaseField(columnName = "USERNAME", unique = true)
    @NotNull
    //@NaturalId
    //@Length(min=USERNAME_LEN_MIN, max=USERNAME_LEN_MAX)
    //per bug 11599:
    // Usernames must be between 3 and 64 characters; may contain any Unicode
    //character in the following range of unicode code points: [#x20-#xD7FF] |
    //[#xE000-#xFFFD] EXCEPT #x7F or #x3A
    // Oh and don't allow ';' or '/' because there are problems with encoding
    // them in urls (tomcat doesn't support it)
    @javax.validation.constraints.Pattern(regexp="^[\\u0020-\\ud7ff\\ue000-\\ufffd&&[^\\u007f\\u003a;/\\\\]]+$")
    private String username;
    
    private transient String oldUsername;
    
    @DatabaseField(columnName = "PASSWORD")
    @NotNull
    private String password;
    
    //@Column(name = "firstname")
    //@Length(min=FIRSTNAME_LEN_MIN, max=FIRSTNAME_LEN_MAX)
    @DatabaseField(columnName = "FIRSTNAME")
    private String firstName;
    
//    @Column(name = "lastname")
//    @Length(min=LASTNAME_LEN_MIN, max=LASTNAME_LEN_MAX)
    @DatabaseField(columnName = "LASTNAME")
    private String lastName;
    
    @DatabaseField(columnName = "MODIFYDATE") 
    private Long modifyDate;
    
//    @Column(name = "email", nullable=true, unique=true)
//    @Length(min=EMAIL_LEN_MIN, max=EMAIL_LEN_MAX)
//    @Email
    @DatabaseField(columnName = "EMAIL")
    private String email;
    
    private transient String oldEmail;
    
//    @Column(name = "activationid", nullable=true, length=255)
//    @Length(min=1, max=255)
    @DatabaseField(columnName = "ACTIVATIONID")
    private String activationId;
    
    //@Column(name = "admin")
    @DatabaseField(columnName = "ADMIN")
    private Boolean admin;
    
    private transient Boolean oldAdmin;
    
    //@Column(name = "locked")
    @DatabaseField(columnName = "LOCKED")
    private Boolean locked;
    
//    @OneToMany(targetEntity=HibPreference.class, mappedBy = "user", 
//            fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    private Set<Preference> preferences = new HashSet<Preference>(0);
    @ForeignCollectionField(eager = true)
    ForeignCollection<OrmlitePreference> preferences;
    
//    @OneToMany(targetEntity=HibCollectionSubscription.class, mappedBy = "owner", 
//            fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    private Set<CollectionSubscription> subscriptions = 
//        new HashSet<CollectionSubscription>(0);
    @ForeignCollectionField(eager = true)
    ForeignCollection<OrmliteCollectionSubscription> subscriptions;

    /**
     */
    public OrmliteUser() {
        admin = Boolean.FALSE;
        locked = Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getUid()
     */
    public String getUid() {
        return uid;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setUid(java.lang.String)
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getUsername()
     */
    public String getUsername() {
        return username;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setUsername(java.lang.String)
     */
    public void setUsername(String username) {
        oldUsername = this.username;
        this.username = username;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getOldUsername()
     */
    public String getOldUsername() {
        return oldUsername != null ? oldUsername : username;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isUsernameChanged()
     */
    public boolean isUsernameChanged() {
        return oldUsername != null && ! oldUsername.equals(username);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getPassword()
     */
    public String getPassword() {
        return password;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setPassword(java.lang.String)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getFirstName()
     */
    public String getFirstName() {
        return firstName;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setFirstName(java.lang.String)
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getLastName()
     */
    public String getLastName() {
        return lastName;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setLastName(java.lang.String)
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getEmail()
     */
    public String getEmail() {
        return email;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setEmail(java.lang.String)
     */
    public void setEmail(String email) {
        oldEmail = this.email;
        this.email = email;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getOldEmail()
     */
    public String getOldEmail() {
        return oldEmail;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isEmailChanged()
     */
    public boolean isEmailChanged() {
        return oldEmail != null && ! oldEmail.equals(email);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getAdmin()
     */
    public Boolean getAdmin() {
        return admin;
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getOldAdmin()
     */
    public Boolean getOldAdmin() {
        return oldAdmin;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isAdminChanged()
     */
    public boolean isAdminChanged() {
        return oldAdmin != null && ! oldAdmin.equals(admin);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setAdmin(java.lang.Boolean)
     */
    public void setAdmin(Boolean admin) {
        oldAdmin = this.admin;
        this.admin = admin;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getActivationId()
     */
    public String getActivationId() {
        return activationId;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setActivationId(java.lang.String)
     */
    public void setActivationId(String activationId) {
        this.activationId = activationId;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isOverlord()
     */
    public boolean isOverlord() {
        return username != null && username.equals(USERNAME_OVERLORD);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isActivated()
     */
    public boolean isActivated() {
        return this.activationId == null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#activate()
     */
    public void activate(){
       this.activationId = null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isLocked()
     */
    public Boolean isLocked() {
        return locked;
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#setLocked(java.lang.Boolean)
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * Username determines equality 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || username == null) {
            return false;
        }
        if (! (obj instanceof User)) {
            return false;
        }
        
        return username.equals(((User) obj).getUsername());
    }

    @Override
        public int hashCode() {
        if (username == null) {
            return super.hashCode();
        }
        else {
            return username.hashCode();
        }
    }

    /**
     */
    public String toString() {
        return new ToStringBuilder(this).
            append("username", username).
            append("password", "xxxxxx").
            append("firstName", firstName).
            append("lastName", lastName).
            append("email", email).
            append("admin", admin).
            append("activationId", activationId).
            append("locked", locked).
            append("uid", uid).
            toString();
    }

   
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#validateRawPassword()
     */
    public void validateRawPassword() {
        if (password == null) {
            throw new ModelValidationException("UserName" + this.getUsername() + " UID" + this.getUid(), 
                    "Password not specified");
        }
        if (password.length() < PASSWORD_LEN_MIN ||
            password.length() > PASSWORD_LEN_MAX) {
            
            throw new ModelValidationException("UserName" + this.getUsername() + " UID" + this.getUid(), 
                                               "Password must be " +
                                               PASSWORD_LEN_MIN + " to " +
                                               PASSWORD_LEN_MAX +
                                               " characters in length");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getPreferences()
     */
    public Set<Preference> getPreferences() {
    	
    	Set<Preference> s = new HashSet<Preference>();
    	for (Preference p : preferences) {
    		s.add(p);
    	}

        return s;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#addPreference(org.unitedinternet.cosmo.model.Preference)
     */
    public void addPreference(Preference preference) {
        preference.setUser(this);
        preferences.add((OrmlitePreference) preference);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getPreference(java.lang.String)
     */
    public Preference getPreference(String key) {
        for (OrmlitePreference pref : preferences) {
            if (pref.getKey().equals(key)) {
                return pref;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#removePreference(java.lang.String)
     */
    public void removePreference(String key) {
        removePreference(getPreference(key));
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#removePreference(org.unitedinternet.cosmo.model.Preference)
     */
    public void removePreference(Preference preference) {
        if (preference != null) {
            preferences.remove((OrmlitePreference) preference);
        }
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getCollectionSubscriptions()
     */
    public Set<CollectionSubscription> getCollectionSubscriptions() {
    	Set<CollectionSubscription> s = new HashSet<CollectionSubscription>();
    	for (CollectionSubscription p : subscriptions) {
    		s.add(p);
    	}
        return s;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#addSubscription(org.unitedinternet.cosmo.model.CollectionSubscription)
     */
    public void addSubscription(CollectionSubscription subscription) {
        subscription.setOwner(this);
        subscriptions.add((OrmliteCollectionSubscription) subscription);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getSubscription(java.lang.String)
     */
    public CollectionSubscription getSubscription(String displayname) {

        for (CollectionSubscription sub : subscriptions) {
            if (sub.getDisplayName().equals(displayname)) {
                return sub;
            }
        }

        return null;
    }
   
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#getSubscription(java.lang.String, java.lang.String)
     */
    public CollectionSubscription getSubscription(String collectionUid, String ticketKey){
        for (CollectionSubscription sub : subscriptions) {
            if (sub.getCollectionUid().equals(collectionUid)) {
                    //&& sub.getTicketKey().equals(ticketKey)) {
                return sub;
            }
        }

        return null;
    }

   
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#removeSubscription(java.lang.String, java.lang.String)
     */
    public void removeSubscription(String collectionUid, String ticketKey){
        removeSubscription(getSubscription(collectionUid, ticketKey));
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#removeSubscription(java.lang.String)
     */
    public void removeSubscription(String displayName) {
        removeSubscription(getSubscription(displayName));
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#removeSubscription(org.unitedinternet.cosmo.model.CollectionSubscription)
     */
    public void removeSubscription(CollectionSubscription sub) {
        if (sub != null) {
            subscriptions.remove(sub);
        }
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.User#isSubscribedTo(org.unitedinternet.cosmo.model.CollectionItem)
     */
    public boolean isSubscribedTo(CollectionItem collection){
        for (CollectionSubscription sub : subscriptions){
            if (collection.getUid().equals(sub.getCollectionUid())) {
                return true; 
            }
        }
        return false;
    }

	@Override
	public Date getModifiedDate() {
		return new Date(modifyDate);
		//return null;
	}

//    public String calculateEntityTag() {
//        String username = getUsername() != null ? getUsername() : "-";
//        String modTime = getModifiedDate() != null ?
//            Long.valueOf(getModifiedDate().getTime()).toString() : "-";
//        String etag = username + ":" + modTime;
//        return encodeEntityTag(etag.getBytes(Charset.forName("UTF-8")));
//    }
}
