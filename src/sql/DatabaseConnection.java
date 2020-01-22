package sql;

import java.sql.*;
import java.util.ArrayList;

import sql.tables.*;

public class DatabaseConnection {
    private static Connection connection;
    private static ArrayList<AffiliatesTable> affiliatesTableArrayList = new ArrayList<AffiliatesTable>();
    private static ArrayList<AuthorsTable> authorsTableArrayList = new ArrayList<AuthorsTable>();
    private static ArrayList<BooksTable> booksTableArrayList = new ArrayList<BooksTable>();
    private static ArrayList<CheckOutsTable> checkOutsTableArrayList = new ArrayList<CheckOutsTable>();
    private static ArrayList<CopiesTable> copiesTableArrayList = new ArrayList<CopiesTable>();
    private static ArrayList<EmployeesTable> employeesTableArrayList = new ArrayList<EmployeesTable>();
    private static ArrayList<OrdersTable> ordersTableArrayList = new ArrayList<OrdersTable>();
    private static ArrayList<PositionsTable> positionsTableArrayList = new ArrayList<PositionsTable>();
    private static ArrayList<ReadersTable> readersTableArrayList = new ArrayList<ReadersTable>();
    private static ArrayList<SectionsTable> sectionsTableArrayList = new ArrayList<SectionsTable>();
    private static ArrayList<ShiftScheduleTable> shiftScheduleTableArrayList = new ArrayList<ShiftScheduleTable>();
    private static ArrayList<ShiftsTable> shiftsTableArrayList = new ArrayList<ShiftsTable>();

    public static Connection getConnection() {
        return connection;
    }

    public static ArrayList<AffiliatesTable> getAffiliatesTableArrayList() {
        return affiliatesTableArrayList;
    }

    public static ArrayList<AuthorsTable> getAuthorsTableArrayList() {
        return authorsTableArrayList;
    }

    public static ArrayList<BooksTable> getBooksTableArrayList() {
        return booksTableArrayList;
    }

    public static ArrayList<CheckOutsTable> getCheckOutsTableArrayList() {
        return checkOutsTableArrayList;
    }

    public static ArrayList<CopiesTable> getCopiesTableArrayList() {
        return copiesTableArrayList;
    }

    public static ArrayList<EmployeesTable> getEmployeesTableArrayList() {
        return employeesTableArrayList;
    }

    public static ArrayList<OrdersTable> getOrdersTableArrayList() {
        return ordersTableArrayList;
    }

    public static ArrayList<PositionsTable> getPositionsTableArrayList() {
        return positionsTableArrayList;
    }

    public static ArrayList<ReadersTable> getReadersTableArrayList() {
        return readersTableArrayList;
    }

    public static ArrayList<SectionsTable> getSectionsTableArrayList() {
        return sectionsTableArrayList;
    }

    public static ArrayList<ShiftScheduleTable> getShiftScheduleTableArrayList() {
        return shiftScheduleTableArrayList;
    }

    public static ArrayList<ShiftsTable> getShiftsTableArrayList() {
        return shiftsTableArrayList;
    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                "inf136729",
                "inf136729"
        );
        // loadEverything();
    }

    public static void addAffiliates(String[] values){
        String number = values[0], address = values[1], open_hour = values[2], close_hour = values[3];
        String query =  "insert into FILIE (adres, godziny_pracy_od, godziny_pracy_do)" +
                "values ( \'" + address + "\', " + open_hour + ", " + close_hour + ")";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadEverything() throws SQLException {
        loadAffiliates();
        loadAuthors();
        loadBooks();
        loadCheckOuts();
        loadCopies();
        loadEmployees();
        loadOrders();
        loadPositions();
        loadReaders();
        loadSections();
        loadShiftSchedule();
        loadShifts();
    }

    public static void loadAffiliates() throws SQLException {
        if (affiliatesTableArrayList.size() > 0) {
            affiliatesTableArrayList.clear();
        }
        String query =  "select numer, adres, godziny_pracy_od, godziny_pracy_do, count(distinct j.id_pracownika) " +
                        "from filie " +
                        "inner join grafik_dyzurow g " +
                        "on numer = g.numer_filii " +
                        "inner join jednostka_pracy j " +
                        "on g.id = j.id_grafiku " +
                        "left join pracownicy p " +
                        "on p.id = j.id_pracownika " +
                        "group by(numer, adres, godziny_pracy_od, godziny_pracy_do)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            affiliatesTableArrayList.add(
                    new AffiliatesTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5)
                    ));

        }
    }

    public static void loadAuthors() throws SQLException {
        if (authorsTableArrayList.size() > 0) {
            authorsTableArrayList.clear();
        }
        String query =  "select id, imie, nazwisko, pseudonim, data_urodzin, data_smierci, narodowosc " +
                        "from autorzy";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            authorsTableArrayList.add(
                    new AuthorsTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDate(5),
                            resultSet.getDate(6),
                            resultSet.getString(7)
                    ));
        }
    }

    public static void loadBooks() throws SQLException {
        if (booksTableArrayList.size() > 0) {
            booksTableArrayList.clear();
        }
        String query =  "select k.id, k.tytul, a.imie, a.nazwisko, count(e.numer), count(z.id_zamowienia) " +
                        "from ksiazki k " +
                        "inner join wspolautorzy w " +
                        "on w.id_ksiazki = k.id " +
                        "inner join autorzy a " +
                        "on a.id = w.id_autora " +
                        "left join egzemplarze e " +
                        "on e.id_ksiazki = k.id " +
                        "left join pozycja_zamowienia z " +
                        "on z.id_ksiazki = k.id " +
                        "group by(k.id, k.tytul, a.imie, a.nazwisko)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            booksTableArrayList.add(
                    new BooksTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6)
                    ));
        }
    }

    public static void loadCheckOuts() throws SQLException {
        if (checkOutsTableArrayList.size() > 0) {
            checkOutsTableArrayList.clear();
        }
        String query =  "select w.id_wypozyczenia, c.imie, c.nazwisko, k.tytul, e.numer, w.data_wypozyczenia, w.termin_zwrotu, w.data_zwrotu " +
                        "from wypozyczenia w " +
                        "inner join czytelnicy c " +
                        "on c.id = w.id_czytelnika " +
                        "inner join pozycja_wypozyczenia pw " +
                        "on pw.id_egzemplarza = w.id_wypozyczenia " +
                        "inner join egzemplarze e " +
                        "on e.numer = pw.id_egzemplarza " +
                        "inner join ksiazki k " +
                        "on e.id_ksiazki = k.id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            checkOutsTableArrayList.add(
                    new CheckOutsTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5),
                            resultSet.getDate(6),
                            resultSet.getInt(7),
                            resultSet.getDate(8)
                    ));
        }
    }

    public static void loadCopies() throws SQLException {
        if (copiesTableArrayList.size() > 0) {
            copiesTableArrayList.clear();
        }
        String query =  "select e.numer, k.tytul, e.dostepnosc, d.nazwa, f.adres, e.wydanie, e.rok_wydania, e.rodzaj_okladki " +
                        "from egzemplarze e " +
                        "inner join ksiazki k " +
                        "on e.id_ksiazki = k.id " +
                        "inner join dzialy d " +
                        "on e.id_dzialu = d.id " +
                        "inner join filie f " +
                        "on f.numer = d.numer_filii";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            copiesTableArrayList.add(
                    new CopiesTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getBoolean(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getDate(7),
                            resultSet.getString(8)
                    ));
        }
    }

    public static void loadEmployees() throws SQLException {
        if (employeesTableArrayList.size() > 0) {
            employeesTableArrayList.clear();
        }
        String query =  "select p.id, p.imie, p.nazwisko, p.stanowisko, f.adres, p.data_zatrudnienia, p.data_podpisania_ostatniej_umowy, p.data_wygasniecia_umowy, p.stawka_godzinowa " +
                        "from pracownicy p " +
                        "inner join jednostka_pracy jp " +
                        "on jp.id_pracownika = p.id " +
                        "inner join grafik_dyzurow gd " +
                        "on gd.id = jp.id_grafiku " +
                        "inner join filie f " +
                        "on f.numer = gd.numer_filii";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            employeesTableArrayList.add(
                    new EmployeesTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getDate(6),
                            resultSet.getDate(7),
                            resultSet.getDate(8),
                            resultSet.getDouble(9)
                    ));
        }
    }

    public static void loadOrders() throws SQLException {
        if (ordersTableArrayList.size() > 0) {
            ordersTableArrayList.clear();
        }
        String query =  "select z.id, z.data_zamowienia, c.imie, c.nazwisko, k.tytul " +
                        "from zamowienia z " +
                        "inner join czytelnicy c " +
                        "on c.id = z.id_czytelnika " +
                        "inner join pozycja_zamowienia pz " +
                        "on pz.id_zamowienia = z.id " +
                        "inner join ksiazki k " +
                        "on k.id = pz.id_ksiazki";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ordersTableArrayList.add(
                    new OrdersTable(
                            resultSet.getInt(1),
                            resultSet.getDate(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    ));
        }
    }

    public static void loadPositions() throws SQLException {
        if (positionsTableArrayList.size() > 0) {
            positionsTableArrayList.clear();
        }
        String query =  "select s.nazwa, s.minimalna_stawka_godzinowa, count(p.id) " +
                        "from stanowiska s " +
                        "left join pracownicy p " +
                        "on p.stanowisko = s.nazwa " +
                        "group by(s.nazwa, s.minimalna_stawka_godzinowa)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            positionsTableArrayList.add(
                    new PositionsTable(
                            resultSet.getString(1),
                            resultSet.getDouble(2),
                            resultSet.getInt(3)
                    ));
        }
    }

    public static void loadReaders() throws SQLException {
        if (readersTableArrayList.size() > 0) {
            readersTableArrayList.clear();
        }
        String query =  "select id, imie, nazwisko, data_urodzenia, data_zapisania, count(w.id_wypozyczenia) " +
                        "from czytelnicy " +
                        "left join wypozyczenia w " +
                        "on w.id_czytelnika = id " +
                        "group by(id, imie, nazwisko, data_urodzenia, data_zapisania)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            readersTableArrayList.add(
                    new ReadersTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDate(4),
                            resultSet.getDate(5),
                            resultSet.getInt(6)
                    ));
        }
    }

    public static void loadSections() throws SQLException {
        if (sectionsTableArrayList.size() > 0) {
            sectionsTableArrayList.clear();
        }
        String query =  "select d.id, d.nazwa, d.skrot, count(e.numer), f.adres " +
                        "from dzialy d " +
                        "inner join filie f " +
                        "on f.numer = d.numer_filii " +
                        "left join egzemplarze e " +
                        "on e.id_dzialu = d.id " +
                        "group by(d.id, d.nazwa, d.skrot, f.adres)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            sectionsTableArrayList.add(
                    new SectionsTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5)
                    ));
        }
    }

    public static void loadShiftSchedule() throws SQLException {
        if (shiftScheduleTableArrayList.size() > 0) {
            shiftScheduleTableArrayList.clear();
        }
        String query =  "select gd.id, gd.czy_obowiazuje, f.adres, gd.nazwa, gd.obowiazuje_od, gd.obowiazuje_do, count(jp.id_grafiku) " +
                        "from grafik_dyzurow gd " +
                        "inner join filie f " +
                        "on gd.numer_filii = f.numer " +
                        "left join jednostka_pracy jp " +
                        "on gd.id = jp.id_grafiku " +
                        "group by(gd.id, gd.czy_obowiazuje, f.adres, gd.nazwa, gd.obowiazuje_od, gd.obowiazuje_do)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            shiftScheduleTableArrayList.add(
                    new ShiftScheduleTable(
                            resultSet.getInt(1),
                            resultSet.getBoolean(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDate(5),
                            resultSet.getDate(6),
                            resultSet.getInt(7)
                    ));
        }
    }

    public static void loadShifts() throws SQLException {
        if (shiftsTableArrayList.size() > 0) {
            shiftsTableArrayList.clear();
        }
        String query =  "select gd.nazwa, od_godziny, do_godziny, p.imie, p.nazwisko, (do_godziny - od_godziny) " +
                        "from jednostka_pracy " +
                        "inner join grafik_dyzurow gd " +
                        "on jednostka_pracy.id_grafiku = gd.id " +
                        "inner join pracownicy p " +
                        "on jednostka_pracy.id_pracownika = p.id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            shiftsTableArrayList.add(
                    new ShiftsTable(
                            resultSet.getString(1),
                            resultSet.getDouble(2),
                            resultSet.getDouble(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getDouble(6)
                    ));
        }
    }
}
