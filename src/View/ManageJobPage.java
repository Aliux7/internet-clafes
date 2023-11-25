package View;

import java.time.LocalDate;
import java.util.List;
import java.util.function.UnaryOperator;

import Component.AdminNavbar;
import Component.ComponentMaker;
import Component.NavigationBar;
import Component.TechnicianNavbar;
import Controller.AuthController;
import Controller.JobController;
import Controller.PC_Controller;
import Controller.UserController;
import Model.Job;
import Model.PC;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class ManageJobPage extends VBox implements ComponentMaker{

	private FlowPane pcPane, buttonPane;
	private GridPane bookPane;
	private VBox root;
	private TextField staffIdField, jobIdField, pcIdField;
	private ObservableList<Job> jobOL;
	private TableView<Job> jobTable;
	
	ObservableList<String> statusOptions = FXCollections.observableArrayList(
            "Complete",
            "Uncomplete"
    );
    ComboBox<String> statusComboBox = new ComboBox<>(statusOptions);

	
	@SuppressWarnings("unchecked")
	private void createJobTable() {
		List<Job> data = null;
		
		User u = AuthController.getAuthController().currentUser;
		
		if(u.getUserRole().equals("Admin")) {
			data = JobController.getJobController().getAllJobData();
			root.getChildren().add(new AdminNavbar());
		}else {
			data = JobController.getJobController().getTechnicianJob(u.getUserID());
			root.getChildren().add(new TechnicianNavbar());
		}
		
		
		
		if(data != null) {
			jobOL = FXCollections.observableList(data);			
		}
		jobTable = new TableView<Job>();
		jobTable.setItems(jobOL);
		jobTable.setPrefWidth(SCENE_WIDTH*0.5);
		jobTable.setMaxWidth(SCENE_WIDTH*0.5);

		TableColumn<Job, Integer> idColumn = new TableColumn<>("Job ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("jobID"));

        TableColumn<Job, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<Job, Integer> pcIdColumn = new TableColumn<>("PC ID");
        pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
        
        TableColumn<Job, String> pcStatusColumn = new TableColumn<>("PC Condition");
        pcStatusColumn.setCellValueFactory(d -> {
            Job job = d.getValue();
            int id = job.getPC_ID();
            
            PC pc = PC_Controller.getPC_Controller().getPCDetail(id);
            String pcCondition = pc.getPC_Condition();
            return new SimpleStringProperty(pcCondition);
        });


        TableColumn<Job, String> statusColumn = new TableColumn<>("Job Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("jobStatus"));

        jobTable.getColumns().addAll(idColumn, userIdColumn, pcIdColumn, pcStatusColumn, statusColumn);
		
        jobTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        jobTable.setPrefHeight(400);
        jobTable.setMaxHeight(400);
        jobTable.setPrefWidth(500);
        jobTable.setMaxWidth(500);
		
	}
	
	private void refreshTable() {
		List<Job> updatedData = null;
		
		User u = AuthController.getAuthController().currentUser;
		
		if(u.getUserRole().equals("Admin")) {
		    updatedData = JobController.getJobController().getAllJobData();			
		}else {
			updatedData = JobController.getJobController().getTechnicianJob(u.getUserID());
		}

	    jobOL.clear();
	    jobOL.addAll(updatedData);

	    jobTable.refresh();
	}

	
	private void createForm() {
		User u = AuthController.getAuthController().currentUser;
		
		bookPane = new GridPane();
	    Label titleLbl = createLabel(PRIMARY, "Manage Job", "Arial", true, 32);
	    bookPane.setHgap(10);
	    bookPane.setVgap(10); 
	    bookPane.setPadding(new Insets(10));

	    Label jobIdLabel = createLabel(PRIMARY, "Job ID:", "Arial", true, 14);
	    Label userIdLabel = createLabel(PRIMARY, "Staff ID:", "Arial", true, 14);
	    Label pcIdLabel = createLabel(PRIMARY, "PC ID:", "Arial", true, 14);
	    Label jobStatusLabel = createLabel(PRIMARY, "Job Status:" ,"Arial", true, 14);
	    

        statusComboBox.setValue("Uncomplete");
        
        staffIdField = new TextField();
        staffIdField.setPrefWidth(200);
        
        jobIdField = new TextField();
        jobIdField.setPrefWidth(200);
        
        pcIdField = new TextField();
        pcIdField.setPrefWidth(200);
        
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change; 
            }

            return null; 
        };
        
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
        
        staffIdField.setTextFormatter(textFormatter);
        pcIdField.setTextFormatter(textFormatter2);
        jobIdField.setTextFormatter(textFormatter3);

	    Button assignBtn = createButton("Assign Job", 150, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    assignBtn.setOnAction(event -> {
	    	String staffId = staffIdField.getText().toString();
	    	String pcId = pcIdField.getText().toString();
	    	
	    	boolean check = JobController.getJobController().addNewJob(staffId, pcId);
	    	
	    	if(check) {
	    		refreshTable();
	    		statusComboBox.setValue("Uncomplete");
	    		staffIdField.setText("");	
	    		pcIdField.setText("");
	    		jobIdField.setText("");
	    	}
	    	
	    });
	    
	    Button updateBtn = createButton("Update Job", 150, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    updateBtn.setOnAction(event -> {
	    	String jobId = jobIdField.getText().toString();
	    	String jobStatus = statusComboBox.getValue().toString();
	    	
	    	boolean check = JobController.getJobController().updateJobStatus(jobId, jobStatus);
	    	
	    	if(check) {
	    		refreshTable();
	    		statusComboBox.setValue("Uncomplete");
	    		staffIdField.setText("");	
	    		pcIdField.setText("");
	    		jobIdField.setText("");
	    	}
	    	
	    });
	    
	    Button completeBtn = createButton("Complete Job", 150, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    completeBtn.setOnAction(event -> {
	    	String jobId = jobIdField.getText().toString();
	    	String jobStatus = "Complete";
	    	
	    	boolean check = JobController.getJobController().updateJobStatus(jobId, jobStatus);
	    	
	    	if(check) {
	    		refreshTable();
	    		statusComboBox.setValue("Uncomplete");
	    		staffIdField.setText("");	
	    		pcIdField.setText("");
	    		jobIdField.setText("");
	    	}
	    	
	    });
	    
	    jobTable.setOnMouseClicked(e -> {
		 	TableSelectionModel<Job> select = jobTable.getSelectionModel();
		 	select.setSelectionMode(SelectionMode.SINGLE);
		 	Job j = select.getSelectedItem();
		 	
		 	if (j != null) {
		 		staffIdField.setText(Integer.toString(j.getUserID()));
		 		jobIdField.setText(Integer.toString(j.getJobID()));
		 		statusComboBox.setValue(j.getJobStatus());
		 		pcIdField.setText(Integer.toString(j.getPC_ID()));
		 	}
		 	 	
	    });

	    bookPane.add(titleLbl, 0, 0);
	    bookPane.add(jobIdLabel, 0, 1);
	    bookPane.add(jobIdField, 1, 1);
	    bookPane.add(userIdLabel, 0, 2);
	    bookPane.add(staffIdField, 1, 2);
	    bookPane.add(pcIdLabel, 0, 3); 
	    bookPane.add(pcIdField, 1, 3);
	    bookPane.add(jobStatusLabel, 0, 4);
	    bookPane.add(statusComboBox, 1, 4); 
	    
	    buttonPane = new FlowPane();
	    buttonPane.setAlignment(Pos.CENTER);
	    buttonPane.setHgap(20);
	    
	    if(u.getUserRole().equals("Admin")) {
	    	buttonPane.getChildren().addAll(assignBtn, updateBtn);	    	
	    }else {
	    	buttonPane.getChildren().add(completeBtn);
	    }
 
	    bookPane.add(buttonPane, 0, 5, 2, 1); 

	    bookPane.setAlignment(Pos.CENTER);
	    bookPane.setBackground(WHITE_BACKGROUND);
	    bookPane.setPrefWidth(SCENE_WIDTH * 0.4);
	    bookPane.setPrefHeight(SCENE_HEIGHT);
	}

	private void createManageJobPage() {
		root = new VBox(10);
		
		createJobTable();
		createForm();			
		
		this.setBackground(WHITE_BACKGROUND);
		
		root.getChildren().addAll(jobTable, bookPane);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
	}


	public ManageJobPage() {
		createManageJobPage();
	}
}
