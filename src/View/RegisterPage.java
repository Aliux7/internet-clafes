package View;

import Controller.AuthController;
import Controller.PageController;
import Component.ComponentMaker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegisterPage extends VBox implements ComponentMaker{

	private Label titleLbl, nameLbl1, nameLbl2;
	private Button registerBtn;
	private GridPane formPane;
	private FlowPane loginPane;
	private TextField nameTxt;
	private PasswordField passwordTxt, conpassTxt;
	private RadioButton maleBtn, femaleBtn;
	private DatePicker datePicker;
	private ToggleGroup genderGroup;
	private HBox genderBox;
	private VBox bannerPane;
	
	private void createTitle() {
		titleLbl = createLabel(PRIMARY, "Register", "Arial", true, 30);
	}
	
	private void createBanner() {
		bannerPane = new VBox(5);
		bannerPane.setPrefWidth(SCENE_WIDTH/2);
		bannerPane.setBackground(PRIMARY_BACKGROUND);

		nameLbl1 = createLabel(SECONDARY, "Internet", "Arial", true, 70);
		nameLbl2 = createLabel(BASE, "CLafes", "Arial", true, 70);
		
		bannerPane.getChildren().addAll(nameLbl1, nameLbl2);
		bannerPane.setAlignment(Pos.CENTER_LEFT);
		bannerPane.setPadding(new Insets(0, 0, 0, SCENE_WIDTH/10));
	}

	private void createForm() {	
		Label nameLbl = createLabel(PRIMARY, "Name", "Arial", true, 15);
		Label passwordLbl = createLabel(PRIMARY, "Password", "Arial", true, 15);
		Label conpassLbl = createLabel(PRIMARY, "Confirm Password", "Arial", true, 15);
		Label genderLbl = createLabel(PRIMARY, "Gender", "Arial", true, 15);
		Label dobLbl = createLabel(PRIMARY, "Date of Birth", "Arial", true, 15);
				
		nameTxt = createTextField(SCENE_WIDTH*0.35, 20, 10, WHITE, PRIMARY, "Arial", 14, false);
		passwordTxt = createPasswordField(SCENE_WIDTH*0.35, 20, 10, WHITE, PRIMARY);
		conpassTxt = createPasswordField(SCENE_WIDTH*0.35, 20, 10, WHITE, PRIMARY);
		
		datePicker = new DatePicker();

		maleBtn = new RadioButton("Male");
		femaleBtn = new RadioButton("Female");
		genderGroup = new ToggleGroup();
		genderGroup.getToggles().addAll(maleBtn, femaleBtn);
		genderBox = new HBox(5);
		genderBox.getChildren().addAll(maleBtn, femaleBtn);

		VBox namePane = new VBox(5);
		VBox passwordPane = new VBox(5);
		VBox conpassPane = new VBox(5);
		VBox genderPane = new VBox(5);
		VBox dobPane = new VBox(5);
		formPane = new GridPane();

		namePane.getChildren().addAll(nameLbl, nameTxt);
		passwordPane.getChildren().addAll(passwordLbl, passwordTxt);
		conpassPane.getChildren().addAll(conpassLbl, conpassTxt);
		genderPane.getChildren().addAll(genderLbl, genderBox);
		dobPane.getChildren().addAll(dobLbl, datePicker);

		formPane.add(namePane, 0, 0);
		formPane.add(passwordPane, 0, 1);
		formPane.add(conpassPane, 0, 2);
		formPane.add(genderPane, 0, 3);
		formPane.add(dobPane, 0, 4);

		GridPane.setMargin(namePane, new Insets(0, 0, 17, 0));
		GridPane.setMargin(conpassPane, new Insets(0, 0, 17, 0));
		GridPane.setMargin(passwordPane, new Insets(0, 0, 17, 0));
		GridPane.setMargin(genderPane, new Insets(0, 0, 17, 0));
		GridPane.setMargin(dobPane, new Insets(0, 0, 17, 0));

		formPane.setAlignment(Pos.CENTER);
	}

	private void createLoginPane() {
		Label loginLbl = new Label("Already have an account?");
		Hyperlink loginLink = new Hyperlink("Log in");
		loginPane = new FlowPane();
		loginPane.getChildren().addAll(loginLbl, loginLink);
		loginPane.setAlignment(Pos.CENTER);

		loginLink.setOnAction(e -> PageController.getPageController().showLoginPage());
	}

	private void createRegisterButton() {
		registerBtn = createButton("Register", 167, 43, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 20, true);

		registerBtn.setOnAction(e -> {
			AuthController.getAuthController().register(nameTxt.getText(), passwordTxt.getText(), conpassTxt.getText(), ((RadioButton)genderGroup.getSelectedToggle()), datePicker.getValue(), "Customer");
		});
		registerBtn.setCursor(Cursor.HAND);
	}

	private void createRegisterView() {
		createBanner();
		createTitle();
		createForm();
		createLoginPane();
		createRegisterButton();
		
		VBox registerPane = new VBox(9);
		registerPane.getChildren().addAll(titleLbl, formPane, loginPane,registerBtn);
		registerPane.setAlignment(Pos.CENTER);
		registerPane.setPrefWidth(SCENE_WIDTH/2);
		registerPane.setPrefHeight(SCENE_HEIGHT);
		
		HBox root = new HBox();
		root.getChildren().addAll(bannerPane, registerPane);
		this.getChildren().add(root);
		this.setAlignment(Pos.CENTER);
		this.setBackground(WHITE_BACKGROUND);
	}

	public RegisterPage() {
		createRegisterView();
	}
}