package Controller;

import View.LandingPage;
import View.LoginPage;
import View.RegisterPage;
import View.ReportPage;
import View.TransactionPage;
import Main.Main;


public class PageController {
	private volatile static PageController instance;

	private PageController() {}

	public static PageController getPageController() {
		if(instance == null) {
			synchronized (PageController.class) {
				if(instance == null) instance = new PageController();
			}
		}
		return instance;
	}
	
	public void showRegisterPage() {
		Main.root.getChildren().add(new RegisterPage());
	}
	
	public void showLoginPage() {
		Main.root.getChildren().add(new LoginPage());
	}
	
	public void showLandingPage() {
		Main.root.getChildren().add(new LandingPage());
	}
	
	public void showTransactionView() {
		Main.root.getChildren().add(new TransactionPage());
	}
	
	public void showReportPage() {
		Main.root.getChildren().add(new ReportPage());
	}
	
//	public void showUserPage() {
//		Main.root.getChildren().add(new ManageUserView());
//	}
	

}
