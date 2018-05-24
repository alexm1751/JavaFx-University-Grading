package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OverviewResultsPane extends VBox {

	private TextArea results;
	private Button save;

	public OverviewResultsPane() {
		results = new TextArea("Results will appear here...");
		results.setEditable(false);
		VBox.setVgrow(results, Priority.ALWAYS);

		this.setPadding(new Insets(70, 70, 70, 70));
		this.setAlignment(Pos.CENTER);

		save = new Button("Save Overview");

		VBox.setMargin(save, new Insets(30, 30, 30, 30));

		this.getChildren().add(results);
		this.getChildren().add(save);
	}

	// methods to update the content of this pane
	public void setResults(String overview) {
		results.setText(overview);
	}

	public String getResults() {
		return this.results.getText();
	}

	public void setColor(String colour) {
		this.setStyle("-fx-background-color: " + colour + ";");
	}

	public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
		save.setOnAction(handler);
	}
}
