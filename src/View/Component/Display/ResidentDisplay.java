package View.Component.Display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.ResidentCtrl;
import Model.Resident;
import View.Component.Object.StatisticCard;

public class ResidentDisplay extends JPanel {
    ArrayList<Resident> residentList, selections;
    DefaultTableModel model;
    JTable table;
    StatisticCard absentCard, livingCard, stayingCard, totalCard;
    String[] header = {"Họ tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Dân tộc", "Quốc tịch", "Tầng", "Phòng", "Mối quan hệ với chủ hộ", "Trạng thái"};
    String[][] data;

    public ResidentDisplay() {
        JPanel statistic = new JPanel();

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 0));

        residentList = ResidentCtrl.getResidentList();
        
        absentCard = new StatisticCard("ic_absent.png", "Tạm vắng", "");
        absentCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        absentCard.setGradient(new Color(255, 0, 0, 100), new Color(255, 255, 255, 100));

        livingCard = new StatisticCard("ic_living.png", "Thường trú", "");
        livingCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        livingCard.setGradient(new Color(0, 92, 254, 100), new Color(240, 250, 255, 100));

        stayingCard = new StatisticCard("ic_staying.png", "Tạm trú", "");
        stayingCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        stayingCard.setGradient(new Color(255, 253, 55, 100), new Color(255, 252, 156, 100));

        totalCard = new StatisticCard("ic_resident.png", "Tổng số cư dân", "");
        totalCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        totalCard.setGradient(new Color(128, 128, 128, 100), new Color(238, 237, 255, 100));

        model = new DefaultTableModel(data, header){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        table = new JTable(model);
        table.setRowHeight(50);
        table.setShowVerticalLines(false);

        statistic.setBackground(Color.WHITE);
        statistic.setLayout(new BoxLayout(statistic, BoxLayout.Y_AXIS));
        statistic.add(totalCard);
        statistic.add(Box.createVerticalStrut(20));
        statistic.add(livingCard);
        statistic.add(Box.createVerticalStrut(20));
        statistic.add(stayingCard);
        statistic.add(Box.createVerticalStrut(20));
        statistic.add(absentCard);

        filter("");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
        add(statistic, BorderLayout.EAST);
    }

    public void filter(String keyword) {
        ArrayList<String[]> filteredData = new ArrayList<>();
        int absent = 0, living = 0, staying = 0;

        selections = new ArrayList<>();

        for (Resident resident : residentList) {
            if (resident.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredData.add(resident.toData());
                selections.add(resident);

                switch (resident.getStatus()) {
                    case 0:
                        ++living;
                        break;
                    case 1:
                        ++staying;
                        break;
                    case 2:
                        ++absent;
                        break;
                    default:
                        break;
                }
            }
        }

        model.setDataVector(filteredData.toArray(new String[0][0]), header);
        absentCard.setContent("" + absent);
        livingCard.setContent("" + living);
        stayingCard.setContent("" + staying);
        totalCard.setContent("" + (absent + living + staying));
    }

    public ArrayList<Long> getSelectionsId() {
        ArrayList<Long> selections = new ArrayList<>();

        for (int i : table.getSelectedRows()) {
            selections.add(this.selections.get(i).getId());
        }

        return selections;
    }
    
    public ArrayList<Resident> getSelections() {
        ArrayList<Resident> selections = new ArrayList<>();

        for (int i : table.getSelectedRows()) {
            selections.add(this.selections.get(i));
        }

        return selections;
    }
}
