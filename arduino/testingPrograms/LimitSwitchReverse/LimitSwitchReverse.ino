const int TIME_DELAY = 200;

bool goBack;

void setup() {
  Serial.begin(9600);
  
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  
  digitalWrite(8, HIGH);
  digitalWrite(9, LOW);
  
  goBack = true;
}

void loop() {
  if(goBack)
      digitalWrite(8, HIGH);
    else
      digitalWrite(8, LOW);
  
  if(!goBack)
    for(short i = 0; i < 6000; i++) {
      digitalWrite(9, LOW);
      delayMicroseconds(TIME_DELAY);
      digitalWrite(9, HIGH);
      delayMicroseconds(TIME_DELAY);
    }
  else
    while(true) {
      if(analogRead(1) > 950)
        break;
      
      for(short j = 0; j < 10; j++) {
        digitalWrite(9, LOW);
        delayMicroseconds(TIME_DELAY);
        digitalWrite(9, HIGH);
        delayMicroseconds(TIME_DELAY);
      }
    }
  
  goBack = !goBack;
  delay(1000);
}
