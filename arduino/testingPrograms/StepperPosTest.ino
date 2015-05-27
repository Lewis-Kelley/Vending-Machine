#define IN_TO_CT (15000 / 21)

void setup() {
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  digitalWrite(8, LOW);
  digitalWrite(9, LOW);
}

void loop() {
  bool flag = true;
  if(flag) {
    flag = false;
    
    setDirection(true);
    moveForCt(5000, 300);
    delay(5000);
    
    setDirection(false);
    moveForCt(5000, 300);
    delay(5000);
    
    setDirection(true);
    moveForCt(10000, 300);
    delay(5000);
    
    setDirection(false);
    moveForCt(10000, 300);
    delay(5000);
    
    setDirection(true);
    moveForCt(15000, 300);
    delay(5000);
    
    setDirection(false);
    moveForCt(15000, 300);
    delay(5000);
  }
}

void setDirection(bool moveForward) {
  if(moveForward)
    digitalWrite(8, LOW);
  else
    digitalWrite(8, HIGH);
}

void moveForCt(int ct, int timeDelay) {
   for(short i = 0; i < ct; i++) {
    digitalWrite(9, LOW);
    delayMicroseconds(timeDelay);
    digitalWrite(9, HIGH);
    delayMicroseconds(timeDelay);
  }
}
