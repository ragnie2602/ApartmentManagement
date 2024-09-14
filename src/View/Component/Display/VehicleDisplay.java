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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Controller.ApartmentCtrl;
import Model.Vehicle;
import Resources.Constant.Constant;
import View.Component.Object.StatisticCard;
public class VehicleDisplay extends JPanel {
    ArrayList<Vehicle> vehicles;
    DefaultTableModel model;
    JTable table;
    StatisticCard carCard, motorCard;
    String[] header = {"Tầng", "Phòng", "Biển số xe", "Loại phương tiện"};
    Object[][] data;

    public VehicleDisplay() {
        UIManager.put("Table.font", Constant.getTitleFont2(0));
        UIManager.put("TableHeader.font", Constant.getTitleFont2(2));

        JPanel statistic = new JPanel();

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 0));

        carCard = new StatisticCard("ic_car.png", "Ô tô", "");
        carCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        carCard.setGradient(new Color(255, 156, 236, 100), new Color(255, 230, 250, 100));
        
        motorCard = new StatisticCard("ic_motor.png", "Xe máy", "");
        motorCard.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        motorCard.setGradient(new Color(52, 0, 241, 100), new Color(184, 156, 255, 100));

        vehicles = ApartmentCtrl.getVehicle();

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
        statistic.add(motorCard);
        statistic.add(Box.createVerticalStrut(20));
        statistic.add(carCard);

        filter("");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
        add(statistic, BorderLayout.EAST);
    }

    public void filter(String keyword) {
        ArrayList<Object[]> filteredData = new ArrayList<>();
        int car = 0, motorbike = 0;

        for (Vehicle vehicle : vehicles) {
            filteredData.add(vehicle.toData());
            if (vehicle.getType() == 0) {++motorbike;}
            else {++car;}
        }

        model.setDataVector(filteredData.toArray(new Object[0][0]), header);
        carCard.setContent("" + car);
        motorCard.setContent("" + motorbike);

        this.revalidate();
        this.repaint();
    }

    public ArrayList<String> getSelections() {
        ArrayList<String> selections = new ArrayList<>();

        for (int i : table.getSelectedRows()) {
            selections.add(table.getValueAt(i, 2).toString());
        }

        return selections;
    }
}