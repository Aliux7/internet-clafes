package View;

import java.time.LocalDate;
import java.util.List;
import java.util.function.UnaryOperator;

import Component.ComponentMaker;
import Component.CustomerNavbar;
import Controller.AuthController;
import Controller.PC_BookController;
import Controller.PC_Controller;
import Model.PC;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class LandingPage extends VBox implements ComponentMaker {

	private FlowPane pcPane, buttonPane;
	private ScrollPane scrollPane;
	private GridPane bookPane;
	private HBox root;
	private Button removeBtn, orderBtn;
	private DatePicker bookDatePicker;
	private TextField pcIdField;
	List<PC>list = PC_Controller.getPC_Controller().getAllPCData();

	private void createPCDisplay() {
		pcPane = new FlowPane();
		pcPane.setHgap(15);
		pcPane.setVgap(30);
		pcPane.setPrefWidth(SCENE_WIDTH*0.6);
		pcPane.setPadding(new Insets(40));

		list = PC_Controller.getPC_Controller().getAllPCData();
		
		for(PC pc : list) {
			VBox card = new VBox();
			if(pc.getPC_Condition().equals("Usable")) {
				card = createMonitorCard("file:src/Usable.png", pc.getPC_ID(), pc.getPC_Condition());				
			}else if(pc.getPC_Condition().equals("Broken")) {
				card = createMonitorCard("file:src/Broken.png", pc.getPC_ID(), pc.getPC_Condition());		
			}else if(pc.getPC_Condition().equals("Maintenance")) {
				card = createMonitorCard("file:src/Maintenance.png", pc.getPC_ID(), pc.getPC_Condition());		
			}
			pcPane.getChildren().add(card);
			pcPane.setBackground(WHITE_BACKGROUND);			
		}
		
		scrollPane = new ScrollPane(pcPane);
		scrollPane.setFitToWidth(true);
		scrollPane.getStyleClass().add("edge-to-edge");
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
	    });
		return card;
	}


	@SuppressWarnings("unchecked")
	private void createBookForm() {
	    bookPane = new GridPane();
	    Label titleLbl = createLabel(PRIMARY, "Book PC", "Arial", true, 40);
	    bookPane.setHgap(10);
	    bookPane.setVgap(10); 
	    bookPane.setPadding(new Insets(10));

	    Label bookDateLabel = createLabel(PRIMARY, "Book Date:", "Arial", true, 14);
	    Label pcIdLabel = createLabel(PRIMARY, "PC ID:", "Arial", true, 14);

	    bookDatePicker = new DatePicker();

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

        Button bookButton = createButton("Book", 150, 30, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 12, true);
	    bookButton.setOnAction(event -> {
	            String pcId = pcIdField.getText().toString();

	            boolean c = PC_BookController.getPC_BookController().addNewBook(pcId, AuthController.getAuthController().getCurrentUser().getUserID(), bookDatePicker.getValue());
	            
	            if(c) {
	            	bookDatePicker.setValue(null);
		            pcIdField.setText("");
	            }
	    });

	    bookPane.add(titleLbl, 0, 0);
	    bookPane.add(bookDateLabel, 0, 1);
	    bookPane.add(bookDatePicker, 1, 1); 
	    bookPane.add(pcIdLabel, 0, 2); 
	    bookPane.add(pcIdField, 1, 2);
	    
	    bookPane.add(bookButton, 0, 3, 2, 1); 

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
		this.getChildren().addAll(new CustomerNavbar(), root);
	}

	public LandingPage() {
		createLandingView();
	}
}
