package View;

import java.time.LocalDate;
import java.util.List;
import java.util.function.UnaryOperator;

import Component.AdminNavbar;
import Component.ComponentMaker;
import Component.CustomerNavbar;
import Controller.AuthController;
import Controller.PC_BookController;
import Controller.PC_Controller;
import Controller.UserController;
import Model.PC;
import Model.TransactionDetail;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class ManageStaffPage extends VBox implements ComponentMaker {

	private FlowPane pcPane, buttonPane;
	private GridPane bookPane;
	private VBox root;
	private TextField staffIdField;
	private ObservableList<User> userOL;
	private TableView<User> userTable;
	
	ObservableList<String> roleOptions = FXCollections.observableArrayList(
            "Admin",
            "Computer Technician",
            "Operator",
            "Customer"
    );
    ComboBox<String> roleComboBox = new ComboBox<>(roleOptions);

	
	@SuppressWarnings("unchecked")
	private void createStaffTable() {
		List<User> data = null;
		
		data = UserController.getUserController().getAllStaff();
		root.getChildren().add(new AdminNavbar());
		
		userOL = FXCollections.observableList(data);
		userTable = new TableView<User>();
		userTable.setItems(userOL);
		userTable.setPrefWidth(SCENE_WIDTH*0.5);
		userTable.setMaxWidth(SCENE_WIDTH*0.5);

		TableColumn<User, Integer> idColumn = new TableColumn<>("User ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<User, String> nameColumn = new TableColumn<>("User Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<User, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("userGender"));

        TableColumn<User, LocalDate> dobColumn = new TableColumn<>("Date of Birth");
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("userDOB"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        
        roleColumn.setMinWidth(75);

        userTable.getColumns().addAll(idColumn, nameColumn, genderColumn, dobColumn, roleColumn);
		
		userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		userTable.setPrefHeight(400);
		userTable.setMaxHeight(400);
		userTable.setPrefWidth(500);
		userTable.setMaxWidth(600);
		
	}
	
	private void refreshTable() {
	    List<User> updatedData = UserController.getUserController().getAllStaff();

	    userOL.clear();
	    userOL.addAll(updatedData);

	    userTable.refresh();
	}

	
	private void createForm() {
		bookPane = new GridPane();
	    Label titleLbl = createLabel(PRIMARY, "Manage Staff", "Arial", true, 32);
	    bookPane.setHgap(10);
	    bookPane.setVgap(10); 
	    bookPane.setPadding(new Insets(10));

	    Label pcStatusLabel = createLabel(PRIMARY, "Staff Role:", "Arial", true, 14);
	    Label pcIdLabel = createLabel(PRIMARY, "Staff ID:", "Arial", true, 14);

        roleComboBox.setValue("Admin");
        
        staffIdField = new TextField();
        staffIdField.setPrefWidth(200);
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change; 
            }

            return null; 
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        staffIdField.setTextFormatter(textFormatter);

	    roleComboBox.setPrefWidth(200);

	    Button changeRoleBtn = createButton("Change Staff Role", 200, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    changeRoleBtn.setOnAction(event -> {	    	
	    	String staffId = staffIdField.getText().toString();
	    	String role = roleComboBox.getValue();
	    	boolean check = UserController.getUserController().changeUserRole(staffId, role);
	    	
	    	if(check) {
	    		refreshTable();
	    		roleComboBox.setValue(null);
	    		staffIdField.setText("");	    		
	    	}
	    	
	    });
	    
	    userTable.setOnMouseClicked(e -> {
		 	TableSelectionModel<User> select = userTable.getSelectionModel();
		 	select.setSelectionMode(SelectionMode.SINGLE);
		 	User u = select.getSelectedItem();
		 	
		 	if (u != null) {
		 		staffIdField.setText(Integer.toString(u.getUserID()));
		 		roleComboBox.setValue(u.getUserRole());		 		
		 	}
		 	 	
	    });

	    bookPane.add(titleLbl, 0, 0);
	    bookPane.add(pcStatusLabel, 0, 1);
	    bookPane.add(roleComboBox, 1, 1); 
	    bookPane.add(pcIdLabel, 0, 2); 
	    bookPane.add(staffIdField, 1, 2);
	    
	    buttonPane = new FlowPane();
	    buttonPane.setAlignment(Pos.CENTER);
	    
	    buttonPane.getChildren().add(changeRoleBtn);
 
	    bookPane.add(buttonPane, 0, 3, 2, 1); 

	    bookPane.setAlignment(Pos.CENTER);
	    bookPane.setBackground(WHITE_BACKGROUND);
	    bookPane.setPrefWidth(SCENE_WIDTH * 0.4);
	    bookPane.setPrefHeight(SCENE_HEIGHT);
	}

	private void createStaffPage() {
		root = new VBox(10);
		createStaffTable();
		createForm();
		
		this.setBackground(WHITE_BACKGROUND);
		
		root.getChildren().addAll(userTable, bookPane);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
	}


	public ManageStaffPage() {
		createStaffPage();
	}
}
