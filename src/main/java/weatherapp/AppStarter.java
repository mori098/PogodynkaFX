package weatherapp;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Scanner;

public class AppStarter extends Application  {


    public static void main(String[] args) {
        launch(args);
    }


    public AppStarter() {



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("bez nazwy.fxml"));
        primaryStage.setTitle("Pogodynka");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }





    }

