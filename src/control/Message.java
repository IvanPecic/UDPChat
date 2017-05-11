package control;

import user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * UDPChat
 * Created by Aleksandar on 3.5.2017..
 */
public class Message {
    private User sender;
    private Date date;
    private String content;
    private ArrayList<String> seen = new ArrayList<>();


    public ArrayList<String> getSeen() {
        return seen;
    }

    public void setSeen(ArrayList<String> seen) {
        this.seen = seen;
    }

    public Message(User sender, Date date, String content) {
        this.sender = sender;
        this.date = date;
        this.content = content;


    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return " ["+df.format(date)+"] " + sender + ": " + content;
    }
}
