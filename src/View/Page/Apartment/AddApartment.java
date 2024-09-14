package View.Page.Apartment;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import Controller.ResidentCtrl;
import Model.Resident;
import Model.User;
import Resources.Constant.Constant;
import SQLServer.DBQuery;
import View.Home;
import View.Component.Display.ApartmentDisplay;

public class AddApartment {
    ArrayList<Resident> residents = ResidentCtrl.getResidentList(0);
    JButton cancelButton, verifyButton;
    JSpinner floorField, roomField;
    JComboBox ownerField;
    JFrame addApartmentFrame, prevFrame;
    JPanel contentPanel = new JPanel(), frPanel, functionPanel = new JPanel();
    User user;

    public AddApartment(JFrame prev, User user) {
        this.prevFrame = prev;
        this.user = user;

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Cảm ơn bạn đã tin tưởng sử dụng dịch vụ của BlueMoon", JLabel.CENTER);
        JPanel frPanel = new JPanel(new GridLayout(1, 3));

        addApartmentFrame = new JFrame("Thêm chủ căn hộ");
        addApartmentFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
                prevFrame.toFront();
            }
        });
        addApartmentFrame.setLayout(new BorderLayout());
        addApartmentFrame.setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 300, prevFrame.getY() + (int)prevFrame.getHeight() / 2 - 140);
        addApartmentFrame.setSize(600, 280);

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(Constant.buttonFont);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });

        floorField = new JSpinner(new SpinnerNumberModel(6, 6, 29, 1));
        roomField = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        frPanel.add(floorField);
        frPanel.add(new JLabel("     Phòng     "));
        frPanel.add(roomField);

        label.setFont(Constant.getTitleFont2(2).deriveFont((float)16));

        // DefaultComboBoxModel model = new DefaultComboBoxModel(residentList.toArray());
        ownerField = new JComboBox<>(residents.toArray());
        ownerField.setEditable(true);
        ownerField.setFont(Constant.contentFont.deriveFont((float)12.0));
        suggestion();

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
        gbc.gridy = 0; contentPanel.add(new JLabel("Tầng", JLabel.RIGHT), gbc);
        gbc.gridy = 1; contentPanel.add(new JLabel("Chủ hộ", JLabel.RIGHT), gbc);
        gbc.gridx = 1; gbc.weightx = 5; gbc.weighty = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; contentPanel.add(frPanel, gbc);
        gbc.gridy = 1; contentPanel.add(ownerField, gbc);
        gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE;

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

        addApartmentFrame.add(label, BorderLayout.NORTH);
        addApartmentFrame.add(contentPanel, BorderLayout.CENTER);
        addApartmentFrame.add(functionPanel, BorderLayout.SOUTH);

        addApartmentFrame.setVisible(true);
    }

    private void cancel() {
        addApartmentFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }

    private void suggestion() {
        JTextField textField = (JTextField)ownerField.getEditor().getEditorComponent();

        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                char c = ke.getKeyChar();

                if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ((int)c == 8))
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (!textField.getText().isEmpty()) {
                            String enteredText = textField.getText();

                            ownerField.removeAllItems();
                            for (Resident r : residents) {
                                if (r.getName().toLowerCase().contains(enteredText.toLowerCase())) {
                                    ownerField.addItem(r);
                                }
                            }
                            if (ownerField.getItemCount() > 0) {
                                ownerField.showPopup();
                            }
                            textField.setText(enteredText);
                        } else {
                            ownerField.hidePopup();
                            ownerField.removeAllItems();
                            for (Resident r : residents) {
                                ownerField.addItem(r);
                            }
                        }
                    }
                });
            }
        });
    }

    private void verify() {
        addApartmentFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();

        DBQuery.addNewApartment((int)floorField.getValue() * 100 + (int)roomField.getValue(), ((Resident)ownerField.getSelectedItem()).getId());

        ((Home)prevFrame).setApartmentDisplay(new ApartmentDisplay());
    }
}