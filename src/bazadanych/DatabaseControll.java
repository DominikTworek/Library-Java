package bazadanych;

import javax.swing.*;
import java.sql.*;

public class DatabaseControll {
    private static DatabaseControll handler = null;

    private static final String DB_URL = "jdbc:derby:bazadanych;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    private DatabaseControll() {
        polaczenie();
        tworzenieTabeliKsiazka();
        tworzenieTabeliUzytkownik();
        tworzenieTabeliWyporzyczenia();
    }

    public static DatabaseControll getInstance(){
        if(handler == null) {
            handler = new DatabaseControll();
        }
        return handler;
    }

    /**
     * Tutaj następuję połączenie się z bazą i jest wywyływane zawsze z uruchomieniem programu
     */
    void polaczenie() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").getConstructor().newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Jeśli tabela KSIAZKA nie istnieje zostaje stworzona tabela KSIAZKA w bazie danych
     */
    void tworzenieTabeliKsiazka() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabele = dbm.getTables(null, null, "KSIAZKA", null);

            if (tabele.next()) {
                //boolean b = stmt.execute("DROP TABLE KSIAZKA");
                //boolean b = stmt.execute("ALTER TABLE KSIAZKA ADD cena Integer");
                System.out.println("Tabela KSIAZKA już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE KSIAZKA (id VARCHAR (200) PRIMARY KEY,tytul VARCHAR (200),autor VARCHAR (200),wydawca VARCHAR (200),dostepnosc boolean default true, cena INTEGER)");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Jeśli tabela UZYTKOWNIK nie istnieje zostaje stworzona tabela UZYTKOWNIK w bazie danych
     */
    void tworzenieTabeliUzytkownik() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabeleuzytkownik = dbm.getTables(null, null, "UZYTKOWNIK", null);

            if (tabeleuzytkownik.next()) {
                //boolean b = stmt.execute("DROP TABLE UZYTKOWNIK");
                System.out.println("Tabela uzytkownik już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE UZYTKOWNIK (id VARCHAR(200) PRIMARY KEY, imie VARCHAR(200),nazwisko VARCHAR(200),login VARCHAR (200),haslo VARCHAR(200),email VARCHAR(200), rola VARCHAR(200), kara INTEGER DEFAULT 0)");
                String qu = "INSERT INTO UZYTKOWNIK VALUES ('1', 'Admin', 'Admin', 'Admin', 'Admin', 'Admin', '2', 0)";
                DatabaseControll.execAction(qu);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Jeśli tabela WYPOZYCZENIA nie istnieje zostaje stworzona tabela WYPOZYCZENIA w bazie danych
     */
    void tworzenieTabeliWyporzyczenia() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabelewyporzyczenia = dbm.getTables(null, null, "WYPOZYCZENIA", null);

            if (tabelewyporzyczenia.next()) {
                //boolean b = stmt.execute("DROP TABLE WYPOZYCZENIA");
                //boolean b = stmt.execute("ALTER TABLE WYPOZYCZENIA ADD cena INTEGER");
                System.out.println("Tabela WYPOZYCZENIA już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE WYPOZYCZENIA (ksiazka_id VARCHAR(200), uzytkownik_id VARCHAR(200), czas TIMESTAMP DEFAULT CURRENT_TIMESTAMP, czas_oddania TIMESTAMP DEFAULT CURRENT_TIMESTAMP,cena INTEGER DEFAULT 0, odnowienia INTEGER DEFAULT 0, FOREIGN KEY (ksiazka_id) REFERENCES KSIAZKA(id), FOREIGN KEY (uzytkownik_id) REFERENCES UZYTKOWNIK(id))");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metoda ta służy do uruchamiania wszystkich poleceń
     *
     * @param query Polecenie, które ma zostać wykonane
     *
     * @return Zwraca wynik zapytania i je wykonuje.
     */
    public static ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    /**
     * Metoda ta służy do uruchamiania wszystkich poleceń
     *
     * @param qu Polecenie, które ma zostać wykonane
     *
     * @return prawdę jeśli wykona się zapytanie bądź fałsz jeśli się ono nie wykona
     */
    public static boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
}
