package client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private Consumer<Serializable> callback;
    private boolean isConnected;
    private Socket socketClient;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public NetworkConnection(Consumer<Serializable> callback) {
        this.callback = callback;
        isConnected = true;
    }

    public void setConnected(boolean c){ this.isConnected = c; }
    public boolean getConnected(){ return this.isConnected; }

    public void send(Serializable data) throws Exception{
        this.out.writeObject(data);
    }

    public void closeConn() throws Exception{
        isConnected = false;
        this.socketClient.close();

    }

    abstract protected String getIP();
    abstract protected int getPort();


    public void clientConnect(){
         try (Socket s = new Socket(getIP(), getPort());
              ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
              ObjectInputStream in = new ObjectInputStream(s.getInputStream())){

            this.socketClient = s;
            socketClient.setTcpNoDelay(true);
            System.out.println("Client: connection established");
            this.out = out;
            this.in = in;

             send("CONNECTED");
             while (isConnected) {
                 Serializable data = (Serializable) in.readObject();
                 callback.accept(data);

                 System.out.println("Client Recieved: " + data);
             }
            } catch (Exception e) {
                callback.accept("NO CONNECTION");
            }

    }

}


