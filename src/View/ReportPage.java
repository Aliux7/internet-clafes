package View;

import java.util.function.UnaryOperator;

import Component.ComponentMaker;
import Component.CustomerNavbar;
import Controller.AuthController;
import Controller.ReportController;
import Controller.TransactionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import jfxtras.labs.internal.scene.control.skin.BigDecimalFieldSkin.NumberTextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import Model.TransactionDetail;
import Model.User;

public class ReportPage extends VBox implements ComponentMaker {

	private VBox root;
    private TextField pcIDField;
    private TextField reportNoteField;

    public ReportPage() {
        createReportPage();
    }

    private void createReportPage() {
		root = new VBox(10);

        User currentUser = AuthController.getAuthController().getCurrentUser();

        if (currentUser.getUserRole().equals("Customer")) {
            CustomerNavbar customerNavbar = new CustomerNavbar();
            root.getChildren().add(customerNavbar);
        } else {
//			data = TransactionController.getTransactionController().findAll();	
//			root.getChildren().add(new AdminNavbar());
        }

        GridPane formPane = createTransactionForm();
		this.setBackground(WHITE_BACKGROUND);
        root.getChildren().addAll(formPane);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
    }

    private GridPane createTransactionForm() {
        GridPane formPane = new GridPane();
        formPane.setAlignment(Pos.CENTER);
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(20));

        Label pcIDLabel = new Label("PC ID:");
        pcIDField = new TextField();
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change; // Allow only numeric input
            }

            return null; // Reject non-numeric input
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        pcIDField.setTextFormatter(textFormatter);


        Label reportNoteLabel = new Label("Report Note:");
        reportNoteField = new TextField();

        formPane.add(pcIDLabel, 0, 0);
        formPane.add(pcIDField, 1, 0);
        formPane.add(reportNoteLabel, 0, 1);
        formPane.add(reportNoteField, 1, 1);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            int pcID = 0;
            try {
                pcID = Integer.parseInt(pcIDField.getText());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid PC ID", "Please enter a valid PC ID.");
                return;
            }
            String reportNote = reportNoteField.getText();

            if (pcID > 0 && !reportNote.isEmpty()) {
                ReportController.getReport_Controller().AddNewReport("Customer", pcID, reportNote);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Report submitted successfully.");
                pcIDField.clear();
                reportNoteField.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Missing Information", "Please fill in all fields.");
            }
        });
        formPane.add(submitButton, 1, 2);

        return formPane;
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
