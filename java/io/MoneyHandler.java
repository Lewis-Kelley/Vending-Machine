package java.io;

class MoneyHandler {
	private class Money {
		//Amounts of all the different types 
		public byte nickels;
		public byte dimes;
		public byte quarters;
		public byte oneDollars;
		public byte fiveDollars;
		public byte tenDollars;
		public byte twentyDollars;
		
		/**
		 * Simply sets all amounts to 0.
		 */
		public Money() {
			nickels = 0;
			dimes = 0;
			quarters = 0;
			oneDollars = 0;
			fiveDollars = 0;
			tenDollars = 0;
			twentyDollars = 0;
		}
		
		/**
		 * Returns the total value of all types money in the object.
		 */
		protected float getValue() {
			return nickels * 0.05f + dimes * 0.1f + quarters * 0.25f
			       + oneDollars * 1.0f + fiveDollars * 5.0f + tenDollars * 10.0f + twentyDollars * 20.0f;
		}
	}

	private Money inputCash; //Amount of money input into the machine.

	/**
	 * Initializes inputCash to an empty Money object, hopefully initializes the ports for the physical money machine.
	 */
	public MoneyHandler() {
		inputCash = new Money();
		
		//TODO implement money machine port
	}
	
	/**
	 * Takes one Money object and adds each value to the inputCash object.
	 */
	public void addMoney(Money added) {
		inputCash.nickels += added.nickels;
		inputCash.dimes += added.dimes;
		inputCash.quarters += added.quarters;
		inputCash.oneDollars += added.oneDollars;
		inputCash.fiveDollars += added.fiveDollars;
		inputCash.tenDollars += added.tenDollars;
		inputCash.twentyDollars += added.twentyDollars;
	}
	
	/**
	 * Returns all input money to the customer (before purchase).
	 */
	public void returnMoney() {
		//TODO implement
	}
	
	/**
	 * Returns change to the customer (after purchase).
	 */
	public void returnChange() {
		//TODO implement
	}
	
	/**
	 * Takes an amount of money input (over the amount) and a goal (the actual needed value)
	 * and returns a Money object of the amount of money to give in change.
	 */
	public Money calcChange(Money input, float goal) {
		Money change = new Money();
		float difference = goal - inputCash.getValue();
		
		//Number of quarters to return
		change.quarters += (byte)(difference / 0.25);
		difference = goal - inputCash.getValue() - change.getValue();
		
		//Number of dimes to return
		change.dimes += (byte)(difference / 0.10);
		difference = goal - inputCash.getValue() - change.getValue();
		
		//Number of nickels to return
		change.nickels += (byte)(difference / 0.05);
		
		return change;
	}
}
