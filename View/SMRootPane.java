package View;


import javafx.scene.layout.BorderPane;


public class SMRootPane extends BorderPane {
	
	private SMMenuBar spmb;
	private SMTabPane smtp;
	
	
	
	public SMRootPane(){
		this.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.getStyleClass().add("root-pane");
		spmb = new SMMenuBar();
		smtp = new SMTabPane();
		
		this.setTop(spmb);
		this.setCenter(smtp);
	
	}

	public SMTabPane getSMRootPane(){
		return smtp;
	}
	
	
	public SMMenuBar getSMMenuBar(){
		return spmb;
	}
	

}

