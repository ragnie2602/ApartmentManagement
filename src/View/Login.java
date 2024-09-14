package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controller.AuthCtrl;
import Model.User;
import Resources.Constant.Constant;
import Resources.Constant.Tool;
import View.Component.Object.Button;
import View.Component.Object.PasswordField;
import View.Component.Object.RoundedPanel;
import View.Component.Object.TextField;

public class Login {
    GridBagConstraints gbc;
    GridBagLayout gb;
    JFrame loginFrame;
    JLabel notifyLabel;
    JPanel backgroundPanel;
    TextField username;
    PasswordField password;

    public Login() {
        try {
            if (!System.getProperty("os.name").startsWith("Linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        UIManager.put("Button.font", Constant.buttonFont.deriveFont((float)12.0));
        UIManager.put("Label.font", Constant.contentFont);
        UIManager.put("PasswordField.font", Constant.contentFont.deriveFont((float)12.0));
        UIManager.put("TextField.font", Constant.contentFont.deriveFont((float)12.0));

        Button button;
        JLabel label1, label2;
        JPanel rightPanel = new JPanel(new BorderLayout(80, 80));
        RoundedPanel authPanel = new RoundedPanel(16);        
        authPanel.setBackground(Color.WHITE);
        authPanel.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 32));
        authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
        authPanel.setShadow(15, 15, 0);

        gb = new GridBagLayout();
        gbc = new GridBagConstraints();

        loginFrame = new JFrame("Đăng nhập");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginFrame.setSize(1920, 1024);
        loginFrame.setLayout(new GridLayout(1, 2));
        int height = loginFrame.getHeight();

        button = new Button("Đăng nhập");
        button.setBackground(new Color(99, 88, 220));
        button.setContentAreaFilled(true);
        button.setFont(Constant.getTitleFont2(Font.BOLD).deriveFont((float)16.0));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(1000, 50));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verify();
            }
        });

        label1 = new JLabel("Chào mừng đến với");
        label1.setFont(Constant.getTitleFont2(Font.PLAIN).deriveFont((float)32));

        label2 = new JLabel("BlueMoon");
        label2.setFont(Constant.getTitleFont2(Font.BOLD).deriveFont((float)50));
        label2.setForeground(new Color(99, 88, 220));

        notifyLabel = new JLabel("     ");
        notifyLabel.setForeground(Color.RED);

        username = new TextField("Tài khoản", 16);
        username.setBackground(new Color(236, 236, 236));
        username.setFont(Constant.contentFont.deriveFont((float)16));

        password = new PasswordField("Mật khẩu");
        password.setBackground(new Color(236, 236, 236));
        password.setFont(Constant.contentFont.deriveFont((float)16));

        authPanel.add(Box.createVerticalStrut(height * 140 / 1024));
        authPanel.add(label1);
        authPanel.add(label2);
        authPanel.add(Box.createVerticalStrut(height * 116 / 1024));
        authPanel.add(username);
        authPanel.add(Box.createVerticalStrut(height * 31 / 1024));
        authPanel.add(password);
        authPanel.add(Box.createVerticalStrut(height * 31 / 1024));
        authPanel.add(notifyLabel);
        authPanel.add(Box.createVerticalStrut(height * 10 / 1024));
        authPanel.add(button);
        authPanel.add(Box.createVerticalStrut(height * 300 / 1024));

        rightPanel.setBorder(BorderFactory.createEmptyBorder(37, 80, 37, 80));
        rightPanel.add(authPanel, BorderLayout.CENTER);

        backgroundPanel = new JPanel(new GridLayout(1, 1, loginFrame.getHeight() / 4, loginFrame.getHeight() / 4));
        
        backgroundPanel.add(new JLabel(Tool.resize(new ImageIcon(Constant.image + "loginBackground.png"), loginFrame.getHeight() * 3 / 5, loginFrame.getHeight() * 3 / 5)));

        loginFrame.add(backgroundPanel);
        loginFrame.add(rightPanel);

        loginFrame.setVisible(true);
    }

    public void verify() {
        if (password.getPassword().toString().isEmpty() || username.getText().isEmpty()) {
            notifyLabel.setText("Tài khoản và mật khẩu không được để trống");
            return;
        }

        User myUser = AuthCtrl.Login(username.getText(), new String(password.getPassword()));
        
        if (myUser == null) {
            notifyLabel.setText("Sai tài khoản hoặc mật khẩu");
        } else {
            loginFrame.setVisible(false);
            new Home(myUser);
        }
    }

    public JFrame getLoginFrame() {
        return this.loginFrame;
    }
}
