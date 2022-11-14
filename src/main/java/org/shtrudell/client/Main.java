package org.shtrudell.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.shtrudell.client.controller.DbEditorController;
import org.shtrudell.client.net.Client;

public class Main extends Application{
    private Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        client = new Client();
        client.connect("127.0.0.1", 55);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbeditor.fxml"));
        Parent root = loader.load();

        var controller = (DbEditorController) loader.getController();
        controller.setPersonnelDAO(client.getPersonnelDAO());
        controller.init();

        stage.setOnCloseRequest(e -> close());

        stage.setTitle("Data Base Editor");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    private void close() {
        client.disconnect();
    }
}