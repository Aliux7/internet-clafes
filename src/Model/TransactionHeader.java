package Model;

import java.time.LocalDate;

public class TransactionHeader {

	private int transactionID, staffID;
	private String staffName;
	private LocalDate transactionDate;
	
	public TransactionHeader(int transactionID, int staffID, String staffName, LocalDate transactionDate) {
		super();
		this.transactionID = transactionID;
		this.staffID = staffID;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}
	
	public TransactionHeader(int staffID, LocalDate transactionDate) {
		super();
		this.staffID = staffID;
		this.transactionDate = transactionDate;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getStaffID() {
		return staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

}
