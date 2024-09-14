package View.Component.Object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Resources.Constant.Constant;

public class StatisticCard extends RoundedPanel {
    Color startColor, endColor;
    RoundedLabel icon;
    JLabel content, title;

    public StatisticCard(String iconPath, String title, String content) {
        super(30);

        JPanel panel = new JPanel();

        this.content = new JLabel(content);
        this.content.setBackground(Color.WHITE);
        this.content.setFont(Constant.getTitleFont2(2).deriveFont((float)32));
        this.content.setForeground(new Color(51, 51, 51));

        icon = new RoundedLabel(new ImageIcon(Constant.image + iconPath));
        // icon.setBackground(new Color(211, 255, 231));
        icon.setBackground(Color.WHITE);

        this.title = new JLabel(title);
        this.title.setBackground(Color.WHITE);
        this.title.setFont(Constant.getTitleFont2(0).deriveFont((float)14));
        this.title.setForeground(new Color(51, 51, 51));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.add(this.title);
        panel.add(this.content);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(200, 112));
        setMaximumSize(new Dimension(200, 112));

        add(icon);
        add(panel);
    }

    public JLabel getContent() {return content;}
    public RoundedLabel getIconLabel() {return icon;}
    public JLabel getTitle() {return title;}

    public void setContent(String content)  {this.content.setText(content);}

    public void setGradient(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);

        g2d.setPaint(gradientPaint);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }
}
