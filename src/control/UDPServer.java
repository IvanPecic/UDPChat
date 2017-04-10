package control;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDPChat
 * Created by Aleksandar on 29.3.2017..
 */
public class UDPServer {
    public static boolean started = false;
    private int port = 2017;
    private DatagramSocket socket;
    byte[] receiveData = new byte[1024];
    private DatagramPacket receivePacket;
    byte[] sendData = new byte[1024];

    public UDPServer() throws Exception{
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        socket = new DatagramSocket(port);
            UDPServerThread serverThread = new UDPServerThread(socket,receivePacket);
            Thread thread = new Thread(serverThread);
            thread.start();
    }

    public static void main(String[] args){
        try {
            new UDPServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
