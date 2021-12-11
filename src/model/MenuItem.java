package model;

public class MenuItem {
	private String name;
	private String type; // Appetizer, main course, dessert, beverage
	private double price;
	
	public MenuItem(String name, String type, double price) {
		this.name = name;
		this.type = type;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return name + " | " + type + " | $" + String.format("%,.2f", price);
	}
	
	// Compares current item to input item for sorting, first by the item type then by name
	public int compareTo(MenuItem item) { 
		if (this.type.compareTo(item.getType()) > 0) {
			return 1;
		}
		if (this.type.compareTo(item.getType()) < 0) {
			return -1;
		}
		if (this.name.compareTo(item.getName()) > 0) {
			return 1;
		}
		if (this.name.compareTo(item.getName()) < 0) {
			return -1;
		}
		
		return 0;
	}
}
