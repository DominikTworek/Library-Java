package listaKsiazek;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class TabelaKsiazek implements Initializable {
    @FXML
    public TableColumn idTab;
    @FXML
    public TableColumn tytulTab;
    @FXML
    public TableColumn autorTab;
    @FXML
    public TableColumn wydawcaTab;
    @FXML
    public TableColumn dostepnaTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    class ksiazki{
        private SimpleStringProperty id;
        private SimpleStringProperty tytul;
        private SimpleStringProperty autor;
        private SimpleStringProperty wydawca;
        private SimpleBooleanProperty dostepnosc;

        ksiazki(String id, String tytul, String autor, String wydawca, Boolean dostepnosc){
            this.id = new SimpleStringProperty(id);
            this.tytul= new SimpleStringProperty(tytul);
            this.autor = new SimpleStringProperty(autor);
            this.wydawca = new SimpleStringProperty(wydawca);
            this.dostepnosc = new SimpleBooleanProperty(dostepnosc);

        }
    }
}
