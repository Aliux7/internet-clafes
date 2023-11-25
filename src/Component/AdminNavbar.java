package Component;

import Controller.PageController;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class AdminNavbar extends NavigationBar{
	
	private Menu reportMenu, transactionMenu, jobMenu, staffMenu;
	private Label reportLbl, transactionLbl, jobLbl, staffLbl;
	
	private void createAdminNavbar() {
		transactionLbl = createLabel(WHITE, "Transaction", "Arial", true, 15);
		reportLbl = createLabel(WHITE, "Report", "Arial", true, 15);
		jobLbl = createLabel(WHITE, "Manage Job", "Arial", true, 15);
		staffLbl = createLabel(WHITE, "Manage Staff", "Arial", true, 15);
		
		transactionMenu = new Menu("", transactionLbl);
		reportMenu = new Menu("", reportLbl);
		jobMenu = new Menu("", jobLbl);
		staffMenu = new Menu("", staffLbl);
		
		super.rightBar.getMenus().clear();
		super.rightBar.getMenus().addAll(staffMenu, jobMenu, transactionMenu, reportMenu, logOutMenu);
		
		transactionLbl.setOnMouseClicked(e -> PageController.getPageController().showTransactionView());
		reportLbl.setOnMouseClicked(e -> PageController.getPageController().showReportPage());
		jobLbl.setOnMouseClicked(e -> PageController.getPageController().showManageJobPage());
		staffLbl.setOnMouseClicked(e -> PageController.getPageController().showManageStaffPage());
	}

	public AdminNavbar() {
		super();
		createAdminNavbar();
	}

}
