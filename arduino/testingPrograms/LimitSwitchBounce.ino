const int TIME_DELAY = 200;

bool goBack;

void setup() {
  Serial.begin(9600);
  
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  
  digitalWrite(8, HIGH);
  digitalWrite(9, LOW);
  
  goBack = true;
}

void loop() {
  if(goBack)
    digitalWrite(8, HIGH);
  else
    digitalWrite(8, LOW);
      
  while(true) {
    if((analogRead(1) > 1020 && goBack) || (analogRead(2) > 1020 && !goBack)) {
      Serial.println("Turning around");
      break;
    }
      
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
