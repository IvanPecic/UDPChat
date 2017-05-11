package database;

import control.UDPClient;
import user.User;

import java.util.ArrayList;

/**
 * UDPChatV2
 * Created by Aleksandar on 9.4.2017..
 */
public class Users {
    public static ArrayList<User> users = new ArrayList<>();
    public static Users instance = new Users();
    static {
        new Users();
    }

    public Users(){
        User u  = new User("user1","123");
        u.setLoggedIn(true);
        User u1  = new User("user2","123");
        u1.setLoggedIn(true);
        users.add(u);
        users.add(u1);
    }

}
