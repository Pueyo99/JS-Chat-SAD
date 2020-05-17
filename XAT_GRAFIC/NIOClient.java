package XAT_GRAFIC;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

    public static void threadWrite(Scanner s, SocketChannel client) {
        Boolean writing = true;

        while (writing) {
            try {
                System.out.println("\n\nIntroduzca su mensaje: ");
                String msg = s.nextLine();

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(msg.getBytes());
                buffer.flip();
                client.write(buffer);
                System.out.println("Mensaje enviado: " + msg);

                if (msg.equals("exit")) {
                    client.close();
                    System.out.println("Cerrando cliente");
                    writing = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void threadRead(SocketChannel client) {
        while (true) {
            try {

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                client.read(buffer);
                String msg = new String(buffer.array()).trim();

                if (msg.length() > 0) {
                    System.out.println("Mensaje recibido: " + msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        try {
            // System.out.println("Starting client...");

            Scanner s = new Scanner(new InputStreamReader(System.in));

            System.out.println("Introduzca su Nick: ");
            String nick = s.nextLine();
            System.out.println("Bienvenido " + nick);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(nick.getBytes());
            buffer.flip();

            SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 8089));
            client.write(buffer);

            new Thread() {
                public void run() {
                    threadRead(client);
                }
            }.start();

            new Thread() {
                public void run() {
                    threadWrite(s, client);
                }
            }.start();
            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
