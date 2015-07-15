#include <Stepper.h>
#include <PololuMaestro.h>

#ifdef SERIAL_PORT_HARDWARE_OPEN
    #define maestroSerial SERIAL_PORT_HARDWARE_OPEN
#else
    #include <SoftwareSerial.h>
    SoftwareSerial maestroSerial(10, 11);
#endif

const int NUM_ROWS = 15;
const int RAIL_STEPS = 500; //Number of steps to take in a single loop

//Coordinates of the dropoff
const int DROPOFF_Y = 9;
const int DROPOFF_Z = 1;

const int TIME_DELAY = 100;
const int BUF_SIZE = 8;

const int STEPS_PER_COLUMN = 6000;

const int MONEY_HOLDER_SIZE = 10;

//Digital
const int MONEY_MCH_INPUT = 2;
const int MONEY_MCH_OUTPUT = 11;
const int STEPPER_DIR = 8;
const int STEPPER_MAIN = 9;

//Analog
const int BACK_LIM_SWITCH = 1;
const int FRONT_LIM_SWITCH = 0;

MicroMaestro maestro(maestroSerial); //May need to be MiniMaestro

bool start; //Variable to check communications at startup
bool cont; //Variable to control if the program should halt

int railState; //Used to check which type of rail movement to use
int armState; //Used to check which portion of the arm movement should run

char commBuffer[BUF_SIZE]; //Holds messsages from the computer
int bufLen;
int sentString;

//Variables to help with the money machine
int moneyHolder[MONEY_HOLDER_SIZE];
bool acceptMoney;

//Variables used to move the rail
int goalRailPos;
int currRailPos;

//Variables used for the arms
int holderY;
int holderZ;

/**
 * Called once to set everything up.
 */
void setup() {
  //These two might be mutually exclusive, but I doubt it
  Serial.begin(9600);
  maestroSerial.begin(9600);
    
  //Initialize limit switch pins
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
    
  //Initialize MoneyMachine pins
  pinMode(MONEY_MCH_INPUT, INPUT);
  pinMode(MONEY_MCH_OUTPUT, OUTPUT);
  digitalWrite(MONEY_MCH_INPUT, HIGH);
  digitalWrite(MONEY_MCH_OUTPUT, LOW);

  //Initialize stepper pins
  pinMode(STEPPER_MAIN, OUTPUT);
  pinMode(STEPPER_DIR, OUTPUT);
  digitalWrite(STEPPER_MAIN, HIGH);
  digitalWrite(STEPPER_DIR, LOW);

  startup();
}

/**
 * Does variable resetting and stops scripts.
 * To be called in setup() and upon STOP and STRT commands.
 */
void startup() {
  start = false;
  armState = 0;
  railState = 0;

  goalRailPos = -1;
  currRailPos = -1;

  holderY = -1;
  holderZ = -1;
    
  acceptMoney = false;
  maestro.stopScript();
  digitalWrite(MONEY_MCH_OUTPUT, LOW);
  resetHolder();

  bufLen = 0;

  if(analogRead(BACK_LIM_SWITCH) < 500) //Problem; rail is at front when it should be at the back
      railState = 5;
}

/**
 * Called every tick. Place a delay if need be.
 */
void loop() {
    digitalWrite(MONEY_MCH_OUTPUT, LOW);
    
    if(start && cont) {
        if(armState == 0 && railState == 0) { //Nothing moving
            if(analogRead(FRONT_LIM_SWITCH) >= 1020) { //Check if front limit switch is triggered for a reset
      	        Serial.println("RSET");
      	        railToPos(1);
      	        railToBack();
                delay(100);
                asm volatile(" jmp 0");
            }
      	
            if(acceptMoney) {
                //Serial.println("Working");
      	        digitalWrite(MONEY_MCH_INPUT, HIGH);
      	        digitalWrite(MONEY_MCH_OUTPUT, HIGH);
      	        checkForMoney();
            }
         } else {
              switch(armState) {
              case 0: //Not doing anything
    	           break;
              case 1: //Moving arms to coordinate
        	        if(maestro.getScriptStatus() != 0) { //If script is finished
        	            maestro.stopScript();
        	            maestro.restartScript(30); //Start moving arms to home
        	            armState = 2;
                      Serial.println("Moving arms home");
        	        }
        	        break;
              case 2: //Moving arms to home
        	        if(maestro.getScriptStatus() != 0) { //If script is finished
        	            maestro.stopScript();
        	            armState = 0;
        	            railState = 1;
                      Serial.println("Moving rail to back");
        	        }
        	        break;
              case 3: //Moving arms to dropoff
        	        if(maestro.getScriptStatus() != 0) { //If script is finished
        	            maestro.stopScript();
        	            maestro.restartScript(30); //Start moving arms to home
        	            armState = 4;
                      Serial.println("Moving arms home again");
        	        }
        	        break;
              case 4: //Moving arms back to home then sends FNDL
        	        if(maestro.getScriptStatus() != 0) { //If script is finished
        	            maestro.stopScript();
        	            Serial.println("FNDL");
        	            armState = 0;
                      delay(100);
                      asm volatile(" jmp 0"); //Resets things
        	        }
        	        break;
              }
        
              switch(railState) {
              case 0: //Not doing anything
        	        break;
              case 1: //Moving to back as a dropoff
        	        digitalWrite(STEPPER_DIR, HIGH);
        		
        	        if(analogRead(BACK_LIM_SWITCH) >= 1020) {
        	            Serial.println("Hit back limit switch");
        	            railState = 0;
        	            armState = 3;
        
        	            maestro.restartScript(31); //Start moving arms to dropoff
                      maestro.restartScript(31); //Start moving arms to dropoff
                      Serial.println("Moving to dropoff");
        	        } else {
        	            for(short i = 0; i < RAIL_STEPS; i++) {
        	                digitalWrite(STEPPER_MAIN, LOW);
        	                delayMicroseconds(TIME_DELAY);
        	                digitalWrite(STEPPER_MAIN, HIGH);
        	                delayMicroseconds(TIME_DELAY);
        	            }
        	        }
        	        break;
        		
              case 2: //Moving rail to given position. Uses currRailPos and increments it
        	        digitalWrite(STEPPER_DIR, LOW);
        		
        	        if(analogRead(FRONT_LIM_SWITCH) >= 1020) {
        	            Serial.println("Hit front limit switch");
        	            currRailPos = 0;
        	            railState = 4;
        	        } else if(currRailPos >= goalRailPos) { //Reached target
        	            goalRailPos = -1;
        	            currRailPos = -1;
        
        	            railState = 0;
        	            armState = 1;
        
        	            calcScript(holderY, holderZ); //Start grabbing can at given coordinate
                      Serial.println("Moving arms to coordinate");
        	            holderY = -1;
        	            holderZ = -1;
        	        } else {
        	            for(short i = 0; i < RAIL_STEPS; i++) {
        	                digitalWrite(STEPPER_MAIN, LOW);
        	                delayMicroseconds(TIME_DELAY);
        	                digitalWrite(STEPPER_MAIN, HIGH);
        	                delayMicroseconds(TIME_DELAY);
        	            }
        
        	            currRailPos += RAIL_STEPS;
        	        }
        	        break;
              case 3: //Moving rail to the front to grab can
        	        digitalWrite(STEPPER_DIR, LOW);
        		
        	        if(analogRead(FRONT_LIM_SWITCH) >= 1020) {
        	            Serial.println("Hit front limit switch");
        	            railState = 0;
        	            armState = 1;
        
        	            calcScript(holderY, holderZ); //Start grabbing can at given coordinate
        	            holderY = -1;
        	            holderZ = -1;
        	        } else {
        	            for(short i = 0; i < RAIL_STEPS; i++) {
        	              digitalWrite(STEPPER_MAIN, LOW);
        	              delayMicroseconds(TIME_DELAY);
        	              digitalWrite(STEPPER_MAIN, HIGH);
        	              delayMicroseconds(TIME_DELAY);
        	            }
        	        }
        	        break;
              case 4: //Move to back to reset position
        	        digitalWrite(STEPPER_DIR, HIGH);
        		
        	        if(analogRead(BACK_LIM_SWITCH) >= 1020) {
        	            Serial.println("Hit back limit switch");
        	            railState = 2;
        	        } else {
        	            for(short i = 0; i < RAIL_STEPS; i++) {
        	                digitalWrite(STEPPER_MAIN, LOW);
        	                delayMicroseconds(TIME_DELAY);
        	                digitalWrite(STEPPER_MAIN, HIGH);
        	                delayMicroseconds(TIME_DELAY);
        	            }
        	        }
        	        break;
              case 5: //Moving to back then stopping
                  digitalWrite(STEPPER_DIR, HIGH);
  
                  Serial.print("Moving backwards. Back lim switch is ");
                  Serial.println(analogRead(BACK_LIM_SWITCH));
            
                  if(analogRead(BACK_LIM_SWITCH) >= 1020) {
                      Serial.println("Hit back limit switch");
                      railState = 0;
                  } else {
                      for(short i = 0; i < RAIL_STEPS; i++) {
                          digitalWrite(STEPPER_MAIN, LOW);
                          delayMicroseconds(TIME_DELAY);
                          digitalWrite(STEPPER_MAIN, HIGH);
                          delayMicroseconds(TIME_DELAY);
                      }
                  }
                  break;
              }
        }
    }
    delay(15);
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
    startup();
    cont = false;
    clearCommBuffer();
  } else if (((String)commBuffer).indexOf("STRT") >= 0) {
    startup();
    start = true;
    cont = true;
    Serial.println("STRT");
    clearCommBuffer();
  } else if (((String)commBuffer).indexOf("PING") >= 0) {
    Serial.println("PONG");
    clearCommBuffer();
  } else if (((String)commBuffer).indexOf("STAT") >= 0) {
    Serial.print("A");
    Serial.print(armState);
    Serial.print("R");
    Serial.println(railState);
    clearCommBuffer();
  } else if(commBuffer[0] == '#' && commBuffer[3] != NULL) {
    int holderX;


    holderX = (byte)commBuffer[1] - 48; //Note this is a local variable; - 48 from ASCII conversion
    holderY = (byte)commBuffer[2];
    holderZ = (byte)commBuffer[3] - 48;

    Serial.print("Received coordinate ");
    Serial.print(holderX);
    Serial.print("-");
    Serial.print(holderY);
    Serial.print("-");
    Serial.println(holderZ);
	
    if(holderX == 4) { //Front row
      railState = 3;
      armState = 0;
    } else if(holderX > 0) { //Middle rows	          
      goalRailPos = holderX * STEPS_PER_COLUMN;
      currRailPos = 0;

      railState = 2;
      armState = 0;
    } else { //Back row
      Serial.println("Coordinate in back row");
      railState = 0;
      armState = 1;

      calcScript(holderY, holderZ);
      
      holderY = -1;
      holderZ = -1;
    }
        
    clearCommBuffer();
  } else if(((String)commBuffer).indexOf("NMNY") >= 0) {
    Serial.println("Acknowledged need money");
    acceptMoney = true;
    clearCommBuffer();
  } else if(((String)commBuffer).indexOf("CNCL") >= 0) {
    Serial.println("Acknowledged cancel");
    acceptMoney = false;
    clearCommBuffer();
  } else if(bufLen == 4) {
    Serial.print("Couldn't recognize ");
    Serial.println((String)commBuffer);
    Serial.println("----");
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
  } //else
    //Serial.println("Checking money");
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

  for(short ct = 0; ct < value * STEPS_PER_COLUMN / 10; ct++) {
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
 * dropoff:31
 */
void armsToCoordinate(byte y, byte z) {
  maestro.restartScript(NUM_ROWS * z + y );
}

/**
 * Convinience function to calculate the appropriate script call given y and z coordinates.
 */
void calcScript(byte y, byte z) {
  maestro.restartScript(z * NUM_ROWS + y);
  maestro.restartScript(z * NUM_ROWS + y);
}
