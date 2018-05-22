package bazadanych;

import javax.swing.*;
import java.sql.*;

public class DatabaseControll {
    private static DatabaseControll handler;

    private static final String DB_URL = "jdbc:derby:bazadanych;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseControll() {
        polaczenie();
        tworzenieTabeliKsiazka();
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
            System.err.println(e.getMessage() + " --- setupDatabase");
        }
    }

    void tworzenieTabeliUzytkownik() {
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tabeleuzytkownik = dbm.getTables(null, null, "KSIAZKA", null);

            if (tabeleuzytkownik.next()) {
                System.out.println("Tabela KSIAZKA już istnieje. Gotowy do pracy.");
            } else {
                boolean b = stmt.execute("CREATE TABLE UZYTKOWNIK (id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),imie VARCHAR (200),nazwisko VARCHAR (200),email VARCHAR (200),telefon NUMBER(200), rola NUMBER(200) DEFAULT 1)");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
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

    public boolean execAction(String qu) {
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
