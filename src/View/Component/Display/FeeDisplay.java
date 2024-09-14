package View.Component.Display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Controller.FeeCtrl;
import Model.Fee;
import Resources.Constant.Constant;
import View.Component.Object.StatisticCard;
public class FeeDisplay extends JPanel {
    ArrayList<Fee> feeList;
    DefaultTableModel model;
    int cycle;
    JTable table;
    StatisticCard forceCard, optionCard, warningCard;
    String[] header = {"Mã phí", "Tên", "VND", "Hạn nộp", "Bắt buộc"};
    Object[][] data;

    public FeeDisplay(int cycle) {
        this.cycle = cycle;
        
        UIManager.put("Table.font", Constant.getTitleFont2(0));
        UIManager.put("TableHeader.font", Constant.getTitleFont2(2));
        UIManager.put("TableHeader.foreground", new Color(131, 133, 142));

        JPanel statistic = new JPanel();

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 0));

        forceCard = new StatisticCard("ic_mandatory.png", "Bắt buộc", "");
        forceCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        forceCard.setGradient(new Color(240, 254, 0, 100), new Color(250, 255, 136, 100));

        optionCard = new StatisticCard("ic_optional.png", "Không bắt buộc", "");
        optionCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        optionCard.setGradient(new Color(12, 254, 0, 100), new Color(136, 255, 146, 100));

        warningCard = new StatisticCard("ic_warning.png", "Sắp hết hạn", "");
        warningCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        warningCard.setGradient(new Color(254, 82, 0, 100), new Color(255, 220, 136, 100));

        feeList = FeeCtrl.getFeeList(cycle);

        model = new DefaultTableModel(data, header) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 4:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(50);
        table.setShowVerticalLines(false);

        statistic.setBackground(Color.WHITE);
        statistic.setLayout(new BoxLayout(statistic, BoxLayout.Y_AXIS));
        if (cycle == 0) {
            statistic.add(warningCard);
            statistic.add(Box.createVerticalStrut(20));
        }
        statistic.add(forceCard);
        statistic.add(Box.createVerticalStrut(20));
        statistic.add(optionCard);
        
        filter("");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
        add(statistic, BorderLayout.EAST);
    }

    public void filter(String keyword) {
        ArrayList<Object[]> filteredData = new ArrayList<>();
        int mandatory = 0, optional = 0, warning = 0;


        for (Fee fee : feeList) {
            if (fee.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredData.add(fee.toData());
                if (fee.getMandatory()) {++mandatory;} else {++optional;}
                if (cycle == 0 && Date.valueOf(fee.getExpirationDate()).compareTo(new Date(System.currentTimeMillis())) > 0) {++warning;}
            }
        }

        model.setDataVector(filteredData.toArray(new Object[0][0]), header);
        forceCard.setContent("" + mandatory);
        optionCard.setContent("" + optional);
        warningCard.setContent("" + warning);

        this.revalidate();
        this.repaint();
    }

    public ArrayList<Integer> getSelections() {
        ArrayList<Integer> selections = new ArrayList<>();

        for (int i : table.getSelectedRows()) {
            selections.add(Integer.parseInt(table.getValueAt(i, 0).toString()));
        }

        return selections;
    }
}