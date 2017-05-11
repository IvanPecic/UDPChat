package control;

import database.Messages;
import database.Users;
import user.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
    
    public void handleRequest(String check, DatagramPacket packet) throws IOException {

        String[] requestTokens = check.split("#");
        //Obradjuje zahtev klijenta
        //
        //Formati zaglavlja:
        //  Registracija:   reg#username#password
        //  Log in:         log#username#password
        //  Poruka:         msg#username#text
        //  Refresh poruka: get#username#time

        if (requestTokens[0].equals("reg")){
            register(requestTokens[1],requestTokens[2],packet);
        }else  if (requestTokens[0].equals("log")){
            login(packet, requestTokens[1],requestTokens[2]);
        }else if (requestTokens[0].equals("msg")){
            chatMessage(requestTokens[1],requestTokens[2]);
        }else if (requestTokens[0].equals("get")){

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = null;
            try {
                date = df.parse(requestTokens[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            updateClient(new User(requestTokens[1]),date,packet);
        }
    }

    //Registruje korinsika na server
    public void register(String username, String password, DatagramPacket packet) throws IOException {
        boolean registered = false;
        User u = new User(username,password);
        if(! Users.users.contains(u)){
            Users.users.add(u);
            registered = true;
        }
        String response;
        if(registered) {
            response = "OK";
        }
        else {
            response = "ERROR";
        }
        sendData = response.getBytes();
        sendPacket =  new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
        socket.send(sendPacket);

    }

    //Loguje korisnika na server
    public void login(DatagramPacket packet, String username, String password) throws IOException {
        String response;
        User u = new User(username,password);
        if (Users.users.contains(u)){
             response = "OK";
             Users.users.get(Users.users.indexOf(u)).setLoggedIn(true);

        }else {
            response = "ERROR";
        }
        sendData = response.getBytes();
        sendPacket =  new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
        socket.send(sendPacket);
    }

    //Dodaje novu poruku na server
    public void chatMessage(String clientName, String clientMessage) {
            User u = User.checkLoggedIn(clientName);
            Message message = new Message(u, Date.from(Instant.now()), clientMessage);
            Messages.instance.addMessage(message);
    }

    //Vraca nove poruke klijentu
    public void updateClient(User u, Date date, DatagramPacket packet) throws IOException {
        Message toSend = null;

        //Ucitavanje poruka iz Queue-a
        if(Messages.instance.getMsgList().size()> 0)
        for(int i = 0; i< Messages.instance.getMsgList().size(); i++) {
            if (!Messages.instance.getMsgList().get(i).getSeen().contains(u.getUsername())) {
                toSend = Messages.instance.getMsgList().get(i);
                Messages.instance.getMsgList().get(i).getSeen().add(u.getUsername());
                break;
            }

        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String response;
        if(toSend == null){
            response = "EMPTY";
        }
        else {
            response = toSend.getSender().getUsername() + "#" + df.format(toSend.getDate()) + "#" + toSend.getContent();
        }

            sendData = response.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
            socket.send(sendPacket);

    }
    @Override
    public void run() {
        try {
            
            while (true) {
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData,receiveData.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData()).trim();
                System.out.print("Klijent kaze: ");
                System.out.println(message);
                handleRequest(message, receivePacket);
            }
        }catch (Exception e){
            
        }
    }

}
