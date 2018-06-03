package tabelaKsiazek;

import bazadanych.DatabaseControll;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleBooleanProperty;
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

public class TabelaKsiazek implements Initializable {

    ObservableList<ksiazki> list = FXCollections.observableArrayList();

    @FXML
    public TableView<ksiazki> tabelaKsiazka;
    @FXML
    public TableColumn<ksiazki, String> idTab;
    @FXML
    public TableColumn<ksiazki, String> tytulTab;
    @FXML
    public TableColumn<ksiazki, String> autorTab;
    @FXML
    public TableColumn<ksiazki, String> wydawcaTab;
    @FXML
    public TableColumn<ksiazki, Boolean> dostepnaTab;
    @FXML
    public TableColumn<ksiazki, Integer> cenaTab;

    @FXML
    private AnchorPane edytowanie;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initKsiaz();
        czytanieKsiazek();
        edytowanie.setVisible(false);
    }

    private void czytanieKsiazek() {
        list.clear();
        DatabaseControll odczyt = DatabaseControll.getInstance();
        String qu = "SELECT * FROM KSIAZKA";
        ResultSet rs = odczyt.execQuery(qu);
        try {
            while (rs.next()) {
                String id_pob = rs.getString("id");
                String tytul_pob = rs.getString("tytul");
                String autor_pob = rs.getString("autor");
                String wydawca_pob = rs.getString("wydawca");
                Boolean dostepnosc = rs.getBoolean("dostepnosc");
                Integer cena_pob = rs.getInt("cena");

                list.add(new ksiazki(id_pob, tytul_pob, autor_pob, wydawca_pob, dostepnosc, cena_pob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaKsiazka.setItems(list);
    }

    private void initKsiaz() {
        idTab.setCellValueFactory(new PropertyValueFactory<>("id"));
        tytulTab.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        autorTab.setCellValueFactory(new PropertyValueFactory<>("autor"));
        wydawcaTab.setCellValueFactory(new PropertyValueFactory<>("wydawca"));
        dostepnaTab.setCellValueFactory(new PropertyValueFactory<>("dostepnosc"));
        cenaTab.setCellValueFactory(new PropertyValueFactory<>("cena"));
    }

    public static class ksiazki {
        private final SimpleStringProperty id;
        private final SimpleStringProperty tytul;
        private final SimpleStringProperty autor;
        private final SimpleStringProperty wydawca;
        private final SimpleBooleanProperty dostepnosc;
        private final SimpleIntegerProperty cena;

        ksiazki(String id, String tytul, String autor, String wydawca, Boolean dostepnosc, Integer cena) {
            this.id = new SimpleStringProperty(id);
            this.tytul = new SimpleStringProperty(tytul);
            this.autor = new SimpleStringProperty(autor);
            this.wydawca = new SimpleStringProperty(wydawca);
            this.dostepnosc = new SimpleBooleanProperty(dostepnosc);
            this.cena = new SimpleIntegerProperty(cena);
        }

        public String getId() {
            return id.get();
        }

        public String getTytul() {
            return tytul.get();
        }

        public String getAutor() {
            return autor.get();
        }

        public String getWydawca() {
            return wydawca.get();
        }

        public boolean isDostepnosc() {
            return dostepnosc.get();
        }

        public Integer getCena() {
            return cena.get();
        }
    }


    @FXML
    public void anuluj(ActionEvent event) {
        tabelaKsiazka.setVisible(true);
        tabelaKsiazka.setDisable(false);
        edytowanie.setVisible(false);
    }


    @FXML
    public void aktualizuj(ActionEvent event) {
        String tytulp = tytul.getText();
        String autorp = autor.getText();
        String wydawcap = wydawca.getText();
        String cenap = cena.getText();
        ksiazki pobieranieDanych = tabelaKsiazka.getSelectionModel().getSelectedItem();


        if (tytulp.isEmpty() || autorp.isEmpty() || wydawcap.isEmpty() || cenap.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Wprowadzania");
            alert.setHeaderText("Edycja Książki");
            alert.setContentText("Proszę wypełnić wszystkie pola");
            alert.showAndWait();
            return;
        }

        String qu = "UPDATE KSIAZKA SET " +
                "tytul = '" + tytulp + "', " +
                "autor ='" + autorp + "'," +
                "wydawca = '" + wydawcap + "', " +
                "cena = " + cenap + " " +
                "WHERE id = '" + pobieranieDanych.getId() + "'";
        System.out.println(qu);

        if (pobieranieDanych.isDostepnosc()) {
            if (DatabaseControll.execAction(qu)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText("Edycja Książki");
                alert.setContentText("Wszystkie dane dotyczące książki zostały poprawnie dodane do bazy danych");
                alert.showAndWait();
                czytanieKsiazek();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setHeaderText("Edycja Książki");
                alert.setContentText("Ksiazka nie zostały dodane do bazy danych. Spróbuj jeszcze raz.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepowodzenie");
            alert.setHeaderText("Edycja Książki");
            alert.setContentText("Wypożyczonych książek nie możesz edytować");
            alert.showAndWait();
        }
    }


    @FXML
    public void edytowanie(ActionEvent event) {
        ksiazki wybieranieDoUsuwania = tabelaKsiazka.getSelectionModel().getSelectedItem();
        if (wybieranieDoUsuwania == null) {
            return;
        } else {
            if(wybieranieDoUsuwania.isDostepnosc()) {
                edytowanie.setDisable(false);
                edytowanie.setVisible(true);
                tabelaKsiazka.setDisable(true);
                tabelaKsiazka.setVisible(false);
                ksiazki pobieranieDanych = tabelaKsiazka.getSelectionModel().getSelectedItem();
                tytul.setText(pobieranieDanych.getTytul());
                autor.setText(pobieranieDanych.getAutor());
                wydawca.setText(pobieranieDanych.getWydawca());
                cena.setText(String.valueOf(pobieranieDanych.getCena()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setHeaderText("Edycja Książki");
                alert.setContentText("Wypożyczonych książek nie możesz edytować");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void usuwanie(ActionEvent event) {
        ksiazki wybieranieDoEdytowania = tabelaKsiazka.getSelectionModel().getSelectedItem();
        if (wybieranieDoEdytowania == null) {
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź usunięcie książki");
            alert.setHeaderText(null);
            alert.setContentText("Czy napewno chcesz usunąć zaznaczoną książkę " + wybieranieDoEdytowania.getTytul() + "?");

            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                String qu = "DELETE FROM KSIAZKA WHERE id = '" + wybieranieDoEdytowania.getId() + "'";
                if (wybieranieDoEdytowania.isDostepnosc()) {
                    if (DatabaseControll.execAction(qu)) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Potwierdzenie usinięcią książki");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Książka " + wybieranieDoEdytowania.getTytul() + " została usunięta");
                        alert2.showAndWait();
                        list.remove(wybieranieDoEdytowania);
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Błąd Usuwania");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Książka " + wybieranieDoEdytowania.getTytul() + "nie została usunięta");
                        alert2.showAndWait();
                        return;
                    }
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Błąd Usuwania");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Wypożyczonej książki nie możesz usunąć");
                    alert2.showAndWait();
                }
            } else {
                return;
            }
        }
    }

    // public void pobieranieUI(){
    //ksiazki pobieranieDanych = tabelaKsiazka.getSelectionModel().getSelectedItem();
    //tytul.setText(pobieranieDanych.getTytul());

    // }

}
