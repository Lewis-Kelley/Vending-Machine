import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font; 
import javax.swing.plaf.FontUIResource; 

public class VendingGUI extends JPanel
{	
	public CardLayout cards;
	
	public Soda can;
	
	public JLabel flipLabel;
	
	public JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, masterBrisk, masterPepsi, home, home2, home3, wildCherryButton, pepsiMax, sweetTea, masterDew, dietDew;
	
	public JPanel cardOne, cardTwo, cardThree, cardFour, backPanel, thanksPanel;
	
	public JLabel[] sodaOption = new JLabel[13];
	public JButton[] yesArray = new JButton[13];
	public JButton[] noArray = new JButton[13];
	public JPanel[] confirmArray = new JPanel[13];
	public JPanel[] holder = new JPanel[13];
	public JLabel[] youSure = new JLabel[13];
	
	
	public ImageIcon mountainDew = new ImageIcon("mountainDew.png");
	public ImageIcon mug = new ImageIcon("mug.png");
	public ImageIcon pepsi = new ImageIcon("pepsi.png");
	public ImageIcon brisk = new ImageIcon("lemonade.png");
	public ImageIcon briskRaspberry = new ImageIcon("raspberry.png");
	public ImageIcon briskHalf = new ImageIcon("half.png");
	public ImageIcon dietPepsi = new ImageIcon("dietPepsi.png");
	public ImageIcon codeRed = new ImageIcon("codeRed.png");
	public ImageIcon crush = new ImageIcon("crush.png");
	public ImageIcon back = new ImageIcon("back.png");
	public ImageIcon b = new ImageIcon("masterBrisk.png");
	public ImageIcon cherry = new ImageIcon("wildCherry.png");
	public ImageIcon max = new ImageIcon("max.png");
	public ImageIcon genericBrisk = new ImageIcon("genericBrisk.png");
	public ImageIcon dewDiet = new ImageIcon("dietDew.png");
	public ImageIcon p = new ImageIcon("masterPepsi.png");
	public ImageIcon noPic = new ImageIcon("no.png");
	public ImageIcon yesPic = new ImageIcon("yes.png");
	public ImageIcon sure = new ImageIcon("youSure.png");
	
	public ImageIcon pictureHolder;
	public ImageIcon spinningCan = new ImageIcon("sodaSpin.gif");
	
	
	private int option, sodaType = 0;
	
	public void setUp()
	{
		this.setPreferredSize(new Dimension(690, 410));
		
		JLabel flipLabel = new JLabel(spinningCan);
		flipLabel.setIcon(spinningCan);
		
		
		JLabel[] sodaOption = new JLabel[13];
		JButton[] yesArray = new JButton[13];
		JButton[] noArray = new JButton[13];
		JPanel[] confirmArray = new JPanel[13];
		JPanel[] holder = new JPanel[13];
		JLabel[] youSure = new JLabel[13];
		
		can = Soda.EMPTY;
		
		ImageIcon pictureHolder = new ImageIcon();
	
		b1 = new JButton(mountainDew);
		b1.setBorderPainted(false);
		b1.setContentAreaFilled(false);
		b1.addActionListener(new ChoiceButtons());

		b2 = new JButton(mug);
		b2.setBorderPainted(false);
		b2.setContentAreaFilled(false);
		b2.addActionListener(new ChoiceButtons());
		 
		b3 = new JButton(pepsi);
		b3.setBorderPainted(false);
		b3.setContentAreaFilled(false);
		b3.addActionListener(new ChoiceButtons());
		
		b4 = new JButton(brisk);
		b4.setBorderPainted(false);
		b4.setContentAreaFilled(false);
		b4.addActionListener(new ChoiceButtons());
		
		b5 = new JButton(briskRaspberry);
		b5.setBorderPainted(false);
		b5.setContentAreaFilled(false);
		b5.addActionListener(new ChoiceButtons());

		b6 = new JButton(crush);
		b6.setBorderPainted(false);
		b6.setContentAreaFilled(false);
		b6.addActionListener(new ChoiceButtons());
		 
		b7 = new JButton(codeRed);
		b7.setBorderPainted(false);
		b7.setContentAreaFilled(false);
		b7.addActionListener(new ChoiceButtons());
		
		b8 = new JButton(dietPepsi);
		b8.setBorderPainted(false);
		b8.setContentAreaFilled(false);
		b8.addActionListener(new ChoiceButtons());
		
		b9 = new JButton(briskHalf);
		b9.setBorderPainted(false);
		b9.setContentAreaFilled(false);
		b9.addActionListener(new ChoiceButtons());
		
		sweetTea = new JButton(b);
		sweetTea.setBorderPainted(false);
		sweetTea.setContentAreaFilled(false);
		sweetTea.addActionListener(new ChoiceButtons());
		
		wildCherryButton = new JButton(cherry);
		wildCherryButton.setBorderPainted(false);
		wildCherryButton.setContentAreaFilled(false);
		wildCherryButton.addActionListener(new ChoiceButtons());
	
		pepsiMax = new JButton(max);
		pepsiMax.setBorderPainted(false);
		pepsiMax.setContentAreaFilled(false);
		pepsiMax.addActionListener(new ChoiceButtons());
		
		dietDew = new JButton(dewDiet);
		dietDew.setBorderPainted(false);
		dietDew.setContentAreaFilled(false);
		dietDew.addActionListener(new ChoiceButtons());
		
		masterBrisk = new JButton(genericBrisk);
		masterBrisk.setBorderPainted(false);
		masterBrisk.setContentAreaFilled(false);
		masterBrisk.addActionListener(new MasterButtons());
	
		masterPepsi = new JButton(p);
		masterPepsi.setBorderPainted(false);
		masterPepsi.setContentAreaFilled(false);
		masterPepsi.addActionListener(new MasterButtons());
		
		masterDew = new JButton(mountainDew);
		masterDew.setBorderPainted(false);
		masterDew.setContentAreaFilled(false);
		masterDew.addActionListener(new MasterButtons());
		
		home = new JButton(back);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.addActionListener(new MasterButtons());
		
		home2 = new JButton(back);
		home2.setBorderPainted(false);
		home2.setContentAreaFilled(false);
		home2.addActionListener(new MasterButtons());
		
		home3 = new JButton(back);
		home3.setBorderPainted(false);
		home3.setContentAreaFilled(false);
		home3.addActionListener(new MasterButtons());
		
		for(int i = 0; i < 13; i++)
		{
			yesArray[i] = new JButton(yesPic);
			yesArray[i].setBorderPainted(false);
			yesArray[i].setContentAreaFilled(false);
			yesArray[i].addActionListener(new yesButton());
		
			noArray[i] = new JButton(noPic);
			noArray[i].setBorderPainted(false);
			noArray[i].setContentAreaFilled(false);
			noArray[i].addActionListener(new noButton());
			
			switch(i)
			{
				case 0:
				sodaOption[i] = new JLabel(mountainDew);
				break;
				
				case 1:
				sodaOption[i] = new JLabel(mug);			
				break;
				
				case 2:
				sodaOption[i] = new JLabel(pepsi);			
				break;
				
				case 3:
				sodaOption[i] = new JLabel(brisk);			
				break;
				
				case 4:
				sodaOption[i] = new JLabel(briskRaspberry);		
				break;
				
				case 5:
				sodaOption[i] = new JLabel(crush);			
				break;
				
				case 6:
				sodaOption[i] = new JLabel(codeRed);			
				break;
				
				case 7:
				sodaOption[i] = new JLabel(dietPepsi);			
				break;
				
				case 8:
				sodaOption[i] = new JLabel(briskHalf);			
				break;
				
				case 9:
				sodaOption[i] = new JLabel(b);			
				break;
				
				case 10:
				sodaOption[i] = new JLabel(cherry);		
				break;
				
				case 11:
				sodaOption[i] = new JLabel(max);			
				break;
				
				case 12:
				sodaOption[i] = new JLabel(dewDiet);			
				break;
			}
			youSure[i] = new JLabel(sure);
			
			holder[i] = new JPanel();
			
			confirmArray[i] = new JPanel();
			confirmArray[i].setLayout(new BorderLayout());
			
			holder[i].add(yesArray[i]);
			holder[i].add(sodaOption[i]);
			holder[i].add(noArray[i]);
			
			confirmArray[i].add(youSure[i], BorderLayout.NORTH);
			confirmArray[i].add(holder[i], BorderLayout.CENTER);
		}
		
		JPanel cardOne = new JPanel();
		cardOne.setLayout(new FlowLayout());

		JPanel cardTwo = new JPanel();
		cardTwo.setLayout(new FlowLayout());

		JPanel cardThree = new JPanel();
		cardThree.setLayout(new FlowLayout());
		
		JPanel cardFour = new JPanel();
		cardFour.setLayout(new FlowLayout());
		
		JPanel thanksPanel = new JPanel();
		thanksPanel.setLayout(new BorderLayout());
				
		cards = new CardLayout();
			
		this.setLayout(cards);
		this.setBackground(Color.blue);
		
		this.add(cardOne, "FirstCard");
		this.add(cardTwo, "SecondCard");
		this.add(cardThree, "ThirdCard");
		this.add(cardFour, "FourthCard");
		this.add(thanksPanel, "ThanksPanel");
		
		for(int j = 0; j < 13; j++)
		{
			this.add(confirmArray[j], j + "");
		}
		
		cards.show(this, "FirstCard");		
		
		cardTwo.add(home);
		cardTwo.add(b4);
		cardTwo.add(b5);
		cardTwo.add(b9);
		
		cardTwo.add(pepsiMax);
		
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
		
		thanksPanel.add(flipLabel, BorderLayout.CENTER);	
	}
	
	public int getSodaType()
	{
		return sodaType;
	}
	
	public Soda getSoda()
	{
		return can;
	}

	private class ChoiceButtons implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(((JButton)e.getSource()).equals(b1))
			{
				cards.show(VendingGUI.this, "0");
				sodaType = 1;
			}
			else if(((JButton)e.getSource()).equals(b2))
			{
				cards.show(VendingGUI.this, "1");
				sodaType = 2;
			}
			else if(((JButton)e.getSource()).equals(b3))
			{
				cards.show(VendingGUI.this, "2");
				sodaType = 3;
			}
			else if(((JButton)e.getSource()).equals(b4))
			{
				cards.show(VendingGUI.this, "3");
				sodaType = 4;	
			}
			else if(((JButton)e.getSource()).equals(b5))
			{
				cards.show(VendingGUI.this, "4");
				sodaType = 5;
			}
			else if(((JButton)e.getSource()).equals(b6))
			{
				cards.show(VendingGUI.this, "5");
				sodaType = 6;
			}
			else if(((JButton)e.getSource()).equals(b7))
			{
				cards.show(VendingGUI.this, "6");
				sodaType = 7;
			}
			else if(((JButton)e.getSource()).equals(b8))
			{	
				cards.show(VendingGUI.this, "7");
				sodaType = 8;
			}
			else if(((JButton)e.getSource()).equals(b9))
			{
				cards.show(VendingGUI.this, "8");
				sodaType = 9;
			}
			else if(((JButton)e.getSource()).equals(sweetTea))
			{
				cards.show(VendingGUI.this, "9");
				sodaType = 10;
			}
			else if(((JButton)e.getSource()).equals(wildCherryButton))
			{
				cards.show(VendingGUI.this, "10");
				sodaType = 11;
			}
			else if(((JButton)e.getSource()).equals(pepsiMax))
			{
				cards.show(VendingGUI.this, "11");
				sodaType = 12;
			}
			else if(((JButton)e.getSource()).equals(dietDew))
			{
				cards.show(VendingGUI.this, "12");
				sodaType = 13;
			}
		}
	}
	
	private class MasterButtons implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(((JButton)e.getSource()).equals(masterBrisk))
				cards.show(VendingGUI.this, "SecondCard");
			else if(((JButton)e.getSource()).equals(home))
				cards.show(VendingGUI.this, "FirstCard");
			else if(((JButton)e.getSource()).equals(home2))
				cards.show(VendingGUI.this, "FirstCard");
			else if(((JButton)e.getSource()).equals(home3))
				cards.show(VendingGUI.this, "FirstCard");
			else if(((JButton)e.getSource()).equals(masterPepsi))
				cards.show(VendingGUI.this, "ThirdCard");
			else if(((JButton)e.getSource()).equals(masterDew))
				cards.show(VendingGUI.this, "FourthCard");
		}
	}
	
	private class yesButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
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
			cards.show(VendingGUI.this, "ThanksPanel");
			
			System.out.println(sodaType + "");
			sodaType = 0;
			
			//cards.show(VendingGUI.this, "FirstCard");
			
			
		}
	}
	
	private class noButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			sodaType = 0;
			cards.show(VendingGUI.this, "FirstCard");
		}
	}
}
