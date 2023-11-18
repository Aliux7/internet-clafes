package Main;
import View.LandingPage;
import View.LoginPage;
import Component.Attribute;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application implements Attribute{

	private Scene scene;
	public static StackPane root;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		root = new StackPane();
		root.getChildren().add(new LoginPage());
//		root.getChildren().add(new LandingPage());
		
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Internet CLafes");
		primaryStage.show();
		
	}
}
