package XAT_GRAFIC;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private static Selector selector = null;
    private static HashSet<String> users = new HashSet<>();

    public static String readBuffer(ByteBuffer buffer){
        return new String(buffer.array()).trim();

    }

    public static void writeBuffer(ByteBuffer buffer, String s){
        buffer.put(s.getBytes());
        buffer.flip();
    }

    public static String getUsers(){
        String userList = "#,";

        for(String user: users){
            userList += (user + ",");
        }

        userList += "#";
        return userList;
    }

    public static void sendUsers(){
        try{
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            writeBuffer(buffer, getUsers());
            
            for(SelectionKey sk: selector.keys()){
                if(sk.attachment() != null){

                    SocketChannel c = (SocketChannel) sk.channel();
                    c.write(buffer);
                    System.out.println("Lista de Usuarios enviada a: " +sk.attachment());
                    buffer.flip();
                }

            }
        }catch(IOException ex){}
    }

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
        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);

        ByteBuffer bufferNick = ByteBuffer.allocate(1024);
        client.read(bufferNick);
        String nick = readBuffer(bufferNick);

        //Asegura conger el nick correctamente
        while(!(nick.length() > 0)){
            client.read(bufferNick);
            nick = readBuffer(bufferNick);
        } 

        users.add(nick);
        clientKey.attach(nick);
        System.out.println("Nuevo cliente: " + clientKey.attachment());

        bufferNick.clear();
        String s = "Se ha unido un nuevo usuario: " + nick;
        writeBuffer(bufferNick, s);


        for(SelectionKey sk: selector.keys()){
            
            if((sk.attachment() != clientKey.attachment()) & (sk.attachment() != null)){
                SocketChannel c = (SocketChannel) sk.channel();

                c.write(bufferNick);
                bufferNick.flip();
            }
        }

        sendUsers();
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

            if (data.equalsIgnoreCase("exit")) {
                users.remove(key.attachment());

                buffer.clear();
                data = "El usario " + key.attachment() + " ha salido";
                writeBuffer(buffer, data);

                for(SelectionKey sk: selector.keys()){
            
                    if((sk.attachment() != key.attachment()) & (sk.attachment() != null)){
                        SocketChannel c = (SocketChannel) sk.channel();
        
                        c.write(buffer);
                        buffer.flip();
                        
                    }
                }

                sendUsers();

                client.close();
                
                System.out.println("Connection closed...");


            }else{
                
                //Damos formato al mensaje a reenviar.
                data = ((String)key.attachment()) + ": " +  data;
                buffer.clear();
                writeBuffer(buffer, data);

                //Reenviamos el mensaje al resto de clientes
                for(SelectionKey sk: selector.keys()){
            
                    if((sk.attachment() != key.attachment()) & (sk.attachment() != null)){
                        SocketChannel c = (SocketChannel) sk.channel();
                        c.write(buffer);
                        buffer.flip();
                }
            }
            }


        }
        
    }
}