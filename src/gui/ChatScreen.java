package gui;

import control.Message;
import control.UDPClient;
import database.Messages;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.Instant;

/**
 * UDPChatV2
 * Created by Aleksandar on 9.4.2017..
 */
public class ChatScreen extends Stage {
    private VBox root = new VBox();
    private TextArea textArea = new TextArea();
    private Button send = new Button("Send");
    private static User user;
    private static ListView<Message> messages = new ListView<>();

    public ChatScreen(User user){
        new Messages();

        this.user = user;
        this.setTitle(user.getUsername());
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.setSpacing(20);
        Messages.messages.add(new Message(user, Date.from(Instant.now()),"DEMO MESSAGE"));
        messages.setItems(Messages.instance.getMsgList());
        root.getChildren().addAll(messages,textArea,send);
        textArea.setMaxHeight(50);

        this.setScene(new Scene(root));
        this.show();

        send.setOnAction(e->{
            try {
                user.sendMessage( textArea.getText());
                textArea.setText("");
                refresh();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Timer za automatsko refreshovanje sa servera
        Timer t = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refresh();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        t.start();

        setOnCloseRequest(e->{
            e.consume();
            Platform.exit();
            System.exit(1);
        });
    }
    public static void refresh() throws Exception {
        UDPClient.instance.getMessages(user);
        System.out.println("Refreshing");
        for(Message m : Messages.instance.getMsgList()){
            System.out.println("Message:");
            System.out.println(m.getContent());
        }
    }


}
