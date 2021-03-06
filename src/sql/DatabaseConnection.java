package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fxapp.AffiliatesController;
import fxapp.CopiesController;
import fxapp.PositionsController;
import fxapp.ReadersController;
import fxapp.containers.*;
import oracle.sql.DATE;
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

    public static void returnCopy(int copy_id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("call ZwrocEgzemplarz(?)");
        preparedStatement.setInt(1, copy_id);
        preparedStatement.execute();
    }

    public static int timeTillExp(Date date) throws SQLException {
        CallableStatement preparedStatement = connection.prepareCall("{? = call dokoncaumowydni(?)}");
        preparedStatement.registerOutParameter(1, java.sql.Types.INTEGER);
        preparedStatement.setDate(2,date);
        preparedStatement.execute();
        return preparedStatement.getInt(1);
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
            if(e.getErrorCode() == 2292){//naruszenie więzów spójności
                return "You cannot delete this object - other objects depend on it";
            }
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
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("adres","godziny_pracy_od","godziny_pracy_do"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.string));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(values));
        return insertStatement("FILIE", fitFields, fitValues, fitTypes);
    }

    public static String modifyAffiliates(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("adres","godziny_pracy_od","godziny_pracy_do"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.string));
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
        String Title = values[0], Author_ID = values[1];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("tytul","id_autora"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(Title,Author_ID));

        return insertStatement("Ksiazki",fitFields,fitValues,fitTypes);
    }

    public static String modifyBook(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String Title = values[0], Author_ID = values[1];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("tytul","id_autora"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(Title,Author_ID));

        return updateStatement("Ksiazki",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteBook(String[] values) {
        String id = values[0];
        return deleteStatement("Ksiazki","id", id);
    }

    public static String addCopy(String[] values){
        String BookID = values[0],  SectionID = values[1], CoversType = values[2], ReleaseYear = values[3], RealaseNumber = values[4];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("id_ksiazki","id_dzialu","dostepnosc"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.value,Types.value,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(BookID,SectionID,"1"));
        if(!(CoversType.equals("null") || CoversType.equals(""))){
            fitFields.add("rodzaj_okladki");
            fitValues.add(CoversType);
            fitTypes.add(Types.string);
        }
        if(!(ReleaseYear.equals("null") || ReleaseYear.equals(""))){
            fitFields.add("rok_wydania");
            fitValues.add(ReleaseYear);
            fitTypes.add(Types.date);
        }
        if(!(RealaseNumber.equals("null") || RealaseNumber.equals(""))){
            fitFields.add("wydanie");
            fitValues.add(RealaseNumber);
            fitTypes.add(Types.value);
        }

        return insertStatement("Egzemplarze",fitFields,fitValues,fitTypes);
    }

    public static String modifyCopy(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String BookID = values[0], SectionID = values[1], CoversType = values[2], ReleaseYear = values[3], RealaseNumber = values[4];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("id_ksiazki","id_dzialu"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.value,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(BookID,SectionID));
        if(!(CoversType.equals("null") || CoversType.equals(""))){
            fitFields.add("rodzaj_okladki");
            fitValues.add(CoversType);
            fitTypes.add(Types.string);
        }
        if(!(ReleaseYear.equals("null") || ReleaseYear.equals(""))){
            fitFields.add("rok_wydania");
            fitValues.add(ReleaseYear);
            fitTypes.add(Types.date);
        }
        if(!(RealaseNumber.equals("null") || RealaseNumber.equals(""))){
            fitFields.add("wydanie");
            fitValues.add(RealaseNumber);
            fitTypes.add(Types.value);
        }
        return updateStatement("Egzemplarze",fitFields,fitValues,fitTypes,"numer",id);
    }

    public static String deleteCopy(String[] values) {
        String id = values[0];
        return deleteStatement("Egzemplarze","numer", id);
    }

    public static String addCheckout(String[] values){
        String Copy_ID = values[0],  Reader_ID = values[1], Date_of_checkout = values[2], Rented_for = values[3];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("id_egzemplarza","id_czytelnika","data_wypozyczenia", "termin_zwrotu"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.value,Types.value,Types.date,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(Copy_ID,Reader_ID,Date_of_checkout,Rented_for));

        String query =  "insert into " + "Wypozyczenia" + " (";
        for(int i=0; i<fitFields.size(); i++){
            query += fitFields.get(i) + ",";
        }
        query = query.substring(0,query.length()-1);
        query += ") values (";

        for(int i=0; i<fitValues.size(); i++){
            query = decorateString(fitValues, (ArrayList<Types>) fitTypes, query, i, ",");
        }

        int result = 0;
        try {
            CallableStatement stmt = connection.prepareCall("{? = call checkifavailable(?)}");
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setInt(2,Integer.parseInt(Copy_ID.trim()));
            stmt.execute();
            result = stmt.getInt(1);
        } catch (SQLException ignore) {
        }
        if(result == 1){
            return insertStatement("Wypozyczenia",fitFields,fitValues,fitTypes);
        }else{
            return "This copy is unavailable";
        }
    }

    public static String modifyCheckout(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String Copy_ID = values[0],  Reader_ID = values[1], Date_of_checkout = values[2], Rented_for = values[3];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("id_egzemplarza","id_czytelnika","data_wypozyczenia", "termin_zwrotu"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.value,Types.value,Types.date,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(Copy_ID,Reader_ID,Date_of_checkout,Rented_for));

        return updateStatement("Wypozyczenia",fitFields,fitValues,fitTypes,"id_wypozyczenia",id);
    }

    public static String deleteCheckout(String[] values) {
        String id = values[0];
        return deleteStatement("Wypozyczenia","id_wypozyczenia", id);
    }

    public static String addEmployee(String[] values){
        String First = values[0],  Last = values[1], Employment_date = values[2], Affiliate = values[3], Position = values[4], Hourly_rate = values[5], Date_of_signing = values[6], Date_of_end = values[7];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("imie","nazwisko","data_zatrudnienia", "id_filii", "id_stanowiska", "stawka_godzinowa", "data_podpisania_ostatniej_umowy"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.date,Types.value,Types.value, Types.value, Types.date));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(First,Last,Employment_date,Affiliate,Position,Hourly_rate,Date_of_signing));
        if(!(Date_of_end.equals("null") || Date_of_end.equals(""))){
            fitFields.add("data_wygasniecia_umowy");
            fitValues.add(Date_of_end);
            fitTypes.add(Types.date);
        }
        return insertStatement("Pracownicy",fitFields,fitValues,fitTypes);
    }

    public static String modifyEmployee(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String First = values[0],  Last = values[1], Employment_date = values[2], Affiliate = values[3], Position = values[4], Hourly_rate = values[5], Date_of_signing = values[6], Date_of_end = values[7];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("imie","nazwisko","data_zatrudnienia", "id_filii", "id_stanowiska", "stawka_godzinowa", "data_podpisania_ostatniej_umowy"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.date,Types.value,Types.value, Types.value, Types.date));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(First,Last,Employment_date,Affiliate,Position,Hourly_rate,Date_of_signing));
        if(!(Date_of_end.equals("null") || Date_of_end.equals(""))){
            fitFields.add("data_wygasniecia_umowy");
            fitValues.add(Date_of_end);
            fitTypes.add(Types.date);
        }
        return updateStatement("Pracownicy",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteEmployee(String[] values) {
        String id = values[0];
        return deleteStatement("Pracownicy","id", id);
    }

    public static String addOrder(String[] values){
        String bookID = values[0],  readerID = values[1], orderDate = values[2];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("id_ksiazki","id_czytelnika","data_zamowienia"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.value,Types.value,Types.date));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(bookID,readerID,orderDate));

        return insertStatement("zamowienia",fitFields,fitValues,fitTypes);
    }

    public static String modifyOrder(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String bookID = values[0],  readerID = values[1], orderDate = values[2];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("id_ksiazki","id_czytelnika","data_zamowienia"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.value,Types.value,Types.date));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(bookID,readerID,orderDate));

        return updateStatement("Zamowienia",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteOrder(String[] values) {
        String id = values[0];
        return deleteStatement("Zamowienia","id", id);
    }

    public static String addPosition(String[] values){
        String name = values[0],  minimal_hourly_rate = values[1];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("nazwa","minimalna_stawka_godzinowa"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(name,minimal_hourly_rate));

        return insertStatement("Stanowiska",fitFields,fitValues,fitTypes);
    }

    public static String modifyPosition(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String name = values[0],  minimal_hourly_rate = values[1];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("nazwa","minimalna_stawka_godzinowa"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(name,minimal_hourly_rate));

        return updateStatement("Stanowiska",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deletePosition(String[] values) {
        String id = values[0];
        return deleteStatement("Stanowiska","id", id);
    }

    public static String addReader(String[] values){
        String first = values[0],  last = values[1], birthday = values[2], singInDate = values[3];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("imie","nazwisko","data_urodzenia","data_zapisania"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.date,Types.date));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(first,last,birthday,singInDate));

        return insertStatement("Czytelnicy",fitFields,fitValues,fitTypes);
    }

    public static String modifyReader(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String first = values[0],  last = values[1], birthday = values[2], singInDate = values[3];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("imie","nazwisko","data_urodzenia","data_zapisania"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.date,Types.date));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(first,last,birthday,singInDate));

        return updateStatement("Czytelnicy",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteReader(String[] values) {
        String id = values[0];
        return deleteStatement("Czytelnicy","id", id);
    }

    public static String addSection(String[] values){
        String name = values[0],  shortName = values[1], affiliate_id = values[2];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("nazwa","skrot","numer_filii"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(name,shortName,affiliate_id));

        return insertStatement("Dzialy",fitFields,fitValues,fitTypes);
    }

    public static String modifySection(String[] values){
        String id = values[0];
        values = Arrays.copyOfRange(values, 1, values.length);
        String name = values[0],  shortName = values[1], affiliate_id = values[2];
        ArrayList<String> fitFields = new ArrayList<>( Arrays.asList("nazwa","skrot","numer_filii"));
        ArrayList<Types> fitTypes = new ArrayList<>( Arrays.asList(Types.string,Types.string,Types.value));
        ArrayList<String> fitValues = new ArrayList<>( Arrays.asList(name,shortName,affiliate_id));

        return updateStatement("Dzialy",fitFields,fitValues,fitTypes,"id",id);
    }

    public static String deleteSection(String[] values) {
        String id = values[0];
        return deleteStatement("Dzialy","id", id);
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
    }

    public static void loadAffiliates() throws SQLException {
        if (affiliatesTableArrayList.size() > 0) {
            affiliatesTableArrayList.clear();
        }
        String query =  "select numer, adres, godziny_pracy_od, godziny_pracy_do, count(distinct p.id) " +
                        "from filie f " +
                        "left join pracownicy p " +
                        "on p.ID_FILII = f.numer " +
                        "group by(numer, adres, godziny_pracy_od, godziny_pracy_do)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            affiliatesTableArrayList.add(
                    new AffiliatesTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
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
        String query =  "select k.id, k.tytul, a.imie, a.nazwisko, count(e.numer), count(z.id), a.ID " +
                        "from ksiazki k " +
                        "inner join autorzy a " +
                        "on a.id = k.id_autora " +
                        "left join egzemplarze e " +
                        "on e.id_ksiazki = k.id " +
                        "left join zamowienia z " +
                        "on z.id_ksiazki = k.id " +
                        "group by(k.id, k.tytul, a.imie, a.nazwisko, a.ID, z.id)";
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
                            resultSet.getInt(6),
                            resultSet.getInt(7)
                    ));
        }
    }
    public static void loadCheckOuts() throws SQLException {
        if (checkOutsTableArrayList.size() > 0) {
            checkOutsTableArrayList.clear();
        }
        String query =  "select w.id_wypozyczenia, c.imie, c.nazwisko, k.tytul, e.numer, w.data_wypozyczenia, w.termin_zwrotu, w.data_zwrotu, c.ID " +
                        "from wypozyczenia w " +
                        "left join czytelnicy c " +
                        "on c.id = w.id_czytelnika " +
                        "inner join egzemplarze e " +
                        "on e.numer = w.id_egzemplarza " +
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
                            resultSet.getDate(8),
                            resultSet.getInt(9)
                    ));
        }
    }

    public static void loadCopies() throws SQLException {
        if (copiesTableArrayList.size() > 0) {
            copiesTableArrayList.clear();
        }
        String query =  "select e.numer, k.tytul, e.dostepnosc, d.nazwa, f.adres, e.wydanie, e.rok_wydania, e.rodzaj_okladki, k.id as book_id, d.id as section_id " +
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
                            resultSet.getString(8),
                            resultSet.getInt(9),
                            resultSet.getInt(10)
                    ));
        }
    }

    public static void loadEmployees() throws SQLException {
        if (employeesTableArrayList.size() > 0) {
            employeesTableArrayList.clear();
        }
        String query =  "select p.id, p.imie, p.nazwisko, s.NAZWA, f.adres, p.data_zatrudnienia, p.data_podpisania_ostatniej_umowy, p.data_wygasniecia_umowy, p.stawka_godzinowa, f.NUMER, p.id_stanowiska " +
                        "from pracownicy p " +
                        "inner join filie f " +
                        "on f.numer = p.ID_FILII " +
                        "inner join STANOWISKA s " +
                        "on p.ID_STANOWISKA = s.ID";
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
                            resultSet.getDouble(9),
                            resultSet.getInt(10),
                            resultSet.getInt(11)
                    ));
        }
    }

    public static void loadOrders() throws SQLException {
        if (ordersTableArrayList.size() > 0) {
            ordersTableArrayList.clear();
        }
        String query =  "select z.id, z.data_zamowienia, c.imie, c.nazwisko, k.tytul, k.id, c.id " +
                        "from zamowienia z " +
                        "inner join czytelnicy c " +
                        "on c.id = z.id_czytelnika " +
                        "inner join ksiazki k " +
                        "on k.id = z.id_ksiazki";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ordersTableArrayList.add(
                    new OrdersTable(
                            resultSet.getInt(1),
                            resultSet.getDate(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7)
                    ));
        }
    }

    public static void loadPositions() throws SQLException {
        if (positionsTableArrayList.size() > 0) {
            positionsTableArrayList.clear();
        }
        String query =  "select s.nazwa, s.minimalna_stawka_godzinowa, count(p.ID), s.id " +
                        "from stanowiska s " +
                        "left join pracownicy p " +
                        "on s.id = p.ID_STANOWISKA " +
                        "group by(s.id, s.nazwa, s.minimalna_stawka_godzinowa)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            positionsTableArrayList.add(
                    new PositionsTable(
                            resultSet.getString(1),
                            resultSet.getDouble(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)
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
        String query =  "select d.id, d.nazwa, d.skrot, count(e.numer), f.adres, f.numer " +
                        "from dzialy d " +
                        "inner join filie f " +
                        "on f.numer = d.numer_filii " +
                        "left join egzemplarze e " +
                        "on e.id_dzialu = d.id " +
                        "group by(d.id, d.nazwa, d.skrot, f.adres, f.numer)";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            sectionsTableArrayList.add(
                    new SectionsTable(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getInt(6)
                    ));
        }
    }
}
