package Model;

import java.time.LocalDate;
import java.util.List;

import Controller.PC_Controller;
import Controller.UserController;
import Repository.UserDAO;

public class User {
	private int userID;
	private String userName, userPassword, userGender;
	private LocalDate userDOB;
	private String userRole;
	
	public User(int userID, String userName, String userPassword, String userGender,
			LocalDate userDOB, String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userRole = userRole;
	}

	public User(String userName, String userPassword, String userGender,
			LocalDate userDOB, String userRole) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userRole = userRole;
	}
	

	public User() {
		
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public LocalDate getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(LocalDate userDOB) {
		this.userDOB = userDOB;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public List<User> getAllStaff(){
		return UserDAO.getUserDAO().getAllStaff();
	}

	public User getUserDetail(int id) {
		return UserDAO.getUserDAO().select(id);
	}

	public void changeUserRole(User u) {
		UserDAO.getUserDAO().update(u);
	}

	public void addNewUser(User user) {
		UserDAO.getUserDAO().save(this);
	}

	public User getUserByName(String name) {
		return UserDAO.getUserDAO().select(name);
	}

}
