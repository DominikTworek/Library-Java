package narzedzia;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
    void Zamknij(javafx.event.ActionEvent event) {
        System.exit(0);
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
