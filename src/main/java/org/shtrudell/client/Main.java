package org.shtrudell.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dbeditor.fxml"));
        //stage.setTitle("DBEditor");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}