package model;
import java.time.LocalTime;
import java.util.LinkedList;

public class Order {
	final double TAX_RATE = 0.05;
	final double TIP_RATE = 0.15;
	private LinkedList<MenuItem> items; // Items in the order
	private LocalTime time; // Time to begin preparing the order
	private double subtotal; // Total of each item's price
	private double tax; // Total tax of the order
	private double tip; // Total tip of the order
	private double total; // Total price of the order
	private int id; // Order ID
	
	public Order(LinkedList<MenuItem> items, int time, int id) {
		this.items = items;
		this.time = LocalTime.now().plusMinutes(time); // Adds selected wait time to the current time
		this.id = id;
		subtotal = 0;
		for (int i = 0; i < items.size(); i++) { // Adds each item's price to the subtotal
			subtotal = subtotal + items.get(i).getPrice();
		}
		tax = subtotal * TAX_RATE;
		tip = subtotal * TIP_RATE;
		total = subtotal + tax + tip;
	}

	public LinkedList<MenuItem> getItems() {
		return items;
	}

	public void setItems(LinkedList<MenuItem> items) {
		this.items = items;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getTax() {
		return tax;
	}

	public double getTip() {
		return tip;
	}

	public double getTotal() {
		return total;
	}
	
	public int getID() {
		return id;
	}
	// Returns a string for use in the OrderPanel list
	public String listString() {
		return "Order #" + id + " | " + items.size() + " items | Total: $" + String.format("%,.2f", total) + " | Time to start order: " + time.getHour() + ":" + time.getMinute();
	}
	
	
}
