package View;

import java.time.LocalDate;
import java.util.List;

import Component.AdminNavbar;
//import Component.AdminNavbar;
import Component.ComponentMaker;
import Component.CustomerNavbar;
import Controller.AuthController;
import Controller.TransactionController;
//import Controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Model.TransactionDetail;
import Model.TransactionHeader;
import Model.User;

public class TransactionPage extends VBox implements ComponentMaker{

	private ObservableList<TransactionDetail> transactionData;
	private TableView<TransactionDetail> transactionTable;
	
	private ObservableList<TransactionHeader> transactionHData;
	private TableView<TransactionHeader> transactionHTable;

	private VBox root;
	private HBox con;

	@SuppressWarnings("unchecked")
	private void createTransactionTable() {
        User currentUser = AuthController.getAuthController().getCurrentUser();

        int userID = currentUser.getUserID();

        if (currentUser.getUserRole().equals("Customer")) {
            List<TransactionDetail> data = TransactionController.getTransactionController().getUserTransactionDetail(userID);
            root.getChildren().add(new CustomerNavbar());

            if(data != null) {
            	transactionData = FXCollections.observableList(data);            	
            }
            transactionTable = new TableView<>();
            transactionTable.setItems(transactionData);
            transactionTable.setPrefWidth(SCENE_WIDTH * 0.5);
            transactionTable.setMaxWidth(SCENE_WIDTH * 0.5);

            TableColumn<TransactionDetail, Integer> c1 = new TableColumn<>("TransactionID");
            c1.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

            TableColumn<TransactionDetail, Integer> c2 = new TableColumn<>("PC_ID");
            c2.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

            TableColumn<TransactionDetail, Integer> c3 = new TableColumn<>("BookedTime");
            c3.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));

            transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            transactionTable.getColumns().setAll(c1, c2, c3);
            root.getChildren().addAll(transactionTable);
        } else {
        	con = new HBox();
            root.getChildren().add(new AdminNavbar());
            
            Label headerTableLabel = new Label("Transaction Header Table");
            headerTableLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
            
            Label detailTableLabel = new Label("Transaction Detail Table");
            detailTableLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
            
            VBox th = new VBox();
            VBox td = new VBox();
            th.setAlignment(Pos.CENTER);
            td.setAlignment(Pos.CENTER);

            List<TransactionHeader> headerData = TransactionController.getTransactionController().getAllTransactionHeaderData();
            transactionHData = FXCollections.observableList(headerData);
            transactionHTable = new TableView<>();
            transactionHTable.setItems(transactionHData);
            transactionHTable.setPrefWidth(SCENE_WIDTH * 0.4);
            transactionHTable.setMaxWidth(SCENE_WIDTH * 0.4);

            double columnWidth = SCENE_WIDTH * 0.4 / 4; 

            TableColumn<TransactionHeader, Integer> headerIdColumn = new TableColumn<>("Header ID");
            headerIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
            headerIdColumn.setPrefWidth(columnWidth);

            TableColumn<TransactionHeader, Integer> staffIdColumn = new TableColumn<>("Staff ID");
            staffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffID"));
            staffIdColumn.setPrefWidth(columnWidth);

            TableColumn<TransactionHeader, String> staffNameColumn = new TableColumn<>("Staff Name");
            staffNameColumn.setCellValueFactory(new PropertyValueFactory<>("staffName"));
            staffNameColumn.setPrefWidth(columnWidth);

            TableColumn<TransactionHeader, LocalDate> transactionDateColumn = new TableColumn<>("Transaction Date");
            transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
            transactionDateColumn.setPrefWidth(columnWidth);

            transactionHTable.getColumns().addAll(headerIdColumn, staffIdColumn, staffNameColumn, transactionDateColumn);

            transactionHTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    int selectedHeaderId = newValue.getTransactionID();
                    List<TransactionDetail> detailData = TransactionController.getTransactionController().getAllTransactionDetail(selectedHeaderId);
                    transactionData = FXCollections.observableList(detailData);
                    transactionTable.setItems(transactionData);
                }
            });
            
            transactionTable = new TableView<>();
            transactionTable.setItems(transactionData);
            transactionTable.setPrefWidth(SCENE_WIDTH * 0.4);
            transactionTable.setMaxWidth(SCENE_WIDTH * 0.4);

            TableColumn<TransactionDetail, Integer> c1 = new TableColumn<>("TransactionID");
            c1.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

            TableColumn<TransactionDetail, Integer> c2 = new TableColumn<>("PC_ID");
            c2.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
            
            TableColumn<TransactionDetail, Integer> c3 = new TableColumn<>("UserID");
            c3.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            TableColumn<TransactionDetail, Integer> c4 = new TableColumn<>("BookedTime");
            c4.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));

            transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            transactionTable.getColumns().setAll(c1, c2, c3, c4);
            
            th.getChildren().addAll(headerTableLabel, transactionHTable);
            td.getChildren().addAll(detailTableLabel, transactionTable);
            
            con.getChildren().addAll(th, td);
            con.setAlignment(Pos.CENTER);
            con.setSpacing(20);
            root.getChildren().add(con);

        }
    }

	private void crateTransactionPage() {
		root = new VBox(10);
		createTransactionTable();
		this.setBackground(WHITE_BACKGROUND);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
	}

	public TransactionPage() {
		crateTransactionPage();
	}
}


