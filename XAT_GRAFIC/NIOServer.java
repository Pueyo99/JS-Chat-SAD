package XAT_GRAFIC;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private static Selector selector = null;

    public static void main(String[] args) {

        try {

            selector = Selector.open();
            HashSet<String> users = new HashSet<>();

            ServerSocketChannel socket = ServerSocketChannel.open();
            ServerSocket serverSocket = socket.socket();

            serverSocket.bind(new InetSocketAddress("localhost", 8089));

            socket.configureBlocking(false);
            int ops = socket.validOps();
            socket.register(selector, ops, null);

            while (true) {

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> i = selectedKeys.iterator();

                while (i.hasNext()) {
                    SelectionKey key = i.next();

                    if (key.isAcceptable()) {

                        handleAccept(socket, key);

                    } else if (key.isReadable()) {

                        handleRead(key);
                    }
                    i.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleAccept(ServerSocketChannel mySocket,
                                     SelectionKey key) throws IOException {

        System.out.println("Connection Accepted...");

        SocketChannel client = mySocket.accept();
        client.configureBlocking(false);
        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);

        ByteBuffer bufferNick = ByteBuffer.allocate(1024);
        client.read(bufferNick);
        String nick = new String(bufferNick.array()).trim();

        //Asegura conger el nick correctamente
        while(!(nick.length() > 0)){
            client.read(bufferNick);
            nick = new String(bufferNick.array()).trim();
        } 

        clientKey.attach(nick);
        System.out.println("Nuevo cliente: " + clientKey.attachment());
    }


    private static void handleRead(SelectionKey key)
            throws IOException {

        System.out.println("Reading...");
        
        SocketChannel client = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        buffer.flip();
        String data = new String(buffer.array()).trim();

        if (data.length() > 0) {

            System.out.println("Message from " + key.attachment() + ": " + data);

            //Reenviamos el mensaje al resto de clientes
            for(SelectionKey sk: selector.keys()){
            
                if((sk.attachment() != key.attachment()) & (sk.attachment() != null)){
                    SocketChannel c = (SocketChannel) sk.channel();
                    c.write(buffer);
                    System.out.println("Mensaje enviado a: " + sk.attachment());
                }
            }

            if (data.equalsIgnoreCase("exit")) {
                
                client.close();
                System.out.println("Connection closed...");
            }
        }
        
    }
}