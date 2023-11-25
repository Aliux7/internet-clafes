package Model;

import java.time.LocalDate;
import java.util.List;

import Controller.PC_BookController;
import Controller.PC_Controller;
import Repository.PC_BookDAO;

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

	public PC_Book() {
		// TODO Auto-generated constructor stub
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

	public List<PC_Book> getAllPCBookedData() {
		return PC_BookDAO.getPC_BookDAO().findAll();
	}

	public PC_Book getPCBookedData(int pcID, LocalDate date) {
		return PC_BookDAO.getPC_BookDAO().select(pcID, date);
	}
	
	public void addNewBook(int PC_ID, int userID, LocalDate bookedDate) {
		PC_BookDAO.getPC_BookDAO().save(this);
	}

	public PC_Book getPCBookedData(int pcID, int userID) {
		return PC_BookDAO.getPC_BookDAO().select(pcID, userID);
	}
	
	public void deleteBookData(int bookID) {
		PC_BookDAO.getPC_BookDAO().delete(this);
	}

	public List<PC_Book> getPCBookedDataByDate(LocalDate date) {
		return PC_BookDAO.getPC_BookDAO().getPCBookedDataByDate(date);
	}

	public PC_Book getPCBookedData(int bookID) {
		return PC_BookDAO.getPC_BookDAO().select(bookID);
	}

	public void assignUserToNewPC(String bookID, String newPCID) {
		this.setPC_ID(Integer.parseInt(newPCID));
		PC_BookDAO.getPC_BookDAO().update(this);
	}
}
