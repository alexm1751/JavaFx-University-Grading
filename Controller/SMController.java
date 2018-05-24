package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Model.Course;
import Model.Module;
import Model.StudentProfile;
import View.OverviewResultsPane;
import View.SMCreationPane;
import View.SMInputPane;
import View.SMMenuBar;
import View.SMRootPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SMController {

	// fields to be used throughout the class
	private SMRootPane view;
	private StudentProfile model;
	private SMCreationPane SMCP;
	private SMInputPane SMIP;
	private SMMenuBar SMMB;
	private OverviewResultsPane ORP;

	public SMController(SMRootPane view, StudentProfile model) {
		this.model = model;
		this.view = view;
		SMCP = view.getSMRootPane().getSMCreationPane();
		SMIP = view.getSMRootPane().getSMInputPane();
		SMMB = view.getSMMenuBar();
		ORP = view.getSMRootPane().getOverviewResultsPane();

		this.attachEventHandlers();

		this.attachBindings();

	}

	private void attachBindings() {

		SMCP.createBtnDisableBind(SMCP.isCreationPaneEmpty());
		SMIP.submitBtnDisableBind(SMIP.isInputPaneEmpty());
	}

	private void attachEventHandlers() {
		SMCP.populateComboBox(setupandGetCourses());
		SMCP.addCourseSubmitListener(new CourseSubmitListener());
		SMIP.addSubmitHandler(new SubmitHandler());
		SMIP.addClearHandler(new ClearHandler());
		ORP.addSaveOverviewHandler(new SaveOverviewHandler());

		SMMB.addLoadHandler(new LoadMenuHandler());
		SMMB.addSaveHandler(new SaveMenuHandler());
		SMMB.addExitHandler(e -> System.exit(0));
		SMMB.addAboutHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", null,
				"Student Mark Input App v1.0 with binding"));
	}

	private Course[] setupandGetCourses() {

		Module ctec2121 = new Module("CTEC2121", "Organisations, Project Management and Research", true);
		Module ctec2122 = new Module("CTEC2122", "Forensics and Security", false);
		Module ctec2602 = new Module("CTEC2602", "OO Software Design and Development", false);
		Module ctec2701 = new Module("CTEC2701", "Multi-tier Web Applications", true);
		Module ctec2901 = new Module("CTEC2901", "Data Structures and Algorithms", false);
		Module lawg2003 = new Module("LAWG2003", "Issues in Criminal Justice", false);
		Module ctec2903 = new Module("CTEC2903", "System Defence Strategies", true);

		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec2121);
		compSci.addModule(ctec2602);
		compSci.addModule(ctec2701);
		compSci.addModule(ctec2901);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec2121);
		softEng.addModule(ctec2602);
		softEng.addModule(ctec2701);
		softEng.addModule(ctec2901);

		Course compSecu = new Course("Computer Security");
		compSecu.addModule(ctec2121);
		compSecu.addModule(ctec2122);
		compSecu.addModule(ctec2701);
		compSecu.addModule(ctec2903);

		Course forenComp = new Course("Forensic Computing");
		forenComp.addModule(ctec2122);
		forenComp.addModule(ctec2121);
		forenComp.addModule(ctec2701);
		forenComp.addModule(lawg2003);

		Course[] courses = new Course[4];
		courses[0] = compSci;
		courses[1] = softEng;
		courses[2] = compSecu;
		courses[3] = forenComp;
		return courses;
	}

	private class CourseSubmitListener implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			model.setCourse(SMCP.getSelectedCourse());
			model.setpNumber(SMCP.getPNumberInput());
			model.setEmail(SMCP.getEmailInput());
			model.setStudentName(SMCP.getStudenNameInput());
			model.setDate(SMCP.getDate());
			SMIP.layoutReset();
			SMIP.layoutViewsCourse(SMCP.getSelectedCourse());

			Collection<Module> modules = model.getCourse().getModulesOnCourse();

			List<String> modulefullname = new ArrayList<>();

			for (Module m : modules) {

				modulefullname.add(m.getModuleCode() + " " + m.getModuleName());

			}

			SMIP.setModule1(modulefullname.get(0).toString());
			SMIP.setModule2(modulefullname.get(1).toString());
			SMIP.setModule3(modulefullname.get(2).toString());
			SMIP.setModule4(modulefullname.get(3).toString());

			view.getSMRootPane().changeTab(1);
		}

	}

	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			int i = 0;
			for (Module module : model.getCourse().getModulesOnCourse()) {
				module.setCwkMark(Integer.parseInt(SMIP.getTxtCwk().get(i).getText()));
				if (!module.isCwkOnly()) {
					module.setExamMark(Integer.parseInt(SMIP.getTxtExm().get(i).getText()));
				}
				i++;
			}

			ORP.setResults(displayResults());
			if (ORP != null)
				;

			view.getSMRootPane().changeTab(2);
		}

	}

	private class ClearHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			for (int i = 0; i < 4; i++) {
				SMIP.getTxtCwk().get(i).setText("0");
				SMIP.getTxtExm().get(i).setText("0");
			}
		}
	}

	public String displayResults() {
		return "Student Details:\n==========" + "\nName: " + model.getStudentName().getFullName() + "\nPNo: "
				+ model.getpNumber() + "\nEmail: " + model.getEmail() + "\nDate: " + model.getDate() + "\nCourse: "
				+ model.getCourse().getCourseName()

				+ "\n\n\n2nd Year Average:\n==========" + moduleOverview()
				+ "\n\nPlease save to view results at a later date.";

	}

	public String moduleOverview() {
		if (model.getCourse().yearAverageMark() < 40 || model.getCourse().creditsPassed() < 120) {
			return "\nCredits Passed: " + model.getCourse().creditsPassed() + "\nYear Avergae Mark: "
					+ model.getCourse().yearAverageMark()
					+ "\nYou have not met the minimum requirements to pass the course.\nPlease find module breakdown bellow:"
					+ moduleLoop();
		} else {
			return "\nCredits Passed: " + model.getCourse().creditsPassed() + "\nYear Avergae Mark: "
					+ model.getCourse().yearAverageMark()
					+ "\nCongratulations you have passed the course.\nPlease find module breakdown bellow:"
					+ moduleLoop();

		}
	}

	public String moduleLoop() {
		Collection<Module> modules = model.getCourse().getModulesOnCourse();

		List<String> modulefullname = new ArrayList<>();

		for (Module m : modules) {
			if (m.requireResit()) {
				modulefullname.add(m.getModuleCode() + ": " + m.getModuleMark() + " Resit Required!");
			} else
				modulefullname.add(m.getModuleCode() + ": " + m.getModuleMark());

		}
		return "\n" + modulefullname.get(0).toString() + "\n" + modulefullname.get(1).toString() + "\n"
				+ modulefullname.get(2).toString() + "\n" + modulefullname.get(3).toString();
	}

	public class SaveOverviewHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			try {
				Files.write(Paths.get(model.getpNumber() + " Marks.txt"), ORP.getResults().getBytes());
			} catch (IOException exc) {
				System.err.println(exc.getMessage());
			}
			alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "File Created", "Results are in .txt file");
		}

	}

	private class SaveMenuHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			// save the data model
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("studentProfileObj.dat"));) {

				oos.writeObject(model);
				oos.flush();

				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Save success",
						"Profule saved to studentProfileObj.dat");
			} catch (IOException ioExcep) {
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Save Failed", "Error Saving");
			}
		}
	}

	private class LoadMenuHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			// load in the data model
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studentProfileObj.dat"));) {

				model = (StudentProfile) ois.readObject();
			} catch (IOException ioExcep) {
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Load Failed", "Nothing to Load");
			} catch (ClassNotFoundException c) {
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Load Failed",
						"The Class you are looking could not be found");
			}

			populateStudentSubmission(model);

			alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Load success",
					"Register loaded from registerObj.dat");
		}
	}

	private void populateStudentSubmission(StudentProfile model) {

		// Creation Pane Tab
		String cp = model.getCourse().getCourseName();
		if (cp.equals("Computer Science")) {
			SMCP.setComboBox(0);
		} else if (cp.equals("Software Engineering")) {
			SMCP.setComboBox(1);
		} else if (cp.equals("Computer Security")) {
			SMCP.setComboBox(2);
		} else {
			SMCP.setComboBox(3);
		}

		SMCP.setPNumberInput(model.getpNumber());
		SMCP.setFName(model.getStudentName().getFirstName());
		SMCP.setLName(model.getStudentName().getFamilyName());
		SMCP.setEmail(model.getEmail());
		SMCP.setDate(model.getDate());

		Collection<Module> modules = model.getCourse().getModulesOnCourse();

		List<String> modulefullname = new ArrayList<>();

		for (Module m : modules) {

			modulefullname.add(m.getModuleCode() + " " + m.getModuleName());

		}
		// Input Student ProfilePane Tab
		SMIP.setModule1(modulefullname.get(0).toString());
		SMIP.setModule2(modulefullname.get(1).toString());
		SMIP.setModule3(modulefullname.get(2).toString());
		SMIP.setModule4(modulefullname.get(3).toString());

		int i = 0;
		for (Module module : model.getCourse().getModulesOnCourse()) {
			SMIP.getTxtCwk().get(i).setText(Integer.toString(module.getCwkMark()));
			if (!module.isCwkOnly()) {
				SMIP.getTxtExm().get(i).setText(Integer.toString(module.getExamMark()));
			}
			i++;
		}
		// Overview Results Pane Tab
		SMIP.layoutViewsCourse(model.getCourse());
		ORP.setResults(displayResults());

	}

	// helper method to build dialogs
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
