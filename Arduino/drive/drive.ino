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
unsigned int frontDistance;
unsigned int backDistance;
boolean autostop = true ;
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

  Serial.readBytes(inputBuffer, Serial.available());      // reads characters from the serial port into a buffer
  String currentline = Serial.readStringUntil(':');
  if (frontDistance <= 5 && (currentline.startsWith("autostop")) ) {
    car.setSpeed(0);

  }
  else {
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

  }
  if (backDistance  <= 5 && (currentline.startsWith("autostop"))) {
    car.setSpeed(0);
  }
  else {
    if (currentline.startsWith("backwards")) {             // GET the car to move backward

      car.setSpeed(-50);
      car.setAngle(0);


    }
  }

  if (currentline.startsWith("stop")) {                                 //decrease the speed of the car until it stops
    car.setSpeed(0);
  }
}
void loop() {


  move();

  frontDistance = Sensor.getDistance();
  backDistance = backSensor.getDistance();

  Serial.println(car.getSpeed());
  if (car.getSpeed() >= 0  ) {                                          //get measurements fro the front sensor when the car's speed is positive

    String Frontdistance = String (frontDistance);
    Serial.println(Frontdistance + "\n");


  }

  else if (car.getSpeed() < 0)  {                                       // get measurements form the back sensor when the car is moving backward.

    String BackDistance = String (backDistance);
    Serial.println(BackDistance + "\n");

  }
  unsigned long CurrentTime = millis();

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
  servo_test.write(angle);
  previousMillis = CurrentTime;

}


