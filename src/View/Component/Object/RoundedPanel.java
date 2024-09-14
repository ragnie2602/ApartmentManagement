package View.Component.Object;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int radius;
    private int shadowX, shadowY, shadowExtended;

    public RoundedPanel(int radius) {
        super();
        this.radius = radius;
        setOpaque(false);
    }

    public void setShadow(int x, int y, int shadowExtended) {
        this.shadowX = x;
        this.shadowY = y;
        this.shadowExtended = shadowExtended;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth() - 1;
        int height = getHeight() - 1;

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow
        graphics.setColor(new Color(0, 0, 0, 21));
        graphics.fillRoundRect(shadowX, shadowY, width + shadowExtended, height + shadowExtended, radius, radius);

        // Rounded
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width, height, radius, radius);
    }
}
