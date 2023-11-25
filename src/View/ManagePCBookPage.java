package View;

import java.time.LocalDate;
import java.util.List;
import java.util.function.UnaryOperator;

import Component.AdminNavbar;
import Component.ComponentMaker;
import Component.NavigationBar;
import Component.OperatorNavbar;
import Component.TechnicianNavbar;
import Controller.AuthController;
import Controller.JobController;
import Controller.PC_BookController;
import Controller.PC_Controller;
import Controller.UserController;
import Model.Job;
import Model.PC;
import Model.PC_Book;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ManagePCBookPage extends VBox implements ComponentMaker{

	private FlowPane buttonPane;
	private GridPane bookPane;
	private VBox root;
	private ObservableList<PC_Book> bookOL;
	private TableView<PC_Book> bookTable;
	private TextField bookIdField, pcIdField;
	
	@SuppressWarnings("unchecked")
	private void createJobTable() {
		List<PC_Book> data = null;
		
		data = PC_BookController.getPC_BookController().getAllPCBookedData();
		
		root.getChildren().add(new OperatorNavbar());
		
		
		if(data != null) {
			bookOL = FXCollections.observableList(data);			
		}
		bookTable = new TableView<PC_Book>();
		bookTable.setItems(bookOL);
		bookTable.setPrefWidth(SCENE_WIDTH*0.5);
		bookTable.setMaxWidth(SCENE_WIDTH*0.5);

		TableColumn<PC_Book, Integer> bookIDColumn = new TableColumn<>("Book ID");
		bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));

		TableColumn<PC_Book, Integer> pcIDColumn = new TableColumn<>("PC ID");
		pcIDColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

		TableColumn<PC_Book, Integer> userIDColumn = new TableColumn<>("User ID");
		userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

		TableColumn<PC_Book, LocalDate> bookedDateColumn = new TableColumn<>("Booked Date");
		bookedDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));

		bookTable.getColumns().addAll(bookIDColumn, pcIDColumn, userIDColumn, bookedDateColumn);
		
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bookTable.setPrefHeight(700);
        bookTable.setMaxHeight(700);
        bookTable.setPrefWidth(500);
        bookTable.setMaxWidth(500);
		
	}
	
	private void refreshTable() {
		List<PC_Book> updatedData = null;
		
		updatedData = PC_BookController.getPC_BookController().getAllPCBookedData();

		bookOL.clear();
		bookOL.addAll(updatedData);

	    bookTable.refresh();
	}

	
	private void createForm() {
		bookPane = new GridPane();
	    Label titleLbl = createLabel(PRIMARY, "Manage PC Book", "Arial", true, 32);
	    bookPane.setHgap(10);
	    bookPane.setVgap(10); 
	    bookPane.setPadding(new Insets(10));

	    Label dateLbl = createLabel(PRIMARY, "Date:" ,"Arial", true, 14);
	    Label bookIdLabel = createLabel(PRIMARY, "Book ID:", "Arial", true, 14);
	    Label pcIdLabel = createLabel(PRIMARY, "PC ID:", "Arial", true, 14);

	    bookIdField = new TextField();
	    bookIdField.setMaxWidth(200);
	    
	    pcIdField = new TextField();
	    pcIdField.setMaxWidth(200);
	    
	    UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change; 
            }

            return null; 
        };
        
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        
        pcIdField.setTextFormatter(textFormatter);
        bookIdField.setTextFormatter(textFormatter2);
	    
	    DatePicker datePicker = new DatePicker();
	    datePicker.setMaxWidth(200);
	    datePicker.setPrefWidth(200);

	    Button cancelBtn = createButton("Cancel Book", 100, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    cancelBtn.setOnAction(e -> {
	    	boolean c = PC_BookController.getPC_BookController().cancelBook(datePicker.getValue());
	    	
	    	if(c) {
	    		datePicker.setValue(null);
	    		bookIdField.setText("");
	    		pcIdField.setText("");
	    		refreshTable();
	    	}
	    });
	    
	    Button finishBtn = createButton("Finish Book", 100, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    finishBtn.setOnAction(e -> {
	    	boolean c = PC_BookController.getPC_BookController().finishBook(datePicker.getValue());
	    	
	    	if(c) {
	    		datePicker.setValue(null);
	    		bookIdField.setText("");
	    		pcIdField.setText("");
	    		refreshTable();
	    	}
	    });
	    
	    Button assignBtn = createButton("Assign to New PC", 150, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    assignBtn.setOnAction(e -> {
	    	String bookID = bookIdField.getText();
	    	String pcID = pcIdField.getText();
	    	
	    	boolean c = PC_BookController.getPC_BookController().assignUserToNewPC(bookID, pcID);
	    	
	    	if(c) {
	    		datePicker.setValue(null);
	    		bookIdField.setText("");
	    		pcIdField.setText("");
	    		refreshTable();
	    	}
	    });
	    
	    bookTable.setOnMouseClicked(e -> {
		 	TableSelectionModel<PC_Book> select = bookTable.getSelectionModel();
		 	select.setSelectionMode(SelectionMode.SINGLE);
		 	PC_Book j = select.getSelectedItem();
		 	
		 	if (j != null) {
		 		datePicker.setValue(j.getBookedDate());
		 		bookIdField.setText(Integer.toString(j.getBookID()));
		 		pcIdField.setText(Integer.toString(j.getPC_ID()));
		 	}
		 	 	
	    });


	    bookPane.add(titleLbl, 0, 0, 2, 1);
	    bookPane.add(dateLbl, 0, 1);
	    bookPane.add(datePicker, 1, 1);
	    bookPane.add(bookIdLabel, 0, 2);
	    bookPane.add(bookIdField, 1, 2);
	    bookPane.add(pcIdLabel, 0, 3);
	    bookPane.add(pcIdField, 1, 3);
	    
	    buttonPane = new FlowPane();
	    buttonPane.setAlignment(Pos.TOP_LEFT);
	    buttonPane.setHgap(20);
	    
	    buttonPane.getChildren().addAll(cancelBtn, finishBtn,assignBtn);
 
	    bookPane.add(buttonPane, 0, 4, 2, 1); 

	    bookPane.setAlignment(Pos.CENTER);
	    bookPane.setBackground(WHITE_BACKGROUND);
	    bookPane.setPrefWidth(SCENE_WIDTH * 0.4);
	    bookPane.setPrefHeight(SCENE_HEIGHT);
	}

	private void createManagePCBookPage() {
		root = new VBox(10);
		
		createJobTable();
		createForm();			
		
		this.setBackground(WHITE_BACKGROUND);
		
		root.getChildren().addAll(bookTable, bookPane);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
	}


	public ManagePCBookPage() {
		createManagePCBookPage();
	}

}
