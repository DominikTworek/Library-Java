package rejestracja;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NowyUzytkownik implements Initializable {

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

    bazadanych.DatabaseControll DatabaseControll;

    @FXML
    void anuluj(ActionEvent event) {
        Stage rejestracja_zamknij = (Stage) rejestracja_main.getScene().getWindow();
        rejestracja_zamknij.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseControll = DatabaseControll.getInstance();
    }

    @FXML
    void dodajuzytkownika(ActionEvent event) {
        String su = "SELECT MAX(id) as id FROM UZYTKOWNIK";
        ResultSet rsid = DatabaseControll.execQuery(su);


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
        try {
            while(rsid.next()) {
                String getid = rsid.getString("id");
                Integer test = Integer.valueOf(getid);
                int test2 = Integer.parseInt(getid)+1;
                String qu = "INSERT INTO UZYTKOWNIK VALUES (" +
                        "'" + test2 + "'," +
                        "'" + uzytimie + "'," +
                        "'" + uzytnazwisko + "'," +
                        "'" + uzytlogin + "'," +
                        "'" + uzythaslo + "'," +
                        "'" + uzytemail + "'," +
                        "'" + 1 + "'," +
                        "" + 0 + "" +
                        ")";
                System.out.println(qu);
                if (DatabaseControll.execAction(qu)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Potwierdzenie");
                    alert.setHeaderText("Dodawanie użytkownika");
                    alert.setContentText("Wszystkie dane dotyczące użytkownika zostały poprawnie dodane do bazy danych");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niepowodzenie");
                    alert.setHeaderText("Dodawanie użytkoniwka");
                    alert.setContentText("Ksiazka nie zostały dodane do bazy danych. Spróbuj jeszcze raz.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
