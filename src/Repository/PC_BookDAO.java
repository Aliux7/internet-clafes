package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Connection.Connect;
import Model.PC_Book;

public class PC_BookDAO extends AbstractGenericDAO<PC_Book>{

	public PC_BookDAO() {
		super(PC_Book.class);
	}

	@Override
	protected String getTableName() {
		return "MsPCBook";
	}

	@Override
	protected PC_Book mapResultSetToObject(ResultSet resultSet) throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int PC_ID = resultSet.getInt("PC_ID");
		int BookID = resultSet.getInt("BookID");
		int UserID = resultSet.getInt("UserID");
		String BookedDate = resultSet.getString("BookedDate");
		return new PC_Book(BookID, PC_ID, UserID, LocalDate.parse(BookedDate, formatter));
	}

	@Override
	protected int getIdFromEntity(PC_Book entity) {
		return 0;
	}
	
	
//	public PC_Book select(int PC_ID) {
//		String query = "SELECT * FROM MsPC WHERE PC_ID = '"+PC_ID+"''";
//
//		Connect connect = Connect.getConnection();
//		PreparedStatement ps = connect.prepare(query);
//		ResultSet rs = connect.executeStatementQuery(ps);
//
//		try {
//			while(rs.next()) {
//				return mapResultSetToObject(rs);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}


	@Override
	protected String getIdName() {
		return "PCID";
	}
}
