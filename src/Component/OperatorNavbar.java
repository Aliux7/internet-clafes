package Component;

import Controller.PageController;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class OperatorNavbar extends NavigationBar{
	
	private Menu reportMenu, transactionMenu, jobMenu, staffMenu;
	private Label reportLbl, transactionLbl, jobLbl, staffLbl;
	
	private void createOperatorNavbar() {
		reportLbl = createLabel(WHITE, "Report", "Arial", true, 15);
		
		reportMenu = new Menu("", reportLbl);
		
		super.rightBar.getMenus().clear();
		super.rightBar.getMenus().addAll(reportMenu, logOutMenu);
		
		reportLbl.setOnMouseClicked(e -> PageController.getPageController().showReportPage());
	}

	public OperatorNavbar() {
		super();
		createOperatorNavbar();
	}

}
