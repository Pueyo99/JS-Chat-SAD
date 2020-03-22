public class EchoServer{
    public static void main(String[] args){
        MyServerSocket ss = new MyServerSocket(30000);

        while(true){
            MySocket s = ss.accept();
            System.out.println("Conexión Correcta");
            new Thread(){
                public void run(){
                    String line;
                    while((line = s.readLine()) != null){
                        s.writeLine(line);
                        System.out.println("Mensaje recibido en el servidor");
                    }
                    s.close();
                }
            }.start(); 
    }
}
}