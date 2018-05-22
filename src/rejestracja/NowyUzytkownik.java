package rejestracja;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class NowyUzytkownik {

    @FXML
    private AnchorPane rejestracja_main;

    @FXML
    private JFXTextField imie;

    @FXML
    private JFXTextField nazwisko;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXTextField haslo;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton zapiszbutton;

    @FXML
    private JFXButton anulujbutton;

    @FXML
    void anuluj(ActionEvent event) {

    }

    @FXML
    void dodajuzytkownika(ActionEvent event) {
        String uzytimie = imie.getText();
        String uzytnazwisko = nazwisko.getText();
        String uzytlogin = login.getText();
        String uzythaslo = haslo.getText();
        String uzytemail = email.getText();

        if (uzytimie.isEmpty() || uzytnazwisko.isEmpty() || uzytlogin.isEmpty() || uzythaslo.isEmpty() || uzytemail.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Wprowadzania");
            alert.setHeaderText("Dodawanie użytkownika");
            alert.setContentText("Proszę wypełnić wszystkie pola");
            alert.showAndWait();
            return;
        }
    }

}
