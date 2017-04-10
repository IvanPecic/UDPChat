package user;

import control.UDPClient;
import database.Users;

import javax.jws.soap.SOAPBinding;

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

    public boolean authenticate(User user){
        if(Users.users.contains(user)){
            this.udpClient =
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
