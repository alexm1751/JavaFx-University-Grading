package View;

import java.util.ArrayList;
import java.util.List;

import Model.Course;
import Model.Module;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SMInputPane extends GridPane {

	private List<TextField> cwkTxtField, exmTxtField;
	private List<Module> modules;
	private Button btnSubmit, btnClear;
	private Label Module1, Module2, Module3, Module4;

	public SMInputPane() {

		cwkTxtField = new ArrayList<>();
		exmTxtField = new ArrayList<>();

		// styling
		// this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(30);
		this.setHgap(40);
		// this.setGridLinesVisible(true);
		this.setAlignment(Pos.CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(column1);
		// column1.setHgrow(Priority.ALWAYS);
		column1.setPercentWidth(45);
		column1.setMinWidth(100);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(column2);
		column2.setPercentWidth(10);
		column2.setMinWidth(70);

		ColumnConstraints column3 = new ColumnConstraints();
		column3.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(column3);
		column3.setPercentWidth(10);
		column3.setMinWidth(80);

		this.setAlignment(Pos.CENTER);

		Label lblTitle = new Label("Module");
		Label lblTitle1 = new Label("Cwk Mark");
		Label lblTitle2 = new Label("Exam Mark");

		Module1 = new Label("Profile not created");
		Module2 = new Label("Profile not created");
		Module3 = new Label("Profile not created");
		Module4 = new Label("Profile not created");

		btnSubmit = new Button("Submit");
		btnClear = new Button("Clear");

		cwkTxtField.add(new TextField("0"));
		cwkTxtField.add(new TextField("0"));
		cwkTxtField.add(new TextField("0"));
		cwkTxtField.add(new TextField("0"));

		exmTxtField.add(new TextField("0"));
		exmTxtField.add(new TextField("0"));
		exmTxtField.add(new TextField("0"));
		exmTxtField.add(new TextField("0"));

		this.add(lblTitle, 0, 0);
		this.add(lblTitle1, 1, 0);
		this.add(lblTitle2, 2, 0);

		this.add(Module1, 0, 1);
		this.add(cwkTxtField.get(0), 1, 1);

		this.add(exmTxtField.get(0), 2, 1);

		this.add(Module2, 0, 2);
		this.add(cwkTxtField.get(1), 1, 2);
		this.add(exmTxtField.get(1), 2, 2);

		this.add(Module3, 0, 3);
		this.add(cwkTxtField.get(2), 1, 3);
		this.add(exmTxtField.get(2), 2, 3);

		this.add(Module4, 0, 4);
		this.add(cwkTxtField.get(3), 1, 4);
		this.add(exmTxtField.get(3), 2, 4);

		this.add(new HBox(), 0, 5);
		this.add(btnSubmit, 2, 5);

		this.add(new HBox(), 0, 5);
		this.add(btnClear, 1, 5);

		cwkTxtField.get(0).setMaxWidth(65);
		cwkTxtField.get(1).setMaxWidth(65);
		cwkTxtField.get(2).setMaxWidth(65);
		cwkTxtField.get(3).setMaxWidth(65);

		exmTxtField.get(0).setMaxWidth(65);
		exmTxtField.get(1).setMaxWidth(65);
		exmTxtField.get(2).setMaxWidth(65);
		exmTxtField.get(3).setMaxWidth(65);

	}

	public void layoutReset() {

		exmTxtField.get(0).setVisible(true);
		exmTxtField.get(1).setVisible(true);
		exmTxtField.get(2).setVisible(true);
		exmTxtField.get(3).setVisible(true);
	}

	public void layoutViewsCourse(Course course) {
		modules = new ArrayList<Module>(course.getModulesOnCourse());

		if (modules.get(0).isCwkOnly()) {
			exmTxtField.get(0).setVisible(false);
		}
		if (modules.get(1).isCwkOnly()) {
			exmTxtField.get(1).setVisible(false);
		}
		if (modules.get(2).isCwkOnly()) {
			exmTxtField.get(2).setVisible(false);
		}
		if (modules.get(3).isCwkOnly()) {
			exmTxtField.get(3).setVisible(false);
		}

	}

	public BooleanBinding isInputPaneEmpty() {

		return (cwkTxtField.get(0).textProperty().isEmpty().or(exmTxtField.get(0).textProperty().isEmpty())
				.or(cwkTxtField.get(1).textProperty().isEmpty().or(exmTxtField.get(1).textProperty().isEmpty())
						.or(cwkTxtField.get(2).textProperty().isEmpty().or(exmTxtField.get(2).textProperty().isEmpty())
								.or(cwkTxtField.get(3).textProperty().isEmpty()
										.or(exmTxtField.get(3).textProperty().isEmpty())))));

	}

	public void setModule2(String ct) {

		Module2.setText(ct);

	}

	public void setModule3(String ct) {
		Module3.setText(ct);

	}

	public void setModule4(String ct) {
		Module4.setText(ct);

	}

	public void setModule1(String ct) {
		Module1.setText(ct);

	}

	public List<TextField> getTxtCwk() {
		return cwkTxtField;
	}

	public List<TextField> getTxtExm() {
		return exmTxtField;
	}

	public void submitBtnDisableBind(BooleanBinding property) {
		btnSubmit.disableProperty().bind(property);
	}

	public void addClearHandler(EventHandler<ActionEvent> handler) {
		btnClear.setOnAction(handler);
	}

	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}

}