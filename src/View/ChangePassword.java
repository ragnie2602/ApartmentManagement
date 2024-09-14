package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import Controller.AuthCtrl;
import Model.User;
import Resources.Constant.Constant;

public class ChangePassword {
    JButton cancelButton, verifyButton;
    JFrame changePasswordFrame, prevFrame;
    JLabel notifyLabel;
    JPanel contentPanel = new JPanel(), functionPanel = new JPanel();
    JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    User user;

    public ChangePassword(JFrame prev, User user) {
        this.prevFrame = prev;
        this.user = user;

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Đổi mật khẩu", JLabel.CENTER);

        changePasswordFrame = new JFrame("Đổi mật khẩu");
        changePasswordFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
            }
        });
        changePasswordFrame.setBackground(Color.WHITE);
        changePasswordFrame.setLayout(new BorderLayout());
        changePasswordFrame.setLocation((int)prevFrame.getLocation().getX() + (int)prevFrame.getSize().getWidth() / 2 - 250, (int)prevFrame.getLocation().getY() + (int)prevFrame.getSize().getHeight() / 2 - 150);
        changePasswordFrame.setSize(500, 300);

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(Constant.buttonFont);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });

        notifyLabel = new JLabel();
        notifyLabel.setForeground(Color.RED);
        notifyLabel.setHorizontalAlignment(JLabel.LEFT);

        verifyButton = new JButton("Xác nhận");
        verifyButton.setFont(Constant.buttonFont);
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verify();
            }
        });

        oldPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        contentPanel.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 5, 0, 15);
        gbc.gridx = 0; gbc.weightx = 2; gbc.weighty = 1;
        gbc.gridy = 0; contentPanel.add(new JLabel("Mật khẩu cũ", JLabel.RIGHT), gbc);
        gbc.gridy = 1; contentPanel.add(new JLabel("Mật khẩu mới", JLabel.RIGHT), gbc);
        gbc.gridy = 2; contentPanel.add(new JLabel("Xác nhân mật khẩu", JLabel.RIGHT), gbc);
        gbc.gridx = 1; gbc.weightx = 5; gbc.weighty = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; contentPanel.add(oldPasswordField, gbc);
        gbc.gridy = 1; contentPanel.add(newPasswordField, gbc);
        gbc.gridy = 2; contentPanel.add(confirmPasswordField, gbc);
        gbc.gridy = 3; contentPanel.add(notifyLabel, gbc);

        functionPanel.setLayout(new GridLayout(2, 5));
        functionPanel.add(new JLabel());
        functionPanel.add(cancelButton);
        functionPanel.add(new JLabel());
        functionPanel.add(verifyButton);
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());

        label.setFont(Constant.titleFont.deriveFont((float)18.0));

        changePasswordFrame.add(label, BorderLayout.NORTH);
        changePasswordFrame.add(contentPanel, BorderLayout.CENTER);
        changePasswordFrame.add(functionPanel, BorderLayout.SOUTH);

        changePasswordFrame.setVisible(true);
    }

    private void cancel() {
        changePasswordFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }

    private void verify() {
        String confirmPassword = new String(confirmPasswordField.getPassword()), newPassword = new String(newPasswordField.getPassword()), oldPassword = new String(oldPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            notifyLabel.setText("Mật khẩu xác nhận không khớp!");
            return ;
        }

        if (!AuthCtrl.checkOldPassword(user.getId(), oldPassword)) {
            notifyLabel.setText("Mật khẩu cũ không khớp!");
            return ;
        }

        if (!AuthCtrl.changePassword(user.getId(), confirmPassword)) {
            notifyLabel.setText("Thay đổi mật khẩu không thành công!");
        }

        changePasswordFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }
}