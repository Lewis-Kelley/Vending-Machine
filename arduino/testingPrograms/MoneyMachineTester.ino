const int SIZE = 10;

int holder[SIZE];

void setup() {
  // put your setup code here, to run once:
   Serial.begin(9600); 
   pinMode(2, INPUT);
   pinMode(3, OUTPUT);
   resetHolder();
}

void loop() {
 // put your main code here, to run repeatedly:
 digitalWrite(2, HIGH);
 digitalWrite(3, HIGH);
 //Serial.println(digitalRead(2));
 //if(digitalRead(2) == 0)
 //  Serial.println("Dollar accepted!");
 updateHolder();
 if(sumHolder() < 7) {
   Serial.println("HACKED");
   resetHolder();
 }

 delay(15);
}

void resetHolder() {
  for(short i = 0; i < SIZE; i++)
    holder[i] = 1;
}

void updateHolder() {
  for(short i = SIZE - 1; i > 0; i--)
    holder[i] = holder[i - 1];
  
  holder[0] = digitalRead(2);
}

int sumHolder() {
  int sum = 0;
  
  for(short i = 0; i < SIZE; i++)
    sum += holder[i];
  
  return sum;
}
