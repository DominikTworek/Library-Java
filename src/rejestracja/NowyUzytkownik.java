package rejestracja;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

    /**
     * Metoda przypisana do przycisku Anuluj
     * Po naciśnięciu przycisku anuluj, zostaje pobrane aktualnie otwarte okno i zamknięte
     *
     * @param event Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    @FXML
    void anuluj(ActionEvent event) {
        Stage rejestracja_zamknij = (Stage) rejestracja_main.getScene().getWindow();
        rejestracja_zamknij.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseControll = DatabaseControll.getInstance();
    }


    /**
     * Dzięki tej metodzie możemy sprawdzić poprawność adresu @.
     * Adres @ możliwy do zaakceptowania to: xxx@x.x
     *
     * @param email Zmienna, do której ładujemy nasz adres @ do sprawdzenia
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Metoda przypisana do przycisku Zapisz
     * Po naciśnięciu przycisku zapisz, zostają pobrane wszystkie dane z pół tekstowych
     * Następnie zostaje uruchomiona walidacja czy pola nie są puste oraz czy adres @ jest poprawny.
     * Jeśli cała walidacja zostaje spełniona użytkownik zostanie dodany do bazy danych.
     * Zostaje wyświetlony stosowny komunikat, z potwierdzeniem operacji.
     * W przeciwnym przypadku zostaje wyświetlony Error.
     *
     * @param event Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
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
        if(!isValidEmailAddress(uzytemail)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Wprowadzania");
            alert.setHeaderText("Dodawanie użytkownika");
            alert.setContentText("Proszę sprawdzić poprawność adresu @");
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
