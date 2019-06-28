package sample.ScenesHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

import java.sql.Connection;
import java.util.HashMap;

public class LoginSceneHandler {
    static HashMap<String, Integer> clientsLoginData = new HashMap<>();

    static HashMap<String, Integer> employeeLoginData = new HashMap<>();

    private  Stage window=null;

    private  Connection connection=null;

    public LoginSceneHandler(Stage w,Connection c){
        this.window=w;
        this.connection=c;
        clientsLoginData.put("k1k1",1);
        clientsLoginData.put("k2k2",2);
        clientsLoginData.put("k3k3",3);
        employeeLoginData.put("p1p1",1);
        employeeLoginData.put("p2p2",2);
    }

    public void loadingLoginScene(){

        VBox loginLayout = new VBox();
        window.setTitle("Audi Cars Dealership");

        TextField login = new TextField();
        login.setPromptText("login");
        login.setPadding(new Insets(10, 10, 10, 10));
        login.setMaxWidth(200);

        PasswordField password = new PasswordField();
        password.setPromptText("password");
        password.setPadding(new Insets(10, 10, 10, 10));
        password.setMaxWidth(200);


        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> logIn(login,password));
        loginButton.setMaxWidth(200);

        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setSpacing(10);
        loginLayout.getChildren().addAll(login, password, loginButton);

        loginLayout.setId("background");
        Scene scene = new Scene(loginLayout, 700, 400);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                logIn(login,password);
            }
        });
        scene.getStylesheets().add("CssFiles/LogIn.css");
        window.setScene(scene);
    }

    private void logIn(TextField login, PasswordField password) {
        if(clientsLoginData.containsKey(login.getText()+password.getText())){
             new ClientSceneHandler(window,connection,this).loadingClientsScene(clientsLoginData.get(login.getText()+password.getText()));

        }
        else if(employeeLoginData.containsKey(login.getText()+password.getText())){
            new EmployeeSceneHandler(window,connection,this).employeeTabScene(employeeLoginData.get(login.getText()+password.getText()));


        }
        else {
            JOptionPane.showMessageDialog(null, "Wrong login or password", "Error during login", JOptionPane.ERROR_MESSAGE);
            password.clear();
            login.clear();
        }
    }
}
