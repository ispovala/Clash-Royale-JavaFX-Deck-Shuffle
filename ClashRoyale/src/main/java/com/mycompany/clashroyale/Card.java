package com.mycompany.clashroyale;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @ispovala
 */
public class Card {

    private final String nombre;
    private final String tipo;
    private final String arena;
    private final String calidad;
    private final String id;
    private Image img;

    public Card(String nombre, String tipo, String arena, String calidad, String id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.arena = arena;
        this.calidad = calidad;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getArena() {
        return arena;
    }

    public String getCalidad() {
        return calidad;
    }

    public String getId() {
        return id;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public static ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(App.pathInfo + "cards.txt", Charset.forName("UTF-8")))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] info = linea.split(",");

                String nombre = info[0];
                String tipo = info[1];
                String arena = info[2];
                String calidad = info[3];
                String id = info[4];

                Card card = new Card(nombre, tipo, arena, calidad, id);

                cards.add(card);
            }

            br.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        for (Card c : cards) {

            Image img;
            try ( FileInputStream fis = new FileInputStream(App.pathImg + c.getId() + ".png")) {
                img = new Image(fis, 120, 180, false, false);
                c.setImg(img);
            } catch (IOException e) {
                System.out.println("No se encontro la imagen...");
            }
        }

        return cards;
    }

    public static ArrayList<Card> randomDeck(ArrayList<Card> deck) {
        ArrayList<Card> randomDeck = new ArrayList<>();
        Random rd = new Random();
        int i = 16;
        while (randomDeck.size() < 8) {
            Card c = deck.get(rd.nextInt(i));
            if (!randomDeck.contains(c)) {
                randomDeck.add(c);
                i--;
            }
        }
        return randomDeck;
    }

    @Override
    public String toString() {
        return "Card{" + "nombre=" + nombre + ", tipo=" + tipo + ", arena=" + arena + ", calidad=" + calidad + ", id=" + id + '}';
    }

}
