package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.DataBaseConnection.DataBaseConnection;
import sample.ScenesHandler.LoginSceneHandler;

import java.sql.Connection;
import java.sql.SQLException;



public class Main extends Application {

    Stage window;
    Connection connection = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        connection = DataBaseConnection.start();
        window.setResizable(false);

        new LoginSceneHandler(window,connection).loadingLoginScene();

        window.show();
        window.setOnCloseRequest(e -> closeProgram());
    }


    public static void main(String[] args) {
        launch(args);

    }

    public void closeProgram() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        window.close();
        System.out.println("End of program");
    }
}


