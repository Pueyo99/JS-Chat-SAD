import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EchoServer {
    public static void main(String[] args) {
        Map<String, MySocket> m = new ConcurrentHashMap<String,MySocket>();
        MyServerSocket ss = new MyServerSocket(40000);
        
        while(true){
            MySocket s = ss.accept();
            
            new Thread() {
                public void run() {
                    try{
                    String nick = s.readLine();
                    m.put(nick,s);
                    System.out.println("\u001b[35mConexión Correcta\u001b[0m");

                    String line;
                    while ((line = s.readLine()) != null) {
                        System.out.println("\u001b[33mMensaje recibido en el servidor: \u001b[0m" + line);
                        for (String n : m.keySet()){
                            if(n!=nick){
                                m.get(n).writeLine(nick+": "+line);
                                System.out.println("\u001b[32mMensaje reenviado a: \u001b[0m" + n);
                            }
                        }  
                    }
                    System.out.println("\u001b[35mConexión cerrada: \u001b[0m"+nick);
                    m.remove(nick);
                    s.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                }
            }.start(); 
    }
}
}
