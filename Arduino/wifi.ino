#include <SPI.h> //What is used to communicate witht he WiFi chip
#include <Smartcar.h>

char ssid[] = "SWAT";      // your network SSID (name)
char pass[] = "raspberry";   // your network password
//int keyIndex = 0;                 // your network key Index number (needed only for WEP)

 //int status = WL_IDLE_STATUS; //status of wifi

 //WiFiServer server(80); //declare server object and specify port, 80 is port used for internet

Gyroscope gyro;
Car car;
SR04 sensor;
char  inputBuffer[15];   
int j=10;
int a=50;
    
    void setup()
{
    gyro.attach();
    gyro.begin();
    sensor.attach(3,4);
    car.begin(gyro);
    Serial.begin(9600);
    pinMode(LED_BUILTIN, OUTPUT);
}

    void sensorWrite()
    {
          unsigned int distance = sensor.getDistance();
          Serial.write(distance);
    }
  
void loop() {

    while (true) 
    {
          digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
       
          Serial.readBytes(inputBuffer, Serial.available());

          String currentline = Serial.readStringUntil(":");
          
          if (currentline.startsWith("forward")) {             // GET the car to move 
            car.setSpeed(50);
            car.setAngle(0);
        
        }
             else if (currentline.startsWith("turnLeft")) {    // GET the car to turn left 
                      car.setSpeed(50);
                       car.setAngle(-75);
            
        }
             else  if (currentline.startsWith("turnRight")) {             // GET the car to turn right  
                       car.setSpeed(50);
                       car.setAngle(75);
           
          
        }
              else if (currentline.startsWith("backwards")) {             // GET the car to decelerate and stop
                       car.setSpeed(-50);
                       car.setAngle(0);
            
        
        } 
              else if (currentline.startsWith("stop")) {
                      car.setSpeed(0);
                      car.setAngle(0);
        }

    sensorWrite();


    }
    
}
    



