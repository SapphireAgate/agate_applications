package org.unitedinternet.cosmo.model.ormlite;

import java.util.Date;

import org.unitedinternet.cosmo.model.Item;
import org.unitedinternet.cosmo.model.Tombstone;

public class OrmliteTombstoneWrapper implements Tombstone {
	protected OrmliteTombstone persistedTombstone;
	
	public OrmliteTombstoneWrapper() {
	}

	public OrmliteTombstone getPersistedTombstone() {
		if (persistedTombstone == null) {
			persistedTombstone = new OrmliteTombstone();
		}
		return persistedTombstone;
	}

	public void setPersistedTombstone(OrmliteTombstone persistedTombstone) {
		this.persistedTombstone = persistedTombstone;
	}

	@Override
	public Date getTimestamp() {
		return getPersistedTombstone().getTimestamp();
	}

	@Override
	public void setTimestamp(Date timestamp) {
		getPersistedTombstone().setTimestamp(timestamp);
	}

	@Override
	public Item getItem() {
		return getPersistedTombstone().getItem();
	}

	@Override
	public void setItem(Item item) {
		getPersistedTombstone().setItem(item);		
	}
}
