package View.Page.Apartment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controller.ApartmentCtrl;
import Model.Resident;
import Resources.Constant.Constant;
import View.Component.Object.ResidentCard;

public class ShowApartment extends JFrame {
    ArrayList<Resident> members;
    Resident owner;

    public ShowApartment(JFrame prevFrame, Integer id) {
        JLabel label1 = new JLabel("Danh sách cư dân căn hộ phòng " + id, JLabel.CENTER), label2, label3;
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();

        members = ApartmentCtrl.getMembers(id);
        owner = ApartmentCtrl.getOwner(id / 100, id % 100);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.revalidate();
                prevFrame.repaint();
                prevFrame.setEnabled(true);
            }
        });
        
        setLayout(new BorderLayout(20, 10));
        setLocation(prevFrame.getWidth() / 2 - 500, prevFrame.getHeight() / 2 - 350);
        setSize(new Dimension(1000, 700));
        setTitle("Danh sách cư dân căn hộ " + id);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panel.setLayout(new GridLayout(members.size() / 2 + 3, 2, 20, 10));

        label1.setFont(Constant.getTitleFont2(2).deriveFont((float)24.0));
        label2 = new JLabel("___________________Cư dân", JLabel.RIGHT);
        label3 = new JLabel("(" + members.size() + ")____________________");
        label2.setFont(Constant.getHintFont(1).deriveFont((float)18));
        label3.setFont(label2.getFont());

        panel.add(new ResidentCard(owner, true));
        panel.add(new JLabel());
        panel.add(label2);
        panel.add(label3);        
        for (Resident r : members) {
            panel.add(new ResidentCard(r, false));
        }

        scrollPane.setViewportView(panel);

        add(label1, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
