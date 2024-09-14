package Controller;

import java.io.InputStream;
import java.sql.Date;

import javax.swing.ImageIcon;

import Model.User;
import SQLServer.DBQuery;
import View.Home;
import View.Login;

public class AuthCtrl {
    public static Boolean ChangeAvatar(int userId, InputStream inputStream) {
        return DBQuery.changeAvatar(userId, inputStream);
    }
    public static Boolean ChangeAvatar(int userId, ImageIcon image) {
        return DBQuery.changeAvatar(userId, image);
    }

    public static Boolean ChangeInfo(int userId, String name, Date birthday, int phoneNumber, String address) {
        return DBQuery.changeInfo(userId, name, birthday, phoneNumber, address);
    }

    public static boolean changePassword(int userId, String newPassword) {
        return DBQuery.changePassword(userId, newPassword);
    }
    public static boolean checkOldPassword(int userId, String oldPassword) {
        return DBQuery.checkOldPassword(userId, oldPassword);
    }

    public static User Login(String username, String password) {
        return DBQuery.findUser(username, password);
    }

    public static String Register(String username, String password) {
        return "Register Successful";
    }

    public static void signOut(Home home) {
        home.getFrame().setVisible(false);
        new Login();
    }
}
