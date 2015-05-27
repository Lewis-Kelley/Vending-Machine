import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font; 
import javax.swing.plaf.FontUIResource; 

public class Test
{
	VendingGUI tester;
	
	public static void main(String[] args)
	{
		VendingGUI tester = new VendingGUI();
		JFrame frame = new JFrame();
		
		tester.setUp();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(tester);
		frame.pack();
		frame.setVisible(true);
	}
}
