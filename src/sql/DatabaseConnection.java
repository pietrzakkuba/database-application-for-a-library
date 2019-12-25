package sql;

import java.sql.*;
import java.util.ArrayList;

import sql.tables.*;

public class DatabaseConnection {
    private static Connection connection;
    private static ArrayList<AffiliatesTable> affiliatesTable = new ArrayList<AffiliatesTable>();
    private static ArrayList<AuthorsTable> authorsTable = new ArrayList<AuthorsTable>();
    private static ArrayList<BooksTable> booksTable = new ArrayList<BooksTable>();
    private static ArrayList<CheckOutsTable> checkOutsTable = new ArrayList<CheckOutsTable>();
    private static ArrayList<CopiesTable> copiesTable = new ArrayList<CopiesTable>();
    private static ArrayList<EmployeesTable> employeesTable = new ArrayList<EmployeesTable>();
    private static ArrayList<OrdersTable> ordersTable = new ArrayList<OrdersTable>();
    private static ArrayList<PositionsTable> positionsTable = new ArrayList<PositionsTable>();
    private static ArrayList<ReadersTable> readersTable = new ArrayList<ReadersTable>();
    private static ArrayList<SectionsTable> sectionsTable = new ArrayList<SectionsTable>();
    private static ArrayList<ShiftScheduleTable> shiftScheduleTable = new ArrayList<ShiftScheduleTable>();
    private static ArrayList<ShiftsTable> shiftsTable = new ArrayList<ShiftsTable>();

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
    }

    public static void loadAffiliates() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM FILIE");
        System.out.println("FILIE");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }

    public static void loadAuthors() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM AUTORZY");
        System.out.println("AUTORZY");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadBooks() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM KSIAZKI");
        System.out.println("KSIAZKI");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadCheckOuts() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM WYPOZYCZENIA");
        System.out.println("WYPOZYCZENIA");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadCopies() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EGZEMPLARZE");
        System.out.println("EGZEMPLARZE");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadEmployees() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM PRACOWNICY");
        System.out.println("PRACOWNICY");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadOrders() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ZAMOWIENIA");
        System.out.println("ZAMOWIENIA");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadPositions() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM STANOWISKA");
        System.out.println("STANOWISKA");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadReaders() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM CZYTELNICY");
        System.out.println("CZYTELNICY");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadSectios() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM DZIALY");
        System.out.println("DZIALY");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadShiftSchedule() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM GRAFIK_DYZUROW");
        System.out.println("GRAFIK_DYZUROW");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
    public static void loadShifts() throws SQLException {
        if (affiliatesTable.size() > 0) {
            affiliatesTable.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM JEDNOSTKA_PRACY");
        System.out.println("JEDNOSTKA_PRACY");
        while (resultSet.next()) {
            for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.print('\n');
        }
    }
}
