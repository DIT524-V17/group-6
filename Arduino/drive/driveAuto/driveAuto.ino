#include <Smartcar.h>   //SmarCar library
#include <Servo.h>


Gyroscope gyro;
Car car;
SR04 Sensor;                          // declare a SR04 sensor object
SR04  backSensor;                    // declare a SR04 sensor object
Servo servo_test;                   // declare a servo object for the connected servo

int angle = 0;
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
  servo_test.write(70);

}

void move () {
  if (Serial.available() > 0) {
    String currentline = Serial.readStringUntil(':');

    if (currentline.startsWith("forward")) {                                      // move the car forward

      car.setAngle(0);
      car.setSpeed(50);
    }

    if (currentline.startsWith("turnLeft")) {           // GET the car to turn left

      car.setSpeed(50);
      car.setAngle(-75);



    }
    if (currentline.startsWith("turnRight")) {             // GET the car to turn right

      car.setSpeed(50);
      car.setAngle(75);


    }
    if (currentline.startsWith("backwards")) {             // GET the car to move backward

      car.setSpeed(-50);
      car.setAngle(0);

    }


    if (currentline.startsWith("stop")) {                                 //decrease the speed of the car until it stops
      car.setSpeed(0);
    }

    if (currentline.startsWith ("driveAuto")) {
      autoDriving = true;
      autoDrive();
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


  unsigned int frontDistance = Sensor.getDistance();
  unsigned int backDistance = backSensor.getDistance();

  if (car.getSpeed() < 0)  {                                       // get measurements form the back sensor when the car is moving backward.

    String BackDistance = String (backDistance);
    Serial.println(BackDistance + "\n");

  }
  unsigned long CurrentTime = millis();

  //if (CurrentTime - previousMillis > 100 ) {                    //set the range of the angle of the servo motors
  /*
    if (revAngle) {
    angle += 20;
    delay(200);
    if (angle > 120) revAngle = false;
    }
    else
    {
    angle -= 20;
    delay(200);
    if (angle < 20) revAngle = true;
    }
    if (angle >= 20 && angle < 50) {

    String Frontdistance = String (frontDistance);
    Serial.println("right:" + Frontdistance + "\n");

    }
    else if (angle >= 50 && angle < 70) {

    String Frontdistance = String (frontDistance);
    Serial.println("center:" + Frontdistance + "\n");


    } else if (angle >= 70 && angle < 120) {

    String Frontdistance = String (frontDistance);
    Serial.println("left:" + Frontdistance + "\n");

    }
    servo_test.write(angle);


    // previousMillis = CurrentTime;
    int angle2 = servo - test.getAngle();
  */
}

int lookLeft()

{
  Serial.println("lookLeft \n");
  int obstacle = 400;
  for (angle = 70 ; angle <= 100; angle += 10) // command to move from 180 degrees to 0 degrees
  {
    Serial.println("In the FORLEFT and ANGLE IS " + (String) angle + "\n");
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();
    if (distance < obstacle && distance != 0 ) {
      Serial.println("In the IFLEFT \n");
      obstacle = distance;
    }
  }
  servo_test.write(70);
  return obstacle;
}

int lookRight()
{
  Serial.println("lookRight \n");
  int obstacle = 400;
  for (angle = 70 ; angle >= 20; angle -= 10) // command to move from 180 degrees to 0 degrees
  {
    Serial.println("In the FORRIGHT and ANGLE IS " + (String) angle + "\n");
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();
    if (distance < obstacle && distance != 0) {
      Serial.println("In the IFRIGHT \n");
      obstacle = Sensor.getDistance();
    }
  }
  servo_test.write(70);
  Serial.println("The OBSTACLE IS" + (String) obstacle + "\n");
  return obstacle;
}


void autoDrive()
{
  Serial.println("autoDrive \n");
  while (autoDriving == true) {

    Serial.println("Looping autoDrive \n");
    servo_test.write(70);
    // put your main code here, to run repeatedly:
    unsigned int distance = Sensor.getDistance();
    Serial.println((String) Sensor.getDistance() + "\n");

    if (Sensor.getDistance() <= 25 && distance > 0) {
      Serial.println("autoDrive too Small distance ");
      car.setSpeed(0);
      distanceL = lookLeft();
      distanceR = lookRight();

      Serial.println((String) distanceR + "the right value \n");
      Serial.println((String) distanceL + "the left value \n");

      if (distanceR >= distanceL)
      {
        Serial.println("the car turning right \n");
        car.rotate(45);
        car.setSpeed(40);
      }

      else if (distanceR < distanceL)
      {
        Serial.println("the car turning left \n");
        car.rotate(-45);
        car.setSpeed(40);

      }
    }

    else {
      Serial.println("the car driving forward \n");
      car.setSpeed(40);
    }

    String currentline2 = Serial.readStringUntil(':');

    if (currentline2.startsWith("stopAuto")) {
      car.setSpeed(0);
      servo_test.write(50);
      break;
    }

  }
}

