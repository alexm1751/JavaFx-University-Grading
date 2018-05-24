
package Main;

import Controller.SMController;
import Model.StudentProfile;
import View.SMRootPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationLoader extends Application {

	private SMRootPane view;

	@Override
	public void init() {
		// create model and view and pass their references to the controller
		StudentProfile model = new StudentProfile();
		view = new SMRootPane();
		new SMController(view, model);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setMinWidth(700); // sets min width and height for the stage
								// window
		stage.setMinHeight(500);
		stage.setTitle("Student Input Mark");

		stage.setScene(new Scene(view));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
