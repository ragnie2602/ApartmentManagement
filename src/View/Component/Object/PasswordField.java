package View.Component.Object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

import Resources.Constant.Constant;

public class PasswordField extends JPasswordField {
    private ImageIcon icon;
    private String hint;

    public PasswordField() {
        super();

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }
    public PasswordField(ImageIcon icon) {
        super();
        this.icon = icon;

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }
    public PasswordField(String hint) {
        super();
        this.hint = hint;

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }
    public PasswordField(ImageIcon icon, String hint) {
        super();
        this.hint = hint;
        this.icon = icon;

        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int height = getHeight(), width = getWidth();

        if (!isOpaque()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fillRoundRect(0, 0, width, height, 24, 24);
            g2.dispose();
        }

        super.paintComponent(g);

        if (icon != null) {
            g.drawImage(icon.getImage(), width - icon.getIconWidth() - 10, (height - icon.getIconHeight()) / 2, this);
        }
        if (hint != null) {
            if (this.getPassword().length == 0) {
                Graphics2D g2d = (Graphics2D)g.create();

                g2d.setColor(Color.GRAY);
                g2d.setFont(Constant.hintFont.deriveFont((float)16));
                g2d.drawString(hint, 10, height / 2 + 7);
                g2d.dispose();
            }
        }
    }
}
