package sql;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM AUTORZY");
            while (resultSet.next()) {
                for (int i = 1; i <=resultSet.getMetaData().getColumnCount(); i++){
                    System.out.print(resultSet.getString(i) + '\t');
                }
                System.out.print('\n');
            }

    }
}
