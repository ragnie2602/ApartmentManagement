package View.Component.Object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Resident;
import Resources.Constant.Constant;

public class ResidentCard2 extends JPanel {
    boolean isOwner;
    GridBagConstraints gbc = new GridBagConstraints();
    Resident resident;

    public ResidentCard2(Resident resident, boolean isOwner) {
        this.isOwner = isOwner;
        this.resident = resident;

        setLayout(new GridBagLayout());
        setMaximumSize(new Dimension(600, 350));

        JPanel information = new JPanel(new GridLayout(4,2));
        information.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        information.add(new JLabel("Tên: " + resident.getName()));
        information.add(new JLabel());
        information.add(new JLabel("Ngày sinh: " + resident.getBirthday().toString()));
        information.add(new JLabel("Số điện thoại: " + resident.getPhoneNumber()));
        information.add(new JLabel("Căn hộ: " + (resident.getFloor() * 100 + resident.getRoom())));
        information.add(new JLabel("Giới tính: " + (resident.getGender() ? "Nữ": "Nam")));
        information.add(new JLabel("Mối quan hệ với chủ hộ: " + resident.getRelationship()));
        information.setOpaque(false);

        for (Component c : information.getComponents()) {
            ((JLabel)c).setOpaque(false);
        }

        ((JLabel)information.getComponent(0)).setFont(Constant.titleFont);
        
        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 25; gbc.weighty = 1; add(information, gbc);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color startColor = new Color(isOwner == true ? 0xFFFDD017 : 0xFF8DD3D1), endColor = new Color(0xFFFDFFF5);
        Dimension arcs = new Dimension(15, 15);
        Graphics2D g2d = (Graphics2D)g;
        int h = getHeight(), w = getWidth();
        
        // Set gradient color
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, w, h, endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRoundRect(0, 0, w - 1, h - 1, arcs.width, arcs.height);

        // Set rounded border
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRoundRect(0, 0, w - 1, h - 1, arcs.width, arcs.height);
        g2d.setColor(Color.GRAY);
        g2d.drawRoundRect(0, 0, w - 1, h - 1, arcs.width, arcs.height);
    }
}
