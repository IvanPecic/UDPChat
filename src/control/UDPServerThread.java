package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDPChat
 * Created by Aleksandar on 29.3.2017..
 */
public class UDPServerThread implements Runnable {
    private  DatagramSocket socket;
    private byte[] receiveData = new byte[1024];
    private DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
    private byte[] sendData = new byte[1024];

    public  UDPServerThread(DatagramSocket socket, DatagramPacket receivePacket){

        this.socket = socket;
        this.receivePacket = receivePacket;
    }

    @Override
    public void run() {
        try {

            while (true) {
                // I/O buffers:
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData()).trim();
                System.out.print("Klijent kaze: ");
                System.out.println(message);
                // ------------------ PROTOKOL ----------------- //
                sendData = ("OK").getBytes();
//                socket.send(new DatagramPacket(sendData, 0, sendData.length));
            }
        }catch (Exception e){

        }
    }
}
