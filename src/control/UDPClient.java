package control;

import user.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * UDPChat
 * Created by Aleksandar on 9.4.2017..
 */
public class UDPClient  {
    public static UDPClient instance;
    private DatagramSocket socket;
    public  UDPClient() throws  Exception {
    socket = new DatagramSocket();
    socket.setSoTimeout(10000);
    Scanner tastatura = new Scanner(System.in);
    String message;

//		while(true) {
//
//            // SLANJE PAKETA
//            System.out.print("Unesi poruku: ");
//            message = tastatura.nextLine();
//            byte[] buffer = message.getBytes();
//            DatagramPacket packet;
//            packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 2017);
//            socket.send(packet);
//
//            //PRIHVATANJE PAKETA
////            buffer = new byte[1500];
////            packet = new DatagramPacket(buffer, 1500);
////            socket.receive(packet);
////            message = new String(buffer).trim();
////            System.out.println("[Receiver]: " + message);
//        }
    }

    public void sendMessage(User u, String message) throws Exception{
        String requestMsg = "msg" +"#"+ u.getUsername() + "#" + message;
        byte[] buffer = requestMsg.getBytes();
        DatagramPacket packet;
        packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 2017);
        socket.send(packet);
    }

    public void sendRegRequest(User u) throws IOException {
        String username = u.getUsername();
        String password = u.getPassword();
        String requestMsg = "reg"+"#"+username + "#" + password;
        byte[] buffer = requestMsg.getBytes();
        DatagramPacket packet;
        packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 2017);
        socket.send(packet);

    }

    public boolean sendLogInRequest(User u) throws IOException {
        String username = u.getUsername();
        String password = u.getPassword();
        String requestMsg = "log"+"#"+username + "#" + password;
        byte[] buffer = requestMsg.getBytes();
        DatagramPacket packet;
        packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 2017);
        socket.send(packet);
        byte[] recvBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(recvBuffer,recvBuffer.length);
        boolean responseReceived = false;

            socket.receive(receivePacket);
            responseReceived = true;
            String responseString  = new String(recvBuffer).trim();
            System.out.println("RESPONSE RECEIVED");
            return responseString.equals("OK");
    }

    public static void main(String[] args){
        try {
            instance = new UDPClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
