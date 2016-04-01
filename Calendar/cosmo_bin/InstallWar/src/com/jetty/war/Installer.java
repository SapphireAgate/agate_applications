//========================================================================
//$Id$
//Copyright 2011 Mort Bay Consulting Pty. Ltd.
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//========================================================================

package com.jetty.war;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.eclipse.jetty.util.IO;

/**
 * InstallerActivity
 *
 *
 */
public class Installer
{
    private static final String TAG = "Console.Inst";
    
    
    public static final String WEBAPPS = "webapps";
    public static final String WEBAPP_CONTEXT = "console";
    public static final String JETTY = "jetty_dir";
    public static final String __JETTY_DIR_PATH = "/scratch/aaasz/agate/agate_code/agate_applications/Calendar/cosmo_bin";
    public static final String __WAR_FILE_PATH = "/scratch/aaasz/agate/agate_code/agate_applications/Calendar/cosmo_bin/jetty_dir/console.war";
    
    public static final int CONFIRM_DIALOG_ID = 1;
    public static final int ERROR_DIALOG_ID = 2;   
    public static final int PROGRESS_DIALOG_ID = 3;
    public static final int FINISH_DIALOG_ID = 4;
    public static final File __JETTY_DIR;
	
    static
    {
        __JETTY_DIR = new File(__JETTY_DIR_PATH,"jetty_dir");
    }
    
    
    
    /**
     * @return the File of the existing unpacked webapp or null
     */
//    public File getWebApp ()
//    {
//        File jettyDir = __JETTY_DIR;
//        if (jettyDir == null)
//            return null;
//        
//        File webappsDir = new File(jettyDir, WEBAPPS);
//        if (!webappsDir.exists())
//            return null;
//        
//        File webapp = new File (webappsDir, WEBAPP_CONTEXT);
//        if (!webapp.exists())
//            return null;
//        
//        return webapp;
//    }
   
    /**
     * Delete the existing unpacked console webapp
     * @param webapp
     */
    public void delete (File webapp)
    {
        if (webapp.isDirectory())
        {
            File[] files = webapp.listFiles();
            for (File f:files)
            {
                delete(f);
            }
            webapp.delete();
        }
        else
            webapp.delete();
    }
    
    /**
     * Extract the war.
     * 
     * @param warStream
     * @throws IOException
     */
    public void extract (InputStream warStream) 
    throws IOException
    {
        if (warStream == null)
            throw new IllegalArgumentException ("No war file found");

        File jettyDir = __JETTY_DIR;
        if (jettyDir == null)
        {
            throw new IllegalStateException ("Jetty dir does not exist");
        }
        
        File webappsDir = new File (jettyDir, "webapps");
        if (!webappsDir.exists())
        {
            throw new IllegalStateException ("Webapps dir does not exist");
        }
        
        File webapp = new File (webappsDir, WEBAPP_CONTEXT);
        
        JarInputStream jin = new JarInputStream(warStream);
        JarEntry entry;
        while((entry=jin.getNextJarEntry())!=null)
        {
            String entryName = entry.getName();             
            File file=new File(webapp,entryName);
            
            System.out.println("EntryName = " + entryName);
            
            if (entry.isDirectory())
            {
                // Make directory
                if (!file.exists())
                    file.mkdirs();
            }
            else
            {
                // make directory (some jars don't list dirs)
                File dir = new File(file.getParent());
                if (!dir.exists())
                    dir.mkdirs();

                // Make file
                FileOutputStream fout = null;
                try
                {
                    fout = new FileOutputStream(file);
                    IO.copy(jin,fout);
                }
                finally
                {
                    IO.close(fout);
                }

                // touch the file.
                if (entry.getTime()>=0)
                    file.setLastModified(entry.getTime());
            }
        }
        IO.close(jin);
    }
    
    public static void main(String[] args) {
    	System.out.println("[Installer] Installing webapp ...");
    	Installer installer = new Installer();
    	
    	try {
			installer.extract(new FileInputStream(__WAR_FILE_PATH));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("[Installer] Webapp installed.");
    }
}
