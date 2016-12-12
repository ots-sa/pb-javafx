package application;
	
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
 


public class Main extends Application {
	
	String fileName = "dw_employee_stathera.srd", type, name, dbname;
	Stage window;
	Label dw_name;
	TextField dw_sql;
	boolean update, updatewhereclause;
	
	
	TableView<DataWindowColumn> table;	
	DataWindowObject dwo = new DataWindowObject();
	OracleConnector ora =  new OracleConnector();
	Statement stmt;
	ResultSet rs ;	
	
	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage) {
		
		window = primaryStage;
		window.setTitle("Integrate DataWindow");
		
		
		table = new TableView<>();					//create table columns
		//table.setItems(getProduct());				//Use this to add rows
		
		readFile();									//read .srd file and populate dwColumns		
		
		for (DataWindowColumn dwc : dwo.columns)	//add columns to table view
		{
			table.getColumns().add(new TableColumn(dwc.name));
		}
			
		dw_name = new Label("File Name: " + fileName);
		dw_sql = new TextField("SQL Query: "+ dwo.sqlRetrieve);
		dw_sql.setMinHeight(50);
		
		try {
			stmt = ora.getCon().createStatement();
			rs = stmt.executeQuery(dwo.sqlRetrieve);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				stmt.close();
				ora.getCon().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
		
		//dw_name.setTextFill(Color.web("#0076a3"));
		VBox vbox = new VBox();
		vbox.getChildren().addAll(dw_name, dw_sql, table);	
		
		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.show();
			
	}
	
	public void readFile()
	{
		BufferedReader in = null;
		
		String line;
		String[] pair;
		
		try {
			in = new BufferedReader( new InputStreamReader( new FileInputStream(fileName), "UTF8"));
			
			while((line = in.readLine()) != null)
			{			    
				if (line.trim().startsWith("column="))			//read columns
				{			
					StringTokenizer st = new StringTokenizer(line.substring(line.indexOf("(")+ 1 ), " ");
					while (st.hasMoreTokens() )
					{	
						pair = st.nextToken().split("=");
						//System.out.println(pair[0] + ", " + pair[1]);
						if(pair[0].equals("type") )
						{
							type = pair[1];
						}
						if(pair[0].equals("update") )
						{
							update =  pair[1] == "yes";
						}
						if(pair[0].equals("updatewhereclause") )
						{
							updatewhereclause =  pair[1] == "yes";
						}
						else if(pair[0].equals("name") )
						{
							name = pair[1];
						}
						else if(pair[0].equals("dbname") )
						{
							dbname = pair[1];
						}					
					}
					dwo.columns.add(new DataWindowColumn(type, update, updatewhereclause, name, dbname));
				}
				
				if (line.trim().startsWith("retrieve="))			//read the sql preview of the datawindow that is inside quotes " "
				{
					dwo.sqlRetrieve = line.substring(line.indexOf("retrieve=") + 9 );
					line = in.readLine();
					while(! line.contains("\""))						
					{
						dwo.sqlRetrieve += line;
						line = in.readLine();
					}
					
				}
			}	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
}
