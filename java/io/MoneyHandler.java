package java.io;

class MoneyHandler {
	private class Money {
		//Amounts of all the different types 
		public byte ones;
		public byte fives;
		public byte tens;
		public byte twenties;
		
		/**
		 * Simply sets all amounts to 0.
		 */
		public Money() {
			ones = 0;
			fives = 0;
			tens = 0;
			twenties = 0;
		}
		
		/**
		 * Returns the total value of all types money in the object.
		 */
		protected short getValue() {
			return ones * 1 + fives * 5 + tens * 10 + twenties * 20
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
		inputCash.ones += added.ones;
		inputCash.fives += added.fives;
		inputCash.tens += added.tens;
		inputCash.twenties += added.twenties;
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
	public Money calcChange(Money input, short goal) {
		Money change = new Money();
		short difference = goal - inputCash.getValue();
		
		//Number of tens to return
		change.tens += (byte)(difference / 10);
		difference = goal - inputCash.getValue() - change.getValue();
		
		//Number of fives to return
		change.fives += (byte)(difference / 5);
		difference = goal - inputCash.getValue() - change.getValue();
		
		//Number of ones to return
		change.ones += (byte)(difference);
		
		return change;
	}
}
