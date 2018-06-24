package tabelaUzytkownikow;

import bazadanych.DatabaseControll;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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

    @FXML
    private TableColumn<uzytkownicy, Integer> karaTab;

    @FXML
    private AnchorPane edytowanie;

    @FXML
    private JFXTextField imie;

    @FXML
    private JFXTextField nazwisko;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField rola;

    @FXML
    private JFXTextField kara;

    /**
     * Inicjalizacja metod wraz z uruchomieniem okna listy użytkowników
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        czytanieUzytkownikow();
        initUzyt();
        edytowanie.setVisible(false);

    }

    /**
     * Metoda ta pobiera wszystkie dane dotyczące użytkownika z bazy danych po przez polecenie zapisane jako String 'qu'
     * Następnie przypisuje dane do odpowiednich pól w tabeli.
     */
    private void czytanieUzytkownikow() {
        list.clear();
        DatabaseControll odczyt = DatabaseControll.getInstance();
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
                Integer kara_pob = rs.getInt("kara");

                list.add(new uzytkownicy(id_pob, imie_pob, nazwisko_pob, login_pob, haslo_pob, email_pob, rola_pob, kara_pob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaUzytkownikow.setItems(list);
    }

    /**
     * Inicjalizacja wszystkich kolumn odpowiednimi wartościami z bazy.
     */
    private void initUzyt() {
        idTab.setCellValueFactory(new PropertyValueFactory<>("id"));
        imieTab.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoTab.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        loginTab.setCellValueFactory(new PropertyValueFactory<>("login"));
        hasloTab.setCellValueFactory(new PropertyValueFactory<>("haslo"));
        emailTab.setCellValueFactory(new PropertyValueFactory<>("email"));
        rolaTab.setCellValueFactory(new PropertyValueFactory<>("rola"));
        karaTab.setCellValueFactory(new PropertyValueFactory<>("kara"));
    }

    public static class uzytkownicy {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty imie;
        private final SimpleStringProperty nazwisko;
        private final SimpleStringProperty login;
        private final SimpleStringProperty haslo;
        private final SimpleStringProperty email;
        private final SimpleIntegerProperty rola;
        private final SimpleIntegerProperty kara;

        uzytkownicy(Integer id, String imie, String nazwisko, String login, String haslo, String email, Integer rola, Integer kara) {
            this.id = new SimpleIntegerProperty(id);
            this.imie = new SimpleStringProperty(imie);
            this.nazwisko = new SimpleStringProperty(nazwisko);
            this.login = new SimpleStringProperty(login);
            this.haslo = new SimpleStringProperty(haslo);
            this.email = new SimpleStringProperty(email);
            this.rola = new SimpleIntegerProperty(rola);
            this.kara = new SimpleIntegerProperty(kara);

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

        public String getLogin() { return login.get(); }

        public String getHaslo() {
            return haslo.get();
        }

        public String getEmail() {
            return email.get();
        }

        public Integer getRola() { return rola.get(); }

        public Integer getKara() {
            return kara.get();
        }
    }

    /**
     * Metoda przypisana do przycisku Aktualizuj
     * Metoda ta pobiera wszystkie dane z pól TextField, następnie pobrane dane są ładowane do zapytania SQL
     * I wykonywane. Jeśli akcja aktualizacji się powiedzie zostanie wyświetlony odpowiedni komunikat.
     * Sytuacja będzie podobna podczas niepowodzenia operacji. Wyskoczy użytkownikowi Error.
     * @param event Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    @FXML
    public void aktualizuj(ActionEvent event) {
        String imiep = imie.getText();
        String nazwiskop = nazwisko.getText();
        String emailp = email.getText();
        String rolap = rola.getText();
        String karap = kara.getText();
        uzytkownicy pobieranieDanych = tabelaUzytkownikow.getSelectionModel().getSelectedItem();


        if (imiep.isEmpty() || nazwiskop.isEmpty() || emailp.isEmpty() || rolap.isEmpty() ||karap.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Wprowadzania");
            alert.setHeaderText("Edycja Użytkownika");
            alert.setContentText("Proszę wypełnić wszystkie pola");
            alert.showAndWait();
            return;
        }

        String qu = "UPDATE UZYTKOWNIK SET " +
                "imie = '" + imiep + "', " +
                "nazwisko ='" + nazwiskop + "'," +
                "email = '" + emailp + "', " +
                "rola = '" + rolap + "', " +
                "kara = " + karap + " " +
                "WHERE id = '" + pobieranieDanych.getId() + "'";

        if(DatabaseControll.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Edycja Użytkownika");
            alert.setContentText("Wszystkie dane dotyczące użytkownika zostały poprawnie dodane do bazy danych");
            alert.showAndWait();
            czytanieUzytkownikow();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepowodzenie");
            alert.setHeaderText("Edycja Użytkownika");
            alert.setContentText("Użytkownik nie został dodany do bazy danych. Spróbuj jeszcze raz.");
            alert.showAndWait();
        }


    }

    /**
     * Metoda przypisana do przycisku Anuluj.
     * Po naciśnięciu przycisku okno z edycją jest ukrywane i zostaje wyświetlona tabela użytkowników.
     * @param event Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    @FXML
    public void anuluj(ActionEvent event) {
        edytowanie.setVisible(false);
        edytowanie.setDisable(true);
        tabelaUzytkownikow.setVisible(true);
        tabelaUzytkownikow.setDisable(false);
    }

    /**
     * Metoda przypisana do przycisku Edytuj.
     * Po naciśnięciu przycisku okno z tabelą użytkowników zostaje ukryte i pokazuje się okno edycji.
     * Zostają pobierane informacje o zaznaczonym użytkowniku do edycji.
     * Po wprowadzeniu zmian wyskoczy komunikat z potwierdzeniem wprowawdzenia zmian.
     * W innym przypadku wyskoczy Error.
     * @param event Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    @FXML
    void edytowanie(ActionEvent event) {
        uzytkownicy wybieranieDoUsuwania = tabelaUzytkownikow.getSelectionModel().getSelectedItem();
        if (wybieranieDoUsuwania == null) {
            return;
        } else {
            edytowanie.setDisable(false);
            edytowanie.setVisible(true);
            tabelaUzytkownikow.setDisable(true);
            tabelaUzytkownikow.setVisible(false);
            uzytkownicy pobieranieDoEdycji = tabelaUzytkownikow.getSelectionModel().getSelectedItem();
            imie.setText(pobieranieDoEdycji.getImie());
            nazwisko.setText(pobieranieDoEdycji.getNazwisko());
            email.setText(pobieranieDoEdycji.getEmail());
            rola.setText(String.valueOf(pobieranieDoEdycji.getRola()));
            kara.setText(String.valueOf(pobieranieDoEdycji.getKara()));
        }

    }

    /**
     * Metoda przypisana do przycisku Usuń.
     * Po naciśnięciu w ten przycisk wyskakuje komunikat, czy na pewno chcesz usunąć użytkownika.
     * Jeśli naciśniemy 'tak' wszystkie dane o zaznaczonym użytkowniku zostaną usunięte.
     * @param event Jest to parametr, określający, że jest tu wykorzystywana akcja przycisku
     */
    @FXML
    void usuwanie(ActionEvent event) {
        uzytkownicy wybieranieDoUsuwania = tabelaUzytkownikow.getSelectionModel().getSelectedItem();
        if (wybieranieDoUsuwania == null) {
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź usunięcie użytkownika");
            alert.setHeaderText(null);
            alert.setContentText("Czy napewno chcesz usunąć zaznaczonego użytkownika "+wybieranieDoUsuwania.getLogin()+ " ?");

            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                String qu ="DELETE FROM UZYTKOWNIK WHERE id = '"+wybieranieDoUsuwania.getId()+"'";
                if(DatabaseControll.execAction(qu)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Potwierdzenie usinięcie użytkownika");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Użytkownik "+wybieranieDoUsuwania.getLogin()+" został usunięty");
                    alert2.showAndWait();
                    list.remove(wybieranieDoUsuwania);
                }
                else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Błąd Usuwania");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Użytkownik "+wybieranieDoUsuwania.getLogin()+ " nie została usunięta");
                    alert2.showAndWait();
                }
            }
            else{
                return;
            }
        }
    }
}
