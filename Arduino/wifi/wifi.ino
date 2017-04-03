
#include <SPI.h> //What is used to communicate witht he WiFi chip
#include <Smartcar.h>


char ssid[] = "SWAT";      // your network SSID (name)
char pass[] = "raspberry";   // your network password


Gyroscope gyro;
Car car;
SR04 sensor;
SR04  backSensor;
char  inputBuffer[15];
int i=40;
int j=0;
long previousMillis =0;
long interval=800;


void setup() {
  gyro.attach();
  gyro.begin();
  sensor.attach(53, 54);
  backSensor.attach(36, 37);
  car.begin(gyro);
  Serial.begin(9600);
  

}
 void getData(){
     unsigned int frontDistance = sensor.getDistance(); 
     unsigned int backDistance = backSensor.getDistance();
     if (car.getSpeed() >= 0 ){
           Serial.print(frontDistance);
     }
     else {
      Serial.print(backDistance);
     }
}

/** 
oid getData(){
  //   unsigned int distance = sensor.getDistance();
     if (distance !=0 ){
           Serial.print(distance);
     }
}
**/
void move (){
   
     Serial.readBytes(inputBuffer, Serial.available());
    //delay(5000);
    // Serial.print("I got this ->");
    // Serial.print(inputBuffer);
    // Serial.println("<-");
    // Serial.write(c);
    unsigned long currentTime = millis();
    String currentline = Serial.readStringUntil(":");
   
    if (currentline.startsWith("forward")) {             // GET the car to move
      car.setAngle(0);

      for (int j=0 ; j<5 ; j++ ) {
       
        if (currentTime - previousMillis > interval){
           
            previousMillis=currentTime;
            i+=5;
            car.setSpeed(i);
        }
        
      }
        
    }
     if (currentline.startsWith("turnLeft")) {    // GET the car to turn left
      car.setSpeed(i);
      car.setAngle(-75);



    }
      if (currentline.startsWith("turnRight")) {             // GET the car to turn right
      car.setSpeed(i);
      car.setAngle(45);


    }
     if (currentline.startsWith("backwards")) {             // GET the car to decelerate and stop
      car.setSpeed(-50);
      car.setAngle(0);


    }
     if (currentline.startsWith("stop")) {
       for (int j=10 ; j<1 ; j-- ) {
        if (currentTime - previousMillis > interval){
            
            previousMillis=currentTime;
             i-=5;
            car.setSpeed(i);
        }
        
            
      }
    }
 
  }

void loop() {
  
move();
getData();


}




