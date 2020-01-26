package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private enum Types{
        string,
        value,
        date
    }

    private static String insertStatement(String table, ArrayList<String> fields, ArrayList<String> values, ArrayList<Types> types){
        String query =  "insert into " + table + " (";
        for(int i=0; i<fields.size(); i++){
            query += fields.get(i) + ",";
        }
        query = query.substring(0,query.length()-1);
        query += ") values (";

        for(int i=0; i<values.size(); i++){
            query = decorateString(values, (ArrayList<Types>) types, query, i, ",");
        }

        query = query.substring(0,query.length()-1);
        query += ")";
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            return "Fatal error occurs...";
        }
        return null;
    }

    private static String updateStatement(String table, ArrayList<String> fields, ArrayList<String> values, ArrayList<Types> types, ArrayList<String> keyFields, ArrayList<String> keyValues, ArrayList<Types> keyTypes){
        String query =  "update " + table + " set ";
        for(int i=0; i<fields.size(); i++){
            query += fields.get(i) + " = ";
            query = decorateString(values, types, query, i, ",");
        }
        query = query.substring(0,query.length()-1);
        query += " where ";
        for(int i=0; i<keyFields.size(); i++){
            query += keyFields.get(i) + " = ";
            query = decorateString(keyValues, keyTypes, query, i, " and ");
        }
        query = query.substring(0,query.length()-5);

        try{
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            return "Fatal error occurs...";
        }
        return null;
    }

    private static String updateStatement(String table, ArrayList<String> fields, ArrayList<String> values, ArrayList<Types> types, String keyField, String keyValue){
        String query =  "update " + table + " set ";
        for(int i=0; i<fields.size(); i++){
            query += fields.get(i) + " = ";
            query = decorateString(values, types, query, i, ",");
        }
        query = query.substring(0,query.length()-1);

        query += " where " + keyField + " = " + keyValue;

        try{
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            return "Fatal error occurs...";
        }
        return null;
    }

    private static String deleteStatement(String table, String id_field, String id_value){
        String query =  "delete from " + table + " where " + id_field + " = " + id_value;
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            return "Fatal error occurs...";
        }
        return null;
    }

    private static String deleteStatement(String table, ArrayList<String> fields, ArrayList<String> values, ArrayList<Types> types){
        String query =  "delete from " + table + " where ";
        for(int i=0; i<fields.size(); i++){
            query += fields.get(i) + " = ";
            query = decorateString(values, types, query, i, " and ");
        }
        query = query.substring(0,query.length()-5);

        try{
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            return "Fatal error occurs...";
        }
        return null;
    }

    private static String decorateString(ArrayList<String> values, ArrayList<Types> types, String query, int i, String delimiter) {
        if(types.get(i) == Types.string){
            query += "'" + values.get(i) + "'";
        }else if(types.get(i) == Types.date){
            query += "TO_DATE('"+values.get(i)+"','YYYY-MM-DD')";
        }else if(types.get(i) == Types.value){
            query += values.get(i);
        }
        query += delimiter;
        return query;
    }

    public static String addAffiliates(String[] values){
        String address = values[0], open_hour = values[1], close_hour = values[2];

        int from, to;
        if(address.equals("")){
            return "Address can't be empty";
        }
        try
        {
            from = Integer.parseInt(open_hour.trim());
            if(from>24){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException nfe)
        {
            return "Incorrect \"Opened from\" value";
        }
        try
        {
            to = Integer.parseInt(close_hour.trim());
            if(to>24){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException nfe)
        {
            return "Incorrect \"Opened from\" value";
        }
        if(from>=to){
            return "Work time must be positive number";
        }
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("adres","godziny_pracy_od","godziny_pracy_do"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.value,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(values));
        return insertStatement("FILIE", fitFields, fitValues, fitTypes);
    }

    public static String modifyAffiliates(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String address = values[0], open_hour = values[1], close_hour = values[2];

        int from, to;
        if(address.equals("")){
            return "Address can't be empty";
        }
        try
        {
            from = Integer.parseInt(open_hour.trim());
            if(from>24){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException nfe)
        {
            return "Incorrect \"Opened from\" value";
        }
        try
        {
            to = Integer.parseInt(close_hour.trim());
            if(to>24){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException nfe)
        {
            return "Incorrect \"Opened from\" value";
        }
        if(from>=to){
            return "Work time must be positive number";
        }
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("adres","godziny_pracy_od","godziny_pracy_do"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.value,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(values));
        return updateStatement("filie", fitFields, fitValues, fitTypes, "numer", id);
    }

    public static String deleteAffiliates(String[] values) {
        String id = values[0];
        return deleteStatement("Filie","numer",id);
    }

    public static String addAuthor(String[] values){
        String FirstName = values[0], LastName = values[1], Pseudonym = values[2], Nationality = values[3], Date_of_birth = values[4], Date_of_death = values[5];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("IMIE","NAZWISKO"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(FirstName,LastName));
        if(!(Pseudonym.equals("null") || Pseudonym.equals(""))){
            fitFields.add("pseudonim");
            fitValues.add(Pseudonym);
            fitTypes.add(Types.string);
        }
        if(!(Date_of_birth.equals("null") || Date_of_birth.equals(""))){
            fitFields.add("data_urodzin");
            fitValues.add(Date_of_birth);
            fitTypes.add(Types.date);
        }
        if(!(Date_of_death.equals("null") || Date_of_death.equals(""))){
            fitFields.add("data_smierci");
            fitValues.add(Date_of_death);
            fitTypes.add(Types.date);
        }
        if(!(Nationality.equals("null") || Nationality.equals(""))){
            fitFields.add("narodowosc");
            fitValues.add(Nationality);
            fitTypes.add(Types.string);
        }
        return insertStatement("Autorzy",fitFields,fitValues,fitTypes);
    }

    public static String modifyAuthor(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String FirstName = values[0], LastName = values[1], Pseudonym = values[2], Nationality = values[3], Date_of_birth = values[4], Date_of_death = values[5];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("IMIE","NAZWISKO"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(FirstName,LastName));
        if(!(Pseudonym.equals("null") || Pseudonym.equals(""))){
            fitFields.add("pseudonim");
            fitValues.add(Pseudonym);
            fitTypes.add(Types.string);
        }
        if(!(Date_of_birth.equals("null") || Date_of_birth.equals(""))){
            fitFields.add("data_urodzin");
            fitValues.add(Date_of_birth);
            fitTypes.add(Types.date);
        }
        if(!(Date_of_death.equals("null") || Date_of_death.equals(""))){
            fitFields.add("data_smierci");
            fitValues.add(Date_of_death);
            fitTypes.add(Types.date);
        }
        if(!(Nationality.equals("null") || Nationality.equals(""))){
            fitFields.add("narodowosc");
            fitValues.add(Nationality);
            fitTypes.add(Types.string);
        }
        return updateStatement("Autorzy",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteAuthor(String[] values) {
        String id = values[0];
        return deleteStatement("Autorzy","id", id);
    }

    public static String addBook(String[] values){
        String FirstName = values[0], LastName = values[1], Pseudonym = values[2], Nationality = values[3], Date_of_birth = values[4], Date_of_death = values[5];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("IMIE","NAZWISKO"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(FirstName,LastName));
        if(!(Pseudonym.equals("null") || Pseudonym.equals(""))){
            fitFields.add("pseudonim");
            fitValues.add(Pseudonym);
            fitTypes.add(Types.string);
        }
        if(!(Date_of_birth.equals("null") || Date_of_birth.equals(""))){
            fitFields.add("data_urodzin");
            fitValues.add(Date_of_birth);
            fitTypes.add(Types.date);
        }
        if(!(Date_of_death.equals("null") || Date_of_death.equals(""))){
            fitFields.add("data_smierci");
            fitValues.add(Date_of_death);
            fitTypes.add(Types.date);
        }
        if(!(Nationality.equals("null") || Nationality.equals(""))){
            fitFields.add("narodowosc");
            fitValues.add(Nationality);
            fitTypes.add(Types.string);
        }
        return insertStatement("Autorzy",fitFields,fitValues,fitTypes);
    }

    public static String modifyBook(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String FirstName = values[0], LastName = values[1], Pseudonym = values[2], Nationality = values[3], Date_of_birth = values[4], Date_of_death = values[5];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("IMIE","NAZWISKO"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(FirstName,LastName));
        if(!(Pseudonym.equals("null") || Pseudonym.equals(""))){
            fitFields.add("pseudonim");
            fitValues.add(Pseudonym);
            fitTypes.add(Types.string);
        }
        if(!(Date_of_birth.equals("null") || Date_of_birth.equals(""))){
            fitFields.add("data_urodzin");
            fitValues.add(Date_of_birth);
            fitTypes.add(Types.date);
        }
        if(!(Date_of_death.equals("null") || Date_of_death.equals(""))){
            fitFields.add("data_smierci");
            fitValues.add(Date_of_death);
            fitTypes.add(Types.date);
        }
        if(!(Nationality.equals("null") || Nationality.equals(""))){
            fitFields.add("narodowosc");
            fitValues.add(Nationality);
            fitTypes.add(Types.string);
        }
        return updateStatement("Ksiazki",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteBook(String[] values) {
        String id = values[0];
        return deleteStatement("Ksiazki","id", id);
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
                        "left join grafik_dyzurow g " +
                        "on numer = g.numer_filii " +
                        "left join jednostka_pracy j " +
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
