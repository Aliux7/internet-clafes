package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.Connect;
import Model.PC;

public class PC_DAO extends AbstractGenericDAO<PC>{

	public PC_DAO() {
		super(PC.class);
	}

	@Override
	protected String getTableName() {
		return "MsPC";
	}

	@Override
	protected PC mapResultSetToObject(ResultSet resultSet) throws SQLException {
		int PC_ID = resultSet.getInt("PC_ID");
		String PC_Condition = resultSet.getString("PC_Condition");
		return new PC(PC_ID, PC_Condition);
	}

	@Override
	protected int getIdFromEntity(PC entity) {
		return 0;
	}
	
	public PC select(int PC_ID, String PC_Condition) {
		String query = "SELECT * FROM MsPC WHERE PC_ID = '"+PC_ID+"' AND PC_Condition = '"+PC_Condition+"'";

		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepare(query);
		ResultSet rs = connect.executeStatementQuery(ps);

		try {
			while(rs.next()) {
				return mapResultSetToObject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PC select(String PC_Condition) {
		String query = "SELECT * FROM MsPC WHERE PC_Condition = '"+PC_Condition+"'";

		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepare(query);
		ResultSet rs = connect.executeStatementQuery(ps);

		try {
			while(rs.next()) {
				return mapResultSetToObject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PC select(int PC_ID) {
		String query = "SELECT * FROM MsPC WHERE PC_ID = '"+PC_ID+"''";

		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepare(query);
		ResultSet rs = connect.executeStatementQuery(ps);

		try {
			while(rs.next()) {
				return mapResultSetToObject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected String getIdName() {
		return "PC_ID";
	}
}
