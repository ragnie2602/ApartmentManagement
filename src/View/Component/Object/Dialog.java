package View.Component.Object;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Resources.Constant.Constant;
import Resources.Constant.Tool;

public class Dialog {
    private JButton okButton;
    private JFrame dialogFrame, prevFrame;

    public Dialog(JFrame prevFrame, int type, String message) {
        this.prevFrame = prevFrame;

        dialogFrame = new JFrame("Thông báo");
        dialogFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
                prevFrame.toFront();
            }
        });
        dialogFrame.setBackground(Color.WHITE);
        dialogFrame.setLayout(new GridLayout(5, 3));
        dialogFrame.setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 225, prevFrame.getY() + prevFrame.getHeight() / 2 - 100);
        dialogFrame.setSize(450, 200);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ok();
            }
        });

        dialogFrame.add(new JLabel()); // first line
        dialogFrame.add(new JLabel());
        dialogFrame.add(new JLabel());
        dialogFrame.add(new JLabel(Tool.resize(new ImageIcon(Constant.dialogIconPath[type]), 30, 30))); // second line
        dialogFrame.add(new JLabel(message));
        dialogFrame.add(new JLabel());
        dialogFrame.add(new JLabel()); // third line
        dialogFrame.add(new JLabel());
        dialogFrame.add(new JLabel());
        dialogFrame.add(new JLabel()); // fourth line
        dialogFrame.add(okButton);
        dialogFrame.add(new JLabel());

        dialogFrame.setVisible(true);
    }

    private void ok() {
        prevFrame.setEnabled(true);
        prevFrame.toFront();

        dialogFrame.setVisible(false);
    }
}
