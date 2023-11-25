package Controller;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import Model.PC;
import Repository.PC_DAO;

public class PC_Controller {
	private volatile static PC_Controller instance;
	private PC_Controller() {}

	private PC pc = new PC();

	public static PC_Controller getPC_Controller() {
		if(instance == null) {
			synchronized (PC_Controller.class) {
				if(instance == null) instance = new PC_Controller();
			}
		}
		return instance;
	}
	
	public List<PC> getAllPCData(){
		return pc.getAllPCData();
	}
	
	public PC getPCDetail(int id) {
		return pc.getPCDetail(id);
	}
	
	public boolean addNewPC(String PC_ID) {
		boolean v = false;
		Alert prompt = validateInsert(PC_ID);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			PC pc = new PC(Integer.parseInt(PC_ID), "Usable");
			pc.addNewPC(Integer.parseInt(PC_ID));
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	public boolean updatePCCondition(String pcID, String condition) {
		boolean v = false;
		Alert prompt = validate(pcID, condition);
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			PC pc = getPCDetail(Integer.parseInt(pcID));
			pc.setPC_Condition(condition);

			pc.updatePCCondition(pc);
			v = true;
		}
		prompt.showAndWait();
		return v;
	}
	
	public boolean deletePC(String PC_ID) {
		boolean v = false;
		Alert prompt = validate(PC_ID);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			PC pc = getPCDetail(Integer.parseInt(PC_ID));
			
			pc.deletePC(pc);
			
			v = true;
		}
		
		prompt.showAndWait();
		
		
		return v;
	}
	
	
	private Alert validate(String pcID, String pcCondition) {
		Alert prompt;
		if (pcID.isEmpty() || pcID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC id is empty!", ButtonType.OK);
		}
		else if(getPCDetail(Integer.parseInt(pcID)) == null){
			prompt = new Alert(AlertType.ERROR, "PC id is not found!", ButtonType.OK);
		}
		else if (pcCondition.isEmpty() || pcCondition.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC condition is empty!", ButtonType.OK);
		}
		else if (!pcCondition.equals("Maintenance") && !pcCondition.equals("Broken") && !pcCondition.equals("Usable")) {
			prompt = new Alert(AlertType.ERROR, "PC condition must be either “Usable”, “Maintenance” or “Broken”.!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "PC Updated!", ButtonType.OK);
		}
		
		return prompt;
	}
	
	private Alert validate(String pcID) {
		Alert prompt;
		if (pcID.isEmpty() || pcID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC id is empty!", ButtonType.OK);
		}
		else if(getPCDetail(Integer.parseInt(pcID)) == null){
			prompt = new Alert(AlertType.ERROR, "PC id is not found!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "PC Deleted!", ButtonType.OK);
		}
		
		return prompt;
	}
	
	private Alert validateInsert(String pcID) {
		Alert prompt;
		if (pcID.isEmpty() || pcID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC id is empty!", ButtonType.OK);
		}
		else if(getPCDetail(Integer.parseInt(pcID)) != null){
			prompt = new Alert(AlertType.ERROR, "PC id already exist!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "PC Inserted!", ButtonType.OK);
		}
		
		return prompt;
	}
}
