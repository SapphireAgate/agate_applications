/*
 * Copyright 2006 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitedinternet.cosmo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.dbcp.BasicDataSource;
//import org.hibernate.HibernateException;
//import org.hibernate.SessionFactory;
//import org.hibernate.tool.hbm2ddl.SchemaValidator;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.unitedinternet.cosmo.datasource.HibernateSessionFactoryBeanDelegate;

/**
 * A helper class that initializes the Cosmo database schema and populates the database with seed data.
 */
public class DbInitializer {

    private static final Log LOG = LogFactory.getLog(DbInitializer.class);

    private static final String PATH_SCHEMA = "/db/cosmo-schema.sql";

    //private HibernateSessionFactoryBeanDelegate localSessionFactory;
    //private SessionFactory localSessionFactory;

    
    private DataSource datasource;

    private Collection<? extends DatabaseInitializationCallback> callbacks = Collections.emptyList();

    /**
     * Performs initialization tasks if required.
     * 
     * @return <code>true</code> if initialization was required, * <code>false</code> otherwise>.
     */
    public void initialize() {
        // Create DB schema if not present
        if (!isSchemaInitialized()) {
        	try {
        		this.executeStatements(PATH_SCHEMA);
        	} catch (Exception e) {
        		LOG.error(e);
        	}
            LOG.warn("[DB-startup] Cosmo database structure created successfully.");
            for (DatabaseInitializationCallback callback : callbacks) {
                callback.execute();
            }
        }
        // More thorough schema validation
        //validateSchema();
    }

    public void executeStatements(String resource) throws SQLException {
        
    	Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
    	conn.setAutoCommit(true);

        for (String statement : this.readStatements(resource)) {
            LOG.info("\n" + statement);
            Statement st = conn.createStatement();
            int i = st.executeUpdate(statement);
            //System.out.println("[AGATE] i = " + i);
            if (i == -1) {
                System.out.println("db error : " + statement);
            }
            st.close();
            //try {
            //    int rows = jdbcTemplate.update(statement);
            //} catch(Exception e) {
            //    LOG.warn(e.toString());
            //}
        }
        conn.close();
    }

    private List<String> readStatements(String resource) {
        List<String> statements = new ArrayList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.getClass().getResourceAsStream(resource), "UTF-8");
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String statement = scanner.next().replace("\n", " ").trim();
                if (!statement.isEmpty()) {
                    statements.add(statement);
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return statements;
    }

    public void setDataSource(DataSource datasource) {
        this.datasource = datasource;
    }

    //public void setLocalSessionFactory(HibernateSessionFactoryBeanDelegate hibernateLocalSessionFactoryDelegate) {
//    public void setLocalSessionFactory(SessionFactory hibernateLocalSessionFactoryDelegate) {
//    	this.localSessionFactory = hibernateLocalSessionFactoryDelegate;
//    }

    public void setCallbacks(Collection<? extends DatabaseInitializationCallback> callbacks) {
        this.callbacks = callbacks;
    }

    // default to allow usage in tests
    /**
     * 
     * @return checks if schema is initialized
     */
    boolean isSchemaInitialized() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = datasource.getConnection();
            ps = conn.prepareStatement("select count(*) from server_properties");
            rs = ps.executeQuery();
            return true;
        } catch (SQLException e) {
            // if the schema is not created yet, conn.prepareStatement fails
            return false;
        } finally {
            try {
                // no rs created if the schema is not created
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                LOG.error(e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                LOG.error(e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

//    /**
//     * Schema validation
//     */
//    private void validateSchema() {
//        try {
//            //new SchemaValidator(localSessionFactory.getConfiguration()).validate();
//            LOG.warn("[AGATE] not validating schema");
//        	//LOG.info("schema validation passed");
//        } catch (HibernateException e) {
//            LOG.error("error validating schema", e);
//            throw e;
//        }
//    }
}
