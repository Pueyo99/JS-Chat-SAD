package XAT_GRAFIC;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private static Selector selector = null;

    public static void main(String[] args) {

        try {

            selector = Selector.open();

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
        client.register(selector, SelectionKey.OP_READ);
    }

    private static void handleRead(SelectionKey key)
            throws IOException {

        System.out.println("Reading...");
        
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        client.read(buffer);
        String data = new String(buffer.array()).trim();

        if (data.length() > 0) {

            System.out.println("Received message: " + data);

            if (data.equalsIgnoreCase("exit")) {
                
                client.close();
                System.out.println("Connection closed...");
            }
        }
    }
}