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

//import javax.persistence.AttributeOverride;
//import javax.persistence.AttributeOverrides;
//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Embedded;
//import javax.persistence.Entity;

//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.hibernate.annotations.Target;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.AttributeTombstone;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

///**
// * Hibernate persistent AttributeTombstone.
// */
//@Entity
//@DiscriminatorValue("attribute")

@DatabaseTable(tableName = "tombstones")
public class OrmliteAttributeTombstone extends OrmliteTombstone implements AttributeTombstone {
    
    //private static final long serialVersionUID = -5316512272555844439L;
    
    @DatabaseField(columnName = "TOMBSTONETYPE", defaultValue = "attribute")
    private String tombstonetypetype;
    
//    @Embedded
//    @Target(OrmliteQName.class)
//    @AttributeOverrides( {
//            @AttributeOverride(name="namespace", column = @Column(name="namespace", length=255) ),
//            @AttributeOverride(name="localName", column = @Column(name="localname", length=255) )
//    } )
//    private QName qname = null;

    @DatabaseField(columnName = "NAMESPACE")
    private String namespace;
    @DatabaseField(columnName = "LOCALNAME")
    private String localName;
    
    public OrmliteAttributeTombstone() {
    }
    
    public OrmliteAttributeTombstone(Item item, Attribute attribute) {
        super(item);
        this.localName = attribute.getQName().getLocalName();
        this.namespace = attribute.getQName().getNamespace();
    }
    
    public OrmliteAttributeTombstone(Item item, QName qname) {
        super(item);
        this.localName = qname.getLocalName();
        this.namespace = qname.getNamespace();
    }
    
    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.AttributeTombstone#getQName()
     */
    public QName getQName() {
        return new OrmliteQName(namespace, localName);
    }

    /* (non-Javadoc)
     * @see org.unitedinternet.cosmo.model.AttributeTombstone#setQName(org.unitedinternet.cosmo.model.QName)
     */
    public void setQName(QName qname) {
        this.localName = qname.getLocalName();
        this.namespace = qname.getNamespace();
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null || !(obj instanceof AttributeTombstone)) {
//            return false;
//        }
//        return new EqualsBuilder().appendSuper(super.equals(obj)).append(
//                qname, ((AttributeTombstone) obj).getQName()).isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(21, 31).appendSuper(super.hashCode())
//                .append(qname.hashCode()).toHashCode();
//    } 
}
