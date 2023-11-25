package Repository;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.TransactionHeader; 

public class TransactionDAO extends AbstractGenericDAO<TransactionHeader>{
	
	private volatile static TransactionDAO instance;
	
	public static TransactionDAO getTransactionDAO() {
		if(instance == null) {
			synchronized (TransactionDAO.class) {
				if(instance == null) instance = new TransactionDAO();
			}
		}
		return instance;
	}

	public TransactionDAO() {
		super(TransactionHeader.class);
	}

	@Override
	protected String getTableName() {
		return "TransactionHeader";
	}
	

	@Override
	protected TransactionHeader mapResultSetToObject(ResultSet resultSet) throws SQLException {
		int transactionID = resultSet.getInt("TransactionID");
		int userID = resultSet.getInt("StaffID");
		String userName = resultSet.getString("StaffName");
		LocalDate transactionDate = resultSet.getDate("TransactionDate").toLocalDate();
		return new TransactionHeader(transactionID, userID, userName, transactionDate);
	}

	@Override
	protected int getIdFromEntity(TransactionHeader entity) {
		return entity.getTransactionID();
	}

	@Override
	protected String getIdName() {
		// TODO Auto-generated method stub
		return "TransactionID";
	}
	
	public List<TransactionHeader> findByUserID(int id) {
		List<TransactionHeader> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName() + " WHERE UserID = '" +id+"'";
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

}
