import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public class VendingGUI extends JPanel implements ActionListener {
    private class AnimatedRunner implements Runnable {
	public void run() {
	    cards.show(VendingGUI.this, "Credits1Panel");
	    spinningCan.getImage().flush();
	}
    }

    private class WaitForFront implements Runnable {
	public void run() {
	    while(!reachedFront) {
		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		    System.out.println("Error waiting in WaitForFront");
		}
	    }

	    reachedFront = false;
	    cards.show(VendingGUI.this, "Credits2Panel");
	    (new Thread(new WaitForBack())).start();
	}
    }

    private class WaitForBack implements Runnable {
	public void run() {
	    while(!reachedBack) {
		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		    System.out.println("Error waiting in WaitForBack");
		}
	    }

	    reachedBack = false;
	    cards.show(VendingGUI.this, "Credits3Panel");
	    (new Thread(new WaitToReturn())).start();
	}
    }
    
    private class WaitToReturn implements Runnable {
	public void run() {
	    while(!finishedDelivery) {
		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		    System.out.println("Error waiting in WaitToReturn");
		}
	    }

	    finishedDelivery = false;
	    cards.show(VendingGUI.this, "MenuPanel");
	}
    }

    private class WaitForMoney implements Runnable {
	public void run() {
	    while(needMoney) {
		if(receivedMoney) {
		    receivedMoney = false;
		    needMoney = false;

		    //(new Thread(new AnimatedRunner())).start();
		    cards.show(VendingGUI.this, "Credits1Panel");
		    (new Thread(new WaitForFront())).start();
		}

		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		    System.out.println("Error waiting in WaitForMoney");
		}
	    }
	}
    }

    public CardLayout cards;

    public Soda can; //Holds the currently selected can after the user confirms. Set to EMPTY when no can has been confirmed.

    //Holds credits
    public JLabel credits1Label;
    public JLabel credits2Label;
    public JLabel credits3Label;
    
    public JLabel noSodaLabel; //Holds the noSoda image
    public JLabel disabledLabel; //Holds the disabled image

    public JButton mountainDewButton, mugButton, pepsiButton, briskLemonadeButton, briskRaspberryButton, crushButton, codeRedButton, dietPepsiButton, briskHalfButton, masterBriskButton, masterPepsiButton, briskHome, pepsiHome, dewHome, wildCherryButton, pepsiMaxButton, briskTeaButton, masterDewButton, dietDewButton, noSodaBackButton; //All the various buttons used on each of the cards

    public JPanel menuPanel, briskPanel, pepsiPanel, mountainDewPanel, credits1Panel, credits2Panel, credits3Panel, noSodaPanel, disabledPanel; //The various cards.

    public JLabel[] sodaOption = new JLabel[13]; //Holds the images for the soda icons on the confirm screen.
    public JButton[] cancelArray = new JButton[13]; //Holds the cancel buttons
    public JPanel[] payPanels = new JPanel[13]; //Holds the cancel, sodaOption, and pay $1 banners.
    public JLabel[] pay = new JLabel[13]; //The banner for the confirmation screen

    //All the images in the program
    public ImageIcon mountainDew = new ImageIcon("mountainDew.png");
    public ImageIcon mug = new ImageIcon("mug.png");
    public ImageIcon pepsi = new ImageIcon("pepsi.png");
    public ImageIcon briskLemonade= new ImageIcon("lemonade.png");
    public ImageIcon briskRaspberry = new ImageIcon("raspberry.png");
    public ImageIcon briskHalf = new ImageIcon("half.png");
    public ImageIcon dietPepsi = new ImageIcon("dietPepsi.png");
    public ImageIcon codeRed = new ImageIcon("codeRed.png");
    public ImageIcon crush = new ImageIcon("crush.png");
    public ImageIcon back = new ImageIcon("back.png");
    public ImageIcon brisk = new ImageIcon("masterBrisk.png");
    public ImageIcon cherry = new ImageIcon("wildCherry.png");
    public ImageIcon max = new ImageIcon("max.png");
    public ImageIcon genericBrisk = new ImageIcon("genericBrisk.png");
    public ImageIcon dewDiet = new ImageIcon("dietDew.png");
    public ImageIcon p = new ImageIcon("masterPepsi.png");
    public ImageIcon cancelPic = new ImageIcon("cancel.png");
    public ImageIcon sure = new ImageIcon("pay.png");
    public ImageIcon noSoda = new ImageIcon("noSoda.png");
    public ImageIcon disabled = new ImageIcon("disabled.png");
    public ImageIcon credits1 = new ImageIcon("Credits1.png");
    public ImageIcon credits2 = new ImageIcon("Credits2.png");
    public ImageIcon credits3 = new ImageIcon("Credits3.png");

    public ImageIcon spinningCan = new ImageIcon("sodaSpin.gif");

    private byte sodaExists = 0; //0 = no input, 1 = does exist, -1 = does not exist
    private boolean needMoney;
    private boolean receivedMoney;
    private boolean cancelled;
    private boolean finishedDelivery;
    private boolean reachedFront;
    private boolean reachedBack;
    
    public void setUp() {
	this.setPreferredSize(new Dimension(1024, 768));

	//Initialize credits1Label
	credits1Label = new JLabel(credits1);
	credits1Label.setIcon(credits1);

	//Initialize credits2Label
	credits2Label = new JLabel(credits2);
	credits2Label.setIcon(credits2);

	//Initialize credits3Label
	credits3Label = new JLabel(credits3);
	credits3Label.setIcon(credits3);
	
	//Initialize noSodaLabel
	noSodaLabel = new JLabel(noSoda);
	noSodaLabel.setIcon(noSoda);

	//Initialize disabledLabel
	disabledLabel = new JLabel(disabled);
	disabledLabel.setIcon(disabled);

	//Initialize all the confirmation screen arrays
	sodaOption = new JLabel[13];
	cancelArray = new JButton[13];
	payPanels = new JPanel[13];
	pay = new JLabel[13];

	can = Soda.EMPTY;

	sodaExists = 0;
	needMoney = false;
	receivedMoney = false;
	cancelled = false;
	finishedDelivery = false;
	reachedFront = false;
	reachedBack = false;

	//Initialize all the buttons
	mountainDewButton = new JButton(mountainDew);
	mountainDewButton.setBorderPainted(false);
	mountainDewButton.setContentAreaFilled(false);
	mountainDewButton.addActionListener(this);

	mugButton = new JButton(mug);
	mugButton.setBorderPainted(false);
	mugButton.setContentAreaFilled(false);
	mugButton.addActionListener(this);

	pepsiButton = new JButton(pepsi);
	pepsiButton.setBorderPainted(false);
	pepsiButton.setContentAreaFilled(false);
	pepsiButton.addActionListener(this);

	briskLemonadeButton = new JButton(briskLemonade);
	briskLemonadeButton.setBorderPainted(false);
	briskLemonadeButton.setContentAreaFilled(false);
	briskLemonadeButton.addActionListener(this);

	briskRaspberryButton = new JButton(briskRaspberry);
	briskRaspberryButton.setBorderPainted(false);
	briskRaspberryButton.setContentAreaFilled(false);
	briskRaspberryButton.addActionListener(this);

	crushButton = new JButton(crush);
	crushButton.setBorderPainted(false);
	crushButton.setContentAreaFilled(false);
	crushButton.addActionListener(this);

	codeRedButton = new JButton(codeRed);
	codeRedButton.setBorderPainted(false);
	codeRedButton.setContentAreaFilled(false);
	codeRedButton.addActionListener(this);

	dietPepsiButton = new JButton(dietPepsi);
	dietPepsiButton.setBorderPainted(false);
	dietPepsiButton.setContentAreaFilled(false);
	dietPepsiButton.addActionListener(this);

	briskHalfButton = new JButton(briskHalf);
	briskHalfButton.setBorderPainted(false);
	briskHalfButton.setContentAreaFilled(false);
	briskHalfButton.addActionListener(this);

	briskTeaButton = new JButton(brisk);
	briskTeaButton.setBorderPainted(false);
	briskTeaButton.setContentAreaFilled(false);
	briskTeaButton.addActionListener(this);

	wildCherryButton = new JButton(cherry);
	wildCherryButton.setBorderPainted(false);
	wildCherryButton.setContentAreaFilled(false);
	wildCherryButton.addActionListener(this);

	pepsiMaxButton = new JButton(max);
	pepsiMaxButton.setBorderPainted(false);
	pepsiMaxButton.setContentAreaFilled(false);
	pepsiMaxButton.addActionListener(this);

	dietDewButton = new JButton(dewDiet);
	dietDewButton.setBorderPainted(false);
	dietDewButton.setContentAreaFilled(false);
	dietDewButton.addActionListener(this);

	masterBriskButton = new JButton(genericBrisk);
	masterBriskButton.setBorderPainted(false);
	masterBriskButton.setContentAreaFilled(false);
	masterBriskButton.addActionListener(this);

	masterPepsiButton = new JButton(p);
	masterPepsiButton.setBorderPainted(false);
	masterPepsiButton.setContentAreaFilled(false);
	masterPepsiButton.addActionListener(this);

	masterDewButton = new JButton(mountainDew);
	masterDewButton.setBorderPainted(false);
	masterDewButton.setContentAreaFilled(false);
	masterDewButton.addActionListener(this);

	briskHome = new JButton(back);
	briskHome.setBorderPainted(false);
	briskHome.setContentAreaFilled(false);
	briskHome.addActionListener(this);

	pepsiHome = new JButton(back);
	pepsiHome.setBorderPainted(false);
	pepsiHome.setContentAreaFilled(false);
	pepsiHome.addActionListener(this);

	dewHome = new JButton(back);
	dewHome.setBorderPainted(false);
	dewHome.setContentAreaFilled(false);
	dewHome.addActionListener(this);

	noSodaBackButton = new JButton(back);
	noSodaBackButton.setBorderPainted(false);
	noSodaBackButton.setContentAreaFilled(false);
	noSodaBackButton.addActionListener(this);

	//Setting up all the payment screens
	for(int i = 0; i < 13; i++) {
	    cancelArray[i] = new JButton(cancelPic);
	    cancelArray[i].setBorderPainted(false);
	    cancelArray[i].setContentAreaFilled(false);
	    cancelArray[i].addActionListener(this);

	    switch(i) {
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
		sodaOption[i] = new JLabel(briskLemonade);
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
		sodaOption[i] = new JLabel(brisk);
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
	    pay[i] = new JLabel(sure);

	    payPanels[i] = new JPanel();
	    payPanels[i].setLayout(new BorderLayout());

	    //Add the combined attributes to the payPanels
	    payPanels[i].add(pay[i], BorderLayout.NORTH);
	    payPanels[i].add(sodaOption[i], BorderLayout.CENTER);
	    payPanels[i].add(cancelArray[i], BorderLayout.SOUTH);
	}

	//Create the cards
	menuPanel = new JPanel();
	menuPanel.setLayout(new FlowLayout());
	menuPanel.setBackground(Color.white);

	briskPanel = new JPanel();
	briskPanel.setLayout(new FlowLayout());
	briskPanel.setBackground(Color.white);

	pepsiPanel = new JPanel();
	pepsiPanel.setLayout(new FlowLayout());
	pepsiPanel.setBackground(Color.white);

	mountainDewPanel = new JPanel();
	mountainDewPanel.setLayout(new FlowLayout());
	mountainDewPanel.setBackground(Color.white);

	credits1Panel = new JPanel();
	credits1Panel.setLayout(new FlowLayout());
	credits1Panel.setBackground(Color.white);

	credits2Panel = new JPanel();
	credits2Panel.setLayout(new FlowLayout());
	credits2Panel.setBackground(Color.white);

	credits3Panel = new JPanel();
	credits3Panel.setLayout(new FlowLayout());
	credits3Panel.setBackground(Color.white);

	noSodaPanel = new JPanel();
	noSodaPanel.setLayout(new BorderLayout());
	noSodaPanel.setBackground(Color.white);

	disabledPanel = new JPanel();
	disabledPanel.setLayout(new BorderLayout());
	disabledPanel.setBackground(Color.white);

	cards = new CardLayout();

	//Add all the cards to the display
	this.setLayout(cards);
	this.setBackground(Color.blue);

	this.add(menuPanel, "MenuPanel");
	this.add(briskPanel, "BriskPanel");
	this.add(pepsiPanel, "PepsiPanel");
	this.add(mountainDewPanel, "MountainDewPanel");
	this.add(credits1Panel, "Credits1Panel");
	this.add(credits2Panel, "Credits2Panel");
	this.add(credits3Panel, "Credits3Panel");
	this.add(noSodaPanel, "NoSodaPanel");
	this.add(disabledPanel, "DisabledPanel");

	for(int j = 0; j < 13; j++)
	    this.add(payPanels[j], j + "");

	//Add all the buttons to all the cards
	cards.show(this, "MenuPanel");

	briskPanel.add(briskHome);
	briskPanel.add(briskLemonadeButton);
	briskPanel.add(briskRaspberryButton);
	briskPanel.add(briskHalfButton);
	briskPanel.add(briskTeaButton);

	pepsiPanel.add(pepsiHome);
	pepsiPanel.add(pepsiButton);
	pepsiPanel.add(dietPepsiButton);
	pepsiPanel.add(wildCherryButton);
	pepsiPanel.add(pepsiMaxButton);

	mountainDewPanel.add(dewHome);
	mountainDewPanel.add(mountainDewButton);
	mountainDewPanel.add(codeRedButton);
	mountainDewPanel.add(dietDewButton);

	menuPanel.add(mugButton);
	menuPanel.add(crushButton);
	menuPanel.add(masterPepsiButton);
	menuPanel.add(masterBriskButton);
	menuPanel.add(masterDewButton);

	credits1Panel.add(credits1Label, BorderLayout.CENTER);
	credits2Panel.add(credits2Label, BorderLayout.CENTER);
	credits3Panel.add(credits3Label, BorderLayout.CENTER);

	noSodaPanel.add(noSodaLabel, BorderLayout.NORTH);
	noSodaPanel.add(noSodaBackButton, BorderLayout.SOUTH);

	disabledPanel.add(disabledLabel, BorderLayout.CENTER);
    }

    /**
     * Returns the current value of can then sets to EMPTY
     */
    public Soda getSoda() {
	Soda retVal;

	switch(can) {
	case BRISK_HALF_AND_HALF:
	    retVal = Soda.BRISK_HALF_AND_HALF;
	    break;
	case BRISK_RASPBERRY:
	    retVal = Soda.BRISK_RASPBERRY;
	    break;
	case BRISK_LEMONADE:
	    retVal = Soda.BRISK_LEMONADE;
	    break;
	case BRISK_SWEET_TEA:
	    retVal = Soda.BRISK_SWEET_TEA;
	    break;
	case PEPSI:
	    retVal = Soda.PEPSI;
	    break;
	case DIET_PEPSI:
	    retVal = Soda.DIET_PEPSI;
	    break;
	case PEPSI_WILD_CHERRY:
	    retVal = Soda.PEPSI_WILD_CHERRY;
	    break;
	case PEPSI_MAX:
	    retVal = Soda.PEPSI_MAX;
	    break;
	case MOUNTAIN_DEW:
	    retVal = Soda.MOUNTAIN_DEW;
	    break;
	case DIET_MOUNTAIN_DEW:
	    retVal = Soda.DIET_MOUNTAIN_DEW;
	    break;
	case MOUNTAIN_DEW_CODE_RED:
	    retVal = Soda.MOUNTAIN_DEW_CODE_RED;
	    break;
	case DIET_MOUNTAIN_DEW_CODE_RED:
	    retVal = Soda.DIET_MOUNTAIN_DEW_CODE_RED;
	    break;
	case DIET_CRUSH:
	    retVal = Soda.DIET_CRUSH;
	    break;
	case DIET_MUG:
	    retVal = Soda.DIET_MUG;
	    break;
	default:
	    retVal = Soda.EMPTY;
	    break;
	}

	can = Soda.EMPTY;
	return retVal;
    }

    /**
     * Methods for main to use to communicate.
     */
    public void setFoundStatus(byte status) {
	sodaExists = status;
    }

    public void setReceivedStatus(boolean status) {
	receivedMoney = status;
    }

    public void setFinishedDelivery(boolean status) {
	finishedDelivery = status;
    }

    public void setReachedFront(boolean status) {
	reachedFront = status;
    }

    public void setReachedBack(boolean status) {
	reachedBack = status;
    }

    public void showDisabled() {
	cards.show(VendingGUI.this, "DisabledPanel");
    }

    public boolean getMoneyStatus() {
	return needMoney;
    }

    public boolean getCancelStatus() {
	boolean cancel = cancelled;
	cancelled = false;
	return cancel;
    }

    /**
     * Deals with all actions.
     */
    public void actionPerformed(ActionEvent e) {
	if(((JButton)e.getSource()).equals(mountainDewButton)) {
	    can = Soda.MOUNTAIN_DEW;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "0");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(mugButton)) {
	    can = Soda.DIET_MUG;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "1");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(pepsiButton)) {
	    can = Soda.PEPSI;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "2");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(briskLemonadeButton)) {
	    can = Soda.BRISK_LEMONADE;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "3");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(briskRaspberryButton)) {
	    can = Soda.BRISK_RASPBERRY;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "4");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(crushButton)) {
	    can = Soda.DIET_CRUSH;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "5");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(codeRedButton)) {
	    can = Soda.MOUNTAIN_DEW_CODE_RED;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "6");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(dietPepsiButton)) {
	    can = Soda.DIET_PEPSI;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "7");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(briskHalfButton)) {
	    can  = Soda. BRISK_HALF_AND_HALF;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "8");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(briskTeaButton)) {
	    can = Soda.BRISK_SWEET_TEA;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "9");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(wildCherryButton)) {
	    can = Soda.PEPSI_WILD_CHERRY;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "10");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(pepsiMaxButton)) {
	    can  = Soda.PEPSI_MAX;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "11");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(dietDewButton)) {
	    can  = Soda.DIET_MOUNTAIN_DEW;

	    while(sodaExists == 0) { //Wait for update from main
		try {
		    Thread.sleep(500);
		} catch(InterruptedException error) {
		    System.err.println(error);
		}
	    }

	    if(sodaExists == 1) {
		cards.show(VendingGUI.this, "12");
		needMoney = true;
	    } else
		cards.show(VendingGUI.this, "NoSodaPanel");

	    sodaExists = 0;
	    (new Thread(new WaitForMoney())).start();
	}
	else if(((JButton)e.getSource()).equals(masterBriskButton))
	    cards.show(VendingGUI.this, "BriskPanel");
	else if(((JButton)e.getSource()).equals(briskHome))
	    cards.show(VendingGUI.this, "MenuPanel");
	else if(((JButton)e.getSource()).equals(pepsiHome))
	    cards.show(VendingGUI.this, "MenuPanel");
	else if(((JButton)e.getSource()).equals(dewHome))
	    cards.show(VendingGUI.this, "MenuPanel");
	else if(((JButton)e.getSource()).equals(masterPepsiButton))
	    cards.show(VendingGUI.this, "PepsiPanel");
	else if(((JButton)e.getSource()).equals(masterDewButton))
	    cards.show(VendingGUI.this, "MountainDewPanel");
	else if (((JButton)e.getSource()).equals(noSodaBackButton))
	    cards.show(VendingGUI.this, "MenuPanel");
	else if(((JButton)e.getSource()).equals(cancelArray[0]) ||
		((JButton)e.getSource()).equals(cancelArray[1]) ||
		((JButton)e.getSource()).equals(cancelArray[2]) ||
		((JButton)e.getSource()).equals(cancelArray[3]) ||
		((JButton)e.getSource()).equals(cancelArray[4]) ||
		((JButton)e.getSource()).equals(cancelArray[5]) ||
		((JButton)e.getSource()).equals(cancelArray[6]) ||
		((JButton)e.getSource()).equals(cancelArray[7]) ||
		((JButton)e.getSource()).equals(cancelArray[8]) ||
		((JButton)e.getSource()).equals(cancelArray[9]) ||
		((JButton)e.getSource()).equals(cancelArray[10]) ||
		((JButton)e.getSource()).equals(cancelArray[11]) ||
		((JButton)e.getSource()).equals(cancelArray[12])) {
	    cards.show(VendingGUI.this, "MenuPanel");
	    needMoney = false;
	    cancelled = true;
	}
	else {
	    System.out.println("Uncaught event. The event was: " + (JButton)e.getSource());
	}
    }
}
