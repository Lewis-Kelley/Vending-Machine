#include <Stepper.h>
#include <Servo.h>

const int TIME_DELAY = 200;
const int BUF_SIZE = 4;

const int STEPPER_MAIN = 9;
const int STEPPER_DIR  = 8;

const int STEPS_PER_COLUMN = 50;

const int MONEY_HOLDER_SIZE = 10;

const int MONEY_MCH_INPUT = 2;
const int MONEY_MCH_OUTPUT = 3;
const int BACK_LIM_SWITCH = 4;
const int FRONT_LIM_SWITCH = 5;

bool start; //Variable to check communications at startup
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
    //cont = true;
    start = false;

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

    center.attach(9);
    middle.attach(10);
    far.attach(11);

    acceptMoney = false;
    resetHolder();

    bufLen = 0;
}

/**
 * Called every tick. Place a delay if need be.
 */
void loop() {
    digitalWrite(MONEY_MCH_INPUT, HIGH);

    if(start && cont) {
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
    //Serial.println("In serialEvent");
    char c;
    while (Serial.available() && bufLen < BUF_SIZE) {
	c = (char)Serial.read();
	if (c == (char)'q') {
	    sentString = 1;
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
    if(((String)commBuffer).indexOf("STOP") >= 0) {
        Serial.println("Stopping");
	cont = false;
        clearCommBuffer();
    } else if (((String)commBuffer).indexOf("STRT") >= 0) {
        start = true;
        cont = true;
        Serial.println("STRT");
        clearCommBuffer();
    } else if(commBuffer[0] == '#') {
	railToPos(commBuffer[1]);
	setArms((int)commBuffer[2], (int)commBuffer[3]);
	armsReturnHome();

	railToBack();
	armsToDropoff();
	armsReturnHome();

	clearCommBuffer();
    } else if(((String)commBuffer).indexOf("NMNY") >= 0) {
	Serial.println("Acknowledged need money");
	acceptMoney = true;
        clearCommBuffer();
    } else if(((String)commBuffer).indexOf("CNCL") >= 0) {
	Serial.print("Acknowledged cancel");
	acceptMoney = false;
        clearCommBuffer();
    } else if(bufLen == 4) {
        Serial.print("Couldn't recognize ");
        Serial.println((String)commBuffer);
        clearCommBuffer();
    }
}

/**
 * Clears commBuffer.
 */
void clearCommBuffer() {
    for(short i = 0; i < BUF_SIZE; i++)
        commBuffer[i] = NULL;
    bufLen = 0;
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
short sumHolder() {
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
 * Moves stepper back until the back limit switch is triggered.
 */
void railToBack() {
    digitalWrite(STEPPER_DIR, HIGH);

    while(true) {
	if(analogRead(BACK_LIM_SWITCH) > 950)
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
 * Steps the steppers forward for a set coordinate.
 * Value 0 is home position, value 4 is front position.
 */
void railToPos(short value) {
    digitalWrite(STEPPER_DIR, LOW);

    if(value == 0)
	return;
    else if(value == 4) {
	railToFront();
	return;
    }

    for(short i = 0; i < value * STEPS_PER_COLUMN; i++) {
	digitalWrite(STEPPER_MAIN, LOW);
	delayMicroseconds(TIME_DELAY);
	digitalWrite(STEPPER_MAIN, HIGH);
	delayMicroseconds(TIME_DELAY);
    }
}

/**
 * Moves the stepper forward until the front limit switch is triggered.
 */
void railToFront() {
    digitalWrite(STEPPER_DIR, LOW);

    while(true) {
        if(analogRead(FRONT_LIM_SWITCH) > 950)
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
 * Takes the y and z portions of a coordinate and calls the necessary methods to grab the can at that position.
 */
void armsToCoordinate(int y, int z) {

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
