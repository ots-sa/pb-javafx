package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnector {
	
	private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String DBUSER = "system";
	private static final String DBPASS = "manager";
	private Connection con;
    
    public OracleConnector(){
    	connect();
    };
    
    public void connect()
    {
    	
    	try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());		// Load Oracle JDBC Driver			
	        con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);		// Connect to Oracle Database

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
    
    


}
