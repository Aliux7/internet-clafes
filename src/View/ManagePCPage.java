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
import Model.PC;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class ManagePCPage extends VBox implements ComponentMaker {

	private FlowPane pcPane, buttonPane;
	private ScrollPane scrollPane;
	private GridPane bookPane;
	private HBox root;
	private TextField pcIdField;
	List<PC> list = PC_Controller.getPC_Controller().getAllPCData();
	ObservableList<String> statusOptions = FXCollections.observableArrayList(
            "Usable",
            "Maintenance",
            "Broken"
    );
    ComboBox<String> statusComboBox = new ComboBox<>(statusOptions);

	private void createPCDisplay() {
		pcPane = new FlowPane();
		pcPane.setHgap(15);
		pcPane.setVgap(30);
		pcPane.setPrefWidth(SCENE_WIDTH*0.6);
		pcPane.setPadding(new Insets(40));
		
		
		buttonPane = new FlowPane();
		buttonPane.setHgap(30);
		buttonPane.setVgap(15);
		buttonPane.setPrefWidth(SCENE_WIDTH * 0.4);
		buttonPane.setAlignment(Pos.CENTER);

		refreshPCDisplay();
		
		scrollPane = new ScrollPane(pcPane);
		scrollPane.setFitToWidth(true);
		scrollPane.getStyleClass().add("edge-to-edge");
	}
	
	 private void refreshPCDisplay() {
        pcPane.getChildren().clear(); // Clear existing PC cards

        list = PC_Controller.getPC_Controller().getAllPCData();

        for (PC pc : list) {
            VBox card = new VBox();
            if (pc.getPC_Condition().equals("Usable")) {
                card = createMonitorCard("file:src/Usable.png", pc.getPC_ID(), pc.getPC_Condition());
            } else if (pc.getPC_Condition().equals("Broken")) {
                card = createMonitorCard("file:src/Broken.png", pc.getPC_ID(), pc.getPC_Condition());
            } else if (pc.getPC_Condition().equals("Maintenance")) {
                card = createMonitorCard("file:src/Maintenance.png", pc.getPC_ID(), pc.getPC_Condition());
            }
            pcPane.getChildren().add(card);
            pcPane.setBackground(WHITE_BACKGROUND);
        }
    }

	private VBox createMonitorCard(String path, int id, String condition) {
		VBox card = new VBox(2);
		card.setPrefSize(SCENE_WIDTH*0.05, SCENE_HEIGHT*0.05);
		card.setMaxSize(SCENE_WIDTH*0.05, SCENE_HEIGHT*0.05);
		card.setPrefWidth(SCENE_WIDTH*0.05);
		card.setMaxWidth(SCENE_WIDTH*0.05);
		ImageView imageIv = createImageView(path, 60, 70);
		TextFlow idTxt = createTextFlow(Integer.toString(id), "Arial", 12, true);
		TextFlow condtionTxt = createTextFlow(condition, "Arial", 10, true);

		card.setAlignment(Pos.CENTER);
		card.getChildren().addAll(imageIv, idTxt, condtionTxt);
		card.setPadding(new Insets(0,10,0,10));
		card.setPrefWidth(SCENE_WIDTH*0.05);
		card.setMaxWidth(SCENE_WIDTH*0.05);

	    card.setOnMouseClicked(event -> {
	        pcIdField.setText(Integer.toString(id));
	        statusComboBox.setValue(condition);
	    });
		return card;
	}


	@SuppressWarnings("unchecked")
	private void createBookForm() {
	    bookPane = new GridPane();
	    Label titleLbl = createLabel(PRIMARY, "Manage PC", "Arial", true, 32);
	    bookPane.setHgap(10);
	    bookPane.setVgap(10); 
	    bookPane.setPadding(new Insets(10));

	    Label pcStatusLabel = createLabel(PRIMARY, "PC Status:", "Arial", true, 14);
	    Label pcIdLabel = createLabel(PRIMARY, "PC ID:", "Arial", true, 14);

        statusComboBox.setValue("Usable");
        
        pcIdField = new TextField();
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change; 
            }

            return null; 
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        pcIdField.setTextFormatter(textFormatter);

	    statusComboBox.setPrefWidth(SCENE_WIDTH * 0.3);
	    pcIdField.setPrefWidth(SCENE_WIDTH * 0.3);

	    Button insertButton = createButton("Insert", 75, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    insertButton.setOnAction(event -> {
	    	String pcId = pcIdField.getText().toString();
	    	boolean check = PC_Controller.getPC_Controller().addNewPC(pcId);
	    	
	    	if(check) {
	    		refreshPCDisplay();
	    		statusComboBox.setValue("Usable");
	    		pcIdField.setText("");	    		
	    	}
	    	
	    });
	    
	    Button updateButton = createButton("Update", 75, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    updateButton.setOnAction(event -> {
	    	String pcId = pcIdField.getText().toString();
	    	boolean check = PC_Controller.getPC_Controller().updatePCCondition(pcId, statusComboBox.getValue());
	    	
	    	if(check) {
	    		refreshPCDisplay();
	    		statusComboBox.setValue("Usable");
	    		pcIdField.setText("");	    		
	    	}
	    });
	    
	    Button deleteButton = createButton("Delete", 75, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    deleteButton.setOnAction(event -> {
	    	String pcId = pcIdField.getText().toString();
	    	boolean check = PC_Controller.getPC_Controller().deletePC(pcId);
	    	
	    	if(check) {
	    		refreshPCDisplay();
	    		statusComboBox.setValue("Usable");
	    		pcIdField.setText("");	    		
	    	}
	    });

	    bookPane.add(titleLbl, 0, 0, 2 , 1);
	    bookPane.add(pcStatusLabel, 0, 1);
	    bookPane.add(statusComboBox, 1, 1); 
	    bookPane.add(pcIdLabel, 0, 2); 
	    bookPane.add(pcIdField, 1, 2);
	    
	    buttonPane.getChildren().add(insertButton);
	    buttonPane.getChildren().add(updateButton);
	    buttonPane.getChildren().add(deleteButton);
	    
	    bookPane.add(buttonPane, 0, 3, 2, 1); 

	    bookPane.setAlignment(Pos.CENTER);
	    bookPane.setBackground(WHITE_BACKGROUND);
	    bookPane.setPrefWidth(SCENE_WIDTH * 0.4);
	    bookPane.setPrefHeight(SCENE_HEIGHT);
	}



	private void createLandingView() {
		createPCDisplay();
		createBookForm();

		root = new HBox();
		root.getChildren().addAll(scrollPane, bookPane);
		this.getChildren().addAll(new AdminNavbar(), root);
	}

	public ManagePCPage() {
		createLandingView();
	}
}
