package application;
	
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
 


public class Main extends Application {
	
	String fileName = "dw_employee_payroll.srd", type, name;
	Stage window;
	Label dw_name;
	
	TableView<Product> table;	
	//TableView<DataWindowObject> table;
	ArrayList<TableColumn> dwColumns = new ArrayList<TableColumn>();
	
	
	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage) {
		
		window = primaryStage;
		window.setTitle("Integrate DataWindow");
		
		//create table columns
	/**	TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name") );
		
		TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(200);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price") );
		
		TableColumn<Product, String> column3 = new TableColumn<>("Column3");
		column3.setMinWidth(200);
		column3.setCellValueFactory(new PropertyValueFactory<>("column3") );
				
		dwColumns.add(nameColumn);
		dwColumns.add(priceColumn);
		dwColumns.add(column3);		
	 **/		
		table = new TableView<>();
		table.setItems(getProduct());
		
		readFile();				//read .srd file and populate dwColumns		
		//add columns to table view
		for (TableColumn tc : dwColumns)
		{
			table.getColumns().add(tc);
		}
			
		dw_name = new Label("File Name: " + fileName);
		//dw_name.setTextFill(Color.web("#0076a3"));
		VBox vbox = new VBox();
		vbox.getChildren().addAll(dw_name, table);	
		
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
				if (line.trim().startsWith("column="))
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
						else if(pair[0].equals("name") )
						{
							name = pair[1];
						}
						//String[] pair = st.nextToken().split("=");
						//System.out.println(pair[1] + ", " + pair[2]);
					}
					
					
					//type = line.substring( line.indexOf("type=")+5  , line.indexOf("type=")+5 + line.indexOf(" "));	//ready column type and name 
					//name = line.substring( line.indexOf("name=")+5  , line.indexOf("name=")+5 + line.indexOf(" "));	 //from column specification
					
					dwColumns.add(new TableColumn<>(name));
				    
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
	
	
	public ObservableList<Product> getProduct()
	{
		ObservableList<Product> products = FXCollections.observableArrayList();
		products.add(new Product("Product 1 ", 100));
		products.add(new Product("Product 2 ", 3));
		products.add(new Product("Product 3 ", 143));
		
		return products;
	}
	
	
}
