package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Controller.PC_Controller;
import Model.TransactionDetail;

public class TransactionDetailDAO extends AbstractGenericDAO<TransactionDetail>{

	private volatile static TransactionDetailDAO instance;
	
	public static TransactionDetailDAO getTransactionDetailDAO() {
		if(instance == null) {
			synchronized (TransactionDetailDAO.class) {
				if(instance == null) instance = new TransactionDetailDAO();
			}
		}
		return instance;
	}
	
	public TransactionDetailDAO() {
		super(TransactionDetail.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "TransactionDetail";
	}

	@Override
	protected TransactionDetail mapResultSetToObject(ResultSet resultSet) throws SQLException {
		int transactionID = resultSet.getInt("TransactionID");
		int pcID = resultSet.getInt("PC_ID");
		int customerID = resultSet.getInt("CustomerID");
		LocalDate bookedTime = resultSet.getDate("BookedTime").toLocalDate();
		return new TransactionDetail(transactionID, pcID, customerID, bookedTime);
	}

	@Override
	protected int getIdFromEntity(TransactionDetail entity) {
		return 0;
	}
	
	public List<TransactionDetail> findAllByUser(int id) {
		List<TransactionDetail> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName() +" WHERE CustomerID = '" + id+ "'";
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
	
	public List<TransactionDetail> findAllByID(int id) {
		List<TransactionDetail> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName() +" WHERE TransactionID = '" + id+ "'";
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
		// TODO Auto-generated method stub
		return "TransactionID";
	}
	
	

}
