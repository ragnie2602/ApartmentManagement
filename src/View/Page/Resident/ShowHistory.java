package View.Page.Resident;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Controller.ResidentCtrl;
import Model.Activity;
import Model.Resident;
import Resources.Constant.Constant;
import View.Component.Object.ResidentCard2;

public class ShowHistory extends JFrame {
    ArrayList<Activity> activities;
    DefaultTableModel model;
    JCheckBox timeInCheckBox, timeOutCheckBox;
    JLabel statistics;
    JSpinner startSpinner, endSpinner;
    JTable table;
    Resident resident;
    String[] header = {"Mã", "Trạng thái", "Ngày vào", "Ngày ra", "Chú thích"};
    String[][] data;

    public ShowHistory(JFrame prevFrame, Long residentId) {
        UIManager.put("Label.font", Constant.contentFont);

        setLayout(new BorderLayout(15, 15));
        setLocation(prevFrame.getWidth() / 2 - 600, prevFrame.getHeight() / 2 - 400);
        setSize(new Dimension(1200, 800));
        setTitle("Lịch sử thường trú, tạm trú, tạm vắng");

        resident = ResidentCtrl.getResident(residentId);
        activities = ResidentCtrl.getHistory(resident.getId());

        int temp = activities.size();
        data = new String[temp][];
        for (int i = 0; i < temp; i++) {
            data[i] = activities.get(i).toData();
        }

        JLabel label1 = new JLabel("Thông tin cá nhân", JLabel.CENTER), label2 = new JLabel("Lịch sử cư trú", JLabel.CENTER);
        JPanel panel1 = new JPanel(), panel3 = new JPanel(new GridLayout(1, 1));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.revalidate();
                prevFrame.repaint();
                prevFrame.setEnabled(true);
            }
        });

        Calendar calendar = Calendar.getInstance();
        java.util.Date initialDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100); java.util.Date startDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 100); java.util.Date endDate = calendar.getTime();
        endSpinner = new JSpinner(new SpinnerDateModel(initialDate, startDate, endDate, Calendar.DAY_OF_MONTH));
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy"));
        endSpinner.setEnabled(false);
        startSpinner = new JSpinner(new SpinnerDateModel(startDate, startDate, endDate, Calendar.DAY_OF_MONTH));
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy"));
        startSpinner.setEnabled(false);

        timeInCheckBox = new JCheckBox("Ngày vào");
        timeOutCheckBox = new JCheckBox("Ngày ra");

        timeInCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                timeOutCheckBox.setSelected(false);
                if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    reset();
                    return;
                }
                filterTimeIn(
                    new Date(((java.util.Date)startSpinner.getValue()).getTime()),
                    new Date(((java.util.Date)endSpinner.getValue()).getTime()));
            }
        });
        timeOutCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                timeInCheckBox.setSelected(false);
                if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    reset();
                    return;
                }
                filterTimeOut(
                    new Date(((java.util.Date)startSpinner.getValue()).getTime()),
                    new Date(((java.util.Date)endSpinner.getValue()).getTime()));
                }
            }
        );

        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label1.setFont(Constant.contentFont.deriveFont(Font.BOLD));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setFont(Constant.contentFont.deriveFont(Font.BOLD));

        model = new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);

        statistics = new JLabel("", JLabel.RIGHT);
        statistics.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 10));

        reset();

        panel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(label1);
        panel1.add(Box.createVerticalStrut(10));
        panel1.add(new ResidentCard2(resident, false));
        panel1.add(Box.createVerticalStrut(10));
        panel1.add(label2);

        panel3.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel3.add(new JScrollPane(table));

        add(panel1, BorderLayout.NORTH);
        add(panel3, BorderLayout.CENTER);
        add(statistics, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void filterTimeIn(Date startDate, Date endDate) {
        ArrayList<String[]> filteredData = new ArrayList<>();
    
        for (Activity activity : activities) {
            if (activity.getTimeIn().compareTo(startDate) >= 0 && activity.getTimeIn().compareTo(endDate) <= 0) {
                filteredData.add(activity.toData());
            }
        }
        
        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        statistics.setText("Số lần di trú: " + filteredData.size());

        this.revalidate();
        this.repaint();
    }
    public void filterTimeOut(Date startDate, Date endDate) {
        ArrayList<String[]> filteredData = new ArrayList<>();
    
        for (Activity activity : activities) {
            if (activity.getTimeIn().compareTo(startDate) >= 0 && activity.getTimeIn().compareTo(endDate) <= 0) {
                filteredData.add(activity.toData());
            }
        }
        
        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        statistics.setText("Số lần di trú: " + filteredData.size());

        this.revalidate();
        this.repaint();
    }
    public void reset() {
        ArrayList<String[]> filteredData = new ArrayList<>();
    
        for (Activity activity : activities) {
            filteredData.add(activity.toData());
        }
        
        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        statistics.setText("Số lần di trú: " + filteredData.size());

        this.revalidate();
        this.repaint();
    }
}