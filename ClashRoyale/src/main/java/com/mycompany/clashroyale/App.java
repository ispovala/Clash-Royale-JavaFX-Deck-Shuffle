package com.mycompany.clashroyale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;


/**
 * JavaFX App
 *
 * @author ispovala
 */
public class App extends Application {

    public static ArrayList<Card> cards = new ArrayList<>();

    public static String pathImg = "src/main/resources/images/";
    public static String pathInfo = "src/main/resources/info/";
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("deck"), 520, 440);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(eh -> exit(0));
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        cards = Card.getCards();
        launch();
    }

}
