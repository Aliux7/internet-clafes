package Controller;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import Model.PC;
import Repository.PC_DAO;

public class PC_Controller {
	private volatile static PC_Controller instance;
	private PC_Controller() {}
	private PC_DAO dao = new PC_DAO();

	public static PC_Controller getPC_Controller() {
		if(instance == null) {
			synchronized (PC_Controller.class) {
				if(instance == null) instance = new PC_Controller();
			}
		}
		return instance;
	}
	
	public List<PC> findAll(){
		return dao.findAll();
	}
	
	public PC find(int id) {
		return dao.select(id);
	}
	
	public void create(int PC_ID, String PC_Condition) {
		PC pc = new PC(1, "Usable");
		dao.save(pc);
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
	
	private Alert validate(String supplementName, LocalDate supplementExpiryDate, int supplementPrice, int supplementStock, String supplementTypeID) {
		Alert prompt;
		if (supplementName.isEmpty() || supplementName.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Supplement name is empty!", ButtonType.OK);
		}
		else if (supplementExpiryDate == null) {
			prompt = new Alert(AlertType.ERROR, "Supplement expiry date is empty!", ButtonType.OK);
		}
		else if (supplementPrice <= 0 ) {
			prompt = new Alert(AlertType.ERROR, "Supplement price is empty!", ButtonType.OK);
		}
		else if (supplementStock <= 0) {
			prompt = new Alert(AlertType.ERROR, "Supplement stock is empty!", ButtonType.OK);
		}
		else if (supplementTypeID.isEmpty() || supplementTypeID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Supplement type id is empty!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "Product Added!", ButtonType.OK);
		}
		
		return prompt;
	}
	
	private Alert validate(String supplementID, String supplementName, LocalDate supplementExpiryDate, int supplementPrice, int supplementStock, String supplementTypeID) {
		Alert prompt;
		if (supplementID.isEmpty() || supplementID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Supplement id is empty!", ButtonType.OK);
		}
		else if (supplementName.isEmpty() || supplementName.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Supplement name is empty!", ButtonType.OK);
		}
		else if (supplementExpiryDate == null) {
			prompt = new Alert(AlertType.ERROR, "Supplement expiry date is empty!", ButtonType.OK);
		}
		else if (supplementPrice <= 0 ) {
			prompt = new Alert(AlertType.ERROR, "Supplement price is empty!", ButtonType.OK);
		}
		else if (supplementStock <= 0) {
			prompt = new Alert(AlertType.ERROR, "Supplement stock is empty!", ButtonType.OK);
		}
		else if (supplementTypeID.isEmpty() || supplementTypeID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Supplement type id is empty!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "Product Updated!", ButtonType.OK);
		}
		
		return prompt;
	}
	
	private Alert validate(String supplementID) {
		Alert prompt;
		if (supplementID.isEmpty() || supplementID.equals("")) {
			prompt = new Alert(AlertType.ERROR, "Supplement id is empty!", ButtonType.OK);
		}
		else {
			prompt = new Alert(AlertType.INFORMATION, "Product Deleted!", ButtonType.OK);
		}
		
		return prompt;
	}
}
