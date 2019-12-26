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
    private static ArrayList<CoathoursTable> coathoursTableArrayList = new ArrayList<CoathoursTable>();
    private static ArrayList<PositionCheckOutsTable> positionCheckOutsTableArrayList = new ArrayList<PositionCheckOutsTable>();
    private static ArrayList<PositionOrdersTable> positionOrdersTableArrayList = new ArrayList<PositionOrdersTable>();


    public static Connection getConnection() {
        return connection;
    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                "inf136729",
                "inf136729"
        );
        loadEverything();
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
        loadSectios();
        loadShiftSchedule();
        loadShifts();
        loadCoauthors();
        loadPositionCheckOuts();
        loadPositionOrders();
    }

    public static void loadAffiliates() throws SQLException {
        if (affiliatesTableArrayList.size() > 0) {
            affiliatesTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM FILIE");
        while (resultSet.next()) {
            affiliatesTableArrayList.add(
                    new AffiliatesTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)
                    ));
        }
    }

    public static void loadAuthors() throws SQLException {
        if (authorsTableArrayList.size() > 0) {
            authorsTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM AUTORZY");
        while (resultSet.next()) {
            authorsTableArrayList.add(
                new AuthorsTable(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getDate(5),
                        resultSet.getString(6),
                        resultSet.getInt(7)
                ));
        }
    }
    public static void loadBooks() throws SQLException {
        if (booksTableArrayList.size() > 0) {
            booksTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM KSIAZKI");
        while (resultSet.next()) {
            booksTableArrayList.add(
                new BooksTable(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
        }
    }
    public static void loadCheckOuts() throws SQLException {
        if (checkOutsTableArrayList.size() > 0) {
            checkOutsTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM WYPOZYCZENIA");
        while (resultSet.next()) {
            checkOutsTableArrayList.add(
                    new CheckOutsTable(
                            resultSet.getDate(1),
                            resultSet.getInt(2),
                            resultSet.getDate(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5)
                    ));
        }
    }
    public static void loadCopies() throws SQLException {
        if (copiesTableArrayList.size() > 0) {
            copiesTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EGZEMPLARZE");
        while (resultSet.next()) {
            copiesTableArrayList.add(
                    new CopiesTable(
                            resultSet.getInt(1),
                            resultSet.getBoolean(2),
                            resultSet.getInt(3),
                            resultSet.getDate(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7)
                    ));
        }
    }
    public static void loadEmployees() throws SQLException {
        if (employeesTableArrayList.size() > 0) {
            employeesTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM PRACOWNICY");
        while (resultSet.next()) {
            employeesTableArrayList.add(
                    new EmployeesTable(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4),
                            resultSet.getDate(5),
                            resultSet.getDouble(6),
                            resultSet.getInt(7),
                            resultSet.getString(8)
                    ));
        }
    }
    public static void loadOrders() throws SQLException {
        if (ordersTableArrayList.size() > 0) {
            ordersTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ZAMOWIENIA");
        while (resultSet.next()) {
            ordersTableArrayList.add(
                    new OrdersTable(
                            resultSet.getDate(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3)
                    ));
        }
    }
    public static void loadPositions() throws SQLException {
        if (positionsTableArrayList.size() > 0) {
            positionsTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM STANOWISKA");
        while (resultSet.next()) {
            positionsTableArrayList.add(
                    new PositionsTable(
                            resultSet.getString(1),
                            resultSet.getDouble(2)
                    ));
        }
    }
    public static void loadReaders() throws SQLException {
        if (readersTableArrayList.size() > 0) {
            readersTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM CZYTELNICY");
        while (resultSet.next()) {
            readersTableArrayList.add(
                    new ReadersTable(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4),
                            resultSet.getInt(5)
                    ));
        }
    }
    public static void loadSectios() throws SQLException {
        if (sectionsTableArrayList.size() > 0) {
            sectionsTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM DZIALY");
        while (resultSet.next()) {
            sectionsTableArrayList.add(
                    new SectionsTable(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)
                    ));
        }
    }
    public static void loadShiftSchedule() throws SQLException {
        if (shiftScheduleTableArrayList.size() > 0) {
            shiftScheduleTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM GRAFIK_DYZUROW");
        while (resultSet.next()) {
            shiftScheduleTableArrayList.add(
                    new ShiftScheduleTable(
                            resultSet.getBoolean(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDate(4),
                            resultSet.getDate(5),
                            resultSet.getInt(6)
                    ));
        }
    }
    public static void loadShifts() throws SQLException {
        if (shiftsTableArrayList.size() > 0) {
            shiftsTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM JEDNOSTKA_PRACY");
        while (resultSet.next()) {
            shiftsTableArrayList.add(
                    new ShiftsTable(
                            resultSet.getInt(1),
                            resultSet.getDouble(2),
                            resultSet.getDouble(3),
                            resultSet.getInt(4)
                    ));
        }
    }
    public static void loadPositionCheckOuts() throws SQLException {
        if (positionCheckOutsTableArrayList.size() > 0) {
            positionCheckOutsTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM POZYCJA_WYPOZYCZENIA");
        while (resultSet.next()) {
            positionCheckOutsTableArrayList.add(
                    new PositionCheckOutsTable(
                            resultSet.getInt(1),
                            resultSet.getInt(2)
                    ));
        }
    }
    public static void loadPositionOrders() throws SQLException {
        if (positionOrdersTableArrayList.size() > 0) {
            positionOrdersTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM POZYCJA_ZAMOWIENIA");
        while (resultSet.next()) {
            positionOrdersTableArrayList.add(
                    new PositionOrdersTable(
                            resultSet.getInt(1),
                            resultSet.getInt(2)
                    ));
        }
    }
    public static void loadCoauthors() throws SQLException {
        if (coathoursTableArrayList.size() > 0) {
            coathoursTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM WSPOLAUTORZY");
        while (resultSet.next()) {
            coathoursTableArrayList.add(
                    new CoathoursTable(
                            resultSet.getInt(1),
                            resultSet.getInt(2)
                    ));
        }
    }
}
