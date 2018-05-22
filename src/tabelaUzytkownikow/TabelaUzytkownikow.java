package tabelaUzytkownikow;

import bazadanych.DatabaseControll;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TabelaUzytkownikow implements Initializable {

    ObservableList<TabelaUzytkownikow.uzytkownicy> list = FXCollections.observableArrayList();

    @FXML
    private TableView<uzytkownicy> tabelaUzytkownikow;

    @FXML
    private TableColumn<uzytkownicy, Integer> idTab;

    @FXML
    private TableColumn<uzytkownicy, String> imieTab;

    @FXML
    private TableColumn<uzytkownicy, String> nazwiskoTab;

    @FXML
    private TableColumn<uzytkownicy, String> loginTab;

    @FXML
    private TableColumn<uzytkownicy, String> hasloTab;

    @FXML
    private TableColumn<uzytkownicy, String> emailTab;

    @FXML
    private TableColumn<uzytkownicy, Integer> rolaTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        czytanieUzytkownikow();
        initKsiaz();
    }

    private void czytanieUzytkownikow() {
        DatabaseControll odczyt = new DatabaseControll();
        String qu = "SELECT * FROM UZYTKOWNIK";
        ResultSet rs = odczyt.execQuery(qu);
        try {
            while (rs.next()) {
                Integer id_pob = rs.getInt("id");
                String imie_pob = rs.getString("imie");
                String nazwisko_pob = rs.getString("nazwisko");
                String login_pob = rs.getString("login");
                String haslo_pob = rs.getString("haslo");
                String email_pob = rs.getString("email");
                Integer rola_pob = rs.getInt("rola");

                list.add(new uzytkownicy(id_pob, imie_pob, nazwisko_pob, login_pob, haslo_pob, email_pob, rola_pob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaUzytkownikow.getItems().setAll(list);
    }


    private void initKsiaz() {
        idTab.setCellValueFactory(new PropertyValueFactory<>("id"));
        imieTab.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoTab.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        loginTab.setCellValueFactory(new PropertyValueFactory<>("login"));
        hasloTab.setCellValueFactory(new PropertyValueFactory<>("haslo"));
        emailTab.setCellValueFactory(new PropertyValueFactory<>("email"));
        rolaTab.setCellValueFactory(new PropertyValueFactory<>("rola"));
    }

    public static class uzytkownicy {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty imie;
        private final SimpleStringProperty nazwisko;
        private final SimpleStringProperty login;
        private final SimpleStringProperty haslo;
        private final SimpleStringProperty email;
        private final SimpleIntegerProperty rola;

        uzytkownicy(Integer id, String imie, String nazwisko, String login, String haslo, String email, Integer rola) {
            this.id = new SimpleIntegerProperty(id);
            this.imie = new SimpleStringProperty(imie);
            this.nazwisko = new SimpleStringProperty(nazwisko);
            this.login = new SimpleStringProperty(login);
            this.haslo = new SimpleStringProperty(haslo);
            this.email = new SimpleStringProperty(email);
            this.rola = new SimpleIntegerProperty(rola);

        }

        public Integer getId() {
            return id.get();
        }

        public String getImie() {
            return imie.get();
        }

        public String getNazwisko() {
            return nazwisko.get();
        }

        public String getLogin() {
            return login.get();
        }

        public String getHaslo() {
            return haslo.get();
        }

        public String getEmail() {
            return email.get();
        }

        public Integer getRola() {
            return rola.get();
        }
    }
}
