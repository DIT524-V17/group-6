#include <Smartcar.h>
#include <Servo.h>


Gyroscope gyro;
Car car;
SR04 Sensor;
Servo servo_test;        //initialize a servo object for the connected servo
SR04  backSensor;
int angle = 0;
int pos = 0;
const int trigPin = 52;
const int echoPin = 53;
const int TrigPin = 41;
const int EchoPin = 40;



void setup() {
  servo_test.attach(30);
  servo_test.write(0);
  gyro.attach();
  gyro.begin();
  backSensor.attach(trigPin, echoPin);
  Sensor.attach(TrigPin , EchoPin);
  car.begin(gyro);
  Serial.begin(9600);

}
void loop() {
  // put your main code here, to run repeatedly:
  unsigned int distance = Sensor.getDistance();
  int distanceR = 0;
  int distanceL =  0;

  if (distance <= 20) {
    car.setSpeed(0);
    distanceR = lookRight();
    delay(200);
    distanceL = lookLeft();
    delay(200);

    if (distanceR >= distanceL)
    {
      car.rotate(45);
      car.setSpeed(40);


    }
    else if (distanceR < distanceL)
    {
      car.rotate(-45);
      car.setSpeed(40);

    }
  }

  else {

    car.setSpeed(40);


  }
}

int lookLeft()

{
  int obstacle = 400;
  for (angle = 70 ; angle >= 100; angle += 10) // command to move from 180 degrees to 0 degrees
  {
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();
    if (distance < obstacle  ) {
      obstacle = distance;
    }
    delay(5);
  }
  return obstacle;
  servo_test.write(0);
  delay(100);
}

int lookRight()
{
  int obstacle = 400;
  for (angle = 70 ; angle <= 20; angle -= 10) // command to move from 180 degrees to 0 degrees
  {
    servo_test.write(angle);              //command to rotate the servo to the specified angle
    int distance = Sensor.getDistance();
    if (distance < obstacle  ) {
      obstacle = distance;
    }
    delay(5);
  }
  servo_test.write(0);
  return obstacle;
}
