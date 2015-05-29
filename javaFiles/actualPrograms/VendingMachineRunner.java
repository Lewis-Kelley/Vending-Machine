import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font; 
import javax.swing.plaf.FontUIResource; 

class VendingMachineRunner {
    public enum State {
        SLEEPING, MENU, PAYING, DELIVERING, DELIVERED, DISABLED
    }

    private static State state;
    private static Coordinate coord;
    
    private static Inventory inv;

    private static JFrame gui;
    private static VendingGUI vGUI;

    //TODO implement Serial Comms classes
    //TODO implement IO handler class
    //TODO implement systems class

    public static void main(String[] args) {
        state = State.MENU;
        coord = null;
        
        inv = new Inventory(true);
        
	gui = new JFrame();
	vGUI = new VendingGUI();
	vGUI.setUp();
	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.add(vGUI);
	gui.pack();
	gui.setVisible(true);

	while(true) //Infinite loop
            switch(state) {
            case SLEEPING:
                sleeping();
                break;
            case MENU:
                menu();
                break;
            case PAYING:
                paying();
                break;
            case DELIVERING:
            	delivering();
                break;
            case DELIVERED:
                delivered();
                break;
            case DISABLED:
                disabled();
                break;
            }
    }
    
    /**
     * Display welcome screen and wait for touchscreen input.
     */
    private static void sleeping() {
       //TODO implement 
    }
    
    /**
     * Display menu screen.
     * Ends when option selected or timeout is reached.
     * If timeout is reached, return to sleeping state.
     */
    private static void menu() {
       
    }
    
    /**
     * Continue showing the soda picture with a new confirm dialog while the customer pays.
     * Waits for total price to be inserted and confirm button to be pressed,
     * cancel button to be pressed, or for a certain amount of time to pass with no input.
     */
    private static void paying() {
        //TODO implement
    }
    
    /**
     * Changes screen to waiting.
     * Moves arms to grab can.
     * Ends when can reaches customer or timeout is reached.
     * Arm will end up in release position, not home position.
     * If timeout is reached, return money and display "Needs maintainence" and disable.
     */
    private static void delivering() {
        //TODO implement
    }
    
    /**
     * Changes screen to "Come again" and moves arm back to home position.
     * Ends when arm reaches home position or when timeout is reached.
     * If timeout is reached, display "Needs maintainence" and disable.
     */
    private static void delivered() {
        //TODO implement
    }
    
    /**
     * Display "Needs maintaince" never leave state until rebooted.
     */
    private static void disabled() {
        //TODO implement
    }
}
