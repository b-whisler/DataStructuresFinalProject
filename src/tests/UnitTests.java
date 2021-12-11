package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import model.Order;
import model.MenuItem;
import view.OrderPanel;

class UnitTests {

	@Test
	void testItemCompareTypes() {
		MenuItem item1 = new MenuItem("Water","Beverage",2);
		MenuItem item2 = new MenuItem("Salad","Appetizer",4);
		assertEquals(1,item1.compareTo(item2));
	}
	@Test
	void testItemCompareNames() {
		MenuItem item1 = new MenuItem("Bread","Appetizer",2);
		MenuItem item2 = new MenuItem("Salad","Appetizer",4);
		assertEquals(-1,item1.compareTo(item2));
	}
	@Test
	void testItemCompareSame() {
		MenuItem item1 = new MenuItem("Water","Beverage",2);
		MenuItem item2 = new MenuItem("Water","Beverage",4);
		assertEquals(0,item1.compareTo(item2));
	}
	@Test
	void testOrderSubtotal() {
		LinkedList items = new LinkedList();
		MenuItem item1 = new MenuItem("Water","Beverage",2);
		MenuItem item2 = new MenuItem("Salad","Appetizer",4);
		items.add(item1);
		items.add(item2);
		Order testOrder = new Order(items,0,0);
		assertEquals(6,testOrder.getSubtotal(), 0.001);	
	}
	@Test
	void testOrderTax() {
		LinkedList items = new LinkedList();
		MenuItem item1 = new MenuItem("Water","Beverage",2);
		MenuItem item2 = new MenuItem("Salad","Appetizer",4);
		items.add(item1);
		items.add(item2);
		Order testOrder = new Order(items,0,0);
		assertEquals(0.3,testOrder.getTax(), 0.001);	
	}
	@Test
	void testOrderTip() {
		LinkedList items = new LinkedList();
		MenuItem item1 = new MenuItem("Water","Beverage",2);
		MenuItem item2 = new MenuItem("Salad","Appetizer",4);
		items.add(item1);
		items.add(item2);
		Order testOrder = new Order(items,0,0);
		assertEquals(0.9,testOrder.getTip(), 0.001);	
	}
	@Test
	void testOrderTotal() {
		LinkedList items = new LinkedList();
		MenuItem item1 = new MenuItem("Water","Beverage",2);
		MenuItem item2 = new MenuItem("Salad","Appetizer",4);
		items.add(item1);
		items.add(item2);
		Order testOrder = new Order(items,0,0);
		assertEquals(7.2,testOrder.getTotal(), 0.001);	
	}
	@Test
	void testOrderID() {
		OrderPanel orderPanel = new OrderPanel();
		assertEquals(100,orderPanel.getID());
		assertEquals(101,orderPanel.getID());
		
	}
}
