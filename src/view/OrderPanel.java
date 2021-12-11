package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import model.MenuItem;
import model.Order;

public class OrderPanel extends JPanel {
	private PriorityQueue<Order> orderQueue = new PriorityQueue<Order>(10, new OrderComparator()); // Priority queue with comparator
	private JList<String> orderList;
	private DefaultListModel<String> orderListModel;
	private int orderID = 100; // Starting order ID number
	private JLabel preparingOrderLabel; // Label for order currently being prepared
	private Order preparingOrder; // Order currently being prepared
	private JButton viewCurrentOrder;

	public OrderPanel() {
		setLayout(new BorderLayout());
		// Set up order list
		orderListModel = new DefaultListModel<String>();
		orderList = new JList<String>(orderListModel);
		orderList.setVisibleRowCount(10);
		JScrollPane orderScrollPane = new JScrollPane(orderList);
		orderScrollPane.setPreferredSize(new Dimension(200,200));
		preparingOrderLabel = new JLabel("None");
		// Create buttons
		JButton placeOrder = new JButton("Place order");
		JButton prepareOrder = new JButton("Prepare next order");
		viewCurrentOrder = new JButton("View current order");
		viewCurrentOrder.setEnabled(false);
		// Create and add listeners
		PlaceOrderListener l1 = new PlaceOrderListener();
		placeOrder.addActionListener(l1);
		PrepareOrderListener l2 = new PrepareOrderListener();
		prepareOrder.addActionListener(l2);
		ViewOrderListener l3 = new ViewOrderListener();
		viewCurrentOrder.addActionListener(l3);
		// Create and add items to subpanel
		JPanel lowerPanel = new JPanel(new GridLayout(3,2));
		lowerPanel.add(placeOrder);
		lowerPanel.add(prepareOrder);
		lowerPanel.add(new JLabel("Order being prepared:"));
		lowerPanel.add(preparingOrderLabel);
		lowerPanel.add(viewCurrentOrder);
		// Add items to OrderPanel
		add(new JLabel("Orders:"), BorderLayout.PAGE_START);
		add(orderScrollPane, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.PAGE_END);
	}
	
	void updateOrderList() {
		ArrayList<Order> queueList = new ArrayList<Order>(orderQueue); // Makes an array list from the order queue
		queueList.sort(new OrderComparator()); // Sorts the array list
		orderListModel.removeAllElements(); // Clears current order list
		for (int i = 0; i < queueList.size(); i++) { // Adds each item from the array list to the order list
			orderListModel.addElement(queueList.get(i).listString());
		}
	}
	
	// Returns the order ID for an order and increases the next ID number
	public int getID() {
		orderID++;
		return orderID - 1;
	}
	// Compares orders based on the time they should be prepared
	class OrderComparator implements Comparator<Order>{

		@Override
		public int compare(Order o1, Order o2) {
			if (o1.getTime().isAfter(o2.getTime())) {
				return 1;
			} else if (o1.getTime().isBefore(o2.getTime())) {
				return -1;
			} else {
			return 0;
			}
		}
		
	}
	
	
	class PlaceOrderListener implements ActionListener{
		private int timing; // Order timing
		private JList menuList; // List of menu items
		private JList orderItemList; // List of items added to the order
		private DefaultListModel menuListModel;
		private DefaultListModel orderItemListModel;
		private LinkedList<MenuItem> orderItems; // Linked list of ordered items
		@Override
		public void actionPerformed(ActionEvent e) {
			if (MenuPanel.menu.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No items are on the menu. Add items or load a menu first.", "Notification", JOptionPane.WARNING_MESSAGE);
				return;
			}
			timing = 0;
			// Set up lists
			orderItems = new LinkedList<MenuItem>();
			orderItemListModel = new DefaultListModel();
			orderItemList = new JList(orderItemListModel);
			orderItemList.setVisibleRowCount(8);
			orderItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			menuListModel = new DefaultListModel();
			menuList = new JList(menuListModel);
			menuList.setVisibleRowCount(8);
			menuList.setSelectedIndex(0);
			menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			for (int i = 0; i < MenuPanel.menu.size(); i++) {
				menuListModel.addElement(MenuPanel.menu.get(i).toString());
			}
			// Create buttons
			JButton addButton = new JButton("Add");
			JButton removeButton = new JButton("Remove");
			JRadioButton radioNow = new JRadioButton("Now");
			JRadioButton radio30m = new JRadioButton("30 min");
			JRadioButton radio1h = new JRadioButton("1 hour");
			JRadioButton radio2h = new JRadioButton("2 hours");
			JRadioButton radio3h = new JRadioButton("3 hours");
			// Create and add listeners
			AddListener addListener = new AddListener();
			addButton.addActionListener(addListener);
			RemoveListener removeListener = new RemoveListener();
			removeButton.addActionListener(removeListener);
			RadioListener rl = new RadioListener();
			radioNow.addActionListener(rl);
			radio30m.addActionListener(rl);
			radio1h.addActionListener(rl);
			radio2h.addActionListener(rl);
			radio3h.addActionListener(rl);
			// Set up radio buttons
			radioNow.setActionCommand("0");
			radioNow.setSelected(true);
			radio30m.setActionCommand("30");
			radio1h.setActionCommand("60");
			radio2h.setActionCommand("120");
			radio3h.setActionCommand("180");
			ButtonGroup radioGroup = new ButtonGroup();
			radioGroup.add(radioNow);
			radioGroup.add(radio30m);
			radioGroup.add(radio1h);
			radioGroup.add(radio2h);
			radioGroup.add(radio3h);
			// Create panels
			JPanel inputPanel = new JPanel(new BorderLayout());
			JPanel listPanel = new JPanel(new GridLayout(1,2));
			JPanel listButtonPanel = new JPanel(new GridLayout(1,3,300,0));
			JPanel radioPanel = new JPanel(new GridLayout(0,2));
			JPanel radioSubPanel = new JPanel(new GridLayout(2,3));
			// Add items to panels
			JScrollPane menuScrollPane = new JScrollPane(menuList);
			JScrollPane orderScrollPane = new JScrollPane(orderItemList);
			listPanel.add(menuScrollPane);
			listPanel.add(orderScrollPane);
			listButtonPanel.add(addButton);
			listButtonPanel.add(removeButton);
			radioSubPanel.add(radioNow);
			radioSubPanel.add(radio30m);
			radioSubPanel.add(radio1h);
			radioSubPanel.add(radio2h);
			radioSubPanel.add(radio3h);
			radioPanel.add(new JLabel("Order timing:"));
			radioPanel.add(radioSubPanel);
			inputPanel.add(listPanel,BorderLayout.PAGE_START);
			inputPanel.add(listButtonPanel,BorderLayout.CENTER);
			inputPanel.add(radioPanel,BorderLayout.PAGE_END);
			// Show window
			int result = JOptionPane.showConfirmDialog(null, inputPanel, "Place order", JOptionPane.OK_CANCEL_OPTION);
		    if (result == JOptionPane.OK_OPTION) {
		    	if (orderItems.isEmpty()) { // Check if no items were ordered
		    		JOptionPane.showMessageDialog(null,"No items were ordered.", "Error", JOptionPane.ERROR_MESSAGE);
		    		return;
		    	}
		    	orderQueue.add(new Order(orderItems,timing,getID())); // Create the order
		    	updateOrderList();
		    }
		}
		
		class RadioListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				timing = Integer.valueOf(e.getActionCommand()); // Sets the order timing to the selected button
			}
		}
		
		class AddListener implements ActionListener{
			// Adds the selected item to the order linked list and the list shown in the panel
			@Override
			public void actionPerformed(ActionEvent e) {
				orderItems.add(MenuPanel.menu.get(menuList.getSelectedIndex()));
				orderItemListModel.addElement(menuList.getSelectedValue());
				
			}
			
		}
		
		class RemoveListener implements ActionListener{
			// Removes the selected item from the order linked list and the list in the panel
			@Override
			public void actionPerformed(ActionEvent e) {
				if (orderItemList.getSelectedIndex() < 0) { // If no item was selected the index is -1
					return;
				}
				orderItems.remove(orderItemList.getSelectedIndex());
				orderItemListModel.remove(orderItemList.getSelectedIndex());
				
			}
			
		}
		
	}
	
	class PrepareOrderListener implements ActionListener{
		// Polls the next order in the priority queue and sets it as the order being prepared
		@Override
		public void actionPerformed(ActionEvent e) {
			if (orderQueue.isEmpty()) {
				return;
			}
			preparingOrder = orderQueue.poll();
			preparingOrderLabel.setText("Order #" + preparingOrder.getID() + " | " + preparingOrder.getItems().size() + " items");
			updateOrderList();
			viewCurrentOrder.setEnabled(true); // Enables the button for viewing the order
			
			
		}
		
	}
	
	class ViewOrderListener implements ActionListener{
		// Creates a window showing the details of the order being prepared
		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel viewOrderPanel = new JPanel();
			String orderString = "Order #" + preparingOrder.getID() + "\n\nItems: ";
			for (int i = 0; i < preparingOrder.getItems().size(); i++) {
				orderString = orderString + "\n" + preparingOrder.getItems().get(i).getName() + " - $" + String.format("%,.2f", preparingOrder.getItems().get(i).getPrice());
			}
			orderString = orderString + "\n\nSubtotal: $" + String.format("%,.2f", preparingOrder.getSubtotal()) + "\nTax: $" + String.format("%,.2f", preparingOrder.getTax()) + "\nTip: $" + String.format("%,.2f", preparingOrder.getTip()) + "\nTotal: $" + String.format("%,.2f", preparingOrder.getTotal());
			
			JTextArea orderText = new JTextArea(orderString);
			viewOrderPanel.add(orderText);
			
			JOptionPane.showMessageDialog(null, viewOrderPanel, "View Order", JOptionPane.PLAIN_MESSAGE);
			
		}
		
	}
	
}
