package user;

import control.UDPClient;
import database.Users;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;

/**
 * UDPChat
 * Created by Aleksandar on 29.3.2017..
 */
public class User {
    private UDPClient udpClient;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void authenticate(User user){
        if(Users.users.contains(user)){
            this.udpClient = UDPClient.instance;
        }
    }

    public void register(){
        try {
            udpClient.sendRegRequest(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(){
        try {
            this.udpClient = UDPClient.instance;
            return udpClient.sendLogInRequest(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendMessage(String message){
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
}
