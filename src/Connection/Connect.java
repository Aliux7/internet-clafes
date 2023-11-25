package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "internet_clafes";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	private Connection con;
	private ResultSet rs;
	private Statement st;
	private static Connect connect;

	private Connect() {
		try {  
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);  
			st = con.createStatement(); 
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to connect the database, the system is terminated!");
			System.exit(0);
		}  
	}

	public static synchronized Connect getConnection() {
		if(connect == null) {
			synchronized (Connect.class) {
				if(connect==null)connect = new Connect();
			}
		}
		return connect;
	}

	public void executeStatementUpdate(PreparedStatement ps) {
		try {
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeStatementQuery(PreparedStatement ps) {
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement prepare (String query) {
		PreparedStatement preps = null;
		try {
			preps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preps;
	}
	
	public PreparedStatement prepare(String query, int flags) {
	    PreparedStatement preps = null;
	    try {
	        preps = con.prepareStatement(query, flags);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return preps;
	}

}
