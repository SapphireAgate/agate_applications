///*
// * DataSourceProviderFactoryBean.java May 6, 2015
// * 
// * Copyright (c) 2015 1&1 Internet AG. All rights reserved.
// * 
// * $Id$
// */
//package org.unitedinternet.cosmo.datasource;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.hibernate.Interceptor;
//import org.hibernate.SessionFactory;
//import org.hibernate.cache.spi.RegionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.cfg.NamingStrategy;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.type.filter.TypeFilter;
//import org.springframework.dao.DataAccessException;
//import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
//import org.unitedinternet.cosmo.api.ExternalComponentInstanceProvider;
//import org.unitedinternet.cosmo.db.DataSourceProvider;
//import org.unitedinternet.cosmo.db.DataSourceType;
//import org.unitedinternet.cosmo.metadata.CalendarRepository;
//
//@SuppressWarnings("deprecation")
//public class HibernateSessionFactoryBeanDelegate implements FactoryBean<SessionFactory>, InitializingBean{
//    private ExternalComponentInstanceProvider instanceProvider;
//    private LocalSessionFactoryBean delegate;
//    private static final String COSMO_MYSQL_DIALECT = "org.unitedinternet.cosmo.hibernate.CosmoMySQL5InnoDBDialect";
//    
//    public HibernateSessionFactoryBeanDelegate(ExternalComponentInstanceProvider instanceProvider){
//        this.instanceProvider = instanceProvider;
//        delegate = new LocalSessionFactoryBean();
//    }
//
//    public int hashCode() {
//        return delegate.hashCode();
//    }
//
//    public DataAccessException translateExceptionIfPossible(RuntimeException ex) throws Exception {
//        //return delegate.translateExceptionIfPossible(ex);
//    	throw new Exception("[AGATE] not implemented");
//    }
//
//    public boolean equals(Object obj) {
//        return delegate.equals(obj);
//    }
//
//    public void setDataSource(DataSource dataSource) {
//        delegate.setDataSource(dataSource);
//    }
//
//    public void setConfigLocation(Resource configLocation) {
//        delegate.setConfigLocation(configLocation);
//    }
//
//    public void setConfigLocations(Resource... configLocations) {
//        delegate.setConfigLocations(configLocations);
//    }
//
//    public void setMappingResources(String... mappingResources) {
//        delegate.setMappingResources(mappingResources);
//    }
//
//    public void setMappingLocations(Resource... mappingLocations) {
//        delegate.setMappingLocations(mappingLocations);
//    }
//
//    public void setCacheableMappingLocations(Resource... cacheableMappingLocations) {
//        delegate.setCacheableMappingLocations(cacheableMappingLocations);
//    }
//
//    public void setMappingJarLocations(Resource... mappingJarLocations) {
//        delegate.setMappingJarLocations(mappingJarLocations);
//    }
//
//    public void setMappingDirectoryLocations(Resource... mappingDirectoryLocations) {
//        delegate.setMappingDirectoryLocations(mappingDirectoryLocations);
//    }
//
//    public void setEntityInterceptor(Interceptor entityInterceptor) throws Exception {
//    	throw new Exception("[AGATE] not implemented");
//        //delegate.setEntityInterceptor(entityInterceptor);
//    }
//
//    public void setNamingStrategy(NamingStrategy namingStrategy) {
//        delegate.setNamingStrategy(namingStrategy);
//    }
//
//    public void setJtaTransactionManager(Object jtaTransactionManager) throws Exception {
//        //delegate.setJtaTransactionManager(jtaTransactionManager);
//    	throw new Exception("[AGATE] not implemented");
//    }
//
//    public String toString() {
//        return delegate.toString();
//    }
//
//    public void setMultiTenantConnectionProvider(Object multiTenantConnectionProvider) throws Exception {
//        //delegate.setMultiTenantConnectionProvider(multiTenantConnectionProvider);
//    	throw new Exception("[AGATE] not implemented");
//    }
//
//    public void setCurrentTenantIdentifierResolver(Object currentTenantIdentifierResolver) throws Exception {
//        //delegate.setCurrentTenantIdentifierResolver(currentTenantIdentifierResolver);
//        throw new Exception("[AGATE] not implemented");
//    }
//
//    public void setCacheRegionFactory(RegionFactory cacheRegionFactory) throws Exception {
//        //delegate.setCacheRegionFactory(cacheRegionFactory);
//        throw new Exception("[AGATE] not implemented");
//    }
//
//    public void setEntityTypeFilters(TypeFilter... entityTypeFilters) throws Exception {
//        //delegate.setEntityTypeFilters(entityTypeFilters);
//        throw new Exception("[AGATE] not implemented");
//    }
//
//    public void setHibernateProperties(Properties hibernateProperties) {
//        delegate.setHibernateProperties(hibernateProperties);
//    }
//
//    public Properties getHibernateProperties() {
//        return delegate.getHibernateProperties();
//    }
//
//    public void setAnnotatedClasses(Class<?>... annotatedClasses) {
//        delegate.setAnnotatedClasses(annotatedClasses);
//    }
//
//    public void setAnnotatedPackages(String... annotatedPackages) {
//        delegate.setAnnotatedPackages(annotatedPackages);
//    }
//
//    public void setPackagesToScan(String... packagesToScan) {
//        delegate.setPackagesToScan(packagesToScan);
//    }
//
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        delegate.setResourceLoader(resourceLoader);
//    }
//
//    public void afterPropertiesSet() throws IOException {
//        if(instanceProvider != null){
//    		Collection<? extends DataSourceProvider> dsps = instanceProvider.getImplInstancesAnnotatedWith(CalendarRepository.class, DataSourceProvider.class);
//    		if(dsps != null && !dsps.isEmpty()){
//    			DataSourceProvider dsp = dsps.iterator().next();
//    			if(dsp.getDataSourceType() != null){
//    				delegate.getHibernateProperties().put("hibernate.dialect", getDialectForDataSourceType(dsp.getDataSourceType()));
//    			}
//    		}
//        }
//        
//        delegate.afterPropertiesSet();
//    }
//    
//    private static String getDialectForDataSourceType(DataSourceType dataSourceType){
//    	if(dataSourceType == DataSourceType.MySQL5InnoDB){
//    		return COSMO_MYSQL_DIALECT;
//    	}
//    	
//    	return "org.hibernate.dialect." + dataSourceType.name() + "Dialect";
//    }
//    public final Configuration getConfiguration() throws Exception {
//        //return delegate.getConfiguration();
//        throw new Exception("[AGATE] not implemented");
//    }
//
//    public SessionFactory getObject() {
//        return delegate.getObject();
//    }
//
//    public Class<?> getObjectType() {
//        return delegate.getObjectType();
//    }
//
//    public boolean isSingleton() {
//        return delegate.isSingleton();
//    }
//
//    public void destroy() {
//        delegate.destroy();
//    }
//}