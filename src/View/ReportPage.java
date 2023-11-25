package View;

import java.time.LocalDate;
import java.util.List;
import java.util.function.UnaryOperator;

import Component.AdminNavbar;
import Component.ComponentMaker;
import Component.CustomerNavbar;
import Component.OperatorNavbar;
import Controller.AuthController;
import Controller.ReportController;
import Controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import jfxtras.labs.internal.scene.control.skin.BigDecimalFieldSkin.NumberTextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import Model.PC_Book;
import Model.Report;
import Model.TransactionDetail;
import Model.User;

public class ReportPage extends VBox implements ComponentMaker {

	private VBox root;
    private TextField pcIDField;
    private TextField reportNoteField;
    private ObservableList<Report> reportOL;
	private TableView<Report> reportTable;

    User currentUser;
    
    public ReportPage() {
    	currentUser = AuthController.getAuthController().getCurrentUser();
    	createReportPage();
    }
    
    

	private void createReportPage() {
		root = new VBox(10);

        if (currentUser.getUserRole().equals("Customer")) {
            CustomerNavbar customerNavbar = new CustomerNavbar();
            root.getChildren().add(customerNavbar);
        } else if (currentUser.getUserRole().equals("Operator")) {
            root.getChildren().add(new OperatorNavbar());
        } else {
        	AdminNavbar adminNavbar = new AdminNavbar();
        	root.getChildren().add(adminNavbar);
        }

        GridPane formPane;
        
        if (currentUser.getUserRole().equals("Customer") || (currentUser.getUserRole().equals("Operator"))) {
        	formPane = createReportForm();  
        	root.getChildren().addAll(formPane);
        }else {
        	createReportTableView();
        }
        
		this.setBackground(WHITE_BACKGROUND);
		root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
    }
	
	@SuppressWarnings("unchecked")
	private void createReportTableView() {
	    reportTable = new TableView<>();
	    reportTable.setPrefWidth(SCENE_WIDTH * 0.5);
	    reportTable.setMaxWidth(SCENE_WIDTH * 0.5);

	    int numColumns = 5;

	    double columnWidth = reportTable.getPrefWidth() / numColumns;

	    TableColumn<Report, Integer> idColumn = new TableColumn<>("Report ID");
	    idColumn.setCellValueFactory(new PropertyValueFactory<>("report_ID"));
	    idColumn.setPrefWidth(columnWidth);

	    TableColumn<Report, String> userRoleColumn = new TableColumn<>("User Role");
	    userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));
	    userRoleColumn.setPrefWidth(columnWidth);

	    TableColumn<Report, Integer> pcIdColumn = new TableColumn<>("PC ID");
	    pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("pC_ID"));
	    pcIdColumn.setPrefWidth(columnWidth);

	    TableColumn<Report, String> reportNoteColumn = new TableColumn<>("Report Note");
	    reportNoteColumn.setCellValueFactory(new PropertyValueFactory<>("reportNote"));
	    reportNoteColumn.setPrefWidth(columnWidth);

	    TableColumn<Report, LocalDate> reportDateColumn = new TableColumn<>("Report Date");
	    reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
	    reportDateColumn.setPrefWidth(columnWidth);

	    reportTable.getColumns().addAll(idColumn, userRoleColumn, pcIdColumn, reportNoteColumn, reportDateColumn);

	    List<Report> reports = ReportController.getReport_Controller().getAllReportData();
	    if (reports != null) {
	        reportOL = FXCollections.observableArrayList(reports);
	    }
	    reportTable.setItems(reportOL);

	    root.getChildren().add(reportTable);
	}


    private GridPane createReportForm() {
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
                return change;
            }

            return null; 
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
        	
        	String pcID = pcIDField.getText();
        	String reportNote = reportNoteField.getText();

            boolean c = ReportController.getReport_Controller().addNewReport(currentUser.getUserRole(), pcID, reportNote);

            if(c) {
            	pcIDField.setText("");
            	reportNoteField.setText("");
            }
        });
        formPane.add(submitButton, 1, 2);

        return formPane;
    }
    
}
