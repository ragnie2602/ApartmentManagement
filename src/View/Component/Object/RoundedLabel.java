package View.Component.Object;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

public class RoundedLabel extends JLabel {
    public RoundedLabel(String text) {
        super(text);
        setOpaque(false);
    }
    public RoundedLabel(Icon icon) {
        super(icon);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillOval(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
