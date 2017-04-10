package control;

import database.Users;
import user.User;

import javax.jws.soap.SOAPBinding;
import java.io.*;
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
    private DatagramPacket sendPacket;
    
    public  UDPServerThread(DatagramSocket socket, DatagramPacket receivePacket){
        
        this.socket = socket;
        this.receivePacket = receivePacket;
    }
    
    public void handleRequest(String check) throws IOException {
        
        String[] newCheck = check.split("#");
        
        if (newCheck[0].equals("reg")){
            register(newCheck[1],newCheck[2]);
        }else  if (newCheck[0].equals("log")){
            login(newCheck[1],newCheck[2]);
        }else if (newCheck[0].equals("msg")){
            chatMessage(newCheck[1],newCheck[2]);
        }else{
            
        }
    }
    
    public void register(String username, String password){     // Registers a new user to the server
        Users.users.add(new User(username,password));
    }
    
    public void login(String username, String password) throws IOException {        // Checks if the user is in the database and send a confirmation
        if (Users.users.contains(new User(username,password))){
            String response = "OK";
            sendData = response.getBytes();
            sendPacket = new DatagramPacket(sendData,sendData.length);
            socket.send(sendPacket);
        }else {
            //TODO : deny request
        }
    }
    
    public void chatMessage(String clientName, String clientMessage){   //Prints a message to the chat screen
        // TODO : Print message to the screen
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
