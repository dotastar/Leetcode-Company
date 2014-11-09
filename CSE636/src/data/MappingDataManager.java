package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MappingDataManager {

	private Connection conn = null;

	private volatile static MappingDataManager instance = null;
	
	public static void main(String[] args){

	}
	
	public static MappingDataManager getInstance(){
		if(instance==null){
			synchronized(MappingDataManager.class){
				if(instance==null){
					instance = new MappingDataManager();
				}
			}
		}
		return instance;
	}
	
	private MappingDataManager(){
		conn = OracleManager.getConnection();
	}	
	
	
	public boolean executeSQLs(String... sqls) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = conn.createStatement();			
			for(String sql : sqls){
				 stmt.executeQuery(sql);
			}
			stmt.close();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	

	public ArrayList<String> queryValue(String sql) {
		ArrayList<String> results = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				results.add(rs.getString(1));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			OracleManager.close(rs, stmt);
		}
		return results;
	}
}
