package View;

import java.util.List;

//import Component.AdminNavbar;
import Component.ComponentMaker;
import Component.CustomerNavbar;
import Controller.AuthController;
import Controller.TransactionController;
//import Controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.window.Window;
import Model.TransactionDetail;
import Model.User;

public class TransactionPage extends VBox implements ComponentMaker{

	private ObservableList<TransactionDetail> transactionData;
	private TableView<TransactionDetail> transactionTable;

	private Pane internalPane;

	private Window window;
	private VBox root;

	private void createTransactionTable() {
		User currentUser = AuthController.getAuthController().getCurrentUser();
		
		int userID = currentUser.getUserID();
		List<TransactionDetail> data = null;
		if(currentUser.getUserRole().equals("Customer")) {
			data = TransactionController.getTransactionController().findAll(userID);	
			root.getChildren().add(new CustomerNavbar());
		}
		else {
//			data = TransactionController.getTransactionController().findAll();	
//			root.getChildren().add(new AdminNavbar());
		}
		transactionData = FXCollections.observableList(data);
		transactionTable = new TableView<TransactionDetail>();
		transactionTable.setItems(transactionData);
		transactionTable.setPrefWidth(SCENE_WIDTH*0.5);
		transactionTable.setMaxWidth(SCENE_WIDTH*0.5);
		
		internalPane = new Pane();

		TableColumn<TransactionDetail, Integer>  c1 = new TableColumn<>("TransactionID");
		c1.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
//		c1.setPrefWidth(100);
		TableColumn<TransactionDetail, Integer>  c2 = new TableColumn<>("PC_ID");
		c2.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
//		c2.setPrefWidth(100);
		TableColumn<TransactionDetail, Integer>  c3 = new TableColumn<>("BookedTime");
		c3.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));
//		c3.setPrefWidth(100);
		transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		transactionTable.getColumns().setAll(c1, c2, c3);
		transactionTable.setPrefHeight(340);
		transactionTable.setMaxHeight(340);
		transactionTable.setPrefWidth(500);
		transactionTable.setMaxWidth(500);
		
	}

	private void crateTransactionPage() {
		root = new VBox(10);
		createTransactionTable();
		this.setBackground(WHITE_BACKGROUND);
		root.getChildren().addAll(transactionTable, internalPane);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
	}

	public TransactionPage() {
		crateTransactionPage();
	}
}


