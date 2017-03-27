	import java.io.BufferedReader;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.io.OutputStream;
	import java.io.PrintStream;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	
	

	class host
	{
	    public static void main(String srgs[])
	    {
	        ServerSocket serverSocket = null;
	        Socket socket = null;
	        BufferedReader bufferedReader = null;
	        PrintStream printStream = null;
	        Arduino arduino;

	        try{
	            serverSocket = new ServerSocket(1337);
	            System.out.println(serverSocket.getInetAddress() + ", " + serverSocket.toString());
	            System.out.println("I'm waiting here: "
	                    + serverSocket.getLocalPort());

	            socket = serverSocket.accept();
	            System.out.println("from " +
	                    socket.getInetAddress() + ":" + socket.getPort());

	            InputStreamReader inputStreamReader =
	                    new InputStreamReader(socket.getInputStream());
	            bufferedReader = new BufferedReader(inputStreamReader);
	            
	            OutputStream outputStream = socket.getOutputStream();
	            printStream = new PrintStream (outputStream);

	            String line;
	            arduino = new Arduino("/dev/ttyACM0", 9600);
	            arduino.openConnection();
	            while ((line = bufferedReader.readLine()) != null)
	            {
	            	System.out.println(line);
	            	arduino.serialWrite(bufferedReader.readLine());
	            }
	            
	            /**
	            String line;
	            while((line=bufferedReader.readLine()) != null){
	                System.out.println(line);
	            }
	            */
	        }catch(IOException e){
	            System.out.println(e.toString());
	        }finally{
	            if(bufferedReader!=null){
	                try {
	                    bufferedReader.close();
	                    System.out.println("bufferedReader closed");
	                } catch (IOException ex) {
	                    System.out.print(ex.toString());
	                }
	            }

	            if(printStream!=null){
	                printStream.close();
	                System.out.println("printStreamer closed");
	            }

	            if(socket!=null){
	                try {
	                    socket.close();
	                    System.out.println("clientSocket closed");
	                } catch (IOException ex) {
	                    System.out.print(ex.toString());
	                }
	            }
	            
	            if(serverSocket != null){
	                try {
	                    serverSocket.close();
	                    System.out.println("serverSocket closed");
	                } catch (IOException ex) {
	                    System.out.println(ex.toString());
	                }
	            }
	        }
	    }
}
