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

        try{
            serverSocket = new ServerSocket(0);
            System.out.println("I'm waiting here: "
                    + serverSocket.getLocalPort());

            socket = serverSocket.accept();
            System.out.println("from " +
                    socket.getInetAddress() + ":" + socket.getPort());

            InputStreamReader inputStreamReader =
                    new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while((line=bufferedReader.readLine()) != null){
                System.out.println(line);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }finally{
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    System.out.print(ex.toString());
                }
            }

            if(printStream!=null){
                printStream.close();
            }

            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.print(ex.toString());
                }
            }
        }
    }
}