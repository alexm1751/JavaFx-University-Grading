package View;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class SMTabPane extends TabPane {
	
	private SMCreationPane spcp;
	private SMInputPane smip;
	private OverviewResultsPane orp;

	
	
	
	public SMTabPane(){
		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.getStyleClass().add("tab-pane");
		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); //don't allow tabs to be closed
		
		spcp = new SMCreationPane();
		smip = new SMInputPane();
		orp = new OverviewResultsPane();
		
		Tab t1 = new Tab("Create Profile", spcp);
		Tab t2 = new Tab("Input Marks", smip);
		Tab t3 = new Tab("Results", orp);
		
		this.getTabs().addAll(t1,t2, t3);
		

	
	}

	
	public SMCreationPane getSMCreationPane(){
		return spcp;
	}
	public SMInputPane getSMInputPane(){
		return smip;
	}
	public OverviewResultsPane getOverviewResultsPane(){
		return orp;
	}
	
	
	public void changeTab(int index){
		this.getSelectionModel().select(index);
	}

}

