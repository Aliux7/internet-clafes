package Model;

import java.time.LocalDate;

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

	public int getCustomerName() {
		return customerID;
	}

	public void setCustomerName(int customerID) {
		this.customerID = customerID;
	}

	public LocalDate getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(LocalDate bookedTime) {
		this.bookedTime = bookedTime;
	}

}
