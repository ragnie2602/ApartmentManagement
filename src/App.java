import SQLServer.DBConnection; // Use for SQLServer
import View.Login;

public class App {
    public static void main(String[] args) {
        DBConnection.connect();
        new Login();
    }
}
