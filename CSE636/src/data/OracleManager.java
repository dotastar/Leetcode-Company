package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class OracleManager {
	private final static String driver = "oracle.jdbc.driver.OracleDriver";
	private final static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final static String user = "test";	//"sys as sysdba";
	private final static String pswd = "12345";
	
	static{
		try {
			Class.forName(driver).newInstance();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to load the class " + driver);
		}
	}
	
	private OracleManager(){}
	
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(url, user, pswd);
		} catch (SQLException e) {
			System.out.println("Failed to connect oracle server connect:"+url
					+" user:"+user
					+" pwd:"+pswd);
		}	
		return null;
	}
	
	public static void close(ResultSet rs, Statement stm) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stm != null) {
			try {
				stm.close();
				stm = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
