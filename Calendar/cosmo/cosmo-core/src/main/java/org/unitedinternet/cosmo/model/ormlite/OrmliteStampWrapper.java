package org.unitedinternet.cosmo.model.ormlite;

import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.Stamp;

public class OrmliteStampWrapper implements Stamp {
	protected OrmliteStamp persistedStamp;
	
	public OrmliteStampWrapper() {
	}
	
	public OrmliteStamp getPersistedStamp() {
		if (persistedStamp == null) {
			this.persistedStamp = new OrmliteStamp();
		}
		return persistedStamp;
	}
	
	@Override
	public Item getItem() {
		return getPersistedStamp().getItem();
	}

	@Override
	public void setItem(Item item) {
		getPersistedStamp().setItem(((OrmliteItemWrapper)item).getPersistedItem());
	}

	@Override
	public String getType() {
		return getPersistedStamp().getType();
	}

	@Override
	public Stamp copy() {
		return getPersistedStamp().copy();
	}

}
