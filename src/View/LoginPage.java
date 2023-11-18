package View;

import Component.ComponentMaker;
import Controller.AuthController;
import Controller.PageController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginPage extends VBox implements ComponentMaker{

	private Label titleLbl, nameLbl1, nameLbl2;
	private Button loginBtn;
	private GridPane formPane;
	private FlowPane registerPane;
	private TextField nameTxt;
	private PasswordField passwordTxt;
	private VBox bannerPane;
	
	private void createTitle() {
		titleLbl = createLabel(PRIMARY, "Login", "Arial", true, 30);
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
				
		nameTxt = createTextField(SCENE_WIDTH*0.35, 20, 10, WHITE, PRIMARY, "Arial", 14, false);
		passwordTxt = createPasswordField(SCENE_WIDTH*0.35, 20, 10, WHITE, PRIMARY);
		

		VBox namePane = new VBox(5);
		VBox passwordPane = new VBox(5);
		formPane = new GridPane();

		namePane.getChildren().addAll(nameLbl, nameTxt);
		passwordPane.getChildren().addAll(passwordLbl, passwordTxt);

		formPane.add(namePane, 0, 0);
		formPane.add(passwordPane, 0, 1);

		GridPane.setMargin(namePane, new Insets(0, 0, 17, 0));
		GridPane.setMargin(passwordPane, new Insets(0, 0, 17, 0));

		formPane.setAlignment(Pos.CENTER);
	}

	private void createLoginPane() {
		Label registerLbl = new Label("to Register");
		Hyperlink registerLink = new Hyperlink("Click Here");
		registerPane = new FlowPane();
		registerPane.getChildren().addAll(registerLink, registerLbl);
		registerPane.setAlignment(Pos.CENTER);

		registerLink.setOnAction(e -> PageController.getPageController().showRegisterPage());
	}

	private void createRegisterButton() {
		loginBtn = createButton("Login", 167, 43, 10, 0, 0, PRIMARY, WHITE, WHITE, "Arial", 20, true);

		loginBtn.setOnAction(e -> {
			AuthController.getAuthController().login(nameTxt.getText(), passwordTxt.getText());
		});
		loginBtn.setCursor(Cursor.HAND);
	}

	private void createLoginView() {
		createBanner();
		createTitle();
		createForm();
		createLoginPane();
		createRegisterButton();
		
		VBox loginPane = new VBox(9);
		loginPane.getChildren().addAll(titleLbl, formPane, registerPane, loginBtn);
		loginPane.setAlignment(Pos.CENTER);
		loginPane.setPrefWidth(SCENE_WIDTH/2);
		loginPane.setPrefHeight(SCENE_HEIGHT);
		
		HBox root = new HBox();
		root.getChildren().addAll(bannerPane, loginPane);
		this.getChildren().add(root);
		this.setAlignment(Pos.CENTER);
		this.setBackground(WHITE_BACKGROUND);
	}
	
	public LoginPage() {
		createLoginView();
	}

}
