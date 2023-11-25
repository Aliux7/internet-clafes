package Model;

import java.util.List;

import Controller.JobController;
import Controller.PC_Controller;
import Repository.JobDAO;

public class Job {

	private int JobID, UserID, PC_ID;
	private String JobStatus;
	
	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Job(int jobID, int userID, int pC_ID, String jobStatus) {
		super();
		JobID = jobID;
		UserID = userID;
		PC_ID = pC_ID;
		JobStatus = jobStatus;
	}

	public Job(int userID, int pC_ID, String jobStatus) {
		super();
		UserID = userID;
		PC_ID = pC_ID;
		JobStatus = jobStatus;
	}

	public int getJobID() {
		return JobID;
	}

	public void setJobID(int jobID) {
		JobID = jobID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public String getJobStatus() {
		return JobStatus;
	}

	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
	}

	public List<Job> getAllJobData() {
		return JobDAO.getJobDAO().findAll();
	}

	public Job getJobDetail(int id) {
		return JobDAO.getJobDAO().select(id);
	}
	
	public void addNewJob(int userID, int pcID) {
		JobDAO.getJobDAO().save(this);
	}

	public void updateJobStatus(String jobID, String jobStatus) {
		JobDAO.getJobDAO().update(this);
	}

	public List<Job> getTechnicianJob(int userID) {
		return JobDAO.getJobDAO().getTechnicianJob(userID);
	}
	

}
