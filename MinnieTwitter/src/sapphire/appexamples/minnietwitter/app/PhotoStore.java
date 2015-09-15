package sapphire.appexamples.minnietwitter.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import dalvik.agate.PolicyManagementModule;

public class PhotoStore implements Serializable {
	private int id = 0;
	private Map<Integer, byte[]> photos = new HashMap<Integer, byte[]>();

	public int uploadPhoto(byte[] photo) {
		//System.out.println("Uploaded photo. Policy on photo: " + Integer.toHexString(PolicyManagementModule.getPolicyByteArray(photo)));
		//PolicyManagementModule.printPolicy(PolicyManagementModule.getPolicyByteArray(photo));
		photos.put(++id, photo);
		return id;
	}

	public byte[] getPhoto(int id) {
		//System.out.println("Get photo. Policy on photo: ");
		//PolicyManagementModule.printPolicy(PolicyManagementModule.getPolicyByteArray(photos.get(id)));
		return photos.get(id);
	}
}
