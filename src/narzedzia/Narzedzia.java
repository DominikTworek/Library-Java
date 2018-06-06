package narzedzia;


import Glowne.GlowneOkno;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Narzedzia {


    @FXML
    void zaladujDodajKsiazke(javafx.event.ActionEvent event) {
        zaladujOkno("../dodawanieKsiazki/nowaksiazka.fxml", "Dodaj nowa ksiazke");

    }

    @FXML
    void zaladujRejestracje(javafx.event.ActionEvent event) {
        zaladujOkno("../rejestracja/rejestracja.fxml", "Dodaj nowego użytkownika");
    }

    @FXML
    void zaladujTabeleKsiazki(javafx.event.ActionEvent event) {
        zaladujOkno("../tabelaKsiazek/tabelaKsiazek.fxml", "Wyświetl książki");
    }

    @FXML
    void zaladujTabeleUzyt(javafx.event.ActionEvent event) {
        zaladujOkno("../tabelaUzytkownikow/tabelaUzytkownikow.fxml", "Wyświetl użytkowników");
    }

    @FXML
    void zmianaSceny(javafx.event.ActionEvent event) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(GlowneOkno.oknoPowitalne);
        fadeTransition.setByValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                FadeTransition fadeTransition2 = new FadeTransition();
                fadeTransition2.setDuration(Duration.millis(1200));
                fadeTransition2.setNode(GlowneOkno.glownyPanel);
                fadeTransition2.setByValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();
            }
        });


    }

    void zaladujOkno(String lokacja, String nazwa) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(lokacja));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(nazwa);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
