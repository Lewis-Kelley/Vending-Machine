#include <Stepper.h>
#include <Servo.h>

const int TIME_DELAY = 200;
const int BUF_SIZE = 256;
    
const int STEPPER_MAIN = 9;
const int STEPPER_DIR  = 8;

const int MONEY_HOLDER_SIZE = 10;

const int BACK_LIM_SWITCH = 2;
const int FRONT_LIM_SWITCH = 3;
const int MONEY_MCH_OUTPUT = 4;
const int MONEY_MCH_INPUT = 5;

bool cont; //Variable to control if the program should halt

char commBuffer[BUF_SIZE]; //Holds messsages from the computer
int bufLen;
int sentString;

Servo center;
Servo middle;
Servo far;

// give the motor control pins names:
const int pwmA = 3;
const int pwmB = 11;
const int brakeA = 9;
const int brakeB = 8;
const int dirA = 12;
const int dirB = 13;

//Variables to help with the money machine
int moneyHolder[MONEY_HOLDER_SIZE];
bool acceptMoney;

/**
 * Called once to set everything up.
 */
void setup() {
    Serial.begin(9600);
    // set the PWM and brake pins so that the direction pins  // can be used to control the motor:
    pinMode(pwmA, OUTPUT);
    pinMode(pwmB, OUTPUT);
    pinMode(brakeA, OUTPUT);
    pinMode(brakeB, OUTPUT);
    digitalWrite(pwmA, HIGH);
    digitalWrite(pwmB, HIGH);
    digitalWrite(brakeA, LOW);
    digitalWrite(brakeB, LOW);

    //Initialize MoneyMachine pins
    pinMode(MONEY_MCH_OUTPUT, INPUT);
    pinMode(MONEY_MCH_INPUT, OUTPUT);

    // initialize the serial port:
    Serial.begin(9600);

    center.attach(9);
    middle.attach(10);
    far.attach(11);

    acceptMoney = false;
}

/**
 * Called every tick. Place a delay if need be.
 */
void loop() {
    if(cont) {
	if(acceptMoney) {
	    digitalWrite(MONEY_MCH_OUTPUT, HIGH);
	    checkForMoney();
	} else
	    digitalWrite(MONEY_MCH_OUTPUT, LOW);
    }
}

/**
 * Handles reading from serial.
 */
void serialEvent() {
    char c;
    while (Serial.available() && bufLen < BUF_SIZE) {
	c = (char)Serial.read();
	if (c == (char)'q') {
	    sentString = 1;
	    Serial.println("String: \"" + String(commBuffer) + "\"");
	    return;
	}
	commBuffer[bufLen++] = c;
    }

    readMsg();
}

/**
 * Updates proper variables according to
 * the transmitted code.
 */
void readMsg() {
    if(((String)commBuffer).equals("STOP"))
	cont = false;
    //else if(commBuffer[0] == '#')
    else if(((String)commBuffer).equals("NMNY"))
	acceptMoney = true;
    else if(((String)commBuffer).equals("CNCL"))
	acceptMoney = false;
}

/**
 * Sends string back to the computer.
 */
void sendString(String s) {
    Serial.println(s);
}


/**
 * Checks for money to be added.
 * If money is added, it sends that to main and changes acceptMoney to false.
 */
void checkForMoney() {
    updateHolder();
    if(sumHolder() < 7) {
	resetHolder();
	Serial.println("GMNY");
	acceptMoney = false;
    }
}

/**
 * Waits for a $1 bill to be entered.
 */
void waitForMoney() {
    while(true) {
	digitalWrite(MONEY_MCH_OUTPUT, HIGH);

	updateHolder();

	if(sumHolder() < 7)
	    break;

	delay(15);
    }
}

/**
 * Slides each cell og holder up, and resets holder[0] to
 * the current sensor value.
 */
void updateHolder() {
    for(short i = MONEY_HOLDER_SIZE - 1; i > 0; i--)
	moneyHolder[i] = moneyHolder[i - 1];
  
    moneyHolder[0] = digitalRead(MONEY_MCH_INPUT);
}

/**
 * Returns the total sum of holder.
 */
short  sumHolder() {
    short sum = 0;
    
    for(short i = 0; i < 10; i++)
	sum += moneyHolder[i];

    return sum;
}

void resetHolder() {
    for(short i = 0; i < MONEY_HOLDER_SIZE; i++)
      moneyHolder[i] = 1;
}

/**
 * Moves stepper back until the limit switch is triggered.
 */
void railReturnHome() {
    digitalWrite(STEPPER_DIR, HIGH);

    while(true) {
	if(analogRead(1) > 950)
	    break;

	for(short i = 0; i < 10; i++) {
	    digitalWrite(STEPPER_MAIN, LOW);
	    delayMicroseconds(TIME_DELAY);
	    digitalWrite(STEPPER_MAIN, HIGH);
	    delayMicroseconds(TIME_DELAY);
	}
    }
}

/**
 * Steps the steppers forward for a set value.
 * Value 0 is home position.
 */
void railToPos(int value) {
    digitalWrite(STEPPER_DIR, LOW);

    for(short i = 0; i < value; i++) {
	digitalWrite(STEPPER_MAIN, LOW);
	delayMicroseconds(TIME_DELAY);
	digitalWrite(STEPPER_MAIN, HIGH);
	delayMicroseconds(TIME_DELAY);
    }
}

/**
 * Moves arms back to home position.
 */
void armsReturnHome() {

}

/**
 * Moves arms into dropoff position.
 */
void armsToDropoff() {

}

/**
 * Moves arms to 3 values. First is central arm, and so on.
 */
void setArms(int first, int second, int third) { //May need to stagger into steps so the arms don't bang against the sides.
    center.writeMicroseconds(first);
    middle.writeMicroseconds(second);
    far.writeMicroseconds(third);
}

/**
 * Opens claw.
 */
void openClaw() {

}

/**
 * Closes claw.
 */
void closeClaw() {

}

/**
 * Returns signal to PC saying that the current step is completed.
 */
void sayFinished() {

}
