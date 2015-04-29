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
                //TODO implement sleeping state
                break;
            case MENU:
                //TODO implement menu state
                break;
            case SHOWING_SODA:
                //TODO implement showing soda state
                break;
            case PAYING:
                //TODO implement paying state
                break;
            case RETURN_MONEY:
                //TODO implement returnMoney state
                break;
            case DELIVERING:
            	//TODO implement delivering state
                break;
            case DELIVERED:
                //TODO implement delivered state
                break;
            }
    }
}
