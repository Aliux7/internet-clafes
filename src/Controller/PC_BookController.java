package Controller;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import Model.PC;
import Model.PC_Book;
import Model.User;
import Repository.PC_BookDAO;

public class PC_BookController {
	private volatile static PC_BookController instance;
	private PC_BookController() {}
	
	private PC_Book pb = new PC_Book();

	public static PC_BookController getPC_BookController() {
		if(instance == null) {
			synchronized (PC_BookController.class) {
				if(instance == null) instance = new PC_BookController();
			}
		}
		return instance;
	}
	
	public List<PC_Book> getAllPCBookedData(){
		return pb.getAllPCBookedData();
	}
	
	public PC_Book getPCBookedData(int pcID, LocalDate date) {
		return pb.getPCBookedData(pcID, date);
	}
	
	public PC_Book getPCBookedData(int pcID, int userID) {
		return pb.getPCBookedData(pcID, userID);
	}
	
	public PC_Book getPCBookedData(int bookID) {
		return pb.getPCBookedData(bookID);
	}
	
	public PC getPCDetail(int id) {
		return PC_Controller.getPC_Controller().getPCDetail(id);
	}
	
	public List<PC_Book> getPCBookedDataByDate(LocalDate date){
		return pb.getPCBookedDataByDate(date);
	}
	
	public boolean finishBook(LocalDate date) {
		boolean v = false;
		Alert prompt = validateFinish(date);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			List<PC_Book> books = getPCBookedDataByDate(date);
			
			for (PC_Book b : books) {
				deleteBookData(b);
			}
			
			TransactionController.getTransactionController().addTransaction(books, AuthController.getAuthController().currentUser.getUserID());
				
			
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	public void deleteBookData(PC_Book book) {
		book.deleteBookData(book.getBookID());
	}
	
	public boolean cancelBook(LocalDate date) {
		boolean v = false;
		Alert prompt = validateCancel(date);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			List<PC_Book> books = getPCBookedDataByDate(date);
			
			for (PC_Book b : books) {
				deleteBookData(b);
			}
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	public boolean assignUserToNewPC(String bookID, String newPCID) {
		boolean v = false;
		Alert prompt = validateAssign(bookID, newPCID);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			PC_Book pb = getPCBookedData(Integer.parseInt(bookID));
			pb.assignUserToNewPC(bookID, newPCID);
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	private Alert validateAssign(String bookID, String newPCID) {
		Alert prompt;

		if(bookID.isEmpty() || bookID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Book ID must be chosen", ButtonType.OK);
		}else if(newPCID.isEmpty() || newPCID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "New PC ID must be chosen", ButtonType.OK);
		}else if(getPCDetail(Integer.parseInt(newPCID)) == null) {
			prompt = new Alert(AlertType.ERROR, "PC ID not found", ButtonType.OK);
		}else if(!getPCDetail(Integer.parseInt(newPCID)).getPC_Condition().equals("Usable")) {
			prompt = new Alert(AlertType.ERROR, "PC is not usable", ButtonType.OK);
		}else if(getPCBookedData(Integer.parseInt(newPCID), getPCBookedData(Integer.parseInt(bookID)).getBookedDate()) != null) {
			prompt = new Alert(AlertType.ERROR, "PC is already booked", ButtonType.OK);
		}else {
			prompt = new Alert(AlertType.INFORMATION, "Assign Success");
		}	
		return prompt;
	}

	public boolean addNewBook(String PC_ID, int userID, LocalDate bookedDate) {
		boolean v = false;
		Alert prompt = validateBook(PC_ID, userID, bookedDate);
		
		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
			PC_Book pc = new PC_Book(Integer.parseInt(PC_ID), userID, bookedDate);
			pc.addNewBook(Integer.parseInt(PC_ID), userID, bookedDate);
			v = true;
		}
		
		prompt.showAndWait();
		
		return v;
	}
	
	public void addJobBook(String PC_ID, int userID, LocalDate bookedDate) {
		PC_Book pc = new PC_Book(Integer.parseInt(PC_ID), userID, bookedDate);
		pc.addNewBook(Integer.parseInt(PC_ID), userID, bookedDate);

	}
	
	private Alert validateFinish(LocalDate date) {
		Alert prompt;

		if(date == null) {
			prompt = new Alert(AlertType.ERROR, "Date must be chosen", ButtonType.OK);
		}else if (date.isAfter(LocalDate.now())) {
			prompt = new Alert(AlertType.ERROR, "Booking date has not passed yet", ButtonType.OK);
	    }else {
			prompt = new Alert(AlertType.INFORMATION, "Finish Success");
		}	
		return prompt;
	}
	
	private Alert validateCancel(LocalDate date) {
		Alert prompt;

		if(date == null) {
			prompt = new Alert(AlertType.ERROR, "Date must be chosen", ButtonType.OK);
		}else if (date.isBefore(LocalDate.now())) {
			prompt = new Alert(AlertType.ERROR, "Booking date already passed", ButtonType.OK);
	    }else {
			prompt = new Alert(AlertType.INFORMATION, "Success");
		}	
		return prompt;
	}

	private Alert validateBook(String PC_ID, int userID, LocalDate bookedDate) {
		Alert prompt;

		if(bookedDate == null) {
			prompt = new Alert(AlertType.ERROR, "Date Must be chosen", ButtonType.OK);
		}else if(bookedDate.isBefore(LocalDate.now()) || bookedDate.isEqual(LocalDate.now())) {
			prompt = new Alert(AlertType.ERROR, "Booking date must be in the future", ButtonType.OK);
		}else if(PC_ID.isEmpty() || PC_ID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "PC_ID must be chosen", ButtonType.OK);
		}else if(getPCDetail(Integer.parseInt(PC_ID)) == null) {
			prompt = new Alert(AlertType.ERROR, "PC ID not found", ButtonType.OK);
		}else if(!getPCDetail(Integer.parseInt(PC_ID)).getPC_Condition().equals("Usable") && AuthController.getAuthController().currentUser.getUserRole().equals("Customer")) {
			prompt = new Alert(AlertType.ERROR, "PC is not usable", ButtonType.OK);
		}else if(getPCBookedData(Integer.parseInt(PC_ID), bookedDate) != null) {
			prompt = new Alert(AlertType.ERROR, "PC is already booked", ButtonType.OK);
		}else {
			prompt = new Alert(AlertType.INFORMATION, "Book Success");
		}	
		return prompt;
	}
	
	
}
