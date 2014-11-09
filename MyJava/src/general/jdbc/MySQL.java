package general.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {							
	private final static String MySQLDriver = "com.mysql.jdbc.Driver"; 
	private final static String MYSQLURL = "jdbc:mysql://192.168.1.118/sys_manager";	//IP + DB name
											//jdbc:mysql://192.168.1.118/sys_manager?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull
	public static void main(String [] args){
		try {
			//register MySQL driver to DriverManager
			Class.forName(MySQLDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection con = DriverManager.getConnection(MYSQLURL,"root","");	//URL, user name, password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from algorithm_list");

			while(rs.next()){
				System.out.println(rs.getString("id") + " " + rs.getString("type") + " " + rs.getString("params"));
			}
			
			con.close();
			stmt.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
