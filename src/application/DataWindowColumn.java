package application;

public class DataWindowColumn {
	
	String type;
	boolean update;
	boolean updatewhereclause;
	String name;
	String dbname;
	
	public DataWindowColumn(String type, boolean update, boolean updatewhereclause, String name, String dbname) {
		super();
		this.type = type;
		this.update = update;
		this.updatewhereclause = updatewhereclause;
		this.name = name;
		this.dbname = dbname;
	}
	
 
}
