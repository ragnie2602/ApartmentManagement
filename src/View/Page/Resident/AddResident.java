package View.Page.Resident;

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
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import Controller.ResidentCtrl;
import Model.Activity;
import Model.Resident;
import Model.User;
import Resources.Constant.Constant;
import View.Home;
import View.Component.Display.ResidentDisplay;

public class AddResident {
    JButton cancelButton, verifyButton;
    JComboBox<String> countryField, ethnicField, genderField;
    JFrame addResidentFrame, prevFrame;
    JLabel notifyLabel;
    JPanel contentPanel = new JPanel(), functionPanel = new JPanel();
    JSpinner dateField, floorField, roomField;
    JTextField idField, nameField, phoneField, relationshipField;
    User user;

    public AddResident(JFrame prev, User user) {
        this.prevFrame = prev;
        this.user = user;

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Chào mừng cư dân mới tới BlueMoon", JLabel.CENTER);
        JPanel frPanel = new JPanel(new GridLayout(1, 3));

        addResidentFrame = new JFrame("Thêm cư dân mới");
        addResidentFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
                prevFrame.toFront();
            }
        });
        addResidentFrame.setBackground(Color.WHITE);
        addResidentFrame.setLayout(new BorderLayout());
        addResidentFrame.setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 400, prevFrame.getY() + prevFrame.getHeight() / 2 - 300);
        addResidentFrame.setSize(800, 600);

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(Constant.buttonFont);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });

        countryField = new JComboBox<String>(Constant.country);
        countryField.setBackground(Color.WHITE);

        Calendar calendar = Calendar.getInstance();
        java.util.Date initialDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100); java.util.Date starDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 100); java.util.Date endDate = calendar.getTime();
        dateField = new JSpinner(new SpinnerDateModel(initialDate, starDate, endDate, Calendar.DAY_OF_MONTH));
        dateField.setEditor(new JSpinner.DateEditor(dateField, "dd/MM/yyyy"));

        ethnicField = new JComboBox<String>(Constant.ethnic);
        ethnicField.setBackground(Color.WHITE);

        floorField = new JSpinner(new SpinnerNumberModel(6, 6, 29, 1));
        roomField = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        frPanel.add(floorField);
        frPanel.add(new JLabel("     Phòng     "));
        frPanel.add(roomField);

        genderField = new JComboBox<String>(Constant.gender);
        genderField.setBackground(Color.WHITE);

        idField = new JTextField();
        idField.setFont(Constant.digitFont);
        
        nameField = new JTextField();

        notifyLabel = new JLabel("", JLabel.LEFT);
        notifyLabel.setFont(Constant.notifyFont);
        notifyLabel.setForeground(Color.RED);

        phoneField = new JTextField();
        phoneField.setFont(Constant.digitFont);

        relationshipField = new JTextField();

        verifyButton = new JButton("Thêm");
        verifyButton.setFont(Constant.buttonFont);
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verify();
            }
        });

        contentPanel.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 5, 0, 15);
        gbc.gridx = 0; gbc.weightx = 2; gbc.weighty = 1;
        gbc.gridy = 0; contentPanel.add(new JLabel("Căn cước công dân / Chứng minh thư", JLabel.RIGHT), gbc);
        gbc.gridy = 1; contentPanel.add(new JLabel("Họ và tên", JLabel.RIGHT), gbc);
        gbc.gridy = 2; contentPanel.add(new JLabel("Số điện thoại", JLabel.RIGHT), gbc);
        gbc.gridy = 3; contentPanel.add(new JLabel("Giới tính", JLabel.RIGHT), gbc);
        gbc.gridy = 4; contentPanel.add(new JLabel("Ngày sinh", JLabel.RIGHT), gbc);
        gbc.gridy = 5; contentPanel.add(new JLabel("Quốc tịch", JLabel.RIGHT), gbc);
        gbc.gridy = 6; contentPanel.add(new JLabel("Dân tộc", JLabel.RIGHT), gbc);
        gbc.gridy = 7; contentPanel.add(new JLabel("Tầng", JLabel.RIGHT), gbc);
        gbc.gridy = 8; contentPanel.add(new JLabel("Mối quan hệ với chủ hộ", JLabel.RIGHT), gbc);
        gbc.gridx = 1; gbc.weightx = 5; gbc.weighty = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; contentPanel.add(idField, gbc);
        gbc.gridy = 1; contentPanel.add(nameField, gbc);
        gbc.gridy = 2; contentPanel.add(phoneField, gbc);
        gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 3; contentPanel.add(genderField, gbc);
        gbc.gridy = 4; contentPanel.add(dateField, gbc);
        gbc.gridy = 5; contentPanel.add(countryField, gbc);
        gbc.gridy = 6; contentPanel.add(ethnicField, gbc);
        gbc.gridy = 7; contentPanel.add(frPanel, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 8; contentPanel.add(relationshipField, gbc);
        gbc.gridy = 9; contentPanel.add(notifyLabel, gbc);

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

        addResidentFrame.add(label, BorderLayout.NORTH);
        addResidentFrame.add(contentPanel, BorderLayout.CENTER);
        addResidentFrame.add(functionPanel, BorderLayout.SOUTH);

        addResidentFrame.setVisible(true);
    }

    private void cancel() {
        addResidentFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }
    private void verify() {
        if (idField.getText().isEmpty()) {
            notifyLabel.setText("Căn cước công dân / Chứng minh nhân dân không thể bỏ trống!");
            return ;
        }
        if (nameField.getText().isEmpty()) {
            notifyLabel.setText("Tên không được bỏ trống");
            return ;
        }
        if (phoneField.getText().length() != 10 && phoneField.getText().length() != 0) {
            notifyLabel.setText("Số điện thoại phải có 10 chữ số (hoặc không nhập gì nếu không có số điện thoại)");
            return ;
        }

        try {
            if (phoneField.getText().length() != 0) {
                if (ResidentCtrl.existsPhoneNumber(Integer.parseInt(phoneField.getText()))) {
                    notifyLabel.setText("Số điện thoại đã tồn tại");
                    return;   
                }
            }
            if (ResidentCtrl.existsResident(Long.parseLong(idField.getText()))) {
                notifyLabel.setText("Số căn cước công dân này đã tồn tại");
                return;
            }

            if (ResidentCtrl.addResident(new Resident(
                Long.parseLong(idField.getText()),
                nameField.getText(),
                new Date(((java.util.Date)dateField.getValue()).getTime()),
                genderField.getSelectedIndex() == 1,
                Integer.parseInt(phoneField.getText().isEmpty() ? "0" : phoneField.getText()),
                countryField.getSelectedItem().toString(),
                ethnicField.getSelectedItem().toString(),
                (Integer)floorField.getValue(), 
                (Integer)roomField.getValue(),
                relationshipField.getText(),
                0))) {
                    ResidentCtrl.addActivity(new Activity(
                        Long.parseLong(idField.getText()), 0, new Date(System.currentTimeMillis()), null, ""));
                }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            notifyLabel.setText("Số căn cước công dân / chứng minh nhân dân, số điện thoại phải là các số");
            return;
        }

        ((Home)prevFrame).setResidentDisplay(new ResidentDisplay());

        addResidentFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }
}