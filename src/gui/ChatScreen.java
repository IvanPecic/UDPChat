package gui;

import control.UDPClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;

/**
 * UDPChatV2
 * Created by Aleksandar on 9.4.2017..
 */
public class ChatScreen extends Stage {
    private VBox root = new VBox();
    private TextArea textArea = new TextArea();
    private Button send = new Button("Send");
    private User user;

    public ChatScreen(User user){
        this.user = user;
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.setSpacing(20);
        root.getChildren().addAll(textArea,send);

        this.setScene(new Scene(root));
        this.show();

        send.setOnAction(e->{
            try {
                user.sendMessage( textArea.getText());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
