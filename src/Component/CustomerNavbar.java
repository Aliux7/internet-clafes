package Component;

import Controller.PageController;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class CustomerNavbar extends NavigationBar{
	private Menu reportMenu, transactionMenu;
	private Label reportLbl, transactionLbl;
	
	private void createCustomerNavbar() {
		transactionLbl = createLabel(WHITE, "Transaction", "Arial", true, 15);
		reportLbl = createLabel(WHITE, "Report", "Arial", true, 15);
		
		transactionMenu = new Menu("", transactionLbl);
		reportMenu = new Menu("", reportLbl);
		
		super.rightBar.getMenus().clear();
		super.rightBar.getMenus().addAll(transactionMenu, reportMenu, logOutMenu);
		
		transactionLbl.setOnMouseClicked(e -> PageController.getPageController().showTransactionView());
		reportLbl.setOnMouseClicked(e -> PageController.getPageController().showReportPage());
	}

	public CustomerNavbar() {
		super();
		createCustomerNavbar();
	}
}

