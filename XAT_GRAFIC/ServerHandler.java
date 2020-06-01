package XAT_GRAFIC;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;

public class ServerHandler {

    public static void handleAccept(ServerSocketChannel mySocket,
    SelectionKey key, HashSet<String> users, Selector selector) throws IOException {

        System.out.println("Connection Accepted...");

        SocketChannel client = mySocket.accept();
        client.configureBlocking(false);
        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);

        ByteBuffer bufferNick = ByteBuffer.allocate(1024);
        client.read(bufferNick);
        String nick = BufferHandler.readBuffer(bufferNick);

        //Asegura conger el nick correctamente
        while(!(nick.length() > 0)){
            client.read(bufferNick);
            nick = BufferHandler.readBuffer(bufferNick);
        } 

        users.add(nick);
        clientKey.attach(nick);
        System.out.println("Nuevo cliente: " + clientKey.attachment());

        bufferNick.clear();
        String s = "Se ha unido un nuevo usuario: " + nick;
        BufferHandler.writeBuffer(bufferNick, s);


        for(SelectionKey sk: selector.keys()){

            if((sk.attachment() != clientKey.attachment()) & (sk.attachment() != null)){
                SocketChannel c = (SocketChannel) sk.channel();

                c.write(bufferNick);
                bufferNick.flip();
            }
        }

        UsersHandler.sendUsers(users, selector);
    }


    public static void handleRead(SelectionKey key, HashSet<String> users, Selector selector)
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
            BufferHandler.writeBuffer(buffer, data);

            for(SelectionKey sk: selector.keys()){

                if((sk.attachment() != key.attachment()) & (sk.attachment() != null)){
                SocketChannel c = (SocketChannel) sk.channel();

                c.write(buffer);
                buffer.flip();

                }
            }

            UsersHandler.sendUsers(users, selector);

            client.close();

            System.out.println("Connection closed...");

            }else{

                //Damos formato al mensaje a reenviar.
                data = ((String)key.attachment()) + ": " +  data;
                buffer.clear();
                BufferHandler.writeBuffer(buffer, data);

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