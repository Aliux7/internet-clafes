package Model;

import java.time.LocalDate;
import java.util.List;

import Repository.TransactionDetailDAO;

public class TransactionDetail {

	private int transactionID, PC_ID, customerID;
	private LocalDate bookedTime;
	
	public TransactionDetail(int transactionID, int pC_ID, int customerID, LocalDate bookedTime) {
		super();
		this.transactionID = transactionID;
		PC_ID = pC_ID;
		this.customerID = customerID;
		this.bookedTime = bookedTime;
	}

	public TransactionDetail() {
		// TODO Auto-generated constructor stub
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public LocalDate getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(LocalDate bookedTime) {
		this.bookedTime = bookedTime;
	}

	public void addTransactionDetail(int tid, List<PC_Book> books) {
		for (PC_Book b : books) {
			TransactionDetail td = new TransactionDetail(tid, b.getPC_ID(), b.getUserID(), b.getBookedDate());
			TransactionDetailDAO.getTransactionDetailDAO().save(td);
		}
		
	}

	public List<TransactionDetail> getAllTransactionDetail(int transactionID) {
		return TransactionDetailDAO.getTransactionDetailDAO().findAllByID(transactionID);
	}

	public List<TransactionDetail> getUserTransactionDetail(int userID) {
		return TransactionDetailDAO.getTransactionDetailDAO().findAllByUser(userID);
	}

}
