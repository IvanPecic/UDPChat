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
        users.add(new User("todor","123"));
    }
}
