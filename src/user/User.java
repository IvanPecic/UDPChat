package user;

import control.UDPClient;
import database.Users;

import java.io.IOException;

/**
 * UDPChat
 * Created by Aleksandar on 29.3.2017..
 */
public class User {
    private UDPClient udpClient;
    private String username;
    private String password;
    private boolean loggedIn;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
        this.password = null;
        this.loggedIn = false;
    }

    public static User checkLoggedIn(String username){
        for(User u: Users.users){
            if(u.getUsername().equals(username)){
                if(u.isLoggedIn())
                    return u;
            }
        }
        return null;
    }

    public void authenticate(User user){
        if(Users.users.contains(user)){
            this.udpClient = UDPClient.instance;
        }
    }

    public boolean register(){
        try {
            if(udpClient == null)
                udpClient = UDPClient.instance;
            return udpClient.sendRegRequest(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Log in-uje korisnika
    public boolean login(){
        try {
            this.udpClient = UDPClient.instance;
            setLoggedIn(udpClient.sendLogInRequest(this));
            if(!Users.users.contains(this) && loggedIn) {
                Users.users.add(this);
                Users.users.get(Users.users.indexOf(this)).setLoggedIn(true);
            }
            System.out.println("LOGIN STATUS:" + loggedIn);
            return loggedIn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UDPClient getUdpClient() {
        return udpClient;
    }

    public void setUdpClient(UDPClient udpClient) {
        this.udpClient = udpClient;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    //Salje korisnikovu poruku
    public void sendMessage(String message){
        if(udpClient == null){
            udpClient = UDPClient.instance;
        }
        try {
            udpClient.sendMessage(this, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return this.username.equals(((User) obj).getUsername()) &&
                    this.password.equals(((User) obj).getPassword());

        }
        return false;
    }

    @Override
    public String toString() {
        return username;
    }
}
