package dodawanieKsiazki;

import bazadanych.DatabaseControll;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;


public class Nowaksiazka implements Initializable {
    @FXML
    private AnchorPane ksiazka_main;
    @FXML
    private JFXTextField tytul;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField autor;
    @FXML
    private JFXTextField wydawca;
    @FXML
    private JFXTextField cena;
    @FXML
    private JFXButton zapiszbutton;
    @FXML
    private JFXButton anulujbutton;

    DatabaseControll DatabaseControll;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseControll = DatabaseControll.getInstance();
    }


    /**
     * Metoda przypisana do przycisku Zapisz
     * Po naciśnięciu przycisku zapisz, zostają pobrane wszystkie dane z pół tekstowych
     * Następnie zostaje uruchomiona walidacja czy pola nie są puste.
     * Jeśli cała walidacja zostaje spełniona książka zostanie dodany do bazy danych.
     * Zostaje wyświetlony stosowny komunikat, z potwierdzeniem operacji.
     * W przeciwnym przypadku zostaje wyświetlony Error.
     *
     * @param actionEvent Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    @FXML
    public void dodajksiazke(javafx.event.ActionEvent actionEvent) {
        String ksiazkaID = id.getText();
        String ksiazkatytul = tytul.getText();
        String ksiazkaautor = autor.getText();
        String ksiazkawydawca = wydawca.getText();
        String ksiazkacena = cena.getText();

        if (ksiazkaID.isEmpty() || ksiazkaautor.isEmpty() || ksiazkatytul.isEmpty() || ksiazkawydawca.isEmpty() || ksiazkacena.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Wprowadzania");
            alert.setHeaderText("Dodawanie książki");
            alert.setContentText("Proszę wypełnić wszystkie pola");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO KSIAZKA VALUES (" +
                "'" + ksiazkaID + "'," +
                "'" + ksiazkatytul + "'," +
                "'" + ksiazkaautor + "'," +
                "'" + ksiazkawydawca + "'," +
                "'" + true + "'," +
                "" + ksiazkacena + "" +
                ")";
        System.out.println(qu);
        if (DatabaseControll.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Dodawanie książki");
            alert.setContentText("Wszystkie dane dotyczące książki zostały poprawnie dodane do bazy danych");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepowodzenie");
            alert.setHeaderText("Dodawanie książki");
            alert.setContentText("Ksiazka nie zostały dodane do bazy danych(nieznany błąd). Spróbuj jeszcze raz.");
            alert.showAndWait();
        }
    }

    /**
     * Metoda przypisana do przycisku Anuluj
     * Po naciśnięciu przycisku anuluj, zostaje pobrane aktualnie otwarte okno i zamknięte
     *
     * @param actionEvent Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    public void anuluj(javafx.event.ActionEvent actionEvent) {
        Stage ksiazka_zamknij = (Stage) ksiazka_main.getScene().getWindow();
        ksiazka_zamknij.close();
    }
}
