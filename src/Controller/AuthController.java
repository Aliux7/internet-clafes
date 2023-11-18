package Controller;

import java.time.LocalDate;
import java.time.Period;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import Model.User;
import Repository.UserDAO;

public class AuthController {
	private volatile static AuthController instance;
	public User currentUser = new User(0, "", "", "", null, "");
	private UserDAO userDAO = new UserDAO();

	private AuthController() {}

	public static AuthController getAuthController() {
		if(instance == null) {
			synchronized (AuthController.class) {
				if(instance == null) instance = new AuthController();
			}
		}
		return instance;
	}
	
	public void register(String userName, String userPassword, String userConPass, RadioButton userGender, LocalDate userDOB, String userRole) {
		Alert prompt = validateRegister(userName, userPassword, userConPass, userGender, userDOB);
		prompt.showAndWait();
		if (prompt.getAlertType().equals(AlertType.INFORMATION)) {
			User user = new User(0, userName, userPassword, userGender.getText(), userDOB, "Customer"); 
			userDAO.save(user);
			PageController.getPageController().showLoginPage();
		}
	}

	public void login(String userName, String password) {
		Alert prompt = validateLogin(userName, password);
		
		if (prompt.getAlertType().equals(AlertType.ERROR)) {
			prompt.showAndWait();
			return;
		}

		if (prompt.getAlertType().equals(AlertType.INFORMATION)) {
			if(currentUser.getUserRole().equals("Customer")) {
				PageController.getPageController().showLandingPage();
			}
			else if(currentUser.getUserRole().equals("Admin")) {
//				PageController.getPageController().showSupplementPage();
			}			
		}
	}

	public void signOut() {
		currentUser = null;
		PageController.getPageController().showLoginPage();
	}

	private Alert validateRegister(String name, String password, String conpass, RadioButton gender, LocalDate userDOB) {
		Alert prompt;
		if (name.isEmpty() || name.equals("") || name.length() < 7) {
			prompt = new Alert(AlertType.ERROR, "Name Minimal 7 character long!", ButtonType.OK);
		}
		else if (password.isEmpty() || password.equals("") || password.length() < 6) {
			prompt = new Alert(AlertType.ERROR, "Password Minimal 6 character long!", ButtonType.OK);
		}
		else if (!password.equals(conpass)) {
			prompt = new Alert(AlertType.ERROR, "Password Not Matches!", ButtonType.OK);
		}
		else if(gender == null) {
			prompt = new Alert(AlertType.ERROR, "Gender is empty!", ButtonType.OK);
		}
		else if(userDOB == null) {
//			|| Period.between(userDOB, LocalDate.now()).getYears() < 13 || Period.between(userDOB, LocalDate.now()).getYears() > 65
			prompt = new Alert(AlertType.ERROR, "Must be between 13 – 65 Years Old!", ButtonType.OK);
		}
		else {
			User user = (User) userDAO.select(name);
			if (user != null) {
				prompt = new Alert(AlertType.ERROR, "Name has already been used", ButtonType.OK);
			}  else {
				prompt = new Alert(AlertType.INFORMATION, "Sucess");
			}
		}	
		return prompt;
	}

	private Alert validateLogin(String userName, String password) {
		Alert prompt;
		if (userName.isEmpty() || userName.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Name is empty!", ButtonType.OK);
		} 
		else if (password.isEmpty() || password.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Password is empty!", ButtonType.OK);
		} 
		else {
			User user = (User) userDAO.select(userName, password);
			if (user == null) {
				prompt = new Alert(AlertType.ERROR, "User not Found", ButtonType.OK);
			}  else {
				System.out.println(user.getUserID());
				prompt = new Alert(AlertType.INFORMATION, "Success");
				currentUser = user;
			}
		}	
		return prompt;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}
}
