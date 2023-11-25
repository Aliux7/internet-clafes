package Model;

import java.util.List;

import Controller.PC_Controller;
import Repository.PC_DAO;

public class PC {

	private int PC_ID;
	private String PC_Condition;
	
	
	public PC(int pC_ID, String pC_Condition) {
		super();
		PC_ID = pC_ID;
		PC_Condition = pC_Condition;
	}
	
	public PC() {

	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public String getPC_Condition() {
		return PC_Condition;
	}

	public void setPC_Condition(String pC_Condition) {
		PC_Condition = pC_Condition;
	}

	public void addNewPC(int PC_ID) {
		PC_DAO.getPCDAO().save(this);
	}

	public void updatePCCondition(PC pc) {
		PC_DAO.getPCDAO().update(pc);
	}
	
	public void deletePC(PC pc) {
		PC_DAO.getPCDAO().delete(pc);
	}

	public PC getPCDetail(int PC_ID) {
		return PC_DAO.getPCDAO().select(PC_ID);
	}

	public List<PC> getAllPCData() {
		return PC_DAO.getPCDAO().findAll();
	}
}
