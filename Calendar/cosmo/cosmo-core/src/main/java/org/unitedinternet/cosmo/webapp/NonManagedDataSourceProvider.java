package org.unitedinternet.cosmo.webapp;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.unitedinternet.cosmo.db.DataSourceProvider;
import org.unitedinternet.cosmo.db.DataSourceType;

public class NonManagedDataSourceProvider implements DataSourceProvider{
    
    DataSource ds;
    public NonManagedDataSourceProvider() {
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMaxActive(100);
        dataSource.setMaxIdle(20);
        dataSource.setMaxWait(10000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setDefaultAutoCommit(true);
        
        this.ds = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return this.ds;
    }

    @Override
    public DataSourceType getDataSourceType() {
        return DataSourceType.HSQL;
    }
}
