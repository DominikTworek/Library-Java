package logowanie;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogowanieOkno implements Initializable {

    bazadanych.DatabaseControll DatabaseControll;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField haslo;

    @FXML
    private AnchorPane logowanieOkno;

    @FXML
    private Label tytulLogowanie;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseControll = DatabaseControll.getInstance();
    }

    /**
     * Metoda przypisana do przycisku Wyjdź
     * Po naciśnięciu przycisku Wyjdź cały program się zamyka.
     */
    @FXML
    void przyciskWyjdz(ActionEvent event) {
        System.exit(0);

    }

    /**
     * Metoda przypisana do przycisku Zaloguj
     * Po naciśnięciu przycisku Zaloguj następuje walidacja danych logowania
     * Jeśli dane są niepoprawne kolor danego pola tekstowego zmieni swój kolor na czerwono.
     * Po przejściu walidacji zostaje zamknięte okno logowania i załadowane okno zarządzania biblioteką
     */
    @FXML
    void przyciskZaloguj(ActionEvent event) {
        login.getStyleClass().remove("blad");
        haslo.getStyleClass().remove("blad");
        login.getStyleClass().remove("dobrze");
        haslo.getStyleClass().remove("dobrze");
        String logowanieLogin = login.getText();
        String logowanieHaslo = haslo.getText();
        if (logowanieLogin.isEmpty() && logowanieHaslo.isEmpty()) {
            login.getStyleClass().add("blad");
            haslo.getStyleClass().add("blad");
        }
        else if(logowanieLogin.isEmpty()){
            login.getStyleClass().add("blad");
        }
        else if(logowanieHaslo.isEmpty()){
            haslo.getStyleClass().add("blad");

        }
        else{
            String qu = "SELECT * FROM UZYTKOWNIK";
            ResultSet rs = DatabaseControll.execQuery(qu);
            try {
                while(rs.next()){
                    String plogin = rs.getString("login");
                    String phaslo = rs.getString("haslo");
                    Integer prola = rs.getInt("rola");


                    if(plogin.equals(logowanieLogin)){
                        login.getStyleClass().add("dobrze");
                        if(phaslo.equals(logowanieHaslo)){
                            haslo.getStyleClass().add("dobrze");
                            if(prola == 2){
                                Stage logowanie_zamknij = (Stage) logowanieOkno.getScene().getWindow();
                                logowanie_zamknij.close();
                                zaladujOkno("../Glowne/GlowneOkno.fxml", "Okno Administracji");
                            }
                            else{
                                Stage logowanie_zamknij = (Stage) logowanieOkno.getScene().getWindow();
                                logowanie_zamknij.close();
                                zaladujOkno("../Glowne/GlowneOkno.fxml", "Okno Użytkownika");
                            }
                        }
                        else {
                            haslo.getStyleClass().add("blad");
                        }
                    }
                    else{
                        login.getStyleClass().add("blad");
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * Dzięki zaladujOkno możemu w prosty sposób pobierać sceny do programu.
     * @param  lokacja Lokalizacja danej sceny
     * @param nazwa Nazwa sceny, która będzie się pokazywać jako tytuł.
     */
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
