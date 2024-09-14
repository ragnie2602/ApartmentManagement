package View.Page.Resident.ChangeStatus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controller.ResidentCtrl;
import Model.Activity;
import Model.Resident;
import Model.User;
import Resources.Constant.Constant;
import Resources.Constant.Tool;
import View.Home;
import View.Component.Display.ResidentDisplay;
import View.Component.Object.Dialog;

public class GetOutTemp {
    JButton cancelButton, verifyButton;
    JFrame getOutTempFrame, prevFrame;
    JLabel notifyLabel;
    JPanel contentPanel = new JPanel(), functionPanel = new JPanel();
    JTextArea reason;
    User user;

    public GetOutTemp(JFrame prev, User user, Long currentId) {
        this.prevFrame = prev;
        this.user = user;

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Đăng ký tạm vắng", JLabel.CENTER);
        Resident resident = ResidentCtrl.getResident(currentId);

        getOutTempFrame = new JFrame("Sửa thông tin cư dân");
        getOutTempFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                prevFrame.setEnabled(true);
                prevFrame.toFront();
            }
        });
        getOutTempFrame.setBackground(Color.WHITE);
        getOutTempFrame.setLayout(new BorderLayout());
        getOutTempFrame.setLocation(prevFrame.getX() + prevFrame.getWidth() / 2 - 400, prevFrame.getY() + prevFrame.getHeight() / 2 - 180);
        getOutTempFrame.setSize(800, 360);

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(Constant.buttonFont);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });

        notifyLabel = new JLabel("", JLabel.LEFT);
        notifyLabel.setFont(Constant.notifyFont);
        if (resident.getStatus() == 1) {
            notifyLabel.setIcon(Tool.resize(new ImageIcon(Constant.image + "alert.png"), 15, 15));
            notifyLabel.setText("<html>Cư dân này hiện đang tạm trú ở chung cư.<br>Việc thực hiện đăng ký tạm vắng cho cư dân này sẽ tương ứng với rời khỏi chung cư.</html>");
        }

        reason = new JTextArea();
        reason.setLineWrap(true);
        reason.setRows(5);

        verifyButton = new JButton("Thêm");
        verifyButton.setFont(Constant.buttonFont);
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                verify(resident);
            }
        });

        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.NORTH; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 5, 0, 15);
        gbc.gridx = 0; gbc.weightx = 2; gbc.weighty = 5;
        gbc.gridy = 0; contentPanel.add(new JLabel("Lý do (nếu có)", JLabel.RIGHT), gbc);

        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.BOTH; 
        gbc.gridx = 1; gbc.weightx = 5;
        gbc.gridy = 0; gbc.weighty = 5; contentPanel.add(new JScrollPane(reason), gbc);
        gbc.gridy = 1; gbc.weighty = 1; contentPanel.add(notifyLabel, gbc);

        functionPanel.setLayout(new GridLayout(2, 5));
        functionPanel.add(new JLabel());
        functionPanel.add(cancelButton);
        functionPanel.add(new JLabel());
        functionPanel.add(verifyButton);
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());
        functionPanel.add(new JLabel());

        label.setFont(Constant.titleFont.deriveFont((float)18.0));

        getOutTempFrame.add(label, BorderLayout.NORTH);
        getOutTempFrame.add(contentPanel, BorderLayout.CENTER);
        getOutTempFrame.add(functionPanel, BorderLayout.SOUTH);

        getOutTempFrame.setVisible(true);
    }

    private void cancel() {
        getOutTempFrame.setVisible(false);
        prevFrame.setEnabled(true);
        prevFrame.toFront();
    }

    private void verify(Resident resident) {
        ArrayList<Resident> temp = new ArrayList<>();
        temp.add(resident);
        
        if (resident.getStatus() == 1) {
            if (ResidentCtrl.deleteResident(temp)) {
                getOutTempFrame.setVisible(false);
                prevFrame.toFront();
                new Dialog(prevFrame, 2, "Thành công");
            } else {
                new Dialog(getOutTempFrame, 0, "Thất bại");
            }
        } else {
            if (ResidentCtrl.addActivity(new Activity(Long.valueOf(resident.getId()), 1, new Date(System.currentTimeMillis()), null, reason.getText()))) {
                getOutTempFrame.setVisible(false);
                prevFrame.toFront();
                new Dialog(prevFrame, 2, "Thành công");
            } else {
                new Dialog(prevFrame, 0, "Thất bại");
            }
        }

        ((Home)prevFrame).setResidentDisplay(new ResidentDisplay());
    }
}
