package Controller;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import Model.PC_Book;
import Model.User;
import Repository.PC_BookDAO;

public class PC_BookController {
	private volatile static PC_BookController instance;
	private PC_BookController() {}
	private PC_BookDAO dao = new PC_BookDAO();

	public static PC_BookController getPC_BookController() {
		if(instance == null) {
			synchronized (PC_BookController.class) {
				if(instance == null) instance = new PC_BookController();
			}
		}
		return instance;
	}
	
	public List<PC_Book> findAll(){
		return dao.findAll();
	}
	
//	public PC_Book find(int id) {
//		return dao.select(id);
//	}
	
	public void create(int PC_ID, int userID, LocalDate bookedDate) {
		Alert prompt = validateBook(PC_ID, userID, bookedDate);		
		if (prompt.getAlertType().equals(AlertType.ERROR)) {
			prompt.showAndWait();
			return;
		}

		if (prompt.getAlertType().equals(AlertType.INFORMATION)) {
			PC_Book pc = new PC_Book(PC_ID, userID, bookedDate);
			dao.save(pc);		
			prompt.showAndWait();
			return;
		}
	}
	
	private Alert validateBook(int PC_ID, int userID, LocalDate bookedDate) {
		Alert prompt;

		if(bookedDate == null) {
			prompt = new Alert(AlertType.ERROR, "Date Must be choosen", ButtonType.OK);
		}else if(bookedDate.isBefore(LocalDate.now())) {
			prompt = new Alert(AlertType.ERROR, "Booking date must be in the future", ButtonType.OK);
		}else if(PC_ID <= 0) {
			prompt = new Alert(AlertType.ERROR, "PC_ID must be choosen", ButtonType.OK);
		}else {
			prompt = new Alert(AlertType.INFORMATION, "Sucess");
		}	
		return prompt;
	}
	
	public void update(String supplementID, String supplementName, LocalDate supplementExpiryDate, int supplementPrice, int supplementStock, String supplementTypeID) {
//		Alert prompt = validate(supplementID, supplementName, supplementExpiryDate, supplementPrice, supplementStock, supplementTypeID);
//		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
//			Supplement supplement = find(supplementID);
//			supplement.setSupplementName(supplementName);
//			supplement.setSupplementExpiryDate(supplementExpiryDate);
//			supplement.setSupplementPrice(supplementPrice);
//			supplement.setSupplementStock(supplementStock);
//			supplement.setSupplementTypeID(supplementTypeID);
//			dao.update(supplement);
//		}
//		prompt.showAndWait();
	}
	
	public void delete(String supplementID) {
//		Alert prompt = validate(supplementID);
//		if(prompt.getAlertType().equals(AlertType.INFORMATION)){
//			Supplement supplement = find(supplementID);
//			dao.delete(supplement);
//		}
//		prompt.showAndWait();
	}
	
}
