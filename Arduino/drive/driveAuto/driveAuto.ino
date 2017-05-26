#include <Smartcar.h>   //SmarCar library
#include <Servo.h>


Gyroscope gyro;
Car car;
SR04 Sensor;                          // declare a SR04 sensor object
SR04  backSensor;                    // declare a SR04 sensor object
Servo servo_test;                   // declare a servo object for the connected servo

int angle = 0;
unsigned int MAX_SPEED = 50;
const int trigPin = 53;
const int echoPin = 52;
const int TrigPin = 41;
const int EchoPin = 40;
int speedSet = 0;
unsigned long previousMillis = 0;
unsigned long sensorMillis = 0;
boolean revAngle = true;
boolean autoDriving = false;
int distanceR;
int distanceL;
boolean autoStop = false;  //added me
boolean isGoingBack = false;

void setup() {
  servo_test.attach(30);                                // attach the servo to pin 30
  gyro.attach();
  gyro.begin();
  backSensor.attach(trigPin, echoPin);                  //attach back sensor
  Sensor.attach(TrigPin , EchoPin);                     //attach front sensor
  car.begin(gyro);
  Serial.begin(9600);                                    //set the baud rate of the serial to 9600 bits er second
  servo_test.write(70);

}

void move () {

  if (Serial.available() > 0) {
    String currentline = Serial.readStringUntil(':');     // reads characters from the serial port into a buffer
    //if does not work we can add very short time out serial.setTimeOut (like 5 milliseconds)

    if (currentline.startsWith("forward")) {                                      // move the car forward

      car.setAngle(0);    //The wheel angle
      car.setSpeed(50);    // The speed
      isGoingBack = false;
    }

    if (currentline.startsWith("autoStop")) {           // turn on autostop

      autoStop = true;

    }
    if (currentline.startsWith("autoStopOff")) {           // turn off autostop

      autoStop = false;

    }
    if (currentline.startsWith("turnLeft")) {           // GET the car to turn left

      car.setSpeed(50);   //The speed
      car.setAngle(-75);    //The wheel angle for turning left
      isGoingBack = false;

    }
    if (currentline.startsWith("turnRight")) {             // GET the car to turn right

      car.setSpeed(50);  //The speed
      car.setAngle(75);   //The wheel angle for turning right
      isGoingBack = false;
    }
    if (currentline.startsWith("backwards")) {             // GET the car to move backward

      car.setSpeed(-50);  //The speed
      car.setAngle(0);    //The wheel angle
      isGoingBack = true;
    }


    if (currentline.startsWith("stop")) {                                 //decrease the speed of the car until it stops
      car.setSpeed(0);  //The speed to 0 to stop the car
      isGoingBack = false;
    }

    if (currentline.startsWith ("driveAuto")) {
      autoDriving = true;   //Boolean for the autonomous driving loop
      autoDrive();          //Autonomous driving method
    }

    if (currentline.startsWith ("tankLeft") && servo_test.read() < 100) {
      //do {
      //currentline = Serial.readStringUntil(':');
      servo_test.write(servo_test.read() + 15);
      //Serial.println("left");
      //} while (!currentline.startsWith("tankStop") || servo_test.read() < 100);
    }
    if (currentline.startsWith ("tankRight") && servo_test.read() > 40) {
      //do {
      //currentline = Serial.readStringUntil(':');
      //Serial.println("right");
      servo_test.write(servo_test.read() - 15);
      //} while (!currentline.startsWith("tankStop") || servo_test.read() > 40);
    }
  }
}

void loop() {


  move();

  unsigned long CurrentTime = millis();

  unsigned int frontDistance = Sensor.getDistance();                      //measure the distance in the front of the car`
  unsigned int backDistance = backSensor.getDistance();                  //measure the distance from the back of the car
  if (autoStop && (frontDistance <= 20 && frontDistance > 0) && !isGoingBack) //med added
  {
    car.setSpeed(0);
    delay(500);
  }

  if ( car.getSpeed() < 0   &&  backDistance < 400 )  {
    String BackDistance = String (backDistance);
    if (backDistance == 0 ) {
      Serial.println("400" );
    } else {
      Serial.println(BackDistance + "\n");
    }
  }


  if ( car.getSpeed() >= 0 && frontDistance < 400 ) {
    String FrontDistance = String (frontDistance);
    if (frontDistance == 0 ) {
      Serial.println("400");
    }  else {
      Serial.println( FrontDistance + "\n");
    }
  }
}


int lookLeft()

{
  int obstacle = 400;
  for (angle = 70 ; angle <= 120; angle += 10) // command to move from 180 degrees to 0 degrees
  {
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();
    if (distance < obstacle && distance != 0 ) {
      obstacle = distance;
    }
  }
  servo_test.write(70);
  return obstacle;
}

int lookRight()
{
  int obstacle = 400;
  for (angle = 70 ; angle >= 20; angle -= 10) // command to move from 180 degrees to 0 degrees
  {
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();
    if (distance < obstacle && distance != 0) {
      obstacle = distance;
    }
  }
  servo_test.write(70);
  return obstacle;
}



void autoDrive()
{
  Serial.println("autoDrive \n");

  //setting the loop to drive automatically until stopping it
  while (autoDriving == true) {
    servo_test.write(70); //Setting the servo motor angle to the middle
    unsigned int distance = Sensor.getDistance(); //getting the distance
    Serial.println((String) Sensor.getDistance() + "\n");



    if (Sensor.getDistance() <= 40 && distance > 0 ) {
      car.setSpeed(0);  //Stop the car is there is an obstacle
      distanceR = lookRight(); //Get the distance on the right side
      delay(500);
      distanceL = lookLeft(); //Get the distance on the left side

      //Choosing which way to take according to the distance on the left and on the right

      if (distanceR <= 40 && distanceL <= 40 ) {        // if the right and left distances are less than 40 the car should go back and checks again

        Serial.println("Should go back");
        car.setSpeed(-40);
        delay(1200);
        car.setSpeed(0);
        delay(200);
        distanceR = lookRight(); //Get the distance on the right side
        delay(500);
        distanceL = lookLeft(); //Get the distance on the left side
      }

      if (distanceR >= distanceL) //turning right
      {
        car.rotate(60);    //turning the wheels to the right
        car.setSpeed(50); //setting the speed
      }

      if (distanceR < distanceL) //turning left
      {
        car.rotate(-60); //turning the wheels to the left
        car.setSpeed(50); //setting the speed
      }


      //if there is no obstacle in the front of the car, drive forward
      else {
        car.setSpeed(50); //setting the speed to 40
      }

      String currentline2 = Serial.readStringUntil(':');
      
      //if the input is stopAuto, break the loop of the autonomous driving
      if (currentline2.startsWith("stopAuto")) {
        car.setSpeed(0); //set the speed to 0 to stop the car
        servo_test.write(70);
        break;           // break to loop
      }
    }
  }
}
