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
    public CardLayout cards;
	
    public Soda can; //Holds the currently selected can after the user confirms. Set to EMPTY when no can has been confirmed.
	
    public JLabel flipLabel; //Holds the spinning can gif
	
    public JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, masterBrisk, masterPepsi, home, home2, home3, wildCherryButton, pepsiMax, sweetTea, masterDew, dietDew; //All the various buttons used on each of the cards
	
    public JPanel cardOne, cardTwo, cardThree, cardFour, backPanel, thanksPanel; //The various cards.
	
    public JLabel[] sodaOption = new JLabel[13]; //Holds the images for the soda icons on the confirm screen.
    public JButton[] yesArray = new JButton[13]; //Holds the yes buttons
    public JButton[] noArray = new JButton[13]; //Holds the no buttons
    public JPanel[] confirmArray = new JPanel[13]; //Holds the yes, no, sodaOption, and youSure banners.
    public JPanel[] holder = new JPanel[13]; //Used to line up the yes, no, and sodaOption labels and buttons
    public JLabel[] youSure = new JLabel[13]; //The banner for the confirmation screen
	
    //All the images in the program
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
	
    //An int showing which soda has been selected, but not confirmed
    private int option, sodaType = 0;
	
    public void setUp()
    {
	this.setPreferredSize(new Dimension(690, 410));

	//Initialize flipLabel
	flipLabel = new JLabel(spinningCan);
	flipLabel.setIcon(spinningCan);
		
	//Initialize all the confirmation screen arrays
	sodaOption = new JLabel[13];
	yesArray = new JButton[13];
	noArray = new JButton[13];
	confirmArray = new JPanel[13];
	holder = new JPanel[13];
	youSure = new JLabel[13];

	can = Soda.EMPTY;
		
	pictureHolder = new ImageIcon();

	//Initialize all the buttons
	b1 = new JButton(mountainDew);
	b1.setBorderPainted(false);
	b1.setContentAreaFilled(false);
	b1.addActionListener(this);

	b2 = new JButton(mug);
	b2.setBorderPainted(false);
	b2.setContentAreaFilled(false);
	b2.addActionListener(this);
		 
	b3 = new JButton(pepsi);
	b3.setBorderPainted(false);
	b3.setContentAreaFilled(false);
	b3.addActionListener(this);
		
	b4 = new JButton(brisk);
	b4.setBorderPainted(false);
	b4.setContentAreaFilled(false);
	b4.addActionListener(this);
		
	b5 = new JButton(briskRaspberry);
	b5.setBorderPainted(false);
	b5.setContentAreaFilled(false);
	b5.addActionListener(this);

	b6 = new JButton(crush);
	b6.setBorderPainted(false);
	b6.setContentAreaFilled(false);
	b6.addActionListener(this);
		 
	b7 = new JButton(codeRed);
	b7.setBorderPainted(false);
	b7.setContentAreaFilled(false);
	b7.addActionListener(this);
		
	b8 = new JButton(dietPepsi);
	b8.setBorderPainted(false);
	b8.setContentAreaFilled(false);
	b8.addActionListener(this);
		
	b9 = new JButton(briskHalf);
	b9.setBorderPainted(false);
	b9.setContentAreaFilled(false);
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
	home.addActionListener(this);
		
	home2 = new JButton(back);
	home2.setBorderPainted(false);
	home2.setContentAreaFilled(false);
	home2.addActionListener(this);
		
	home3 = new JButton(back);
	home3.setBorderPainted(false);
	home3.setContentAreaFilled(false);
	home3.addActionListener(this);

	//Setting up all the confirmation screens
	for(int i = 0; i < 13; i++)
	    {
		yesArray[i] = new JButton(yesPic);
		yesArray[i].setBorderPainted(false);
		yesArray[i].setContentAreaFilled(false);
		yesArray[i].addActionListener(this);
		
		noArray[i] = new JButton(noPic);
		noArray[i].setBorderPainted(false);
		noArray[i].setContentAreaFilled(false);
		noArray[i].addActionListener(this);
			
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

		//Add the combined attributes to the confirmArray
		confirmArray[i].add(youSure[i], BorderLayout.NORTH);
		confirmArray[i].add(holder[i], BorderLayout.CENTER);
	    }

	//Create the cards
	cardOne = new JPanel();
	cardOne.setLayout(new FlowLayout());

	cardTwo = new JPanel();
	cardTwo.setLayout(new FlowLayout());

	cardThree = new JPanel();
	cardThree.setLayout(new FlowLayout());
		
	cardFour = new JPanel();
	cardFour.setLayout(new FlowLayout());
		
	thanksPanel = new JPanel();
	thanksPanel.setLayout(new BorderLayout());
				
	cards = new CardLayout();

	//Add all the cards to the display
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

	//Add all the buttons to all the cards
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

    /**
     * Returns the current value of sodaType.
     */
    public int getSodaType()
    {
	return sodaType;
    }

    /**
     * Returns the current value of can then sets to EMPTY
     */
    public Soda getSoda()
    {
	return can;
    }

    /**
     * Deals with all actions.
     */
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
	else if(((JButton)e.getSource()).equals(masterBrisk))
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
	else if(((JButton)e.getSource()).equals(noArray[0]) ||
		((JButton)e.getSource()).equals(noArray[1]) ||
		((JButton)e.getSource()).equals(noArray[2]) ||
		((JButton)e.getSource()).equals(noArray[3]) ||
		((JButton)e.getSource()).equals(noArray[4]) ||
		((JButton)e.getSource()).equals(noArray[5]) ||
		((JButton)e.getSource()).equals(noArray[6]) ||
		((JButton)e.getSource()).equals(noArray[7]) ||
		((JButton)e.getSource()).equals(noArray[8]) ||
		((JButton)e.getSource()).equals(noArray[9]) ||
		((JButton)e.getSource()).equals(noArray[10]) ||
		((JButton)e.getSource()).equals(noArray[11]) ||
		((JButton)e.getSource()).equals(noArray[12])) {
	    	    sodaType = 0;
		    cards.show(VendingGUI.this, "FirstCard");
	}
	    
	else if(((JButton)e.getSource()).equals(yesArray[0]) ||
		((JButton)e.getSource()).equals(yesArray[1]) ||
		((JButton)e.getSource()).equals(yesArray[2]) ||
		((JButton)e.getSource()).equals(yesArray[3]) ||
		((JButton)e.getSource()).equals(yesArray[4]) ||
		((JButton)e.getSource()).equals(yesArray[5]) ||
		((JButton)e.getSource()).equals(yesArray[6]) ||
		((JButton)e.getSource()).equals(yesArray[7]) ||
		((JButton)e.getSource()).equals(yesArray[8]) ||
		((JButton)e.getSource()).equals(yesArray[9]) ||
		((JButton)e.getSource()).equals(yesArray[10]) ||
		((JButton)e.getSource()).equals(yesArray[11]) ||
		((JButton)e.getSource()).equals(yesArray[12])) {
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
		default:
		    break;
		}
	    cards.show(VendingGUI.this, "ThanksPanel");

	    sodaType = 0;
	}
	else
	    {
		System.out.println("Uncaught event. The event was: " + (JButton)e.getSource());
	    }
    }
}
