package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Connection.Connect;
import Model.User;

public class UserDAO extends AbstractGenericDAO<User>{

	public UserDAO() {
		super(User.class);
	}

	@Override
	protected String getTableName() {
		return "MsUser";
	}

	@Override
	protected User mapResultSetToObject(ResultSet resultSet) throws SQLException {
		int userID = resultSet.getInt("UserID");
		String userName = resultSet.getString("UserName");
		String userPassword = resultSet.getString("UserPassword");
		String userGender = resultSet.getString("UserGender");
		LocalDate userDOB = resultSet.getDate("UserDOB").toLocalDate();
		String userRole = resultSet.getString("UserRole");
		return new User(userID, userName, userPassword, userGender, userDOB, userRole);
	}

	@Override
	protected int getIdFromEntity(User entity) {
		return 0;
	}
	
	public User select(String userName, String password) {
		String query = "SELECT * FROM MsUser WHERE UserName = '"+userName+"' AND UserPassword = '"+password+"'";

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
	
	public User select(String userName) {
		String query = "SELECT * FROM MsUser WHERE UserName = '"+userName+"'";

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
		return "UserID";
	}
}
