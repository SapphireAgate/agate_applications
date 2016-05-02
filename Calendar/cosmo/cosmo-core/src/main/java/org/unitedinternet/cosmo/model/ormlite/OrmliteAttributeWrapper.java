package org.unitedinternet.cosmo.model.ormlite;


import org.apache.commons.lang.NotImplementedException;
import org.unitedinternet.cosmo.model.Attribute;
import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.QName;

public class OrmliteAttributeWrapper implements Attribute {
    protected OrmliteAttribute persistedAttribute;
	
	public OrmliteAttributeWrapper() {
	}
    
	public OrmliteAttribute getPersistedAttribute() {
		if (persistedAttribute == null) {
			this.persistedAttribute = new OrmliteAttribute();
		}
		return persistedAttribute;
	}
	
	public void setPersistedAttribute(OrmliteAttribute a) {
	    this.persistedAttribute = a;
	}
	
	
	@Override
	public QName getQName() {
		return getPersistedAttribute().getQName();
	}

	@Override
	public void setQName(QName qname) {
		getPersistedAttribute().setQName(qname);
	}

	@Override
	public String getName() {
		return getPersistedAttribute().getName();
	}

	@Override
	public Item getItem() {
		return getPersistedAttribute().getItem();
	}

	@Override
	public void setItem(Item item) {
		getPersistedAttribute().setItem(((OrmliteItemWrapper)item).getPersistedItem());		
	}

	@Override
	public Object getValue() {
		return getPersistedAttribute().getValue();
	}

	@Override
	public void setValue(Object value) {
		getPersistedAttribute().setValue(value);		
	}

	@Override
	public Attribute copy() {
		//return null;
		throw new NotImplementedException();
	}

	@Override
	public String calculateEntityTag() {
		// TODO Auto-generated method stub
		return null;
	}
}
