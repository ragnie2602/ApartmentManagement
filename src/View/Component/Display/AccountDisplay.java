package View.Component.Display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.AuthCtrl;
import Model.User;
import Resources.Constant.Constant;
import Resources.Constant.Tool;

public class AccountDisplay extends JPanel {
    private ImageIcon updatedImg;
    private JButton cancelButton, verifyButton;
    private JLabel avatarLabel, addressLabel, birthdayLabel, nameLabel, phoneLabel;
    private JPanel panel = new JPanel(new GridLayout(7, 3, 0, 15));
    private JTextField addressField, birthdayField, nameField, phoneField;
    private User user;
    private String imagePath = null;
    
    public AccountDisplay(User user) {
        this.user = user;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        avatarLabel = new JLabel(Tool.resize(user.getImg(), 616, 616), JLabel.CENTER);
        avatarLabel.setLayout(new BorderLayout());
        
        addressLabel = new JLabel(user.getAddress());
        birthdayLabel = new JLabel(user.getBirthday());
        nameLabel = new JLabel(user.getName());
        phoneLabel = new JLabel(user.getPhoneNumber());

        addressField = new JTextField(user.getAddress());
        birthdayField = new JTextField(user.getBirthday());
        nameField = new JTextField(user.getName());
        phoneField = new JTextField(user.getPhoneNumber());

        this.add(avatarLabel, BorderLayout.WEST);

        detail();
    }

    private void detail() {
        JLabel label = new JLabel("Chung cư BlueMoon");
        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)), subPanel = new JPanel(new BorderLayout());

        cancelButton = new JButton("Hủy");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setVisible(false);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                turnEditModeOff(false);
            }
        });

        verifyButton = new JButton("Xác nhận");
        verifyButton.setBackground(Color.WHITE);
        verifyButton.setVisible(false);
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verifyEdit();
            }
        });

        editPanel.setBackground(Color.WHITE);
        editPanel.add(cancelButton);
        editPanel.add(verifyButton);

        label.setFont(Constant.getTitleFont2(Font.BOLD).deriveFont((float)32));
        label.setForeground(Color.BLUE);

        panel.setBackground(Color.WHITE);        
        panel.add(new JLabel());                                            panel.add(label);        panel.add(new JLabel());
        panel.add(new JLabel());                                            panel.add(new JLabel()); panel.add(new JLabel());
        panel.add(new JLabel("Đại diện ban quản lý: ", JLabel.RIGHT)); panel.add(new JLabel()); panel.add(nameLabel);
        panel.add(new JLabel("Ngày khánh thành: ", JLabel.RIGHT));     panel.add(new JLabel()); panel.add(birthdayLabel);
        panel.add(new JLabel("Số điện thoại liên hệ: ", JLabel.RIGHT)); panel.add(new JLabel()); panel.add(phoneLabel);
        panel.add(new JLabel("Địa chỉ: ", JLabel.RIGHT));              panel.add(new JLabel()); panel.add(addressLabel);
        panel.add(new JLabel());                                            panel.add(editPanel);    panel.add(new JLabel());

        subPanel.setBackground(Color.WHITE);
        subPanel.add(panel, BorderLayout.NORTH);
        this.add(subPanel, BorderLayout.CENTER);
    }

    public void pickImage() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tệp hình ảnh", ImageIO.getReaderFileSuffixes());
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        fileChooser.setFileFilter(filter);

        if (result == JFileChooser.APPROVE_OPTION) {
            updatedImg = Tool.resize(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()), 616, 616);
            avatarLabel.setIcon(updatedImg);
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    public void turnEditModeOff(boolean accepted) {
        avatarLabel.removeMouseListener(avatarLabel.getMouseListeners()[0]);
        avatarLabel.removeAll();
        if (!accepted) {
            avatarLabel.setIcon(Tool.resize(user.getImg(), 616, 616));
            imagePath = null;
            updatedImg = null;
        }
        avatarLabel.revalidate();
        avatarLabel.repaint();

        cancelButton.setVisible(false);

        panel.remove(8); panel.add(nameLabel, 8);
        panel.remove(11); panel.add(birthdayLabel, 11);
        panel.remove(14); panel.add(phoneLabel, 14);
        panel.remove(17); panel.add(addressLabel, 17);

        panel.revalidate();
        panel.repaint();

        verifyButton.setVisible(false);
    }
    public void turnEditModeOn() {
        if (avatarLabel.getComponentCount() == 0) avatarLabel.add(new JLabel(new ImageIcon(Constant.image + "/pickImage.png")), BorderLayout.CENTER);

        if (avatarLabel.getMouseListeners().length == 0)
            avatarLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    pickImage();
                }
            });

        cancelButton.setVisible(true);

        panel.remove(8); panel.add(nameField, 8);
        panel.remove(11); panel.add(birthdayField, 11);
        panel.remove(14); panel.add(phoneField, 14);
        panel.remove(17); panel.add(addressField, 17);

        panel.revalidate();
        panel.repaint();

        verifyButton.setVisible(true);
    }
    public void verifyEdit() {
        if(updatedImg != null) {
            user.setImg(updatedImg);
        }
        nameLabel.setText(nameField.getText());
        birthdayLabel.setText(birthdayField.getText());
        phoneLabel.setText(phoneField.getText());
        addressLabel.setText(addressField.getText());

        turnEditModeOff(true);

        AuthCtrl.ChangeInfo(user.getId(), nameField.getText(), Date.valueOf(birthdayField.getText()), Integer.parseInt(phoneField.getText()), addressField.getText());

        // update image to database

        if(imagePath == null) return;

        // try(final InputStream inputStream = Files.newInputStream(Paths.get(imagePath))) {
        //     AuthCtrl.ChangeAvatar(user.getId(), inputStream);
        //     imagePath = null;
        //     updatedImg = null;
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        try {
            AuthCtrl.ChangeAvatar(user.getId(), updatedImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}