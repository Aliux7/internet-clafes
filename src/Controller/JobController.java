package Controller;

import java.time.LocalDate;
import java.util.List;

import Model.Job;
import Model.PC;
import Model.PC_Book;
import Model.User;
import Repository.JobDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class JobController {
	private volatile static JobController instance;
	private JobController() {}
	
	private Job j = new Job();

	public static JobController getJobController() {
		if(instance == null) {
			synchronized (PC_Controller.class) {
				if(instance == null) instance = new JobController();
			}
		}
		return instance;
	}
	
	public List<Job> getAllJobData(){
		return j.getAllJobData();
	}
	
	public List<Job> getTechnicianJob(int userID){
		return j.getTechnicianJob(userID);
	}
	
	public Job getJobDetail(int id) {
		return j.getJobDetail(id);
	}
	
	public PC getPCDetail(int id) {
		return PC_Controller.getPC_Controller().getPCDetail(id);
	}
	
	public PC_Book getPCBookedData(int id, LocalDate date) {
		return PC_BookController.getPC_BookController().getPCBookedData(id, date);
	}
	
	public User getUserDetail(int id) {
		return UserController.getUserController().getUserDetail(id);
	}
	
	public boolean addNewJob(String userID, String pcID) {
		boolean v = false;
		Alert prompt = validate(userID, pcID);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			Job j = new Job(Integer.parseInt(userID), Integer.parseInt(pcID), "Uncomplete");
			j.addNewJob(Integer.parseInt(userID), Integer.parseInt(pcID));
			
			PC_BookController.getPC_BookController().addNewBook(pcID, Integer.parseInt(userID) , LocalDate.now());
			
			PC_Controller.getPC_Controller().updatePCCondition(pcID, "Maintenance");
			
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	public boolean updateJobStatus(String jobID, String jobStatus) {
		boolean v = false;
		Alert prompt = validateUpdate(jobID, jobStatus);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			Job j = getJobDetail(Integer.parseInt(jobID));
			
			if(!jobStatus.equals(j.getJobStatus())) {
				j.setJobStatus(jobStatus);
				j.updateJobStatus(jobID, jobStatus);
				
				String condition = jobStatus.equals("Uncomplete") ? "Maintenance" : "Usable";
				System.out.println(condition);
				
				PC_Controller.getPC_Controller().updatePCCondition(Integer.toString(j.getPC_ID()), condition);
				
				PC_Book pb = PC_BookController.getPC_BookController().getPCBookedData(j.getPC_ID(), j.getUserID());
				
				if(condition.equals("Usable")) {
					if(pb != null) {
						pb.deleteBookData(pb.getBookID());
					}
				}else {
					if(pb == null) {
						PC_BookController.getPC_BookController().addNewBook(Integer.toString(j.getPC_ID()), j.getUserID() , LocalDate.now());
					}
				}
			}
	
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	private Alert validateUpdate(String jobID, String jobStatus) {
		Alert prompt;
		if (jobID.isEmpty() || jobID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Job id is empty!", ButtonType.OK);
		}
		else if (jobStatus.isEmpty() || jobStatus.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Job status is empty!", ButtonType.OK);
		}
		else if (!jobStatus.equals("Complete") && !jobStatus.equals("Uncomplete")) {
			prompt = new Alert(AlertType.ERROR, "Job status must be either complete or uncomplete!", ButtonType.OK);
		}
		else if (getJobDetail(Integer.parseInt(jobID)) == null) {
			prompt = new Alert(AlertType.ERROR, "Job id is not found!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "Job Updated!", ButtonType.OK);
		}
		
		return prompt;
	}

	private Alert validate(String userID, String pcID) {
		Alert prompt;
		if (pcID.isEmpty() || pcID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC id is empty!", ButtonType.OK);
		}
		else if (userID.isEmpty() || userID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "User id is empty!", ButtonType.OK);
		}
		else if(getUserDetail(Integer.parseInt(userID)) == null){
			prompt = new Alert(AlertType.ERROR, "User id is not found!", ButtonType.OK);
		}
		else if(!getUserDetail(Integer.parseInt(userID)).getUserRole().equals("Computer Technician")){
			prompt = new Alert(AlertType.ERROR, "User is not technician!", ButtonType.OK);
		}
		else if(getPCDetail(Integer.parseInt(pcID)) == null){
			prompt = new Alert(AlertType.ERROR, "PC id not found!", ButtonType.OK);
		}
		else if(getPCBookedData(Integer.parseInt(pcID), LocalDate.now()) != null){
			prompt = new Alert(AlertType.ERROR, "Other technician is repairing PC!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "Job Inserted!", ButtonType.OK);
		}
		
		return prompt;
	}
	
	
}
