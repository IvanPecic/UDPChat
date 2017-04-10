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

    public void login(){
        try {
            udpClient.sendLogInRequest(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        udpClient.posaljiPoruku(this, message);

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
