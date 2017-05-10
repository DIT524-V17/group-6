import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fazecast.jSerialComm.*;
import arduino.*;

class host {
	static Arduino arduino = null;
	public static void main(String args[]) {
		
		// Initializing the socket that connects to the socket on android
		
		ServerSocket serverSocket = null;
		Socket socket = null;

		// Initializing Writer, Buffer and Reader that prints and reads to/from the Android
		
		BufferedReader bufferedReader = null;
		PrintStream printStream = null;
		PrintWriter printWriter = null;
		
		// Opening the Serial Port
		
		SerialPort serialPort = SerialPort.getCommPort("/dev/ttyACM0");
		
		try {
			
			serverSocket = new ServerSocket(1337);
			
			// Socket is opening that awaits a connecting
			System.out.println(serverSocket.getInetAddress() + ", " + serverSocket.toString());
			System.out.println("I'm waiting here: " + serverSocket.getLocalPort());

			// Socket incoming
			socket = serverSocket.accept();
			System.out.println("from " + socket.getInetAddress() + ":" + socket.getPort());

			// 
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);

			OutputStream outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream, true);

			// Creating an Arduino connection with Baudrate 9600
			arduino = new Arduino("/dev/ttyACM0", 9600);
			arduino.openConnection();
			
			
			// Creating a new thread to write/print to the Arduino via the serialconnection
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					PrintWriter printWriter = new PrintWriter(outputStream, true);
					String serialRead;
					while (true) {
						serialRead = arduino.serialRead(1);
						printWriter.println(serialRead);
						System.out.println(serialRead);
						try{
							Thread.sleep(100);
						}catch (InterruptedException e) {
							System.out.print(e.toString());
						}
					}
				}
			});
			t1.start();
			
			String serialRead;
			//When the buffered Reader isn't empty, read from the Android and send to the Arduino
			while (bufferedReader != null) {
				String command = bufferedReader.readLine();
				System.out.println(command);
				arduino.serialWrite(command);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					System.out.print(ex.toString());
				}
			}

			if (printStream != null) {
				printStream.close();
			}

			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					System.out.print(ex.toString());
				}
			}
		}
	}
}
