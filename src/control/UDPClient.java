package control;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

    public void posaljiPoruku(String poruka) throws Exception{
        String message = poruka;
        byte[] buffer = message.getBytes();
        DatagramPacket packet;
        packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 2017);
        socket.send(packet);
    }

    public static void main(String[] args){
        try {
            instance = new UDPClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
