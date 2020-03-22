import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;

public class MyServerSocket{
    ServerSocket ss;
    BufferedReader br;
    PrintWriter pw;

    public MyServerSocket(final int port){
        try {
            ss = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MySocket accept() {
        Socket s;
        try {
            s = ss.accept();
            return new MySocket(s.getInetAddress().getHostName(), s.getLocalPort());
        } catch (final Exception e) {
            e.printStackTrace();
        }
            return null;
    }
    
}
