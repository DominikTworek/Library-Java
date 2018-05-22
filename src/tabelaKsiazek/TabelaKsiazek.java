package tabelaKsiazek;

import bazadanych.DatabaseControll;
import javafx.beans.property.SimpleBooleanProperty;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initKsiaz();
        czytanieKsiazek();
    }

    private void czytanieKsiazek() {
        DatabaseControll odczyt = new DatabaseControll();
        String qu = "SELECT * FROM KSIAZKA";
        ResultSet rs = odczyt.execQuery(qu);
        try {
            while (rs.next()) {
                String id_pob = rs.getString("id");
                String tytul_pob = rs.getString("tytul");
                String autor_pob = rs.getString("autor");
                String wydawca_pob = rs.getString("wydawca");
                Boolean dostepnosc = rs.getBoolean("dostepnosc");

                list.add(new ksiazki(id_pob, tytul_pob, autor_pob, wydawca_pob, dostepnosc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaKsiazka.getItems().setAll(list);
    }

    private void initKsiaz() {
        idTab.setCellValueFactory(new PropertyValueFactory<>("id"));
        tytulTab.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        autorTab.setCellValueFactory(new PropertyValueFactory<>("autor"));
        wydawcaTab.setCellValueFactory(new PropertyValueFactory<>("wydawca"));
        dostepnaTab.setCellValueFactory(new PropertyValueFactory<>("dostepnosc"));
    }

    public static class ksiazki {
        private final SimpleStringProperty id;
        private final SimpleStringProperty tytul;
        private final SimpleStringProperty autor;
        private final SimpleStringProperty wydawca;
        private final SimpleBooleanProperty dostepnosc;

        ksiazki(String id, String tytul, String autor, String wydawca, Boolean dostepnosc) {
            this.id = new SimpleStringProperty(id);
            this.tytul = new SimpleStringProperty(tytul);
            this.autor = new SimpleStringProperty(autor);
            this.wydawca = new SimpleStringProperty(wydawca);
            this.dostepnosc = new SimpleBooleanProperty(dostepnosc);

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
    }
}
