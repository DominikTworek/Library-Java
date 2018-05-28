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

    void polaczenie() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").getConstructor().newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tworzenieTabeliKsiazka() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabele = dbm.getTables(null, null, "KSIAZKA", null);

            if (tabele.next()) {
                System.out.println("Tabela KSIAZKA już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE KSIAZKA (id VARCHAR (200) PRIMARY KEY,tytul VARCHAR (200),autor VARCHAR (200),wydawca VARCHAR (200),dostepnosc boolean default true)");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    void tworzenieTabeliUzytkownik() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabeleuzytkownik = dbm.getTables(null, null, "UZYTKOWNIK", null);

            if (tabeleuzytkownik.next()) {
                //boolean b = stmt.execute("DROP TABLE UZYTKOWNIK");
                System.out.println("Tabela uzytkownik już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE UZYTKOWNIK (id VARCHAR(200) PRIMARY KEY, imie VARCHAR(200),nazwisko VARCHAR(200),login VARCHAR (200),haslo VARCHAR(200),email VARCHAR(200), rola VARCHAR(200))");
                String qu = "INSERT INTO UZYTKOWNIK VALUES ('1', 'Admin', 'Admin', 'Admin', 'Admin', 'Admin', '2')";
                DatabaseControll.execAction(qu);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    void tworzenieTabeliWyporzyczenia() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabelewyporzyczenia = dbm.getTables(null, null, "WYPOZYCZENIA", null);

            if (tabelewyporzyczenia.next()) {
                //boolean b = stmt.execute("DROP TABLE WYPOZYCZENIA");
                System.out.println("Tabela WYPOZYCZENIA już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE WYPOZYCZENIA (ksiazka_id VARCHAR(200), uzytkownik_id VARCHAR(200), czas TIMESTAMP DEFAULT CURRENT_TIMESTAMP, odnowienia INTEGER DEFAULT 0, FOREIGN KEY (ksiazka_id) REFERENCES KSIAZKA(id), FOREIGN KEY (uzytkownik_id) REFERENCES UZYTKOWNIK(id))");
                String qu = "INSERT INTO UZYTKOWNIK VALUES ('1', 'Admin', 'Admin', 'Admin', 'Admin', 'Admin', '2')";
                DatabaseControll.execAction(qu);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

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
