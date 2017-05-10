#include <SPI.h> //What is used to communicate witht he WiFi chip
#include <Smartcar.h>   //SmarCar library
#include <Servo.h>


Gyroscope gyro;
Car car;
SR04 Sensor;                          // declare a SR04 sensor object
SR04  backSensor;                    // declare a SR04 sensor object
Servo servo_test;                   // declare a servo object for the connected servo

int angle = 0;
char  inputBuffer[15];
unsigned int MAX_SPEED = 50;
const int trigPin = 52;
const int echoPin = 53;
const int TrigPin = 41;
const int EchoPin = 40;
int speedSet = 0;
unsigned long previousMillis = 0;
unsigned long sensorMillis = 0;
boolean revAngle = true;
boolean autoDriving = false;
 int distanceR;
 int distanceL;

void setup() {
  servo_test.attach(30);                                // attach the servo to pin 30
  gyro.attach();
  gyro.begin();
  backSensor.attach(trigPin, echoPin);                  //attach back sensor
  Sensor.attach(TrigPin , EchoPin);                     //attach front sensor
  car.begin(gyro);
  Serial.begin(9600);                                    //set the baud rate of the serial to 9600 bits er second
  servo_test.write(50);                                  // set the camera motor angle

}

void move () {

//the serial is available
  if (Serial.available()){  
      
  String currentline = Serial.readStringUntil(':'); // reads characters from the serial port into a buffer
  //if does not work we can add very short time out serial.setTimeOut (like 5 milliseconds)
  
  if (currentline.startsWith("forward")) {            // move the car forward
   
    car.setAngle(0); //The wheel angle
    car.setSpeed(50);// The speed
  }

  if (currentline.startsWith("turnLeft")) {           // GET the car to turn left

    car.setSpeed(50);   //The speed
    car.setAngle(-75); //The wheel angle for turning left


  }
  if (currentline.startsWith("turnRight")) {             // GET the car to turn right

    car.setSpeed(50); //The speed
    car.setAngle(75); //The wheel angle for turning right


  }
  if (currentline.startsWith("backwards")) {             // GET the car to move backward

    car.setSpeed(-50);  //The speed
    car.setAngle(0); //The wheel angle

  }


  if (currentline.startsWith("stop")) {                                 //decrease the speed of the car until it stops
    car.setSpeed(0); //The speed to 0 to stop the car
}

  if (currentline.startsWith ("driveAuto")) {
    autoDriving = true; //Boolean for the autonomous driving loop
    autoDrive();        //Autonomous driving method
   }
  }
}

void loop() {

  move();

  unsigned int frontDistance = Sensor.getDistance();      //measure the distance in the front of the car
  unsigned int backDistance = backSensor.getDistance();   //measure the distance from the back of the car
  
  if (car.getSpeed() >= 0  ) {                                          //get measurements from the front sensor when the car's speed is positive

    String Frontdistance = String (frontDistance);
    Serial.println(Frontdistance + "\n");
 
  }

  else if (car.getSpeed() < 0)  {                                       //get measurements form the back sensor when the car is moving backward.

    String BackDistance = String (backDistance);
    Serial.println(BackDistance + "\n");   

  }
  
/*  unsigned long CurrentTime = millis();

  if (CurrentTime - previousMillis > 100 ) {                    //set the range of the angle of the servo motors
    if (revAngle) {
      angle += 20;

      if (angle > 120) revAngle = false;
    }
    else
    {
      angle -= 20;
      if (angle < 20) revAngle = true;
    }
  }
  
  servo_test.write(angle);                                      //setting the angle of the servo motor
  previousMillis = CurrentTime;*/

}

int lookLeft()
{
  Serial.println("lookLeft \n");  
  int obstacle = 400; //the max distance when no obstacle

  // looping the motor to turn to the left and checking if there is an obstacle
 
  for (angle = 50 ; angle <= 100; angle += 10) 
 { 
    Serial.println("In the FORLEFT and ANGLE IS " + (String) angle + "\n");   
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();  //getting the distance

    //if the there is an obstacle closer than 400, sign the distance to that
    if (distance < obstacle && distance != 0 ) { 
      Serial.println("In the IFLEFT \n");  
      obstacle = distance;
    }
  }
  servo_test.write(50); //setting the servo motor to turn on the middle
  return obstacle;
}


int lookRight()
{
   Serial.println("lookRight \n");  
  int obstacle = 400; //the max distance when no obstacle

   // looping the motor to turn to the left and checking if there is an obstacle
  for (angle = 50 ; angle >= 0; angle -= 10) // command to move from 180 degrees to 0 degrees
  {
    Serial.println("In the FORRIGHT and ANGLE IS " + (String) angle + "\n");  
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();  //getting the distance

   //if the there is an obstacle closer than 400, sign the distance to that
    if (distance < obstacle && distance != 0) {
       Serial.println("In the IFRIGHT \n");  
      obstacle = Sensor.getDistance();
    }
  }
  servo_test.write(50); //setting the servo motor to turn on the middle
  return obstacle;
}


void autoDrive()
{
  Serial.println("autoDrive \n");  

  //setting the loop to drive automatically until stopping it
  while (autoDriving == true){

    Serial.println("Looping autoDrive \n");  
    servo_test.write(50); //Setting the servo motor angle to the middle
    
   unsigned int distance = Sensor.getDistance(); //getting the distance
   Serial.println((String) Sensor.getDistance() + "\n");

  if (distance <= 40 && distance > 0) {
    Serial.println("autoDrive too Small distance "); 
    car.setSpeed(0);  //Stop the car is there is an obstacle
    distanceR = lookRight(); //Get the distance on the right side
    distanceL = lookLeft(); //Get the distance on the left side

   Serial.println((String) distanceR + "the right value \n");
   Serial.println((String) distanceL + "the left value \n");

//Choosing which way to take according to the distance on the left and on the right
    if (distanceR >= distanceL) //turning right
    {
      Serial.println("the car turning right \n"); 
      car.rotate(60); //turning the wheels to the right
      car.setSpeed(40); //setting the speed
    }
    
    else if (distanceR < distanceL) //turning left
    {
       Serial.println("the car turning left \n"); 
      car.rotate(-60); //turning the wheels to the left
      car.setSpeed(40); //setting the speed

    }
  }

//if there is no obstacle in the front of the car, drive forward
  else {
     Serial.println("the car driving forward \n"); 
    car.setSpeed(40); //setting the speed to 40
    }

 String currentline2 = Serial.readStringUntil(':');

 //if the input is stopAuto, break the loop of the autonomous driving
  if (currentline2.startsWith("stopAuto")) {     
  car.setSpeed(0); //set the speed to 0 to stop the car
  servo_test.write(50);
  break;           // break to loop
    }
  }
}

