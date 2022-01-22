/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.clashroyale;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ispovala
 */
public class DeckController implements Initializable {

    ArrayList<Card> cards = new ArrayList<>();
    boolean shuffleBool = true;
    int shuffleClicked = -1;

    @FXML
    private FlowPane flowPane;
    @FXML
    private Button shuffleButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.updateDeck();
    }

    private void setDeck() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                flowPane.getChildren().clear();

                for (Card c : cards) {
                    Pane pane = new Pane();
                    ImageView iv = new ImageView(c.getImg());
                    pane.getChildren().add(iv);
                    pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent me) {
                            crearNuevaVentana(new ImageView(c.getImg()), c.getNombre(), c.getTipo(), c.getArena(), c.getCalidad());
                        }
                    });
                    flowPane.getChildren().add(pane);
                }
            }
        });

    }

    private void crearNuevaVentana(ImageView iv, String nombre, String tipo, String arena, String calidad) {
        Stage stage = new Stage();

        Label nombreLabel = new Label(nombre);
        TextFlow nombreContainer = new TextFlow(nombreLabel);
        nombreContainer.setTextAlignment(TextAlignment.CENTER);
        nombreContainer.setStyle("-fx-font-weight: bold");
        nombreContainer.setStyle("-fx-font-size: 20px");

        Label tipoLabel = new Label("Tipo: " + tipo);
        Label arenaLabel = new Label("Arena: " + arena);
        Label calidadLabel = new Label("Calidad: " + calidad);

        Button btnSalir = new Button();
        btnSalir.setText("Salir");
        btnSalir.setOnAction(e -> {
            stage.close();
        });

        VBox root = new VBox(nombreContainer, iv, tipoLabel, arenaLabel, calidadLabel, btnSalir);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        Scene scene = new Scene(root, 300, 420);
        stage.setScene(scene);

        stage.show();
    }

    public void updateDeck() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (shuffleBool) {
                    cards = Card.randomDeck(App.cards);
                    setDeck();
                    if (shuffleClicked < 0) {
                        shuffleBool = false;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                shuffleButton.setText("Shuffle");
                            }
                        });
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public void shuffle() {
        shuffleBool = true;
        shuffleClicked *= -1;
        updateDeck();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                shuffleButton.setText("Stop");
            }
        });

    }

}
