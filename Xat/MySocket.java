import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class MySocket{
    Socket s;
    BufferedReader br;
    PrintWriter pw;

    public MySocket(String host, int port){
        try{
            s = new Socket(host,port);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String readLine() {
        String s = "";
        try {
            s = br.readLine();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void writeLine(final String line) {
        pw.println(line);
    }

    public void close(){
        try{
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


