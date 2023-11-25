package Controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import Model.PC;
import Model.User;
import Repository.UserDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class UserController {
	private volatile static UserController instance;
	private UserController() {}
	private User u = new User();

	public static UserController getUserController() {
		if(instance == null) {
			synchronized (PC_Controller.class) {
				if(instance == null) instance = new UserController();
			}
		}
		return instance;
	}
	
	public List<User> getAllStaff(){
		return u.getAllStaff();
	}
	
	public User getUserDetail(int id) {
		return u.getUserDetail(id);
	}
	
	public User getUserByName(String name) {
		return u.getUserByName(name);
	}
	
	public boolean changeUserRole(String userID, String role) {
		boolean v = false;
		Alert prompt = validate(userID, role);
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			User u = getUserDetail(Integer.parseInt(userID));
			u.setUserRole(role);
			
			System.out.println(u.getUserRole() + u.getUserID());

			u.changeUserRole(u);
			v = true;
		}
		prompt.showAndWait();
		return v;
	}
	
	public void addNewUser(String userName, String userPassword, String userGender, LocalDate userDOB, String userRole) {
		User user = new User(0, userName, userPassword, userGender, userDOB, userRole); 
		user.addNewUser(user);
	}
	
	private boolean isValidUserRole(String role) {
	    List<String> validRoles = Arrays.asList("Admin", "Customer", "Operator", "Computer Technician");
	    return validRoles.contains(role);
	}
	
	private Alert validate(String userID, String userRole) {
		Alert prompt;
		if (userID.isEmpty() || userID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "User id is empty!", ButtonType.OK);
		}
		else if (userRole.isEmpty() || userRole.equals("")) {
			prompt = new Alert(AlertType.ERROR, "User role is empty!", ButtonType.OK);
		}
		else if (!isValidUserRole(userRole)) {
			prompt = new Alert(AlertType.ERROR, "User role must be either Admin, Customer, Operator ,or Computer Technician!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "User Updated!", ButtonType.OK);
		}
		
		return prompt;
	}
}
