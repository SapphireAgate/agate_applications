package sapphire.appexamples.minnietwitter.app;

import sapphire.app.SapphireObject;
import static sapphire.runtime.Sapphire.*;

// begin WITH_SAPPHIRE_AGATE
import dalvik.agate.UserManagementModule;
// end WITH_SAPPHIRE_AGATE

public class TwitterManager implements SapphireObject {
	private UserManager userManager;
	private TagManager tagManager;
	
	public TwitterManager() {
		tagManager = (TagManager) new_(TagManager.class);
		userManager = (UserManager) new_(UserManager.class, tagManager);
		UserManagementModule.addUser("admin", "admin");
		UserManagementModule.login("admin", "admin");
		//System.out.println("Added admin account");
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public TagManager getTagManager() {
		return tagManager;
	}
}
