import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.MenuPanel;
import view.OrderPanel;

public class Main {
	public MenuPanel menuPanel;
	private OrderPanel orderPanel;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		MenuPanel menuPanel = new MenuPanel();
		OrderPanel orderPanel = new OrderPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Menu Management", menuPanel);
		tabbedPane.addTab("Orders", orderPanel);
		frame.add(tabbedPane);
		
		frame.setSize(500,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
