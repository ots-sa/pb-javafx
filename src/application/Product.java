package application;

public class Product {
	
	String 	name;
	int  	price;
	
	public Product (String _name, int _price)
	{
		name = _name;
		price = _price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
