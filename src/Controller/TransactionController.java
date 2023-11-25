package Controller;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
//import Model.Cart;
import Model.TransactionHeader;
import Model.User;
import Model.PC_Book;
import Model.TransactionDetail;
import Repository.TransactionDAO;
import Repository.TransactionDetailDAO;

public class TransactionController {
	private volatile static TransactionController instance;
	private TransactionController() {}
	
	private TransactionHeader th = new TransactionHeader();
	private TransactionDetail td = new TransactionDetail();

	public static TransactionController getTransactionController() {
		if(instance == null) {
			synchronized (TransactionController.class) {
				if(instance == null) instance = new TransactionController();
			}
		}
		return instance;
	}
	
	public void addTransaction(List<PC_Book> books, int staffID) {
		User u = UserController.getUserController().getUserDetail(staffID);
		
		TransactionHeader h = new TransactionHeader(staffID, u.getUserName(), LocalDate.now());
		int tid = h.addNewTransactionHeader(books, staffID);
		
		td.addTransactionDetail(tid, books);
	}
	
	public List<TransactionHeader> getAllTransactionHeaderData(){
		return th.getAllTransactionHeaderData();
	}
	
	public List<TransactionDetail> getAllTransactionDetail(int transactionID){
		return td.getAllTransactionDetail(transactionID);
	}
	
	public List<TransactionDetail> getUserTransactionDetail(int userID){
		return td.getUserTransactionDetail(userID);
	}

	
}
