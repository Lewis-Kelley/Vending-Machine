import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font; 
import javax.swing.plaf.FontUIResource; 

public class VendingGUI extends JApplet implements ActionListener
{
	private Container cp;
	
	private CardLayout cards;
	
	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, masterBrisk, masterPepsi, home, home2, home3, wildCherryButton, pepsiMax, sweetTea, masterDew, dietDew;
	
	private JPanel cardOne, cardTwo, cardThree, cardFour, backPanel;
	
	private ImageIcon mountainDew = new ImageIcon("mountainDew.png");
	private ImageIcon mug = new ImageIcon("mug.png");
	private ImageIcon pepsi = new ImageIcon("pepsi.png");
	private ImageIcon brisk = new ImageIcon("lemonade.png");
	private ImageIcon briskRaspberry = new ImageIcon("raspberry.png");
	private ImageIcon briskHalf = new ImageIcon("half.png");
	private ImageIcon dietPepsi = new ImageIcon("dietPepsi.png");
	private ImageIcon codeRed = new ImageIcon("codeRed.png");
	private ImageIcon crush = new ImageIcon("crush.png");
	private ImageIcon back = new ImageIcon("back.png");
	private ImageIcon b = new ImageIcon("masterBrisk.png");
	private ImageIcon cherry = new ImageIcon("wildCherry.png");
	private ImageIcon max = new ImageIcon("max.png");
	private ImageIcon genericBrisk = new ImageIcon("genericBrisk.png");
	private ImageIcon dewDiet = new ImageIcon("dietDew.png");
	private ImageIcon p = new ImageIcon("masterPepsi.png");
	
	private int option;
	
	public void init()
	{
		b1 = new JButton(mountainDew);
		b1.setBorderPainted(false);
		b1.setContentAreaFilled(false);
		//b1.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b1.addActionListener(this);

		b2 = new JButton(mug);
		b2.setBorderPainted(false);
		b2.setContentAreaFilled(false);
		//b2.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b2.addActionListener(this);
		 
		b3 = new JButton(pepsi);
		b3.setBorderPainted(false);
		b3.setContentAreaFilled(false);
		//b3.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b3.addActionListener(this);
		
		b4 = new JButton(brisk);
		b4.setBorderPainted(false);
		b4.setContentAreaFilled(false);
		//b4.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b4.addActionListener(this);
		
		b5 = new JButton(briskRaspberry);
		b5.setBorderPainted(false);
		b5.setContentAreaFilled(false);
		//b5.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b5.addActionListener(this);

		b6 = new JButton(crush);
		b6.setBorderPainted(false);
		b6.setContentAreaFilled(false);
		//b6.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b6.addActionListener(this);
		 
		b7 = new JButton(codeRed);
		b7.setBorderPainted(false);
		b7.setContentAreaFilled(false);
		//b7.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b7.addActionListener(this);
		
		b8 = new JButton(dietPepsi);
		b8.setBorderPainted(false);
		b8.setContentAreaFilled(false);
		//b8.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b8.addActionListener(this);
		
		b9 = new JButton(briskHalf);
		b9.setBorderPainted(false);
		b9.setContentAreaFilled(false);
		//b9.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		b9.addActionListener(this);
		
		sweetTea = new JButton(b);
		sweetTea.setBorderPainted(false);
		sweetTea.setContentAreaFilled(false);
		sweetTea.addActionListener(this);
		
		wildCherryButton = new JButton(cherry);
		wildCherryButton.setBorderPainted(false);
		wildCherryButton.setContentAreaFilled(false);
		wildCherryButton.addActionListener(this);
	
		pepsiMax = new JButton(max);
		pepsiMax.setBorderPainted(false);
		pepsiMax.setContentAreaFilled(false);
		pepsiMax.addActionListener(this);
		
		dietDew = new JButton(dewDiet);
		dietDew.setBorderPainted(false);
		dietDew.setContentAreaFilled(false);
		dietDew.addActionListener(this);
		
		masterBrisk = new JButton(genericBrisk);
		masterBrisk.setBorderPainted(false);
		masterBrisk.setContentAreaFilled(false);
		//masterBrisk.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		masterBrisk.addActionListener(this);
	
		masterPepsi = new JButton(p);
		masterPepsi.setBorderPainted(false);
		masterPepsi.setContentAreaFilled(false);
		masterPepsi.addActionListener(this);
		
		masterDew = new JButton(mountainDew);
		masterDew.setBorderPainted(false);
		masterDew.setContentAreaFilled(false);
		masterDew.addActionListener(this);
		
		home = new JButton(back);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		//home.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		home.addActionListener(this);
		
		home2 = new JButton(back);
		home2.setBorderPainted(false);
		home2.setContentAreaFilled(false);
		//home.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		home2.addActionListener(this);
		
		home3 = new JButton(back);
		home3.setBorderPainted(false);
		home3.setContentAreaFilled(false);
		//home.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		home3.addActionListener(this);
		
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
		
		JPanel cardOne = new JPanel();
		//cardOne.setBackground(Color.green);
		cardOne.setLayout(new FlowLayout());//GridLayout(0,3));

		JPanel cardTwo = new JPanel();
		//cardTwo.setBackground(Color.blue);
		cardTwo.setLayout(new FlowLayout());//GridLayout(0,2));		//new GridLayout(0,3));

		JPanel cardThree = new JPanel();
		cardThree.setLayout(new FlowLayout());//GridLayout(0,3));
		//cardThree.setBackground(Color.orange);
		
		JPanel cardFour = new JPanel();
		cardFour.setLayout(new FlowLayout());//GridLayout(0,3));
		//cardThree.setBackground(Color.orange);
		
		cards = new CardLayout();
			
		cp = this.getContentPane();
		cp.setLayout(cards);
		cp.setBackground(Color.blue);
		
		cp.add(cardOne, "FirstCard");
		cp.add(cardTwo, "SecondCard");
		cp.add(cardThree, "ThirdCard");
		cp.add(cardFour, "FourthCard");
		
		cards.show(cp, "FirstCard");
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//		
		
		
		cardTwo.add(home);
		cardTwo.add(b4);
		cardTwo.add(b5);
		cardTwo.add(b9);
		cardTwo.add(sweetTea);
		
		cardThree.add(home2);
		cardThree.add(b3);
		cardThree.add(b8);
		cardThree.add(wildCherryButton);
		cardThree.add(pepsiMax);
		
		cardFour.add(home3);
		cardFour.add(b1);
		cardFour.add(b7);
		cardFour.add(dietDew);
		
		cardOne.add(b2);
		cardOne.add(b6);
		cardOne.add(masterPepsi);
		cardOne.add(masterBrisk);
		cardOne.add(masterDew);		
	}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e)
	{
		if(((JButton)e.getSource()).equals(masterBrisk))
			cards.show(cp, "SecondCard");
		if(((JButton)e.getSource()).equals(home))
			cards.show(cp, "FirstCard");
		if(((JButton)e.getSource()).equals(home2))
			cards.show(cp, "FirstCard");
		if(((JButton)e.getSource()).equals(home3))
			cards.show(cp, "FirstCard");
		if(((JButton)e.getSource()).equals(masterPepsi))
			cards.show(cp, "ThirdCard");
		if(((JButton)e.getSource()).equals(masterDew))
			cards.show(cp, "FourthCard");
		if(((JButton)e.getSource()).equals(b1))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");
		}
		if(((JButton)e.getSource()).equals(b2))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");
		}
		if(((JButton)e.getSource()).equals(b3))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(b4))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(b5))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(b6))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(b7))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(b8))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");		
		}
		if(((JButton)e.getSource()).equals(b9))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");
		}
		if(((JButton)e.getSource()).equals(sweetTea))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(wildCherryButton))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(pepsiMax))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");	
		}
		if(((JButton)e.getSource()).equals(dietDew))
		{
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			cards.show(cp, "FirstCard");
		}
	}
}
		
