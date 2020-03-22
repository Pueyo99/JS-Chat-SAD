import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EchoClient{
ReentrantLock mon = new ReentrantLock();
Condition endWritring = mon.newCondition();
Boolean endWrite = false;

public void threadInput(MySocket sc){
    String s;
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    mon.lock();
    try{
        while(endWrite==true){
            endWritring.wait();
        }
        while((s=bf.readLine()) != null){
            sc.writeLine(s);
            System.out.println("Mensaje enviado");  
        }
        System.out.println("Mensaje enviado");
        endWrite=true;
        endWritring.signal();
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        mon.unlock();
    }  
}

public void threadOutput(MySocket sc){
    mon.lock();
    try{
        String s;
        while(endWrite==false){
            endWritring.wait();
        }
        while((s=sc.readLine()) != null){
            System.out.println("Recibido: " + s);
        }
        endWrite=false;
        endWritring.signal();
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        mon.unlock();
    }
}

public static void main(String[] args){
    EchoClient ec = new EchoClient();
    MySocket sc = new MySocket("localhost", 30000);

    new Thread(){
        public void run(){
            while(true){
                ec.threadInput(sc);
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
            


