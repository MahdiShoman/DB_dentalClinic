package DB;

import java.sql.*;
import java.util.Properties;


public class ConnectDB {

    private static String dbUserName = "root";
    private static String dbPassword = "asdf13579";
    private static String dbUrl = "127.0.0.1";
    private static String dbPort = "3306";
    private static String dbName = "dintal";
    static String DbUrl = "";
    static Properties properties = new Properties();
    public static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        ConnectDB.connection = connection;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        properties.setProperty("user", dbUserName);
        properties.setProperty("password", dbPassword);
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        DbUrl = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + dbName + "?verifyServerCertificate=false";
        connection = DriverManager.getConnection(DbUrl, properties);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Doctors");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)
                    + " " + resultSet.getString(4) + " " + resultSet.getString(5) + " " + resultSet.getString(6));
        }
        connection.close();
        statement.close();
    }

    public void startDBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        properties.setProperty("user", dbUserName);
        properties.setProperty("password", dbPassword);
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        DbUrl = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + dbName + "?verifyServerCertificate=false";
        connection = DriverManager.getConnection(DbUrl, properties);
    }

    public void closeDBConnection() throws SQLException {
        connection.close();
    }

    public void ExecuteStatement(String SQL) throws SQLException {

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (SQLIntegrityConstraintViolationException e ){
            throw e;
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");

        }


    }

    public boolean getResult(String SQL) throws SQLException {

        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(SQL);
            stmt.close();
            return resultSet.next();

        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");
            return false;
        }


    }
    public  boolean isUsernameDuplicate(Connection connection, String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Assuming 'COUNT(*)' returns the number of rows with the given username
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, the username already exists (duplicate)
                }
            }
        }
        return false; // Default to false if an exception occurs
    }

}
