package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Connection.Connect;
import Controller.PC_Controller;
import Model.Job;
import Model.PC_Book;

public class PC_BookDAO extends AbstractGenericDAO<PC_Book>{
	
	private volatile static PC_BookDAO instance;
	
	public static PC_BookDAO getPC_BookDAO() {
		if(instance == null) {
			synchronized (PC_BookDAO.class) {
				if(instance == null) instance = new PC_BookDAO();
			}
		}
		return instance;
	}

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
		return entity.getBookID();
	}
	
	
	public PC_Book select(int PC_ID, LocalDate date) {
		String query = "SELECT * FROM "+ getTableName() +" WHERE PC_ID = '"+PC_ID+"' AND BookedDate = '" + date + "'";
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
	public List<PC_Book> findAll() {
		List<PC_Book> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName() + " mp JOIN MsUser mu ON mp.UserID = mu.UserID WHERE UserRole = 'Customer'";
//		System.out.println(query);
		try { 
			PreparedStatement statement = connect.prepare(query);
			ResultSet resultSet = statement.executeQuery(query); 
			while (resultSet.next()) {
				entities.add(mapResultSetToObject(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}
	
	public List<PC_Book> getPCBookedDataByDate(LocalDate date) {
		List<PC_Book> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName() + " mp JOIN MsUser mu ON mp.UserID = mu.UserID  WHERE BookedDate = '" + date.toString() + "' AND UserRole = 'Customer'";
//		System.out.println(query);
		try { 
			PreparedStatement statement = connect.prepare(query);
			ResultSet resultSet = statement.executeQuery(query); 
			while (resultSet.next()) {
				entities.add(mapResultSetToObject(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}


	@Override
	protected String getIdName() {
		return "BookID";
	}

	public PC_Book select(int pcID, int userID) {
		String query = "SELECT * FROM "+ getTableName() +" WHERE PC_ID = '"+pcID+"' AND UserID = '" + userID + "'";
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

	public PC_Book select(int bookID) {
		String query = "SELECT * FROM "+ getTableName() +" WHERE BookID = '"+bookID+"'";
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

}
