//========================================================================
//$Id$
//Copyright 2008 Mort Bay Consulting Pty. Ltd.
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

package com.jetty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.mortbay.ijetty.deployer.AndroidContextDeployer;
import org.mortbay.ijetty.deployer.AndroidWebAppDeployer;

import com.jetty.handler.DefaultHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.ssl.SslContextFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.util.security.Credential;

/**
 * IJettyService
 *
 * Runs the Jetty server.
 */
public class StartJetty
{
    public static final String __PORT_DEFAULT = "8080";
    public static final boolean __NIO_DEFAULT = false;
    public static final boolean __SSL_DEFAULT = false;

    public static final String __CONSOLE_PWD_DEFAULT = "admin";
    
    public static final String __WEBAPP_DIR = "webapps";
    public static final String __ETC_DIR = "etc";
    public static final String __CONTEXTS_DIR = "contexts";

    public static final String __TMP_DIR = "tmp";
    public static final String __WORK_DIR = "work";
    
    public static final String __JETTY_DIR_PATH = "/scratch/aaasz/agate/agate_code/agate_applications/Calendar/cosmo_bin";
    public static final File __JETTY_DIR;
	
    static
    {
        __JETTY_DIR = new File(__JETTY_DIR_PATH,"jetty_dir");
    }
	
    private static final String CONTENT_RESOLVER_ATTRIBUTE = "org.mortbay.ijetty.contentResolver";
    private static final String ANDROID_CONTEXT_ATTRIBUTE = "org.mortbay.ijetty.context"; 

    public static final String[] __configurationClasses = 
        new String[]
        {
            "org.mortbay.ijetty.webapp.AndroidWebInfConfiguration",
            "org.eclipse.jetty.webapp.WebXmlConfiguration",
            "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
            "org.eclipse.jetty.webapp.TagLibConfiguration" 
        };
    
    private static boolean __isRunning;
 
    private Server server;
    private ContextHandlerCollection contexts;
    private String _consolePassword;
    
    /**
     * Get a reference to the Jetty Server instance
     * @return
     */
    public Server getServer()
    {
        return server;
    }
    

    
    protected Server newServer()
    {
        return new Server();
    }
    
    protected ContextHandlerCollection newContexts()
    {
        return new ContextHandlerCollection();
    }
  
   
    
    protected void configureConnectors()
    {
        if (server != null)
        {
            if (__NIO_DEFAULT)
        	{
                SelectChannelConnector nioConnector = new SelectChannelConnector();
                nioConnector.setUseDirectBuffers(false);
                nioConnector.setPort(Integer.parseInt(__PORT_DEFAULT));
                server.addConnector(nioConnector);
                System.out.println("[StartJetty] Configured "+SelectChannelConnector.class.getName()+" on port "+__PORT_DEFAULT);
            }
            else
            {
                SocketConnector bioConnector = new SocketConnector();
                bioConnector.setPort(Integer.parseInt(__PORT_DEFAULT));
                server.addConnector(bioConnector);
                System.out.println("[StartJetty] Configured "+SocketConnector.class.getName()+" on port "+__PORT_DEFAULT);
            }

            /*if (_useSSL)
            {
                SslContextFactory sslContextFactory = new SslContextFactory();
                sslContextFactory.setKeyStore(_keystoreFile);
                sslContextFactory.setTrustStore(_truststoreFile);
                sslContextFactory.setKeyStorePassword(_keystorePassword);
                sslContextFactory.setKeyManagerPassword(_keymgrPassword);
                sslContextFactory.setKeyStoreType("bks");
                sslContextFactory.setTrustStorePassword(_truststorePassword);
                sslContextFactory.setTrustStoreType("bks");

                //TODO SslSelectChannelConnector does not work on android 1.6, but does work on android 2.2
                if (_useNIO)
                {
                    SslSelectChannelConnector sslConnector = new SslSelectChannelConnector(sslContextFactory);
                    sslConnector.setPort(_sslPort);
                    server.addConnector(sslConnector);
                    Log.i(TAG, "Configured "+sslConnector.getClass().getName()+" on port "+_sslPort); 
                }
                else
                {
                    SslSocketConnector sslConnector = new SslSocketConnector(sslContextFactory);
                    sslConnector.setPort(_sslPort);
                    server.addConnector(sslConnector);
                    Log.i(TAG, "Configured "+sslConnector.getClass().getName()+" on port "+_sslPort); 
                }
            }*/
        }
    }
    
    protected void configureHandlers()
    {
        if (server != null)
        {
            HandlerCollection handlers = new HandlerCollection();
            contexts = new ContextHandlerCollection();
            handlers.setHandlers(new Handler[] {contexts, new DefaultHandler()});
            server.setHandler(handlers);
        }
    }
    
    protected void configureDeployers () throws Exception
    {
        AndroidWebAppDeployer staticDeployer =  new AndroidWebAppDeployer();
        AndroidContextDeployer contextDeployer = new AndroidContextDeployer();
     
        File jettyDir = __JETTY_DIR;
        System.out.println("Jetty dir is: " + jettyDir.getAbsolutePath());

        if (jettyDir.exists())
        {
            // If the webapps dir exists, start the static webapp deployer
            if (new File(jettyDir, __WEBAPP_DIR).exists())
            {
                staticDeployer.setWebAppDir(__JETTY_DIR+"/"+__WEBAPP_DIR);
                staticDeployer.setDefaultsDescriptor(__JETTY_DIR+"/"+__ETC_DIR+"/webdefault.xml");
                staticDeployer.setContexts(contexts);
                //staticDeployer.setAttribute(CONTENT_RESOLVER_ATTRIBUTE, getContentResolver());
                //staticDeployer.setAttribute(ANDROID_CONTEXT_ATTRIBUTE, (Context) StartJetty.this);
                staticDeployer.setConfigurationClasses(__configurationClasses);
                staticDeployer.setAllowDuplicates(false);
            }          
           
            // Use a ContextDeploy so we can hot-deploy webapps and config at startup.
            if (new File(jettyDir, __CONTEXTS_DIR).exists())
            {
                contextDeployer.setScanInterval(10); // Don't eat the battery
                contextDeployer.setConfigurationDir(__JETTY_DIR+"/"+__CONTEXTS_DIR);                
                //contextDeployer.setAttribute(CONTENT_RESOLVER_ATTRIBUTE, getContentResolver());
                //contextDeployer.setAttribute(ANDROID_CONTEXT_ATTRIBUTE, (Context) StartJetty.this);             
                contextDeployer.setContexts(contexts);
            }
            
            if (server != null)
            {
                System.out.println("[StartJetty] Adding context deployer: ");
                server.addBean(contextDeployer);
                System.out.println("[StartJetty] Adding webapp deployer: ");
                server.addBean(staticDeployer); 
            }
        }
        else
        {
            System.out.println("[StartJetty] Not loading any webapps - none on SD card.");
        }
    }
    
    public void configureRealm () throws IOException
    {
        File realmProps = new File(__JETTY_DIR+"/"+__ETC_DIR+"/realm.properties");
        if (realmProps.exists())
        {
            HashLoginService realm = new HashLoginService("Console", __JETTY_DIR+"/"+__ETC_DIR+"/realm.properties");
            realm.setRefreshInterval(0);
            if (_consolePassword != null)
                realm.putUser("admin", Credential.getCredential(_consolePassword), new String[]{"admin"}); //set the admin password for console webapp
            server.addBean(realm);
        }
    }
    
    
    public void startJetty() throws Exception
    {

        //Set jetty.home
        System.setProperty ("jetty.home", __JETTY_DIR.getAbsolutePath());

        //ipv6 workaround for froyo
        System.setProperty("java.net.preferIPv6Addresses", "false");
        
        server = newServer();
        
        configureConnectors();
        configureHandlers();
        configureDeployers();
        configureRealm ();
    
        server.start();
        
        //TODO
        // Less than ideal solution to the problem that dalvik doesn't know about manifests of jars.
        // A as the version field is private to Server, its difficult
        //if not impossible to set it any other way. Note this means that ContextHandler.SContext.getServerInfo()
        //will still return 0.0.
        //HttpGenerator.setServerVersion("i-jetty "+pi.versionName);
        HttpGenerator.setServerVersion("i-jetty 3.2-SNAPSHOT");
    }
    
    public static void main(String[] args) {
    	System.out.println("[StartJetty] Starting Jetty");
    	StartJetty sj = new StartJetty();
    	try {
			sj.startJetty();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("[StartJetty] Jetty started");
    }
}
