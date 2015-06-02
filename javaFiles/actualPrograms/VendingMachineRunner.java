import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public class VendingMachineRunner {
	public enum State { MENU, PAYING, DELIVERING, DELIVERED, DISABLED };

	private State state;
	private Coordinate coord;
	private Inventory inv;

	private JFrame gui;
	private VendingGUI vGUI;

/*	private BenSerialListener serial; */

	//TODO implement IO handler class
	//TODO implement systems class

	public VendingMachineRunner()
	{
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
/*		serial = new BenSerialListener("/dev/ttyACM0"); */
	}
	public static void main(String[] args) {
		VendingMachineRunner vmr = new VendingMachineRunner();

		while(true) {
			switch(vmr.getState()) {
			case MENU:
				vmr.menu();
				break;
			case PAYING:
				vmr.paying();
				break;
			case DELIVERING:
				vmr.delivering();
				break;
			case DELIVERED:
				vmr.delivered();
				break;
			case DISABLED:
				vmr.disabled();
				break;
			}
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				System.err.println(e);
			}
		}
	}

	/*
	 * Gets the current state of the machine.
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * Display welcome screen and wait for touchscreen input.
	 */
	private void sleeping() {
		//TODO implement
	}

	/**
	 * Display menu screen.
	 * Ends when option selected or timeout is reached.
	 * If timeout is reached, return to sleeping state.
	 */
	private void menu() {
		Soda holder = vGUI.getSoda();
		if(!holder.equals(Soda.EMPTY))
			System.out.println("In main, can is: " + holder);
	}

	/*
	 * Continue showing the soda picture with a new confirm dialog while the customer pays.
	 * Waits for total price to be inserted and confirm button to be pressed,
	 * cancel button to be pressed, or for a certain amount of time to pass with no input.
	 */
	private void paying() {
		//TODO implement
	}

	/*
	 * Changes screen to waiting.
	 * Moves arms to grab can.
	 * Ends when can reaches customer or timeout is reached.
	 * Arm will end up in release position, not home position.
	 * If timeout is reached, return money and display "Needs maintainence" and disable.
	 */
	private void delivering() {
		//TODO implement
	}

	/*
	 * Changes screen to "Come again" and moves arm back to home position.
	 * Ends when arm reaches home position or when timeout is reached.
	 * If timeout is reached, display "Needs maintainence" and disable.
	 */
	private void delivered() {
		//TODO implement
	}

	/*
	 * Display "Needs maintaince" never leave state until rebooted.
	 */
	private void disabled() {
		//TODO implement
	}
}
