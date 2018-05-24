package Glowne;


import bazadanych.DatabaseControll;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GlowneOkno implements Initializable {


    @FXML
    public TextField idKsiazki;

    @FXML
    public TextField idUzytkownika;

    @FXML
    private VBox oknoPowitalne;

    @FXML
    private AnchorPane oknoWyp;

    @FXML
    private AnchorPane zakladka;

    @FXML
    private AnchorPane zakladka_dwa;

    @FXML
    private HBox tloKsiazka;

    @FXML
    private HBox tloUzytkownik;

    @FXML
    private Text nazwaKsiazki;

    @FXML
    private Text autorKsiazki;

    @FXML
    private Text wydawcaKsiazki;

    @FXML
    private Text dostepnoscKsiazka;

    @FXML
    private Text imieU;

    @FXML
    private Text nazwiskoU;

    @FXML
    private Text loginU;

    @FXML
    private Text emailU;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oknoWyp.setDisable(true);
        oknoWyp.setOpacity(0);
        zakladka_dwa.setOpacity(0);
        nazwaKsiazki.setOpacity(1);
        autorKsiazki.setOpacity(1);
        wydawcaKsiazki.setOpacity(1);
        dostepnoscKsiazka.setOpacity(1);
        JFXDepthManager.setDepth(tloKsiazka, 1);
        JFXDepthManager.setDepth(tloUzytkownik, 1);
    }

    @FXML
    void zmientab01(Event event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(501));
        fadeTransition.setNode(zakladka_dwa);
        fadeTransition.setByValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        zakladka_dwa.setDisable(true);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                zakladka.setDisable(false);
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
        zakladka.setDisable(true);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                zakladka_dwa.setDisable(false);
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
                oknoWyp.setDisable(false);
                FadeTransition fadeTransition2 = new FadeTransition();
                fadeTransition2.setDuration(Duration.millis(1200));
                fadeTransition2.setNode(oknoWyp);
                fadeTransition2.setByValue(0);
                fadeTransition2.setToValue(1);
                fadeTransition2.play();
                zakladka_dwa.setDisable(true);
                oknoPowitalne.setDisable(true);
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

    void animacje_tekstu_ksiazka() {
        nazwaKsiazki.setVisible(true);
        wydawcaKsiazki.setVisible(true);
        dostepnoscKsiazka.setVisible(true);
        nazwaKsiazki.setOpacity(0);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1501));
        fadeTransition.setNode(nazwaKsiazki);
        fadeTransition.setByValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        autorKsiazki.setOpacity(0);
        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setDuration(Duration.millis(1500));
        fadeTransition2.setNode(autorKsiazki);
        fadeTransition2.setByValue(0);
        fadeTransition2.setToValue(1);
        fadeTransition2.play();
        wydawcaKsiazki.setOpacity(0);
        FadeTransition fadeTransition3 = new FadeTransition();
        fadeTransition3.setDuration(Duration.millis(1500));
        fadeTransition3.setNode(wydawcaKsiazki);
        fadeTransition3.setByValue(0);
        fadeTransition3.setToValue(1);
        fadeTransition3.play();
        dostepnoscKsiazka.setOpacity(0);
        FadeTransition fadeTransition4 = new FadeTransition();
        fadeTransition4.setDuration(Duration.millis(1500));
        fadeTransition4.setNode(dostepnoscKsiazka);
        fadeTransition4.setByValue(0);
        fadeTransition4.setToValue(1);
        fadeTransition4.play();
    }

    void tekst_false_ksiazka (){
        autorKsiazki.setOpacity(0);
        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setDuration(Duration.millis(1502));
        fadeTransition2.setNode(autorKsiazki);
        fadeTransition2.setByValue(0);
        fadeTransition2.setToValue(1);
        fadeTransition2.play();
    }

    void animacje_tekstu_uzyt() {
        imieU.setVisible(true);
        loginU.setVisible(true);
        emailU.setVisible(true);
        imieU.setOpacity(0);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1501));
        fadeTransition.setNode(imieU);
        fadeTransition.setByValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        nazwiskoU.setOpacity(0);
        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setDuration(Duration.millis(1501));
        fadeTransition2.setNode(nazwiskoU);
        fadeTransition2.setByValue(0);
        fadeTransition2.setToValue(1);
        fadeTransition2.play();
        loginU.setOpacity(0);
        FadeTransition fadeTransition3 = new FadeTransition();
        fadeTransition3.setDuration(Duration.millis(1501));
        fadeTransition3.setNode(loginU);
        fadeTransition3.setByValue(0);
        fadeTransition3.setToValue(1);
        fadeTransition3.play();
        emailU.setOpacity(0);
        FadeTransition fadeTransition4 = new FadeTransition();
        fadeTransition4.setDuration(Duration.millis(1501));
        fadeTransition4.setNode(emailU);
        fadeTransition4.setByValue(0);
        fadeTransition4.setToValue(1);
        fadeTransition4.play();
    }

    void tekst_false_uzyt(){
        nazwiskoU.setOpacity(0);
        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setDuration(Duration.millis(1501));
        fadeTransition2.setNode(nazwiskoU);
        fadeTransition2.setByValue(0);
        fadeTransition2.setToValue(1);
        fadeTransition2.play();
    }


    @FXML
    void idKsiazki(KeyEvent event) {
        String id = idKsiazki.getText();
        String qu = "SELECT * FROM KSIAZKA WHERE id = '" + id + "'";
        ResultSet rs = DatabaseControll.execQuery(qu);
        Boolean spr_ksiazki = false;
        String status_ksiazki;
        try {
            while (rs.next()) {
                String ptytul = rs.getString("tytul");
                String pautor = rs.getString("autor");
                String pwydawca = rs.getString("wydawca");
                Boolean pdostepnosc = rs.getBoolean("dostepnosc");
                status_ksiazki = String.valueOf((pdostepnosc));
                nazwaKsiazki.setText(ptytul);
                autorKsiazki.setText(pautor);
                wydawcaKsiazki.setText(pwydawca);
                if(status_ksiazki == "true")
                    status_ksiazki = "Dostępna";
                else
                    status_ksiazki = "Niedostepna";
                dostepnoscKsiazka.setText(status_ksiazki);
                spr_ksiazki = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!spr_ksiazki){
            nazwaKsiazki.setVisible(false);
            autorKsiazki.setText("Nie ma takiej książki");
            wydawcaKsiazki.setVisible(false);
            dostepnoscKsiazka.setVisible(false);
            tekst_false_ksiazka();
        }
        else
            animacje_tekstu_ksiazka();
    }

    @FXML
    void idUzytkownika(KeyEvent event) {
        String id = idUzytkownika.getText();
        //language=GenericSQL
        String qu = "SELECT * FROM UZYTKOWNIK WHERE id = '" + id + "'";
        ResultSet rs = DatabaseControll.execQuery(qu);
        Boolean spr_uzyt = false;
        try {
            while (rs.next()) {
                String pimie = rs.getString("imie");
                String pnazwisko = rs.getString("nazwisko");
                String plogin = rs.getString("login");
                String pemail = rs.getString("email");
                imieU.setText(pimie);
                nazwiskoU.setText(pnazwisko);
                loginU.setText(plogin);
                emailU.setText(pemail);
                spr_uzyt = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!spr_uzyt){
            nazwiskoU.setText("Nie ma takiego uzytkownika");
            imieU.setVisible(false);
            nazwiskoU.setOpacity(1);
            loginU.setVisible(false);
            emailU.setVisible(false);
            tekst_false_uzyt();
        }
        else
            animacje_tekstu_uzyt();
    }


}
