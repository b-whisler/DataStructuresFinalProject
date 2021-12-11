package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.MenuItem;

public class MenuPanel extends JPanel{
	public String[] courses = {"Appetizer","Beverage","Dessert","Main"}; // Item courses
	public static LinkedList<MenuItem> menu = new LinkedList<MenuItem>(); // Linked list of menu items
	private TableModel tableModel = new DefaultTableModel(0,3); // Table model for menu table
	private JTable menuTable = new JTable(); // Table holding menu items
	private final String DELIMITER = "#"; // Delimiter for saving/loading files
	
	public MenuPanel() {
		setLayout(new BorderLayout());
		// Set up table
		((DefaultTableModel) tableModel).setColumnIdentifiers(new String[] {"Name", "Course", "Price"}); // Set table columns
		menuTable.setModel(tableModel);
		menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Create pane and panels
		JScrollPane menuScrollPane = new JScrollPane(menuTable);
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		// Create buttons
		JButton addItem = new JButton("Add menu item");
		JButton loadFile = new JButton("Load menu from file");
		JButton saveFile = new JButton("Save menu to file");
		JButton removeItem = new JButton("Remove");
		JButton editItem = new JButton("Edit");
		// Create and add listeners
		AddItemListener l1 = new AddItemListener();
		addItem.addActionListener(l1);
		LoadListener l2 = new LoadListener();
		loadFile.addActionListener(l2);
		SaveListener l3 = new SaveListener();
		saveFile.addActionListener(l3);
		RemoveItemListener l4 = new RemoveItemListener();
		removeItem.addActionListener(l4);
		EditListener l5 = new EditListener();
		editItem.addActionListener(l5);
		// Set button appearance
		addItem.setBackground(new Color(0,210,0));
		removeItem.setBackground(new Color(220,0,0));
		removeItem.setForeground(Color.WHITE);
		editItem.setBackground(new Color(230,230,0));
		// Add items to subpanels
		topPanel.add(loadFile);
		topPanel.add(saveFile);
		bottomPanel.add(addItem);
		bottomPanel.add(editItem);
		bottomPanel.add(removeItem);
		// Add subpanels to MenuPanel
		add(topPanel, BorderLayout.PAGE_START);
		add(menuScrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	// Updates the menu table
	private void updateTableData() {
		sortMenu(); // Sorts the menu first
		DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel(); // Creates a DefaultTableModel to avoid casting on every reference to it
		while (tableModel.getRowCount() > 0) { // Removes every item in the model
			tableModel.removeRow(0);
		}
		for (int i = 0; i < menu.size(); i++) { // Adds each item from the menu to the model
			tableModel.addRow(new String[] {menu.get(i).getName(), menu.get(i).getType(), "$" + String.format("%,.2f", menu.get(i).getPrice())});
		}
		tableModel.fireTableDataChanged(); // Tells the table to update the graphics
	}
	// Sorts the menu using insertion sort
	private void sortMenu() {
		for (int i = 1; i < menu.size(); i++) {
			MenuItem comparing = menu.get(i);
			int compareToIndex = i - 1;
			while (compareToIndex >= 0 && menu.get(compareToIndex).compareTo(comparing) == 1) {
				menu.set(compareToIndex + 1, menu.get(compareToIndex));
				compareToIndex--;
			}
			menu.set(compareToIndex + 1, comparing);
		}
	}
	
	class AddItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Initialize elements
			JTextField name = new JTextField();
		    JComboBox course = new JComboBox(courses);
		    JTextField price = new JTextField();
		    JPanel inputPanel = new JPanel(new GridLayout(3, 2));
		    // Add elements to panel
		    inputPanel.add(new JLabel("Item name:"));
		    inputPanel.add(name);
		    inputPanel.add(new JLabel("Item course:"));
		    inputPanel.add(course);
		    inputPanel.add(new JLabel("Price: $"));
		    inputPanel.add(price);
		    // Display window
		    int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Menu Item", JOptionPane.OK_CANCEL_OPTION);
		    if (result == JOptionPane.OK_OPTION) {
		    	try {
		    		menu.add(new MenuItem(name.getText(), course.getSelectedItem().toString(), Double.parseDouble(price.getText())));
		    		updateTableData();
		    	} catch(NumberFormatException ex) {
		    		JOptionPane.showMessageDialog(null,"The input price was not valid, the item was not added.", "Error", JOptionPane.ERROR_MESSAGE);
		    	} 
		    }
		}
	}
	
	class RemoveItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (menuTable.getSelectedRow() < 0) { // If no row is selected, return
				return;
			}
			menu.remove(menuTable.getSelectedRow()); // Removes the selected item from the menu
			updateTableData();
		}
		
	}
	
	class EditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = menuTable.getSelectedRow();
			if (selectedRow < 0) { // If no row is selected, return
				return;
			}
			// Initialize elements
			JTextField name = new JTextField(menu.get(selectedRow).getName());
		    JComboBox course = new JComboBox(courses);
		    course.setSelectedItem(menu.get(selectedRow).getType());
		    JTextField price = new JTextField(String.valueOf(menu.get(selectedRow).getPrice()));
		    JPanel inputPanel = new JPanel(new GridLayout(3, 2));
		    // Add to panel
		    inputPanel.add(new JLabel("Item name:"));
		    inputPanel.add(name);
		    inputPanel.add(new JLabel("Item course:"));
		    inputPanel.add(course);
		    inputPanel.add(new JLabel("Price: $"));
		    inputPanel.add(price);
		    // Display window
		    int result = JOptionPane.showConfirmDialog(null, inputPanel, "Edit Item", JOptionPane.OK_CANCEL_OPTION);
		    if (result == JOptionPane.OK_OPTION) {
		    	try {
		    		menu.set(selectedRow, new MenuItem(name.getText(), course.getSelectedItem().toString(), Double.parseDouble(price.getText())));
		    		updateTableData();
		    	} catch(NumberFormatException ex) {
		    		JOptionPane.showMessageDialog(null,"The input price was not valid, the item was not changed.", "Error", JOptionPane.ERROR_MESSAGE);
		    	} 
		    }
		}
	}
	
	class LoadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Initialize elements
			String fileName = "menu.txt"; // Default file name
			JTextField fileNameInput = new JTextField(fileName);
			JPanel inputPanel = new JPanel(new GridLayout(1, 2));
			// Add elements to panel
			inputPanel.add(new JLabel("File name:"));
			inputPanel.add(fileNameInput);
			// Show window
			int result = JOptionPane.showConfirmDialog(null, inputPanel, "Load File", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				fileName = fileNameInput.getText();
			} else {
				return;
			}
			// Load selected file
			try {
			Scanner in = new Scanner(new File(fileName));
			LinkedList<MenuItem> importedMenu = new LinkedList<MenuItem>(); // List to hold the items in the file
			while (in.hasNextLine()) {
				String[] data = in.nextLine().split(DELIMITER);
				if (data.length == 3) { // Checks that there are 3 data points in the line
					try {
						importedMenu.add(new MenuItem(data[0],data[1],Double.parseDouble(data[2])));
					} catch(NumberFormatException ex) {
						
					}
				}
			}
			if (!importedMenu.isEmpty()) { // Replace the menu with the new menu if it is not empty
				menu = importedMenu;
				updateTableData();
			}
			} catch(FileNotFoundException ex) {
				JOptionPane.showMessageDialog(null,"File was not found.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Initialize elements
			String fileName = "menu.txt"; // Default file name
			JTextField fileNameInput = new JTextField(fileName);
			JPanel inputPanel = new JPanel(new GridLayout(1, 2));
			// Add elements to panel
			inputPanel.add(new JLabel("File name:"));
			inputPanel.add(fileNameInput);
			// Show window
			int result = JOptionPane.showConfirmDialog(null, inputPanel, "Save File", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				fileName = fileNameInput.getText();
			} else {
				return;
			}
			try {
				PrintWriter output = new PrintWriter(fileName);
				for (int i = 0; i < menu.size(); i++) { // Saves each menu item as a line
					String line = menu.get(i).getName() + DELIMITER + menu.get(i).getType() + DELIMITER + menu.get(i).getPrice();
					output.println(line); 
				}
				output.close();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(null,"Could not write the file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
