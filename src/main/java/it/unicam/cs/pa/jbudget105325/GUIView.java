package it.unicam.cs.pa.jbudget105325;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

/**
 * <p>Classe che implementa l'interfaccia grafica dell'applicazione</p>
 */
public class GUIView extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Paths.get("./src/main/resources/startwindow.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("JBudget");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.requestFocus();

    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
