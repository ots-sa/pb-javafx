package application;

import java.util.ArrayList;

public class DataWindowObject {
	
	ArrayList<DataWindowColumn> columns = new ArrayList<DataWindowColumn>();
	String sqlRetrieve;
	
	public DataWindowObject(){};
	
	public DataWindowObject(ArrayList<DataWindowColumn> columns, String sqlRetrieve) {
		super();
		this.columns = columns;
		this.sqlRetrieve = sqlRetrieve;
	}
	
	

}
