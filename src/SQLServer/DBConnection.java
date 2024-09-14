package SQLServer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection database = null;

    private static String host = "jdbc:sqlserver://localhost:1433";
    private static String databaseName = "ApartmentManagement";
    private static String username = "sa";
    private static String password = "admin";

    public static void connect() {
        try {
            String connectionUrl = host + ";"
                    + "database=" + databaseName + ";"
                    + "user=" + username + ";"
                    + "password=" + password + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
            database = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHost(String host) {DBConnection.host = host;}
    public void setDatabaseName(String databaseName) {DBConnection.databaseName = databaseName;}
    public void setUsername (String username) {DBConnection.username = username;}
    public void setPasswrod (String password) {DBConnection.password = password;}
}
