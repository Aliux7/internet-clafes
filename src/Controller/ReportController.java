package Controller;

import java.util.List;

import Model.Report;
import Repository.ReportDAO;

public class ReportController {
	private volatile static ReportController instance;
	private ReportController() {}
	private ReportDAO dao = new ReportDAO();

	public static ReportController getReport_Controller() {
		if(instance == null) {
			synchronized (ReportController.class) {
				if(instance == null) instance = new ReportController();
			}
		}
		return instance;
	}
	
	public void AddNewReport(String userRole, int pC_ID, String reportNote) {
		Report report = new Report(userRole, pC_ID, reportNote);
		dao.save(report);
	}
	
	public List<Report> findAll(){
		return dao.findAll();
	}
	
	

}
