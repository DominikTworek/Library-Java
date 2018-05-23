package Glowne;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GlowneOkno implements Initializable{

    @FXML
    private VBox oknoPowitalne;

    @FXML
    private AnchorPane oknoWyp;

    @FXML
    private AnchorPane zakladka;

    @FXML
    private AnchorPane zakladka_dwa;

    @FXML
    void zaladujDodajKsiazke(ActionEvent event) {
        zaladujOkno("../dodawanieKsiazki/nowaksiazka.fxml", "Dodaj nowa ksiazke");

    }

    @FXML
    void zaladujRejestracje(ActionEvent event) {
        zaladujOkno("../rejestracja/rejestracja.fxml", "Dodaj nowego użytkownika");
    }

    @FXML
    void zaladujTabeleKsiazki(ActionEvent event) {
        zaladujOkno("../tabelaKsiazek/tabelaKsiazek.fxml", "Wyświetl książki");
    }

    @FXML
    void zaladujTabeleUzyt(ActionEvent event) {
        zaladujOkno("../tabelaUzytkownikow/tabelaUzytkownikow.fxml", "Wyświetl użytkowników");
    }

    @FXML
    void zmientab01(Event event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(501));
        fadeTransition.setNode(zakladka_dwa);
        fadeTransition.setByValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FadeTransition fadeTransition2 = new FadeTransition();
                fadeTransition2.setDuration(Duration.millis(501));
                fadeTransition2.setNode(zakladka);
                fadeTransition2.setByValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();
            }
        });
    }

    @FXML
    void zmientab02(Event event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(zakladka);
        fadeTransition.setByValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FadeTransition fadeTransition2 = new FadeTransition();
                fadeTransition2.setDuration(Duration.millis(500));
                fadeTransition2.setNode(zakladka_dwa);
                fadeTransition2.setByValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();
            }
        });
    }



    @FXML
    void zmianaSceny(ActionEvent event) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(oknoPowitalne);
        fadeTransition.setByValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FadeTransition fadeTransition2 = new FadeTransition();
                fadeTransition2.setDuration(Duration.millis(1000));
                fadeTransition2.setNode(oknoWyp);
                fadeTransition2.setByValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();
                oknoPowitalne.setDisable(true);
            }
        });


    }

    void zaladujOkno(String lokacja, String nazwa){
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oknoWyp.setOpacity(0);
        zakladka_dwa.setOpacity(0);
    }
}
