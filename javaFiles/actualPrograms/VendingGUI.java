import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font; 
import javax.swing.plaf.FontUIResource; 

public class VendingGUI extends JPanel implements ActionListener
{
	//private Container cp;
	
	private CardLayout cards;
	
	private Soda can;
	
	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, masterBrisk, masterPepsi, home, home2, home3, wildCherryButton, pepsiMax, sweetTea, masterDew, dietDew, yesButton, noButton;
	
	private JPanel cardOne, cardTwo, cardThree, cardFour, backPanel;
	
	public JLabel youSure = new JLabel("Are you sure?");
	
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
	private ImageIcon noPic = new ImageIcon("no.png");
	private ImageIcon yesPic = new ImageIcon("yes.png");
	
	private int option, sodaType = 0;
	
	public void setUp()
	{
		this.setPreferredSize(new Dimension(690, 410));
		
		can = Soda.EMPTY;

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
		
		yesButton = new JButton(yesPic);
		yesButton.setBorderPainted(false);
		yesButton.setContentAreaFilled(false);
		//home.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		yesButton.addActionListener(this);
		
		noButton = new JButton(noPic);
		noButton.setBorderPainted(false);
		noButton.setContentAreaFilled(false);
		//home.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		noButton.addActionListener(this);
		
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
		
		JPanel confirmation = new JPanel();
		confirmation.setLayout(new GridLayout(1,0));
		
		
		
		//JLabel confirmPic = new JLabel();
		//confirmPic.setBackground(back);
		
		
		cards = new CardLayout();
			
		//cp = this.getContentPane();
		this.setLayout(cards);
		this.setBackground(Color.blue);
		
		this.add(cardOne, "FirstCard");
		this.add(cardTwo, "SecondCard");
		this.add(cardThree, "ThirdCard");
		this.add(cardFour, "FourthCard");
		this.add(confirmation, "Confirmation");
		
		cards.show(this, "FirstCard");
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
		
		confirmation.add(yesButton);
		confirmation.add(youSure);
		confirmation.add(noButton);
	}
	
	
	
	
	
	public void actionPerformed(ActionEvent e)
	{
		if(((JButton)e.getSource()).equals(masterBrisk))
			cards.show(this, "SecondCard");
		else if(((JButton)e.getSource()).equals(home))
			cards.show(this, "FirstCard");
		else if(((JButton)e.getSource()).equals(home2))
			cards.show(this, "FirstCard");
		else if(((JButton)e.getSource()).equals(home3))
			cards.show(this, "FirstCard");
		else if(((JButton)e.getSource()).equals(masterPepsi))
			cards.show(this, "ThirdCard");
		else if(((JButton)e.getSource()).equals(masterDew))
			cards.show(this, "FourthCard");
		else if(((JButton)e.getSource()).equals(b1))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");
			cards.show(this, "Confirmation");
			sodaType = 1;
		}
		else if(((JButton)e.getSource()).equals(b2))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");
			cards.show(this, "Confirmation");
			sodaType = 2;
		}
		else if(((JButton)e.getSource()).equals(b3))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 3;
		}
		else if(((JButton)e.getSource()).equals(b4))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");
			cards.show(this, "Confirmation");
			sodaType = 4;	
		}
		else if(((JButton)e.getSource()).equals(b5))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");
			cards.show(this, "Confirmation");
			sodaType = 5;
		}
		else if(((JButton)e.getSource()).equals(b6))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 6;
		}
		else if(((JButton)e.getSource()).equals(b7))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 7;
		}
		else if(((JButton)e.getSource()).equals(b8))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 8;
		}
		else if(((JButton)e.getSource()).equals(b9))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");
			cards.show(this, "Confirmation");
			sodaType = 9;
		}
		else if(((JButton)e.getSource()).equals(sweetTea))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 10;
		}
		else if(((JButton)e.getSource()).equals(wildCherryButton))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 11;
		}
		else if(((JButton)e.getSource()).equals(pepsiMax))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");	
			cards.show(this, "Confirmation");
			sodaType = 12;
		}
		else if(((JButton)e.getSource()).equals(dietDew))
		{
			//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("SERIF",Font.PLAIN,50))); // from http://stackoverflow.com/questions/4017042/how-to-enlarge-buttons-on-joptionpane-dialog-boxes
			//option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
			//cards.show(cp, "FirstCard");
			cards.show(this, "Confirmation");
			sodaType = 13;
		}
		
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		else if(((JButton)e.getSource()).equals(yesButton))
		{
			cards.show(this, "Confirmation");
			switch(sodaType)
			{
				case 1:
				can = Soda.MOUNTAIN_DEW;
				break;
				
				case 2:
				can = Soda.DIET_MUG;			
				break;
				
				case 3:
				can = Soda.PEPSI;			
				break;
				
				case 4:
				can = Soda.BRISK_LEMONADE;			
				break;
				
				case 5:
				can = Soda.BRISK_RASPBERRY;			
				break;
				
				case 6:
				can = Soda.DIET_CRUSH;			
				break;
				
				case 7:
				can = Soda.MOUNTAIN_DEW_CODE_RED;			
				break;
				
				case 8:
				can = Soda.DIET_PEPSI;			
				break;
				
				case 9:
				can = Soda.BRISK_HALF_AND_HALF;			
				break;
				
				case 10:
				can = Soda.BRISK_SWEET_TEA;			
				break;
				
				case 11:
				can = Soda.PEPSI_WILD_CHERRY;			
				break;
				
				case 12:
				can = Soda.PEPSI_MAX;			
				break;
				
				case 13:
				can = Soda.DIET_MOUNTAIN_DEW;			
				break;
			}
			sodaType = 0;
			cards.show(this, "FirstCard");
					
		}
		else if(((JButton)e.getSource()).equals(noButton))
		{
			cards.show(this, "Confirmation");
			sodaType = 0;
			cards.show(this, "FirstCard");
		}
	}
}
