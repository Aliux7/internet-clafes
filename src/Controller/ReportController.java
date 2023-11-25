package Controller;

import java.time.LocalDate;
import java.util.List;

import Model.PC_Book;
import Model.Report;
import Repository.ReportDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ReportController {
	private volatile static ReportController instance;
	private ReportController() {}
	private Report r = new Report();

	public static ReportController getReport_Controller() {
		if(instance == null) {
			synchronized (ReportController.class) {
				if(instance == null) instance = new ReportController();
			}
		}
		return instance;
	}
	
	public boolean addNewReport(String userRole, String pC_ID, String reportNote) {
		boolean v = false;
		Alert prompt = validate(pC_ID, reportNote);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			Report report = new Report(userRole, Integer.parseInt(pC_ID), reportNote);
			report.addNewReport(userRole, pC_ID, reportNote);
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	public List<Report> getAllReportData(){
		return r.getAllReportData();
	}
	
	private Alert validate(String pC_ID, String reportNote) {
		Alert prompt;

		if(pC_ID.isEmpty() || pC_ID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC ID must be filled", ButtonType.OK);
		}else if(reportNote.isEmpty() || reportNote.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Report note must be filled", ButtonType.OK);
		}else if(PC_Controller.getPC_Controller().getPCDetail(Integer.parseInt(pC_ID)) == null){
			prompt = new Alert(AlertType.ERROR, "PC id is not found!", ButtonType.OK);
		}else {
			prompt = new Alert(AlertType.INFORMATION, "Success");
		}	
		return prompt;
	}

}
