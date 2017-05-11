package database;

import control.Message;
import gui.ChatScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import user.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * UDPChat
 * Created by Aleksandar on 3.5.2017..
 */
public class Messages {
    public static ArrayList<Message> messages = new ArrayList<>();
    public static ObservableList<Message> msgList = FXCollections.observableArrayList(messages);
    public static Messages instance = null;

    public Messages() {
        if(instance == null){
            instance = this;
        }
    }

    public  Messages getInstance() {
        return instance;
    }

    public void setInstance(Messages instance) {
        this.instance = instance;
    }

    public static ObservableList<Message> getMsgList() {
        return msgList;
    }

    public static void setMsgList(ObservableList<Message> msgList) {
        Messages.msgList = msgList;
    }

    public static void addMessage(Message msg){

        System.out.println(Messages.instance);
        instance.getMsgList().add(msg);
        for(Message m : instance.getMsgList()){
            System.out.println("Message:");
            System.out.println(m.getContent());
        }
    }
}
