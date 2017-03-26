
public class BackgroundService
{
	private static final String PORT_NAMES[] = { 
	    "/dev/tty.usbmodem", // Mac OS X
	    "/dev/usbdev", // Linux
	    "/dev/tty", // Linux
	    "/dev/serial", // Linux
	    "COM3", // Windows
	};
	private String getString() {
		ServerSocket serverSocket = null;
		Socket client = null;
		DataInputStream inputstream = null;
		try {
			serverSocket = new ServerSocket(8988);
			client = serverSocket.accept();
			inputstream = new DataInputStream(client.getInputStream());
			String str = inputstream.readUTF();
			serverSocket.close();
			return str;
		} catch (IOException e) {
			Log.e(WiFiDirectActivity.TAG, e.getMessage());
			return null;
		}finally{
			if(inputstream != null){
				try{
					inputstream.close();
				} catch (IOException e) {
					Log.e(WiFiDirectActivity.TAG, e.getMessage());
				}
			}
			if(client != null){
				try{
					client.close();
				} catch (IOException e) {
					Log.e(WiFiDirectActivity.TAG, e.getMessage());
				}
			}
			 if(serverSocket != null){
				try{
					serverSocket.close();
				} catch (IOException e) {
					Log.e(WiFiDirectActivity.TAG, e.getMessage());
				}
			}
		}
	}
}
