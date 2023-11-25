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
			UserController.getUserController().addNewUser(userName, userPassword, userGender.getText(), userDOB, "Customer");
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
				PageController.getPageController().showManagePCPage();
			}	
			else if(currentUser.getUserRole().equals("Operator")) {
				PageController.getPageController().showManagePCBookPage();
			}	
			else if(currentUser.getUserRole().equals("Computer Technician")) {
				PageController.getPageController().showManageJobPage();
			}	
		}
	}

	public void signOut() {
		currentUser = null;
		PageController.getPageController().showLoginPage();
	}
	
	private boolean isAlphanumeric(String str) {
		boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLetter && hasDigit;
    }
	
	private boolean isAgeInRange(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthdate, currentDate);

//        System.out.println(age.getYears());
        return age.getYears() >= 13 && age.getYears() <= 65;
    }

	private Alert validateRegister(String name, String password, String conpass, RadioButton gender, LocalDate userDOB) {
		Alert prompt;
		if (name.isEmpty() || name.equals("") || name.length() < 7) {
			prompt = new Alert(AlertType.ERROR, "Name Minimal 7 character long!", ButtonType.OK);
		}
		else if (UserController.getUserController().getUserByName(name) != null) {
			prompt = new Alert(AlertType.ERROR, "UserName already exist!", ButtonType.OK);
		}
		else if (password.isEmpty() || password.equals("") || password.length() < 6) {
			prompt = new Alert(AlertType.ERROR, "Password Minimal 6 character long!", ButtonType.OK);
		}
		else if (!isAlphanumeric(password)) {
			prompt = new Alert(AlertType.ERROR, "Password must contains alpha numeric character!", ButtonType.OK);
		}
		else if (!password.equals(conpass)) {
			prompt = new Alert(AlertType.ERROR, "Password Not Matches!", ButtonType.OK);
		}
		else if(gender == null) {
			prompt = new Alert(AlertType.ERROR, "Gender is empty!", ButtonType.OK);
		}
		else if(userDOB == null || !isAgeInRange(userDOB)) {
			prompt = new Alert(AlertType.ERROR, "Must be between 13 – 65 Years Old!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "Success");
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
//				System.out.println(user.getUserID());
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
