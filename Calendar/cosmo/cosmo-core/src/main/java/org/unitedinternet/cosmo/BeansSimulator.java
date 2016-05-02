package org.unitedinternet.cosmo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.Interceptor;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
import org.unitedinternet.cosmo.api.DefaultExternalComponentFactory;
import org.unitedinternet.cosmo.api.ExternalComponentFactoryChain;
import org.unitedinternet.cosmo.api.ExternalComponentInstanceProvider;
import org.unitedinternet.cosmo.api.SpringExternalComponentFactory;
import org.unitedinternet.cosmo.api.TypesFinder;
import org.unitedinternet.cosmo.calendar.query.impl.StandardCalendarQueryProcessor;
import org.unitedinternet.cosmo.dao.CalendarDao;
import org.unitedinternet.cosmo.dao.ContentDao;
import org.unitedinternet.cosmo.dao.EventLogDao;
import org.unitedinternet.cosmo.dao.ItemDao;
import org.unitedinternet.cosmo.dao.ScheduleDao;
import org.unitedinternet.cosmo.dao.ServerPropertyDao;
import org.unitedinternet.cosmo.dao.UserDao;
import org.unitedinternet.cosmo.dao.external.ContentDaoExternal;
import org.unitedinternet.cosmo.dao.external.ContentDaoInvocationHandler;
import org.unitedinternet.cosmo.dao.external.ContentDaoProxyFactory;
import org.unitedinternet.cosmo.dao.hibernate.CalendarDaoImpl;
import org.unitedinternet.cosmo.dao.hibernate.ContentDaoImpl;
import org.unitedinternet.cosmo.dao.hibernate.EventLogDaoImpl;
import org.unitedinternet.cosmo.dao.hibernate.ItemDaoImpl;
import org.unitedinternet.cosmo.dao.hibernate.ServerPropertyDaoImpl;
import org.unitedinternet.cosmo.dao.hibernate.UserDaoImpl;
import org.unitedinternet.cosmo.dao.query.hibernate.DefaultItemPathTranslator;
import org.unitedinternet.cosmo.dao.query.hibernate.StandardItemFilterProcessor;
import org.unitedinternet.cosmo.datasource.DataSourceFactoryBean;
//import org.unitedinternet.cosmo.datasource.HibernateSessionFactoryBeanDelegate;
import org.unitedinternet.cosmo.dav.StandardResourceFactory;
import org.unitedinternet.cosmo.dav.StandardResourceLocatorFactory;
import org.unitedinternet.cosmo.db.DbInitializerFactoryBean;
import org.unitedinternet.cosmo.ext.ContentConverter;
import org.unitedinternet.cosmo.ext.ContentSourceFactoryBean;
import org.unitedinternet.cosmo.ext.UrlContentReader;
import org.unitedinternet.cosmo.ext.UrlContentSource;
//import org.unitedinternet.cosmo.hibernate.CompoundInterceptor;
import org.unitedinternet.cosmo.icalendar.ICal3ClientFilter;
import org.unitedinternet.cosmo.icalendar.ICalendarClientFilterManager;
//import org.unitedinternet.cosmo.model.hibernate.AuditableObjectInterceptor;
import org.unitedinternet.cosmo.model.ormlite.EntityConverter;
import org.unitedinternet.cosmo.model.ormlite.OrmliteCollectionItemDetails;
import org.unitedinternet.cosmo.model.ormlite.OrmliteItem;
import org.unitedinternet.cosmo.model.ormlite.OrmliteServerProperty;
import org.unitedinternet.cosmo.model.ormlite.OrmliteUser;
//import org.unitedinternet.cosmo.model.hibernate.EventStampInterceptor;
//import org.unitedinternet.cosmo.model.hibernate.HibEntityFactory;
import org.unitedinternet.cosmo.service.impl.StandardContentService;
import org.unitedinternet.cosmo.service.impl.StandardTriageStatusQueryProcessor;
import org.unitedinternet.cosmo.service.impl.StandardUserService;
import org.unitedinternet.cosmo.service.lock.SingleVMLockManager;
import org.unitedinternet.cosmo.servletcontext.ServletContextConfigurer;
import org.unitedinternet.cosmo.servletcontext.ServletContextListenerDelegatesFactoryBean;
import org.unitedinternet.cosmo.util.VersionFourGenerator;
import org.unitedinternet.cosmo.webapp.NonManagedDataSourceProvider;
import org.unitedinternet.cosmo.icalendar.ICalendarClientFilter;
import org.unitedinternet.cosmo.model.ormlite.OrmliteEntityFactory;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class BeansSimulator {
	private static final Log LOG = LogFactory.getLog(BeansSimulator.class);
	
	// ORMlite Basic DAOs
    public static Dao<OrmliteItem, String> baseItemDao = null;
    public static Dao<OrmliteUser, String> baseUsersDao = null;
    public static Dao<OrmliteServerProperty, String> baseServerPropertiesDao = null;
    public static Dao<OrmliteCollectionItemDetails, String> baseCollectionItemDao = null;
	//
    
    // Advanced cosmo DAOs
    public static ContentDaoProxyFactory contentDao = null;
    public static UserDaoImpl userDao = null;
    //
    
    
    // Services
	public static StandardContentService contentService = null;
	public static StandardUserService userService = null;
    //
    
	public static ServletContextConfigurer servletContextConfigurer = null;
	public static TypesFinder typesFinder = null;
	public static ExternalComponentInstanceProvider externalComponentInstanceProvider = null;
	public static ExternalComponentFactoryChain externalComponentFactoryChain = null;
	public static SpringExternalComponentFactory springExternalComponentFactory = null;
	public static DataSourceFactoryBean jdbcDataSource = null;
    //public static SessionFactory sessionFactory = null;
//    public static AuditableObjectInterceptor auditableObjectInterceptor = null;
//    public static EventStampInterceptor eventStampInterceptor = null;
	//public static CompoundInterceptor cosmoHibernateInterceptor = null;
	public static DbInitializerFactoryBean dbInitializer = null;
	public static ServletContextListenerDelegatesFactoryBean servletContextListenersDelegate = null;
	public static DataSource dataSource = null;
	public static StandardResourceLocatorFactory davResourceLocatorFactory = null;
    public static StandardResourceFactory davResourceFactory = null;
    public static ContentDaoImpl contentDaoInternal = null;
    public static ContentDaoExternal contentDaoExternal = null;
    public static DefaultItemPathTranslator itemPathTranslator = null;
    public static VersionFourGenerator idGenerator = null;
    public static StandardItemFilterProcessor itemFilterProcessor = null;
    public static ContentSourceFactoryBean contentSourceFactory = null;
    public static UrlContentSource urlContentSource = null;
    public static UrlContentReader urlContentReader = null;
    public static OrmliteEntityFactory cosmoEntityFactory = null;
    public static EntityConverter entityConverter = null;
    public static ContentConverter contentConverter = null;
    public static StandardCalendarQueryProcessor calendarQueryProcessor = null;
    public static CalendarDaoImpl caldendarDao = null;
    public static ICal3ClientFilter ical3ClientFilter = null;
    public static ICalendarClientFilterManager iCalendarClientFilterManager = null;
    public static SingleVMLockManager contentLockManager = null;
    public static StandardTriageStatusQueryProcessor triageStatusQueryProcessor = null;
    public static ConnectionSource connectionSource = null;
    
    
	public static ServletContextConfigurer getServletContextConfigurer() {
		if (servletContextConfigurer == null) {
			servletContextConfigurer = new ServletContextConfigurer();
		}
		return servletContextConfigurer;
	}
	
	public static Dao<OrmliteItem, String> getBaseItemDao() {
		if (baseItemDao == null) {
			try {
				baseItemDao = DaoManager.createDao(getConnectionSource(), OrmliteItem.class);
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return baseItemDao;
	}

	public static Dao<OrmliteCollectionItemDetails, String> getBaseCollectionItemDetailsDao() {
		if (baseCollectionItemDao == null) {
			try {
				baseCollectionItemDao = DaoManager.createDao(getConnectionSource(), OrmliteCollectionItemDetails.class);
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return baseCollectionItemDao;
	}

	public static Dao<OrmliteUser, String> getBaseUserDao() {
		if (baseUsersDao == null) {
			try {
				baseUsersDao = DaoManager.createDao(getConnectionSource(), OrmliteUser.class);
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return baseUsersDao;
	}

	public static Dao<OrmliteServerProperty, String> getBaseServerPropertiesDao() {
		if (baseServerPropertiesDao == null) {
			try {
				baseServerPropertiesDao = DaoManager.createDao(getConnectionSource(), OrmliteServerProperty.class);
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return baseServerPropertiesDao;
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			NonManagedDataSourceProvider nds = new NonManagedDataSourceProvider();
			dataSource = nds.getDataSource();
		}
		return dataSource;
	}	
	
	public static OrmliteEntityFactory getCosmoEntityFactory() {
		if (cosmoEntityFactory == null) {
			cosmoEntityFactory = new OrmliteEntityFactory();
		}
		return cosmoEntityFactory;
	}
	
	public static StandardTriageStatusQueryProcessor getTriageStatusQueryProcessor() {
		if (triageStatusQueryProcessor == null) {
			triageStatusQueryProcessor = new StandardTriageStatusQueryProcessor();
			try {
				triageStatusQueryProcessor.setContentDao(BeansSimulator.getContentDao().getObject());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return triageStatusQueryProcessor;
	}
	
	public static ICal3ClientFilter getIcal3ClientFilter() {
		if (ical3ClientFilter == null) {
			ical3ClientFilter = new ICal3ClientFilter();
		}
		return ical3ClientFilter;
	}
	
	public static SingleVMLockManager getContentLockManager() {
		if (contentLockManager == null) {
			contentLockManager = new SingleVMLockManager();
		}
		return contentLockManager;
	}
	
	public static ICalendarClientFilterManager getICalendarClientFilterManager() {
		if (iCalendarClientFilterManager == null) {
			iCalendarClientFilterManager = new ICalendarClientFilterManager();
			Map<String, ICalendarClientFilter> clientFilters = new HashMap<String, ICalendarClientFilter>();
			clientFilters.put("ical2", BeansSimulator.getIcal3ClientFilter());
			clientFilters.put("ical3", BeansSimulator.getIcal3ClientFilter());
			iCalendarClientFilterManager.setClientFilters(clientFilters);
		}
		return iCalendarClientFilterManager;
	}
	
	public static CalendarDaoImpl getCalendarDao() {
		if (caldendarDao == null) {
			caldendarDao = new CalendarDaoImpl();
			caldendarDao.setEntityFactory(BeansSimulator.getCosmoEntityFactory());
			caldendarDao.setItemFilterProcessor(BeansSimulator.getItemFilterProcessor());
			//caldendarDao.setSessionFactory(BeansSimulator.getSessionFactory());
		}
		return caldendarDao;
	}
	
	public static StandardCalendarQueryProcessor getCalendarQueryProcessor() {
		if (calendarQueryProcessor == null) {
			calendarQueryProcessor = new StandardCalendarQueryProcessor();
			calendarQueryProcessor.setCalendarDao(BeansSimulator.getCalendarDao());
			try {
				calendarQueryProcessor.setContentDao(BeansSimulator.getContentDao().getObject());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return calendarQueryProcessor;
	}
	
	public static EntityConverter getEntityConverter() {
		if (entityConverter == null) {
			entityConverter = new EntityConverter(BeansSimulator.getCosmoEntityFactory());
		}
		return entityConverter;
	}
	
	public static ContentConverter getContentConverter() {
		if (contentConverter == null) {
			contentConverter = new ContentConverter(BeansSimulator.getEntityConverter());
		}
		return contentConverter;
	}
	
	public static UrlContentReader getUrlContentReader() {
		if (urlContentReader == null) {
			//TODO: check if EnvironmentProxyFactory and validator are needed
			urlContentReader = new UrlContentReader(BeansSimulator.getContentConverter(), null, null, 1048576);
		}
		return urlContentReader;
	}
	
	public static UrlContentSource getUrlContentSource() {
		if (urlContentSource == null) {
			urlContentSource = new UrlContentSource(BeansSimulator.getUrlContentReader(), 10000);
		}
		return urlContentSource;
	}

	public static StandardItemFilterProcessor getItemFilterProcessor() {
		if (itemFilterProcessor == null) {
			itemFilterProcessor = new StandardItemFilterProcessor();
			//itemFilterProcessor.setSessionFactory(BeansSimulator.getSessionFactory());
		}
		return itemFilterProcessor;
	}
	
	public static VersionFourGenerator getIdGenerator() {
		if (idGenerator == null) {
			idGenerator = new VersionFourGenerator();
		}
		return idGenerator;
	}
	
	public static ContentSourceFactoryBean getContentSourceFactory() {
		if (contentSourceFactory == null) {
			contentSourceFactory = new ContentSourceFactoryBean(BeansSimulator.getUrlContentSource(), BeansSimulator.getExternalComponentInstanceProvider());
		}
		return contentSourceFactory;
	}
	
	public static StandardResourceLocatorFactory getDavResourceLocatorFactory() {
		if (davResourceLocatorFactory == null) {
			davResourceLocatorFactory = new StandardResourceLocatorFactory();
		}
		return davResourceLocatorFactory;
	}
	
	public static DefaultItemPathTranslator getItemPathTranslator() {
		if (itemPathTranslator == null) {
			itemPathTranslator = new DefaultItemPathTranslator();
			//itemPathTranslator.setSessionFactory(BeansSimulator.getSessionFactory());
		}
		return itemPathTranslator;
	}
	
	public static StandardResourceFactory getDavResourceFactory() {
		if (davResourceFactory == null) {
			davResourceFactory = new StandardResourceFactory(BeansSimulator.getContentService(), null, null, BeansSimulator.getCosmoEntityFactory(), BeansSimulator.getCalendarQueryProcessor(), BeansSimulator.getICalendarClientFilterManager());
			davResourceFactory.setSchedulingEnabled(false);
		}
		return davResourceFactory;
	}

	public static ContentDaoImpl getContentDaoInternal() {
		if (contentDaoInternal == null) {
			contentDaoInternal = new ContentDaoImpl();
			contentDaoInternal.setItemPathTranslator(BeansSimulator.getItemPathTranslator());
			contentDaoInternal.setIdGenerator(BeansSimulator.getIdGenerator());
			contentDaoInternal.setTicketKeyGenerator(null);
			contentDaoInternal.setItemFilterProcessor(BeansSimulator.getItemFilterProcessor());
			//LOG.warn("[AGATE] setting session factory: " + BeansSimulator.getSessionFactory());
			//contentDaoInternal.setSessionFactory(BeansSimulator.getSessionFactory());
		}
		return contentDaoInternal;
	}
	
	public static ContentDaoExternal getContentDaoExternal() {
		if (contentDaoExternal == null) {
			try {
				contentDaoExternal = new ContentDaoExternal(BeansSimulator.getContentSourceFactory().getObject(), BeansSimulator.getContentDaoInternal());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return contentDaoExternal;
	}
	
	public static ContentDaoProxyFactory getContentDao() {
		if (contentDao == null) {
			contentDao = new ContentDaoProxyFactory(new ContentDaoInvocationHandler(BeansSimulator.getContentDaoInternal(), BeansSimulator.getContentDaoExternal()));
		}
		return contentDao;
	}
	
	public static UserDaoImpl getUserDao() {
		if (userDao == null) {
			userDao = new UserDaoImpl();
			userDao.setIdGenerator(BeansSimulator.getIdGenerator());
		}
		return userDao;
	}
	
	public static StandardContentService getContentService() {
		if (contentService == null) {
			contentService = new StandardContentService();
			try {
				contentService.setContentDao(getContentDao().getObject());
			} catch (Exception e) {
				e.printStackTrace();
			}
			contentService.setLockManager(BeansSimulator.getContentLockManager());
			contentService.setTriageStatusQueryProcessor(BeansSimulator.getTriageStatusQueryProcessor());
		}
		return contentService;
	}
	
	public static StandardUserService getUserService() {
		if (userService == null) {
			userService = new StandardUserService();
			try {
				userService.setContentDao(BeansSimulator.getContentDao().getObject());
				userService.setUserDao(BeansSimulator.getUserDao());
                userService.init();
			} catch (Exception e) {
				e.printStackTrace();
			}
				}
		return userService;
	}

	public static TypesFinder getTypesFinder() {
		if (typesFinder == null) {
			typesFinder = new TypesFinder();
		}
		return typesFinder;
	}
	
	public static ServletContextListenerDelegatesFactoryBean getServletContextListenersDelegate() {
		if (servletContextListenersDelegate == null) {
			servletContextListenersDelegate = new ServletContextListenerDelegatesFactoryBean(BeansSimulator.getExternalComponentInstanceProvider());
		}
		return servletContextListenersDelegate;
	}
	
//	public static AuditableObjectInterceptor getAuditableObjectInterceptor() {
//		if (auditableObjectInterceptor == null) {
//			auditableObjectInterceptor = new AuditableObjectInterceptor();
//		}
//		return auditableObjectInterceptor;
//	}

//	public static CompoundInterceptor getCosmoHibernateInterceptor() {
//		if (cosmoHibernateInterceptor == null) {
//			cosmoHibernateInterceptor = new CompoundInterceptor();
//			List<Interceptor> interceptors = new ArrayList<Interceptor>();
//			interceptors.add(BeansSimulator.getAuditableObjectInterceptor());
//			interceptors.add(BeansSimulator.getEventStampInterceptor());
//			cosmoHibernateInterceptor.setInterceptors(interceptors);
//		}
//		return cosmoHibernateInterceptor;
//	}
	
	
//	public static EventStampInterceptor getEventStampInterceptor() {
//		if (eventStampInterceptor == null) {
//			eventStampInterceptor = new EventStampInterceptor();
//		}
//		return eventStampInterceptor;
//	}
	
	public static SpringExternalComponentFactory getSpringExternalComponentFactory() {
		if (springExternalComponentFactory == null) {
			springExternalComponentFactory = new SpringExternalComponentFactory();
		}
		return springExternalComponentFactory;
	}

	public static ExternalComponentFactoryChain getExternalComponentFactoryChain() {
		if (externalComponentFactoryChain == null) {
			externalComponentFactoryChain = new ExternalComponentFactoryChain(BeansSimulator.getSpringExternalComponentFactory(), new DefaultExternalComponentFactory());
		}
		return externalComponentFactoryChain;
	}
	
	public static ExternalComponentInstanceProvider getExternalComponentInstanceProvider() {
		if (externalComponentInstanceProvider == null) {
			externalComponentInstanceProvider = new ExternalComponentInstanceProvider(BeansSimulator.getTypesFinder(), BeansSimulator.getExternalComponentFactoryChain());
		}
		return externalComponentInstanceProvider;
	}
	
	public static DataSourceFactoryBean getJdbcDataSource() {
		if (jdbcDataSource == null) {
			jdbcDataSource = new DataSourceFactoryBean(BeansSimulator.getExternalComponentInstanceProvider());
		}
		return jdbcDataSource;
	}
	
//	public static SessionFactory getSessionFactory() {
//		if (sessionFactory == null) {
//			//sessionFactory = new HibernateSessionFactoryBeanDelegate(BeansSimulator.getExternalComponentInstanceProvider());
//			//sessionFactory.setPackagesToScan("org.unitedinternet.cosmo.model.hibernate");
//			
//			Properties c = new Properties();
//            c.setProperty("hibernate.cache.use_query_cache", "false");
//            c.setProperty("hibernate.cache.use_second_level_cache", "false");
//            c.setProperty("current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
//            c.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
//            c.setProperty("hibernate.jdbc.batch_size", "25");
//            c.setProperty("hibernate.connection.autoReconnect", "true");
//            c.setProperty("hibernate.connection.release_mode", "after_transaction");
//            c.setProperty("hibernate.generate_statistics", "true");
//			c.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//			c.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
//			c.setProperty("hibernate.connection.username", "sa");    
//			c.setProperty("hibernate.connection.password", "");
//			c.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:testdb");  
//
//            Configuration cfg = new Configuration().setProperties(c);
//            sessionFactory = cfg.buildSessionFactory();
//            
//            //sessionFactory. .setHibernateProperties(c);
//			//try {
//				//sessionFactory.setEntityInterceptor(BeansSimulator.getCosmoHibernateInterceptor());
//				//sessionFactory.setDataSource(BeansSimulator.getJdbcDataSource().getObject());	
//				//sessionFactory.afterPropertiesSet();
//			//} catch (Exception e) {
//			//	e.printStackTrace();
//			//}
//		}
//		return sessionFactory;
//	}
	
	public static ConnectionSource getConnectionSource() {		
		BasicDataSource dataSource = new BasicDataSource();
		String databaseUrl = "jdbc:hsqldb:mem:testdb";
		dataSource.setUrl(databaseUrl);
		dataSource.setPassword("");
		dataSource.setUsername("sa");
		
		// we wrap it in the DataSourceConnectionSource
		if (connectionSource == null) {
			try {
				connectionSource = new DataSourceConnectionSource(dataSource, databaseUrl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connectionSource;
	}

	public static DbInitializerFactoryBean getDbInitializer() {
		if (dbInitializer == null) {
			try {
				dbInitializer = new DbInitializerFactoryBean(BeansSimulator.getJdbcDataSource().getObject(), BeansSimulator.getExternalComponentInstanceProvider());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dbInitializer;
	}
}
