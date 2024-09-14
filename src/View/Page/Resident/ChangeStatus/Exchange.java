package View.Page.Resident.ChangeStatus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Controller.ResidentCtrl;
import Model.Activity;
import Model.Resident;
import Resources.Constant.Constant;
import View.Home;
import View.Component.Display.ResidentDisplay;
import View.Component.Object.Dialog;

public class Exchange {
    private ArrayList<String[]> fromFilteredData, toFilteredData = new ArrayList<>();
    private DefaultTableModel fromModel, toModel;
    private JButton cancelButton, moveLeftButton, moveRightButton, resetButton, verifyButton;
    private JFrame exchangeFrame, prevFrame;
    private JSpinner fromFloorField, fromRoomField, toFloorField, toRoomField;
    private JTable fromTable, toTable;
    private String[] header = {"Họ tên", "Ngày sinh", "CCCD/CMT", "Số điện thoại"};
    private String[][] fromData, toData;

    public Exchange(JFrame prev) {
        this.prevFrame = prev;

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label;
        JPanel centerPanel, fromFrPanel = new JPanel(new GridLayout(1, 4)), fromPanel, functionPanel, movePanel,
               toFrPanel = new JPanel(new GridLayout(1, 4)), toPanel;

        exchangeFrame = new JFrame();

        exchangeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
                prevFrame.toFront();
            }
        });
        ((JPanel)exchangeFrame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        exchangeFrame.setLayout(new BorderLayout(15, 15));
        exchangeFrame.setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 600, prevFrame.getY() + prevFrame.getHeight() / 2 - 330);
        exchangeFrame.setSize(new Dimension(1200, 660));

        cancelButton = new JButton("Hủy");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });

        centerPanel = new JPanel(new GridBagLayout());

        fromModel = new DefaultTableModel(fromData, header) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        fromFloorField = new JSpinner(new SpinnerNumberModel(6, 6, 29, 1));
        fromFloorField.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                updateFrom();
            }
        });

        fromRoomField = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        fromRoomField.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                updateFrom();
            }
        });

        fromFrPanel.add(new JLabel("Tầng", JLabel.CENTER));
        fromFrPanel.add(fromFloorField);
        fromFrPanel.add(new JLabel("Phòng", JLabel.CENTER));
        fromFrPanel.add(fromRoomField);

        fromPanel = new JPanel(new BorderLayout(0, 15));

        fromTable = new JTable(fromModel);
        fromTable.setRowHeight(30);
        fromTable.setShowVerticalLines(false);

        fromPanel.add(fromFrPanel, BorderLayout.NORTH);
        fromPanel.add(new JScrollPane(fromTable), BorderLayout.CENTER);

        functionPanel = new JPanel(new GridLayout(2, 5));
        
        label = new JLabel("Chuyển căn hộ", JLabel.CENTER);
        label.setFont(Constant.getTitleFont2(2).deriveFont((float)22));

        movePanel = new JPanel();
        movePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.Y_AXIS));
        movePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        moveLeftButton = new JButton("Xóa thay đổi");
        moveLeftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                moveLeft();
            }
        });
        moveRightButton = new JButton("Sang phải >>");
        moveRightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                moveRight();
            }
        });
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {updateFrom();}
        });

        movePanel.add(resetButton);
        movePanel.add(Box.createVerticalStrut(15));
        movePanel.add(moveLeftButton);
        movePanel.add(Box.createVerticalStrut(15));
        movePanel.add(moveRightButton);

        toFloorField = new JSpinner(new SpinnerNumberModel(6, 6, 29, 1));
        toModel = new DefaultTableModel(toData, header) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        toRoomField = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        toFrPanel.add(new JLabel("Tầng", JLabel.CENTER));
        toFrPanel.add(toFloorField);
        toFrPanel.add(new JLabel("Phòng", JLabel.CENTER));
        toFrPanel.add(toRoomField);

        toPanel = new JPanel(new BorderLayout(0, 15));

        toTable = new JTable(toModel);
        toTable.setRowHeight(30);
        toTable.setShowVerticalLines(false);

        toPanel.add(toFrPanel, BorderLayout.NORTH);
        toPanel.add(new JScrollPane(toTable), BorderLayout.CENTER);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 560; centerPanel.add(fromPanel, gbc);
        gbc.gridx = 2;                                   centerPanel.add(toPanel, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;                gbc.weightx = 50;  centerPanel.add(movePanel, gbc);

        verifyButton = new JButton("Xác nhận");
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verify();
            }
        });

        exchangeFrame.add(label, BorderLayout.NORTH);
        exchangeFrame.add(centerPanel, BorderLayout.CENTER);
        exchangeFrame.add(functionPanel, BorderLayout.SOUTH);

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

        updateFrom();

        exchangeFrame.setVisible(true);
    }

    private void cancel() {
        exchangeFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }

    public ArrayList<Long> getToSelections() {
        ArrayList<Long> toId = new ArrayList<>();

        for (int i = 0; i < toFilteredData.size(); i++) {
            toId.add(Long.parseLong(toTable.getValueAt(i, 2).toString()));
        }

        return toId;
    }

    private void moveLeft() {
        toTable.setEnabled(false);

        int[] selectedRows = toTable.getSelectedRows();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            toFilteredData.remove(selectedRows[i]);
        }

        toModel.setDataVector(toFilteredData.toArray(new String[0][0]), header);

        toTable.setEnabled(true);
    }
    
    private void moveRight() {
        fromTable.setEnabled(false);

        int[] selectedRows = fromTable.getSelectedRows();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            toFilteredData.add(fromFilteredData.get(selectedRows[i]));
            fromFilteredData.remove(selectedRows[i]);
        }

        fromModel.setDataVector(fromFilteredData.toArray(new String[0][0]), header);
        toModel.setDataVector(toFilteredData.toArray(new String[0][0]), header);

        fromTable.setEnabled(true);
    }

    private void updateFrom() {
        ArrayList<Resident> residents = ResidentCtrl.getResidentList((int)fromFloorField.getValue(), (int)fromRoomField.getValue());

        fromFilteredData = new ArrayList<>();
        for (Resident resident : residents) {
            fromFilteredData.add(resident.toDataLite());
        }

        fromModel.setDataVector(fromFilteredData.toArray(new String[0][0]), header);
    }
    
    private void verify() {
        if ((int)fromFloorField.getValue() == (int)toFloorField.getValue() && (int)fromRoomField.getValue() == (int)toRoomField.getValue()) {
            new Dialog(exchangeFrame, 0, "Số phòng cũ và mới không được giống nhau!");
            return;
        }
        ArrayList<Long> selections = getToSelections();
        if (!ResidentCtrl.exchange(selections, (int)toFloorField.getValue(), (int)toRoomField.getValue())) {
            new Dialog(exchangeFrame, 0, "Lỗi!");
            return;
        } else {
            Date now = new Date(System.currentTimeMillis());
            int from = (int)fromFloorField.getValue() * 100 + (int)fromRoomField.getValue(), to = (int)toFloorField.getValue() * 100 + (int)toRoomField.getValue();
            for (Long long1 : selections) {
                ResidentCtrl.addActivity(new Activity(
                    long1,
                    4,
                    now,
                    now,
                    "" + from + " -> " + to));
            }
        }
        new Dialog(prevFrame, 2, "Thành công!");

        ((Home)prevFrame).setResidentDisplay(new ResidentDisplay());

        exchangeFrame.setVisible(false);
        prevFrame.setEnabled(true);
    }
}
