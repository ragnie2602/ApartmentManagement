package View.Page.Payment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Controller.FeeCtrl;
import Model.Fee;
import Model.Payment;
import Resources.Constant.Constant;
import View.Component.Object.TextField;

public class ShowPaymentList extends JFrame {
    ArrayList<Payment> payments;
    DefaultTableModel model;
    Fee fee;
    JCheckBox dateCheckBox;
    JLabel statistics;
    JSpinner endSpinner, startSpinner;
    JTable table;
    TextField paymentSearchBox = new TextField(new ImageIcon(Constant.image + "search.png"), "Số phòng", 14);
    String[] header = {"Tầng", "Phòng", "Người nộp", "Ngày nộp", "Định kỳ", "Số lượng\n(m2, người, ...)", "Đã nộp"};
    String[][] data;

    public ShowPaymentList(JFrame prevFrame, Integer feeId) {
        UIManager.put("Label.font", Constant.contentFont);
        UIManager.put("Table.font", Constant.contentFont);
        UIManager.put("TableHeader.font", Constant.titleFont);

        setLayout(new BorderLayout(15, 15));
        setLocation(prevFrame.getWidth() / 2 - 600, prevFrame.getHeight() / 2 - 400);
        setSize(new Dimension(1200, 800));
        setTitle("Danh sách nộp phí");

        fee = FeeCtrl.getFee(feeId);
        payments = FeeCtrl.getPaymentList(fee.getId());

        int temp = payments.size();
        data = new String[temp][];
        for (int i = 0; i < temp; i++) {
            data[i] = payments.get(i).toData();
        }

        JCheckBox checkBox = new JCheckBox();
        JLabel label1 = new JLabel("Loại phí: "), label2 = new JLabel("   Bắt buộc: "), label3 = new JLabel("Phải nộp: ");
        JPanel panel = new JPanel(new BorderLayout(15, 15)), panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT)),
               panel2 = new JPanel(new FlowLayout()), panel3 = new JPanel(new GridLayout(1, 1));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.revalidate();
                prevFrame.repaint();
                prevFrame.setEnabled(true);
            }
        });

        checkBox.setEnabled(false);
        checkBox.setSelected(true);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1); java.util.Date initialDate = calendar.getTime(); calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.YEAR, -100); java.util.Date startDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 100); java.util.Date endDate = calendar.getTime();
        startSpinner = new JSpinner(new SpinnerDateModel(initialDate, startDate, endDate, Calendar.DAY_OF_MONTH));
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy"));
        startSpinner.setEnabled(false);
        endSpinner = new JSpinner(new SpinnerDateModel(initialDate, startDate, endDate, Calendar.DAY_OF_MONTH));
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy"));
        endSpinner.setEnabled(false);

        dateCheckBox = new JCheckBox();
        dateCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                startSpinner.setEnabled(ie.getStateChange() == ItemEvent.SELECTED);
                endSpinner.setEnabled(ie.getStateChange() == ItemEvent.SELECTED);
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    filter(paymentSearchBox.getText(),
                           new Date(((java.util.Date)startSpinner.getValue()).getTime()),
                           new Date(((java.util.Date)endSpinner.getValue()).getTime()));
                } else {
                    filter(paymentSearchBox.getText());
                }
            }
        });

        label1.setFont(Constant.contentFont.deriveFont(Font.BOLD));
        label2.setFont(Constant.contentFont.deriveFont(Font.BOLD));
        label3.setFont(Constant.contentFont.deriveFont(Font.BOLD));

        model = new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);

        startSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                filter(paymentSearchBox.getText(),
                       new Date(((java.util.Date)startSpinner.getValue()).getTime()),
                       new Date(((java.util.Date)endSpinner.getValue()).getTime()));
            }
        });
        endSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                filter(paymentSearchBox.getText(),
                       new Date(((java.util.Date)startSpinner.getValue()).getTime()),
                       new Date(((java.util.Date)endSpinner.getValue()).getTime()));
            }
        });

        statistics = new JLabel("", JLabel.RIGHT);
        statistics.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 10));

        filter("");

        panel1.add(label1);
        panel1.add(new JLabel(fee.getName()));
        panel1.add(label2);
        panel1.add(checkBox);
        panel1.add(label3);
        panel1.add(new JLabel("" + fee.getCost()));

        panel2.add(paymentSearchBox);
        panel2.add(Box.createHorizontalStrut(20));
        panel2.add(dateCheckBox);
        panel2.add(new JLabel("Từ"));
        panel2.add(startSpinner);
        panel2.add(new JLabel("Đến"));
        panel2.add(endSpinner);

        paymentSearchBox.setPreferredSize(new Dimension(200, 25));
        paymentSearchBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == '\n') {
                    if (dateCheckBox.isSelected()) {
                        filter(paymentSearchBox.getText(),
                            new Date(((java.util.Date)startSpinner.getValue()).getTime()),
                            new Date(((java.util.Date)endSpinner.getValue()).getTime()));
                    } else {
                        filter(paymentSearchBox.getText());
                    }
                }
            }
        });

        panel.add(panel1, BorderLayout.WEST);
        panel.add(panel2, BorderLayout.SOUTH);

        panel3.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel3.add(new JScrollPane(table));

        add(panel, BorderLayout.NORTH);
        add(panel3, BorderLayout.CENTER);
        add(statistics, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void filter(String keyword) {
        ArrayList<String[]> filteredData = new ArrayList<>();

        if (keyword.isEmpty()) {
            for (Payment payment : payments) {
                filteredData.add(payment.toData());
            }
        } else if (keyword.matches("\\d+")) {
            int keyNum = Integer.valueOf(keyword);

            for (Payment payment : payments) {
                if (payment.getFloor() * 100 + payment.getRoom() == keyNum || payment.getFloor() == keyNum || payment.getRoom() == keyNum) {
                    filteredData.add(payment.toData());
                }
            }
        }

        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        statistics.setText("Số lần dã đóng: " + filteredData.size());

        this.revalidate();
        this.repaint();
    }
    public void filter(String keyword, Date startDate, Date endDate) {
        ArrayList<String[]> filteredData = new ArrayList<>();
        long sum = 0;

        if (keyword.isEmpty()) {
            for (Payment payment : payments) {
                if (payment.getTimeValidate().compareTo(startDate) >= 0 && payment.getTimeValidate().compareTo(endDate) <= 0) {
                    filteredData.add(payment.toData());
                    sum += payment.getPaid();
                }
            }
        } else if (keyword.matches("\\d+")) {
            int keyNum = Integer.valueOf(keyword);

            for (Payment payment : payments) {
                if (
                    (payment.getFloor() * 100 + payment.getRoom() == keyNum
                    || payment.getFloor() == keyNum
                    || payment.getRoom() == keyNum) &&
                    payment.getTimeValidate().compareTo(startDate) >= 0 && payment.getTimeValidate().compareTo(endDate) <= 0
                ) {
                    filteredData.add(payment.toData());
                    sum += payment.getPaid();
                }
            }
        }

        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        statistics.setText("Số lần dã đóng: " + filteredData.size() + "\tTổng tiền: " + sum);

        this.revalidate();
        this.repaint();
    }
}