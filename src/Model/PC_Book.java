package Model;

import java.time.LocalDate;

public class PC_Book {

	private int bookID, PC_ID, userID;
	private LocalDate bookedDate;
	

	public PC_Book(int bookID, int pC_ID, int userID, LocalDate bookedDate) {
		super();
		this.bookID = bookID;
		this.PC_ID = pC_ID;
		this.userID = userID;
		this.bookedDate = bookedDate;
	}
	
	public PC_Book(int pC_ID, int userID, LocalDate bookedDate) {
		super();
		this.PC_ID = pC_ID;
		this.userID = userID;
		this.bookedDate = bookedDate;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public LocalDate getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDate bookedDate) {
		this.bookedDate = bookedDate;
	}
}
