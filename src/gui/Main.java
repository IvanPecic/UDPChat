package gui;

import control.UDPClient;
import control.UDPServer;
import database.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;


public class Main extends Application{

   @Override
    public void start(Stage primaryStage) throws Exception{
        TextField userField = new TextField();
        userField.setPromptText("username");
        PasswordField passField = new PasswordField();
        passField.setPromptText("password");
        Button logIn = new Button("Log in");
        logIn.requestFocus();
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(20);
        root.getChildren().addAll(userField,passField,logIn);
        root.setAlignment(Pos.CENTER);
        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        logIn.setOnAction(e->{
            String uName = userField.getText();
            String pWord = passField.getText();
            User u = new User(uName,pWord);
            UDPClient.main(null);
            if(u.login()){
                new ChatScreen(u);
                primaryStage.close();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
