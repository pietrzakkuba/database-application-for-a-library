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
        loadSections();
        loadShiftSchedule();
        loadShifts();
    }

    public static void loadAffiliates() throws SQLException {
        if (affiliatesTableArrayList.size() > 0) {
            affiliatesTableArrayList.clear();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("");
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
