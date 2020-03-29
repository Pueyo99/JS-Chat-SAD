import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class MySocket{
    Socket s;
    BufferedReader br;
    PrintWriter pw;
    String nick;

    public MySocket(Socket sp){
        try{
            s = sp;
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MySocket(String n, String host, int port){
        try{
            nick = n;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void writeLine(String line) {
        pw.println(line);
        pw.flush();
    }

    public void close(){
        try{
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getNick(){
        return nick;
    }
}


