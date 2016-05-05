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

import java.nio.charset.Charset;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.unitedinternet.cosmo.model.Preference;
import org.unitedinternet.cosmo.model.User;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent Preference.
// */
//@Entity
//@Table(name="user_preferences", uniqueConstraints = {
//    @UniqueConstraint(columnNames={"userid", "preferencename"})})
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@DatabaseTable(tableName = "user_preferences")
public class OrmlitePreference implements Preference {//extends HibAuditableObject implements Preference {

    private static final long serialVersionUID = 1376628118792909420L;
    
    //@ManyToOne(targetEntity=OrmliteUser.class, fetch = FetchType.LAZY)
    //@JoinColumn(name = "userid", nullable = false)
    //@NotNull
    @DatabaseField(columnName = "USERID", foreign = true)
    private OrmliteUser user;
    
    //@Column(name = "preferencename", nullable = false, length = 255)
    //@NotNull
    @DatabaseField(columnName = "PREFERENCENAME")
    private String key;
    
    //@Column(name = "preferencevalue", nullable = false, length = 255)
    //@NotNull
    @DatabaseField(columnName = "PREFERENCEVALUE")
    private String value;
    
    public OrmlitePreference() {
    }

    public OrmlitePreference(String key,
                      String value) {
        this.key = key;
        this.value = value;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Preference#getKey()
     */
    public String getKey() {
        return key;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Preference#setKey(java.lang.String)
     */
    public void setKey(String key) {
        this.key = key;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Preference#getValue()
     */
    public String getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Preference#setValue(java.lang.String)
     */
    public void setValue(String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Preference#getUser()
     */
    public User getUser() {
        return user;
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.Preference#setUser(org.unitedinternet.cosmo.model.User)
     */
    public void setUser(User user) {
        this.user = (OrmliteUser) user;
    }
    
//    public String calculateEntityTag() {
//        // preference is unique by name for its user
//        String uid = getUser() != null && getUser().getUid() != null ?
//            getUser().getUid() : "-";
//        String key = getKey() != null ? getKey() : "-";
//        String modTime = getModifiedDate() != null ?
//            Long.valueOf(getModifiedDate().getTime()).toString() : "-";
//        String etag = uid + ":" + key + ":" + modTime;
//        return encodeEntityTag(etag.getBytes(Charset.forName("UTF-8")));
//    }
}
