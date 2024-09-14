package View.Component.Display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Controller.ApartmentCtrl;
import Model.Apartment;
import Resources.Constant.Constant;
import View.Component.Object.StatisticCard;

public class ApartmentDisplay extends JPanel {
    ArrayList<Apartment> apartmentList;
    DefaultTableModel model;
    JTable table;
    StatisticCard card;
    String[] header = {"Tầng", "Phòng", "Tên chủ sở hữu", "Số điện thoại", "Diện tích (m^2)"};
    String[][] data;

    public ApartmentDisplay() {
        UIManager.put("Table.font", Constant.getTitleFont2(0));
        UIManager.put("TableHeader.font", Constant.getTitleFont2(3));
        UIManager.put("TableHeader.foreground", new Color(131, 133, 142));

        JPanel statistic = new JPanel();

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 0));

        apartmentList = ApartmentCtrl.getApartmentList();

        card = new StatisticCard("ic_apartment.png", "Tổng số căn hộ", "");
        card.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        card.setGradient(new Color(0, 212, 255, 100), new Color(238, 237, 255, 100));

        model = new DefaultTableModel(data, header) {
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
        statistic.add(card);

        filter("");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
        add(statistic, BorderLayout.EAST);
    }
    
    public void filter(String keyword) {
        ArrayList<String[]> filteredData = new ArrayList<>();

        for (Apartment apartment : apartmentList) {
            if (apartment.getOwnerName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredData.add(apartment.toData());
            }
        }

        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        card.setContent("" + filteredData.size());

        this.revalidate();
        this.repaint();
    }

    public ArrayList<Integer> getSelections() {
        ArrayList<Integer> selections = new ArrayList<>();

        for (int i : table.getSelectedRows()) {
            selections.add(Integer.parseInt(table.getValueAt(i,0).toString()) * 100 + Integer.parseInt(table.getValueAt(i, 1).toString()));
        }

        return selections;
    }
}
