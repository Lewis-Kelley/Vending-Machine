#include <Stepper.h>
#include <PololuMaestro.h>

#ifdef SERIAL_PORT_HARDWARE_OPEN
    #define maestroSerial SERIAL_PORT_HARDWARE_OPEN
#else
    #include <SoftwareSerial.h>
    SoftwareSerial maestroSerial(10, 11);
#endif

const int NUM_ROWS = 15;

//Coordinates of the dropoff
const int DROPOFF_Y = 7; //Guess
const int DROPOFF_Z = 1;

const int TIME_DELAY = 200;
const int BUF_SIZE = 4;

const int STEPS_PER_COLUMN = 6000;

const int MONEY_HOLDER_SIZE = 10;

//Digital
const int MONEY_MCH_INPUT = 2;
const int MONEY_MCH_OUTPUT = 3;
const int STEPPER_DIR = 8;
const int STEPPER_MAIN = 9;

//Analog
const int BACK_LIM_SWITCH = 1;
const int FRONT_LIM_SWITCH = 0;

MicroMaestro maestro(maestroSerial); //May need to be MiniMaestro

bool start; //Variable to check communications at startup
bool cont; //Variable to control if the program should halt

char commBuffer[BUF_SIZE]; //Holds messsages from the computer
int bufLen;
int sentString;

//Variables to help with the money machine
int moneyHolder[MONEY_HOLDER_SIZE];
bool acceptMoney;

/**
 * Called once to set everything up.
 */
void setup() {
    start = false;

    //These two might be mutually exclusive, but I doubt it
    maestroSerial.begin(9600);
    Serial.begin(9600);

    //Initialize MoneyMachine pins
    pinMode(MONEY_MCH_OUTPUT, INPUT);
    pinMode(MONEY_MCH_INPUT, OUTPUT);
    digitalWrite(MONEY_MCH_INPUT, HIGH);

    //Initialize stepper pins
    pinMode(STEPPER_MAIN, OUTPUT);
    pinMode(STEPPER_DIR, OUTPUT);
    digitalWrite(STEPPER_MAIN, HIGH);
    digitalWrite(STEPPER_DIR, LOW);

    acceptMoney = false;
    resetHolder();

    bufLen = 0;
}

/**
 * Called every tick. Place a delay if need be.
 */
void loop() {
    if(start && cont) {
	if(analogRead(FRONT_LIM_SWITCH) >= 1020) { //Check if front limit switch is triggered for a reset
	    Serial.println("RSET");
	    railToPos(1);
	    railToBack();
	}
	
        if(acceptMoney) {
	    digitalWrite(MONEY_MCH_OUTPUT, HIGH);
	    checkForMoney();
	} else
	    digitalWrite(MONEY_MCH_OUTPUT, LOW);
    }

    delay(500);
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
    } else if(commBuffer[0] == '#' && commBuffer[3] != NULL) {
        railToPos((byte)commBuffer[1] - 48); //- 48 from ASCII conversion
	armsToCoordinate((byte)commBuffer[2] - 48, (byte)commBuffer[3] - 48);
	closeClaw();
	armsReturnHome();

	railToBack();
	armsToDropoff();
	openClaw();
	armsReturnHome();

	clearCommBuffer();
        Serial.println("FNDL");
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
	if(analogRead(BACK_LIM_SWITCH) >= 1020)
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
void railToPos(byte value) {
    digitalWrite(STEPPER_DIR, LOW);

    if(value == 0) {
        railToBack();
	return;
    }
    else if(value == 4) {
	railToFront();
	return;
    }

    for(short ct = 0; ct < value * STEPS_PER_COLUMN / 10; ct++) {
        if(analogRead(FRONT_LIM_SWITCH) >= 1020) { //Hit front limit switch; there's a problem so try again
            railToBack();
            railToPos(value);
            break;
        }
        for(short i = 0; i < 10; i++) {
	    digitalWrite(STEPPER_MAIN, LOW);
	    delayMicroseconds(TIME_DELAY);
	    digitalWrite(STEPPER_MAIN, HIGH);
	    delayMicroseconds(TIME_DELAY);
        }
    }
}

/**
 * Moves the stepper forward until the front limit switch is triggered.
 */
void railToFront() {
    digitalWrite(STEPPER_DIR, LOW);

    while(true) {
        if(analogRead(FRONT_LIM_SWITCH) >= 1020)
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
 * Assumes that there is one script for each y-z combo and that they are numbered as:
 * y:z:script
 * 0:0:0
 * 1:0:1
 * 2:0:2
 * ...
 * 14:0:14
 * 0:1:15
 * 1:1:16
 * ...
 * home:30
 */
void armsToCoordinate(byte y, byte z) {
    maestro.restartScript(NUM_ROWS * z + y);
    while(maestro.getScriptStatus() == 0); //Waits for script to finish
    maestro.stopScript();
}

/**
 * Moves arms back to home position.
 */
void armsReturnHome() {
    maestro.restartScript(NUM_ROWS + 9); //Coordinate for dropoff
    while(maestro.getScriptStatus() == 0); //Waits for script to finish
    maestro.stopScript();
}

/**
 * Moves arms into dropoff position.
 */
void armsToDropoff() {
    armsToCoordinate(DROPOFF_Y, DROPOFF_Z);
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
