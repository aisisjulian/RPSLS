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

    public void clientConnect(String username){
         try (Socket s = new Socket(getIP(), getPort());
              ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
              ObjectInputStream in = new ObjectInputStream(s.getInputStream())){

            this.socketClient = s;
            socketClient.setTcpNoDelay(true);
            System.out.println("Client: connection established");
            this.out = out;
            this.in = in;

           //  send("CONNECTED");
             try{ send("NAME: " + username); } catch(Exception e){ System.out.println("didn't send name");}
             while (isConnected) {
                 Serializable data = (Serializable) in.readObject();

                 if(data.toString().split(" ")[0].equals("PLAY-REQUEST:")){
                     send(data);
                 }
                 if(data.toString().split(" ")[0].equals("WAIT")){
                     send(data);
                 }
                 if(data.toString().split(" ")[0].equals("ACCEPTED")){
                     send(data);
                 }
                 if(data.toString().split(" ")[0].equals("DISCONNECTED")){
                     send(data);
                 }
                 callback.accept(data);

                 System.out.println("Client Recieved: " + data);
             }
            } catch (Exception e) {
                callback.accept("NO CONNECTION");
            }
    }
}