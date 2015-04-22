package java.cores;

class VendingMachineRunner {
    private enum State {
        SLEEPING, CHOOSING, PAYING, RETURN_MONEY, DELIVERING, DELIVERED
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
            case CHOOSING:
                //TODO implement choosing state
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
