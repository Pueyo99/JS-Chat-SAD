package XAT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EchoClient {
    BufferedReader bf;

    public EchoClient(){
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    public void threadInput(MySocket sc) throws IOException {
    String s;

    try{
        while((s=bf.readLine()) != null){
            sc.writeLine(s);
            System.out.println("\u001b[32mMensaje enviado\u001b[0m\n");  
        }
    }catch(NullPointerException ex){
           sc.close();   
    }
    System.out.println("\u001b[35mConexión cerrada\u001b[0m");   
    }

    public void threadOutput(MySocket sc){
        try{
        String lectura = sc.readLine();
        System.out.println("\u001b[31m"+lectura.split(":")[0]+":"+"\u001b[0m"+lectura.split(":")[1]+"\n"); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

public static void main(String[] args){
    EchoClient ec = new EchoClient();
    System.out.print("\u001b[34mIntroduzca nick:\u001b[0m ");
    String nick = "";
    try{
        nick = ec.bf.readLine();
        System.out.println("Bienvenido \u001b[31m" + nick + "\u001b[0m\n");
    }catch(Exception e){
        e.printStackTrace();
    }
    MySocket sc = new MySocket(nick, "localhost", 40000);
    sc.writeLine(nick);

    new Thread(){
        public void run(){
            try{
                    ec.threadInput(sc);
            }catch(Exception e ){
                e.printStackTrace();
            }
        }
    }.start();

    new Thread(){
        public void run(){
            while(true){
                ec.threadOutput(sc); 
            }         
        }
    }.start();


}

}
   




