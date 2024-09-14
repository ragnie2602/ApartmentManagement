package View.Page.Fee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import Controller.FeeCtrl;
import Model.Fee;
import Model.User;
import Resources.Constant.Constant;
import Resources.Constant.Tool;
import View.Home;
import View.Component.Display.FeeDisplay;

public class EditFee {
    JButton cancelButton, verifyButton;
    JCheckBox mandatoryField = new JCheckBox();
    JComboBox<String> cycleField;
    JFrame editFeeFrame, prevFrame;
    JLabel notifyLabel;
    JPanel contentPanel = new JPanel(), functionPanel = new JPanel();
    JSpinner dateField;
    JTextField costField, nameField;
    User user;

    public EditFee(JFrame prev, User user, Integer currentId) {
        this.prevFrame = prev;
        this.user = user;

        Fee current = FeeCtrl.getFee(currentId);
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Thêm loại phí mới", JLabel.CENTER);
        JPanel datePanel = new JPanel(new GridLayout(1, 6));

        editFeeFrame = new JFrame("Thêm loại phí mới");
        editFeeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
                prevFrame.toFront();
            }
        });
        editFeeFrame.setBackground(Color.WHITE);
        editFeeFrame.setLayout(new BorderLayout());
        editFeeFrame.setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 350, prevFrame.getY() + prevFrame.getHeight() / 2 - 200);
        editFeeFrame.setSize(700, 400);

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(Constant.buttonFont);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });

        costField = new JTextField();
        costField.setFont(Constant.digitFont);
        costField.setText(current.getCost() + "");

        cycleField = new JComboBox<>(Constant.cycleType);
        cycleField.setBackground(Color.WHITE);
        cycleField.setFont(Constant.contentFont);
        cycleField.setSelectedIndex(current.getCycle());

        Calendar calendar = Calendar.getInstance();
        java.util.Date initialDate = calendar.getTime();
        java.util.Date starDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 10); java.util.Date endDate = calendar.getTime();
        dateField = new JSpinner(new SpinnerDateModel(initialDate, starDate, endDate, Calendar.YEAR));
        dateField.setEditor(new JSpinner.DateEditor(dateField, "dd/MM/yyyy"));
        
        cycleField.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (cycleField.getSelectedIndex() != 0) {
                    dateField.setEnabled(false);
                } else {
                    dateField.setEnabled(true);
                }
            }
        });

        mandatoryField.setSelected(current.getMandatory());

        nameField = new JTextField();
        nameField.setText(current.getName());

        notifyLabel = new JLabel();

        if (current.getId() < 6) {
            cycleField.setEnabled(false);
            mandatoryField.setEnabled(false);
            nameField.setEnabled(false);

            notifyLabel.setIcon(Tool.resize(new ImageIcon(Constant.image + "alert.png"), 25, 25));
            notifyLabel.setText("Đây là loại phí đặc biệt, chỉ có thể sửa đơn giá và không thể xóa.");
        }

        verifyButton = new JButton("Thêm");
        verifyButton.setFont(Constant.buttonFont);
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verify(currentId);
            }
        });

        contentPanel.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 5, 0, 15);
        gbc.gridx = 0; gbc.weightx = 2; gbc.weighty = 1;
        gbc.gridy = 0; contentPanel.add(new JLabel("Tên khoản phí", JLabel.RIGHT), gbc);
        gbc.gridy = 1; contentPanel.add(new JLabel("Số tiền", JLabel.RIGHT), gbc);
        gbc.gridy = 2; contentPanel.add(new JLabel("Bắt buộc", JLabel.RIGHT), gbc);
        gbc.gridy = 3; contentPanel.add(new JLabel("Chu kỳ đóng phí", JLabel.RIGHT), gbc);
        gbc.gridy = 4; contentPanel.add(new JLabel("Hạn nộp", JLabel.RIGHT), gbc);
        gbc.gridx = 1; gbc.weightx = 5; gbc.weighty = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; contentPanel.add(nameField, gbc);
        gbc.gridy = 1; contentPanel.add(costField, gbc);
        gbc.gridy = 2; contentPanel.add(mandatoryField, gbc);
        gbc.gridy = 4; contentPanel.add(datePanel, gbc);
        gbc.gridy = 5; contentPanel.add(notifyLabel, gbc);
        gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 3; contentPanel.add(cycleField, gbc);

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

        editFeeFrame.add(label, BorderLayout.NORTH);
        editFeeFrame.add(contentPanel, BorderLayout.CENTER);
        editFeeFrame.add(functionPanel, BorderLayout.SOUTH);

        editFeeFrame.setVisible(true);
    }

    private void cancel() {
        editFeeFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }

    private void verify(Integer currentId) {
        editFeeFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();

        try {
            String feeName = nameField.getText();
            int feeCost = Integer.parseInt(costField.getText());
            boolean feeMandatory = mandatoryField.isSelected() ? true : false;
            int feeCycle = java.util.Arrays.asList(Constant.cycleType).indexOf(cycleField.getSelectedItem().toString());
            String expirationDate = (new SimpleDateFormat("yyyy-MM-dd")).format(dateField.getValue());

            FeeCtrl.editFee(new Fee(currentId, feeName, feeCost, feeMandatory, feeCycle, expirationDate.toString()));
        } catch (NumberFormatException e) {
            
        }

        ((Home)prevFrame).getFeeTabbedPane().setComponentAt(0, new FeeDisplay(0));
        ((Home)prevFrame).getFeeTabbedPane().setComponentAt(1, new FeeDisplay(1));
        ((Home)prevFrame).getFeeTabbedPane().setComponentAt(2, new FeeDisplay(2));
    }
}