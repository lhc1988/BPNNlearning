package lab.cgcl.aliBigdata.BPNNlearning.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DbcpPool {
	
	private static String propertyPath = "properties/jdbc.property";
	private DataSource dataSource;
	
	
	public static String getPropertyPath() {
		return propertyPath;
	}

	public static void setPropertyPath(String propertyPath) {
		DbcpPool.propertyPath = propertyPath;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static DataSource setupDataSource(String cfgFileName) {  
        BasicDataSource ds = new BasicDataSource();  
        try {  
            Properties cfgpp = new Properties();  
            FileInputStream fis = new FileInputStream(new File(cfgFileName));
            cfgpp.load(fis);
            ds.setDriverClassName(cfgpp.getProperty("jdbc.driverClassName").trim());  
            ds.setUrl(cfgpp.getProperty("jdbc.url").trim());  
            ds.setUsername(cfgpp.getProperty("jdbc.username").trim());  
            ds.setPassword(cfgpp.getProperty("jdbc.password").trim());  
            fis.close();
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
  
        return ds;  
    }  
	
    public static void shutdownDataSource(DataSource ds) throws SQLException {  
        BasicDataSource bds = (BasicDataSource) ds;  
        bds.close();  
    }
    
    /**
     * Constructor
     */
    public DbcpPool () {
    	dataSource = setupDataSource(propertyPath);
    }
    
    /**
     * Constructor
     */
    public DbcpPool (String path) {
    	dataSource = setupDataSource(path);
    }
	
	public static void main(String[] args) {
		//DataSource ds = setupDataSource(propertyPath);
		
		DbcpPool pool = new DbcpPool();
		Connection conn = null;  
        Statement stmt = null;  
        ResultSet rset = null;  
        
        String testSql = " select * from dual;";
        
  
        try {  
            System.out.println("Creating connection start.");  
            conn = pool.getDataSource().getConnection();  
  
            System.out.println("Creating statement start.");  
            stmt = conn.createStatement();  
  
            System.out.println("Executing statement start.");  
            rset = stmt.executeQuery(testSql);  
  
            System.out.println("executeQuery Results:");  
            int numcols = rset.getMetaData().getColumnCount();  
  
            while (rset.next()) {  
                for (int i = 1; i <= numcols; i++) {  
                	System.out.print(rset.getMetaData().getColumnName(i) + " : " );
                    System.out.println(rset.getString(i));  
                }  
                System.out.println("");  
            }  
            System.out.println("Results display done.");  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (rset != null)  
                    rset.close();  
            } catch (Exception e) {  
            }  
            try {  
                if (stmt != null)  
                    stmt.close();  
            } catch (Exception e) {  
            }  
            try {  
                if (conn != null)  
                    conn.close();  
                shutdownDataSource(pool.getDataSource());  
            } catch (Exception e) {  
            }  
  
        }
        
	}

}
