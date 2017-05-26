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

		ServerSocket serverSocket = null;
		Socket socket = null;

		BufferedReader bufferedReader = null;
		PrintStream printStream = null;
		PrintWriter printWriter = null;
		SerialPort serialPort = SerialPort.getCommPort("/dev/ttyACM0");
		try {
			serverSocket = new ServerSocket(1337);

			System.out.println(serverSocket.getInetAddress() + ", " + serverSocket.toString());
			System.out.println("I'm waiting here: " + serverSocket.getLocalPort());

			socket = serverSocket.accept();
			System.out.println("from " + socket.getInetAddress() + ":" + socket.getPort());

			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);

			OutputStream outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream, true);

			arduino = new Arduino("/dev/ttyACM0", 9600);
			arduino.openConnection();
			
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
