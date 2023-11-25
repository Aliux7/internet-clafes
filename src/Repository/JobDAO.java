package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.Connect;
import Controller.JobController;
import Controller.PC_Controller;
import Model.Job;
import Model.PC;

public class JobDAO extends AbstractGenericDAO<Job>{
	
	private volatile static JobDAO instance;
	
	public static JobDAO getJobDAO() {
		if(instance == null) {
			synchronized (JobDAO.class) {
				if(instance == null) instance = new JobDAO();
			}
		}
		return instance;
	}

	public JobDAO() {
		super(Job.class);
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "MsJob";
	}

	@Override
	protected Job mapResultSetToObject(ResultSet resultSet) throws SQLException {
		int jobID = resultSet.getInt("JobID");
		int userID = resultSet.getInt("UserID");
		int pcID = resultSet.getInt("PC_ID");
		String jobStatus = resultSet.getString("JobStatus");
		return new Job(jobID, userID, pcID, jobStatus);
	}

	@Override
	protected int getIdFromEntity(Job entity) {
		// TODO Auto-generated method stub
		return entity.getJobID();
	}

	@Override
	protected String getIdName() {
		// TODO Auto-generated method stub
		return "JobID";
	}
	
	public Job select(int jobId) {
		String query = "SELECT * FROM "+ getTableName() +" WHERE JobID = '"+jobId+"'";

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
	
	public List<Job> getTechnicianJob(int userId) {
		List<Job> entities = new ArrayList<>();
		String query = "SELECT * FROM " + getTableName() + " WHERE UserID = '" + userId+"'";
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
