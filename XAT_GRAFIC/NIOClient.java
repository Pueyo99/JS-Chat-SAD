package XAT_GRAFIC;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) {

        Boolean writing = true;

        try {
            System.out.println("Starting client...");
            SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 8089));

            Scanner s = new Scanner(new InputStreamReader(System.in));


            while(writing){

                System.out.println("Introduzca su mensaje: ");
                String msg = s.next();

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(msg.getBytes());
                buffer.flip();
                client.write(buffer);
                System.out.println("Mensaje enviado: " + msg);

                if(msg.equals("exit")){
                    client.close();
                    System.out.println("Cerrando cliente");
                    writing = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
