/**************************************************************
* Name        : FinalProject
* Author      : Benjamin Whisler
* Created     : 10/25/2021
* Course      : CIS 152 Data Structures
* Version     : 2.0
* OS          : Windows 10
* Copyright   : This is my own original work based on
*               specifications issued by our instructor
* Description : A restaurant order and menu management system.
*               Input:  Item information and orders.
*               Output: Java Swing GUI
* Academic Honesty: I attest that this is my original work.
* I have not used unauthorized source code, either modified or 
* unmodified. I have not given other fellow student(s) access to
* my program.         
***************************************************************/
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
