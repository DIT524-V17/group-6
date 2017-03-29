#include <Smartcar.h>

Gyroscope gyro;
Car car;
SR04 sensor;

void setup() {
  gyro.attach();
  gyro.begin();
  sensor.attach(4,5);
  car.begin(gyro);
}

void loop(){
  unsigned int distance = sensor.getDistance();
  serial.write(distance);

} 
