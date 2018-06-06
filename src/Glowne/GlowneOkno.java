package Glowne;


import bazadanych.DatabaseControll;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;

import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.Event;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.util.Duration;



import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class GlowneOkno implements Initializable {



    @FXML
    public static JFXTabPane glownyPanel;

    @FXML
    public TextField idKsiazki;

    @FXML
    public TextField idUzytkownika;

    @FXML
    public static VBox oknoPowitalne;

    @FXML
    private HBox tloKsiazka;

    @FXML
    private HBox tloUzytkownik;

    @FXML
    private Text cenaKsiazki;

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
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXTextField idKsiazki2;

    @FXML
    private HBox tablicaInformacji;

    @FXML
    private Text infoTytul;

    @FXML
    private Text infoAutor;

    @FXML
    private Text infoWydawca;

    @FXML
    private Text infoImie;

    @FXML
    private Text infoNazwisko;

    @FXML
    private Text infoEmail;

    @FXML
    private Text infoDataWyp;

    @FXML
    private Text infoDataOdd;

    @FXML
    private Text infoOdnowienia;

    @FXML
    private Text infoDodatkowaOplata;

    @FXML
    private Text infoIdKsiazki;

    @FXML
    private Text infoIdUzytkownika;

    @FXML
    private ListView<String> listaInformacji;

    Boolean sprWczytaniaKsiazki = false;



    DatabaseControll databaseControll;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazwaKsiazki.setOpacity(1);
        autorKsiazki.setOpacity(1);
        wydawcaKsiazki.setOpacity(1);
        dostepnoscKsiazka.setOpacity(1);
        JFXDepthManager.setDepth(tloKsiazka, 1);
        JFXDepthManager.setDepth(tloUzytkownik, 1);

        databaseControll = DatabaseControll.getInstance();
        initRamka();
    }

    private void initRamka() {
        try {
            VBox narzedzia = FXMLLoader.load(getClass().getResource("../narzedzia/narzedzia.fxml"));
            drawer.setSidePane(narzedzia);
            HamburgerSlideCloseTransition zadanie = new HamburgerSlideCloseTransition (hamburger);
            zadanie.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (Event event) -> {
                zadanie.setRate(zadanie.getRate() * -1);
                zadanie.play();
                if(drawer.isClosed()){
                    drawer.open();
                    drawer.setPrefWidth(Region.USE_COMPUTED_SIZE);
                } else {
                    drawer.close();
                    drawer.setPrefWidth(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void animacje_informacji(){
        ScaleTransition fadeTransition = new ScaleTransition();
        fadeTransition.setDuration(Duration.millis(300));
        fadeTransition.setNode(tablicaInformacji);
        fadeTransition.setFromX(1);
        fadeTransition.setToX(0);
        fadeTransition.setFromZ(1);
        fadeTransition.setToZ(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ScaleTransition fadeTransition2 = new ScaleTransition();
                fadeTransition2.setDuration(Duration.millis(300));
                fadeTransition2.setNode(tablicaInformacji);
                fadeTransition2.setFromX(0);
                fadeTransition2.setToX(1);
                fadeTransition2.setFromZ(0);
                fadeTransition2.setToZ(1);
                fadeTransition2.play();
            }
        });
    }



    void animacje_tekstu_ksiazka() {
        nazwaKsiazki.setVisible(true);
        wydawcaKsiazki.setVisible(true);
        dostepnoscKsiazka.setVisible(true);
        cenaKsiazki.setVisible(true);
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
        cenaKsiazki.setOpacity(0);
        FadeTransition fadeTransition5 = new FadeTransition();
        fadeTransition5.setDuration(Duration.millis(1500));
        fadeTransition5.setNode(cenaKsiazki);
        fadeTransition5.setByValue(0);
        fadeTransition5.setToValue(1);
        fadeTransition5.play();
    }

    void tekst_false_ksiazka() {
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

    void tekst_false_uzyt() {
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
                Integer pcena = rs.getInt("cena");
                String wyswietlanie_ceny = String.valueOf(pcena);
                status_ksiazki = String.valueOf((pdostepnosc));
                nazwaKsiazki.setText(ptytul);
                autorKsiazki.setText(pautor);
                wydawcaKsiazki.setText(pwydawca);
                cenaKsiazki.setText(wyswietlanie_ceny+" zł");
                if (status_ksiazki == "true")
                    status_ksiazki = "Dostępna";
                else
                    status_ksiazki = "Niedostepna";
                dostepnoscKsiazka.setText(status_ksiazki);
                spr_ksiazki = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!spr_ksiazki) {
            nazwaKsiazki.setVisible(false);
            autorKsiazki.setText("Nie ma takiej książki");
            wydawcaKsiazki.setVisible(false);
            dostepnoscKsiazka.setVisible(false);
            cenaKsiazki.setVisible(false);
            tekst_false_ksiazka();
        } else
            animacje_tekstu_ksiazka();
    }

    @FXML
    void idUzytkownika(KeyEvent event) {
        String id = idUzytkownika.getText();
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
        if (!spr_uzyt) {
            nazwiskoU.setText("Nie ma takiego uzytkownika");
            imieU.setVisible(false);
            nazwiskoU.setOpacity(1);
            loginU.setVisible(false);
            emailU.setVisible(false);
            tekst_false_uzyt();
        } else
            animacje_tekstu_uzyt();
    }

    @FXML
    void przyciskWypozycz(ActionEvent event) {
        String id_ksiazki = idKsiazki.getText();
        String id_uzytkownika = idUzytkownika.getText();


        String spr = "SELECT dostepnosc FROM KSIAZKA WHERE  id = '" + id_ksiazki + "'";
        ResultSet sprawdzenie = DatabaseControll.execQuery(spr);
        String bospr = null;
        try {
            while (sprawdzenie.next()) {
                bospr = sprawdzenie.getString("dostepnosc");
                Boolean bospr2 = Boolean.valueOf(bospr);
                if (bospr2) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potwierdź poprawność danych");
                    alert.setHeaderText(null);
                    alert.setContentText("Czy napewno chcesz wypożyczyć książkę \n" + nazwaKsiazki.getText() + " dla " + imieU.getText() + " " + nazwiskoU.getText() + " ?");

                    Optional<ButtonType> response = alert.showAndWait();
                    if (response.get() == ButtonType.OK) {

                        String qu = "INSERT INTO WYPOZYCZENIA(ksiazka_id, uzytkownik_id) VALUES ("
                                + "'" + id_ksiazki + "',"
                                + "'" + id_uzytkownika + "')";
                        String qu2 = "UPDATE KSIAZKA SET dostepnosc = false WHERE id = '" + id_ksiazki + "'";
                        System.out.println(qu + " i " + qu2);


                        if (DatabaseControll.execAction(qu) && DatabaseControll.execAction(qu2)) {

                            String qu3 = "SELECT czas FROM WYPOZYCZENIA WHERE ksiazka_id = '" + id_ksiazki + "'";
                            ResultSet rs3 = DatabaseControll.execQuery(qu3);

                            try {
                                while (rs3.next()) {
                                    Timestamp pczas = rs3.getTimestamp("czas");
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(pczas.getTime());

                                    cal.add(Calendar.DAY_OF_MONTH, 30);
                                    pczas = new Timestamp(cal.getTime().getTime());

                                    String qu4 = "UPDATE WYPOZYCZENIA SET czas_oddania = '" + pczas + "' WHERE ksiazka_id = '" + id_ksiazki + "'";
                                    DatabaseControll.execAction(qu4);
                                    System.out.println(qu4);
                                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                    alert2.setTitle("Potwierdzenie");
                                    alert2.setHeaderText("Dodawanie wypożyczenia");
                                    alert2.setContentText("Wszystkie dane dotyczące wypożyczenia zostały poprawnie dodane do bazy danych");
                                    alert2.showAndWait();
                                }
                                } catch (SQLException e){
                                e.printStackTrace();
                            }
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Niepowodzenie");
                            alert2.setHeaderText("Dodawanie wypożyczenia");
                            alert2.setContentText("Ksiazka nie zostały dodane do bazy danych(nieznany błąd). Spróbuj jeszcze raz.");
                            alert2.showAndWait();
                        }
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Niepowodzenie");
                        alert2.setHeaderText("Dodawanie wypożyczenia");
                        alert2.setContentText("Ksiazka nie zostały dodane do bazy danych(nieznany błąd).Spróbuj jeszcze raz.");
                        alert2.showAndWait();
                    }
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Niepowodzenie");
                    alert3.setHeaderText("Dodawanie wypożyczenia");
                    alert3.setContentText("Ta książka jest już wypożyczona");
                    alert3.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void zaladujInformacje(KeyEvent event) {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = "";
        String data2 = "";
        Calendar c = Calendar.getInstance();
        sprWczytaniaKsiazki = false;

        String id_ksiazki = idKsiazki2.getText();

        String qu = "SELECT * FROM WYPOZYCZENIA WHERE ksiazka_id = '" + id_ksiazki + "'";
        ResultSet rs = DatabaseControll.execQuery(qu);
        try {
            while (rs.next()) {
                animacje_informacji();
                String pid_ksiazki = rs.getString("ksiazka_id");
                String pid_uzytkownika = rs.getString("uzytkownik_id");
                Timestamp pczas = rs.getTimestamp("czas");
                Timestamp pczas_odd = rs.getTimestamp("czas_oddania");
                Integer podnowienia = rs.getInt("odnowienia");
                Integer pid_cena = rs.getInt("cena");

                data = dfDate.format(pczas);
                data2 = dfDate.format(pczas_odd);

                infoDataWyp.setText(data);
                infoDataOdd.setText(data2);
                infoOdnowienia.setText("Ilość Odnowień: " + String.valueOf(podnowienia));
                infoDodatkowaOplata.setText(String.valueOf(pid_cena)+ " zł");


                qu = "SELECT * FROM KSIAZKA WHERE id = '" + pid_ksiazki + "'";
                ResultSet rs2 = DatabaseControll.execQuery(qu);
                while (rs2.next()) {
                    infoTytul.setText(rs2.getString("tytul"));
                    infoAutor.setText(rs2.getString("autor"));
                    infoWydawca.setText(rs2.getString("wydawca"));
                    infoIdKsiazki.setText("Id Książki: " +rs2.getString("id"));
                }

                qu = "SELECT * FROM UZYTKOWNIK WHERE id = '" + pid_uzytkownika + "'";
                ResultSet rs3 = DatabaseControll.execQuery(qu);
                while (rs3.next()) {
                    infoImie.setText(rs3.getString("imie"));
                    infoNazwisko.setText(rs3.getString("nazwisko"));
                    infoEmail.setText(rs3.getString("email"));
                    infoIdUzytkownika.setText("Id Użytkownika: " +rs3.getString("id"));
                }
                sprWczytaniaKsiazki = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(id_ksiazki.isEmpty()){
            sprWczytaniaKsiazki = false;
            infoImie.setText("Imie");
            infoNazwisko.setText("Nazwisko");
            infoEmail.setText("Email");
            infoDataOdd.setText("Data Oddania");
            infoDataWyp.setText("Data Wypożyczenia");
            infoOdnowienia.setText("Ilość Odnowień");
            infoTytul.setText("Tytuł Książki");
            infoAutor.setText("Autor Książki");
            infoWydawca.setText("Wydawca Książki");
            infoIdUzytkownika.setText("Id Uzytkownika");
            infoIdKsiazki.setText("Id Książki");
            animacje_informacji();
        }
        if(!sprWczytaniaKsiazki){
            infoImie.setText("Imie");
            infoNazwisko.setText("Nazwisko");
            infoEmail.setText("Email");
            infoDataOdd.setText("Data Oddania");
            infoDataWyp.setText("Data Wypożyczenia");
            infoOdnowienia.setText("Ilość Odnowień");
            infoTytul.setText("Tytuł");
            infoAutor.setText("Autor");
            infoWydawca.setText("Wydawca");
            infoIdUzytkownika.setText("Id Uzytkownika");
            infoIdKsiazki.setText("Id Książki");
            animacje_informacji();
        }
    }

    @FXML
    void przycyskOddaj(ActionEvent event) {
        String id_ksiazki = idKsiazki2.getText();
        if (!sprWczytaniaKsiazki) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepowodzenie");
            alert.setHeaderText("Oddanie Książki");
            alert.setContentText("Najpierw załaduj dane o książce");
            alert.showAndWait();
            return;
        }
        String qu = "DELETE FROM WYPOZYCZENIA WHERE ksiazka_id = '" + id_ksiazki + "'";
        String qu2 = "UPDATE KSIAZKA SET dostepnosc = TRUE WHERE id = '" + id_ksiazki + "'";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź poprawność danych");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz oddać książkę \n" + nazwaKsiazki.getText() + " ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            if (DatabaseControll.execAction(qu) && DatabaseControll.execAction(qu2)) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Potwierdzenie");
                alert2.setHeaderText("Oddanie Książki");
                alert2.setContentText("Książka została poprawnie dodana");
                alert2.showAndWait();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Niepowodzenie");
                alert2.setHeaderText("Oddanie Książki");
                alert2.setContentText("Książka nie została pomyślnie oddana");
                alert2.showAndWait();
            }
        }
    }

    @FXML
    void przyciskOdnow(ActionEvent event) {

        if (!sprWczytaniaKsiazki) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepowodzenie");
            alert.setHeaderText("Przedłużenie Terminu");
            alert.setContentText("Najpierw załaduj dane o książce");
            alert.showAndWait();
            return;
        }

        String id_ksiazki = idKsiazki2.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź poprawność danych");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz przedłużyć termin oddania książki \n" + nazwaKsiazki.getText() + " ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String qu = "UPDATE WYPOZYCZENIA SET czas = CURRENT_TIMESTAMP, cena = cena+10, odnowienia = odnowienia+1 WHERE ksiazka_id = '" + id_ksiazki + "'";
            if (DatabaseControll.execAction(qu)) {
                String qu3 = "SELECT czas FROM WYPOZYCZENIA WHERE ksiazka_id = '" + id_ksiazki + "'";
                ResultSet rs = DatabaseControll.execQuery(qu3);
                try {
                    while (rs.next()) {
                        Timestamp pczas = rs.getTimestamp("czas");
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(pczas.getTime());

                        cal.add(Calendar.DAY_OF_MONTH, 7);
                        pczas = new Timestamp(cal.getTime().getTime());

                        String qu4 = "UPDATE WYPOZYCZENIA SET czas_oddania = '" + pczas + "' WHERE ksiazka_id = '" + id_ksiazki + "'";
                        DatabaseControll.execAction(qu4);
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Potwierdzenie");
                        alert2.setHeaderText("Przedłużenie Terminu");
                        alert2.setContentText("Termin oddania książki został zwiększony o kolejny tydzień");
                        alert2.showAndWait();
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Niepowodzenie");
                alert2.setHeaderText("Przedłużenie Terminu");
                alert2.setContentText("Termin oddania nie został przedłużony");
                alert2.showAndWait();
            }
        }
    }

}
