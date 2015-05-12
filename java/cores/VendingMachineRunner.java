package java.cores;

class VendingMachineRunner {
    public enum State {
        SLEEPING, MENU, SHOWING_SODA, PAYING, RETURN_MONEY, DELIVERING, DELIVERED
    }

    private State state;

    //TODO implement Serial Comms classes
    //TODO implement file maintainence class
    //TODO implement IO handler class
    //TODO implement systems class

    public static void main(String[] args) {
        while(true) //Infinite loop
            switch(state) {
            case SLEEPING:
                sleeping();
                break;
            case MENU:
                menu();
                break;
            case SHOWING_SODA:
                showingSoda();
                break;
            case PAYING:
                paying();
                break;
            case RETURN_MONEY:
                returnMoney();
                break;
            case DELIVERING:
            	delivering();
                break;
            case DELIVERED:
                delivered();
                break;
            }
    }
    
    /**
     * Wait for input on touchscreen
     */
    private void sleeping() {
       //TODO implement 
    }
    
    /**
     * Display menu and wait for selection
     */
    private void menu() {
        //TODO implement
    }
    
    /**
     * Display picture of soda with a confirmation dialog
     */
    private void showingSoda() {
        //TODO implement
    }
    
    /**
     * Continue showing the soda picture with a new confirm dialog while the customer pays.
     * Waits for total price to be inserted and confirm button to be pressed,
     * cancel button to be pressed, or for a certain amount of time to pass with no input.
     */
    private void paying() {
     //TODO implement
    }
     
    /**
     * Return input money when customer cancels order.
     * Display changes to "Come again" screen.
     */
    private void returnMoney() {
        //TODO implement
    }
    
    /**
     * Changes screen to waiting.
     * Moves arms to grab can.
     * Ends when can reaches customer or timeout is reached.
     * Arm will end up in release position, not home position.
     * If timeout is reached, return money and display "Needs maintainence" and disable.
     */
    private void delivering() {
        //TODO implement
    }
    
    /**
     * Changes screen to "Come again" and moves arm back to home position.
     * Ends when arm reaches home position or when timeout is reached.
     * If timeout is reached, display "Needs maintainence" and disable.
     */
    private void delivered() {
        //TODO implement
    }
}
