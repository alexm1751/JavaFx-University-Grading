package View;

import java.time.LocalDate;

import Model.Course;
import Model.Name;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SMCreationPane extends GridPane {

	private ComboBox<Course> cboCourses;
	private DatePicker DatePicker;
	private TextField txtPNumber, txtFirstName, txtSurname, txtEmail;
	private Button btnCreateProf;

	public SMCreationPane() {
		// styling

		// this.setGridLinesVisible(true);
		this.setVgap(20);
		this.setHgap(30);
		this.setAlignment(Pos.CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(column1);
		// column1.setHgrow(Priority.ALWAYS);
		column1.setPercentWidth(25);
		column1.setMinWidth(60);
		// create labels
		Label lblTitle = new Label("Select Course: ");
		Label lblPNumber = new Label("Input P Number: ");
		Label lblFirstName = new Label("Input First name: ");
		Label lblSurname = new Label("Input Surname: ");
		Label lblEmail = new Label("Input Email: ");
		Label lblDate = new Label("Input Date: ");

		// setup combobox
		cboCourses = new ComboBox<Course>(); // will be populated via method
												// towards end of class

		//

		DatePicker = new DatePicker();

		// setup text fields
		txtPNumber = new TextField();
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtEmail = new TextField();

		// initialise play button
		btnCreateProf = new Button("Create Profile");

		// add controls and labels to container
		this.add(lblTitle, 0, 0);
		this.add(cboCourses, 1, 0);

		this.add(lblPNumber, 0, 1);
		this.add(txtPNumber, 1, 1);

		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);

		this.add(lblDate, 0, 5);
		this.add(DatePicker, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(btnCreateProf, 1, 6);

	}

	public void populateComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0);
	}

	public Course getSelectedCourse() {
		return cboCourses.getSelectionModel().getSelectedItem();
	}

	public String getPNumberInput() {
		return txtPNumber.getText();
	}

	public LocalDate getDate() {
		return DatePicker.getValue();

	}

	public Name getStudenNameInput() {
		String fName = txtFirstName.getText();
		String lName = txtSurname.getText();

		return new Name(fName, lName);
	}

	public void setDate(LocalDate date) {
		this.DatePicker.setValue(date);
	}

	public void setComboBox(int i) {
		cboCourses.getSelectionModel().select(i);
	}

	public void setPNumberInput(String PNumber) {
		this.txtPNumber.setText(PNumber);
	}

	public String getEmailInput() {
		return txtEmail.getText();
	}

	public void setFName(String firstName) {
		this.txtFirstName.setText(firstName);
	}

	public void setLName(String lastName) {
		this.txtSurname.setText(lastName);
	}

	public void setEmail(String email) {
		this.txtEmail.setText(email);
	}

	public BooleanBinding isCreationPaneEmpty() {
		return txtPNumber.textProperty().isEmpty().or(txtFirstName.textProperty().isEmpty()
				.or(txtSurname.textProperty().isEmpty().or(txtEmail.textProperty().isEmpty())));
	}

	public void createBtnDisableBind(BooleanBinding property) {
		btnCreateProf.disableProperty().bind(property);
	}

	public int getIndex() {
		return this.cboCourses.getSelectionModel().getSelectedIndex();
	}

	public void addCourseSubmitListener(EventHandler<ActionEvent> handler) {
		btnCreateProf.setOnAction(handler);
	}

}
