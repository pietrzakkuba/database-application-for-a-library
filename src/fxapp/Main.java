package fxapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sql.DatabaseConnection;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main-menu.fxml"))));
        primaryStage.setTitle("Library");
        primaryStage.show();
        mainStage = primaryStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }


    public static void main(String[] args) {
        try {
            DatabaseConnection.connect();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(0);
        }
        launch(args);
    }
}
