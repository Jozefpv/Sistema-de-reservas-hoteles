import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HotelesApp extends Application {
	public static Stage primaryStage;
	private Controller controller = new Controller();
	@Override
	public void start(Stage primaryStage) throws Exception {
		HotelesApp.primaryStage = primaryStage;
		
		primaryStage.setTitle("Hoteles application");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
