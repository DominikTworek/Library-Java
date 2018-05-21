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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class nowaksiazka implements Initializable {
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
    private JFXButton zapiszbutton;
    @FXML
    private JFXButton anulujbutton;

    DatabaseControll DatabaseControll;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseControll = new DatabaseControll();
        sprawdzanie();
    }

    private void sprawdzanie() {
        String qu = "SELECT tytul FROM KSIAZKA";
        ResultSet rs = DatabaseControll.execQuery(qu);
        try {
            while (rs.next()) {
                String tytul_pob = rs.getString("tytul");
                System.out.println(tytul_pob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void dodajksiazke(javafx.event.ActionEvent actionEvent) {
        String ksiazkaID = id.getText();
        String ksiazkatytul = tytul.getText();
        String ksiazkaautor = autor.getText();
        String ksiazkawydawca = wydawca.getText();

        if (ksiazkaID.isEmpty() || ksiazkaautor.isEmpty() || ksiazkatytul.isEmpty() || ksiazkawydawca.isEmpty()) {
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
                "'" + true + "'" +
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
            alert.setContentText("Dane nie zostały dodane do bazy danych(nieznany błąd). Spróbuj jeszcze raz.");
            alert.showAndWait();
        }
    }


    public void anuluj(javafx.event.ActionEvent actionEvent) {
        Stage ksiazka_zamknij = (Stage) ksiazka_main.getScene().getWindow();
        ksiazka_zamknij.close();
    }
}
