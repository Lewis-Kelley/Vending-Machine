package java.cores;

class VendingMachineRunner {
    private enum State {
        sleeping, choosing, paying, returnMoney, delivering, delivered
    }

    private State state;

    public static PortMap portMap;
    //TODO implement Serial Comms classes
    //TODO implement file maintainence class
    //TODO implement IO handler class
    //TODO implement systems class

    public static void main(String[] args) {
        portMap = new PortMap();
    
        while(true) //Infinite loop
            switch(state) {
            case sleeping:
                //TODO implement sleeping state
                break;
            case choosing:
                //TODO implement choosing state
                break;
            case paying:
                //TODO implement paying state
                break;
            case returnMoney:
                //TODO implement returnMoney state
                break;
            case delivering:
            	//TODO implement delivering state
                break;
            case delivered:
                //TODO implement delivered state
                break;
            }
    }
}
