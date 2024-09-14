package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.ApartmentCtrl;
import Controller.AuthCtrl;
import Controller.FeeCtrl;
import Controller.ResidentCtrl;
import Model.Resident;
import Model.User;
import Resources.Constant.Constant;
import View.Component.Display.AccountDisplay;
import View.Component.Display.ApartmentDisplay;
import View.Component.Display.FeeDisplay;
import View.Component.Display.ResidentDisplay;
import View.Component.Display.VehicleDisplay;
import View.Component.Object.Dialog;
import View.Component.Object.RoundedPanel;
import View.Component.Object.TextField;
import View.Page.Apartment.*;
import View.Page.Fee.AddFee;
import View.Page.Fee.EditFee;
import View.Page.Payment.AddPayment;
import View.Page.Payment.ShowPaymentList;
import View.Page.Resident.*;
import View.Page.Resident.ChangeStatus.Exchange;
import View.Page.Resident.ChangeStatus.GetInTemp;
import View.Page.Resident.ChangeStatus.GetOutTemp;
import View.Page.Vehicle.AddVehicle;
import View.Page.Vehicle.EditVehicle;

public class Home extends JFrame {
    AccountDisplay accountDisplay;
    FeeDisplay annualFeeDisplay, monthlyFeeDisplay, oneTimeFeeDisplay;
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();
    Integer mode = 0;
    JButton addApartment, deleteApartment, editApartment, showApartment,
            addResident, changeStatus, deleteResident, editResident, exchange, getInTemp, getOutTemp, historyStatus, addVehicle, deleteVehicle, editVehicle,
            addFee, deleteFee, editFee, pay, payList,
            changePassword, editAccount, signOut;
    RoundedPanel contentPanel, functionPanel;
    JPanel accountManagePanel = new JPanel(), feeManagePanel = new JPanel(), residentManagePanel = new JPanel(),
           accountContentPanel = new JPanel(), feeContentPanel = new JPanel(), residentContentPanel = new JPanel();
    JPopupMenu changeStatusPopupMenu = new JPopupMenu();
    JScrollPane residentScroll = new JScrollPane();
    JTabbedPane functionTabbedPane = new JTabbedPane(), feeTabbedPane = new JTabbedPane(), residentTabbedPane = new JTabbedPane();
    TextField apartmentSearchBox = new TextField(new ImageIcon(Constant.image + "search.png"), "Nhập từ khóa để tìm kiếm", 14),
              feeSearchBox = new TextField(new ImageIcon(Constant.image + "search.png"), "Nhập từ khóa để tìm kiếm", 14);
    User user;

    public Home(User user) {
        this.user = user;

        UIManager.put("Button.font", Constant.buttonFont.deriveFont((float)13.0));
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("ComboBox.font", Constant.contentFont);
        UIManager.put("Label.font", Constant.contentFont);
        UIManager.put("PopupMenu.Background", Color.WHITE);
        UIManager.put("TabbedPane.font", Constant.contentFont);
        UIManager.put("Table.font", Constant.getTitleFont2(0));
        UIManager.put("TableHeader.font", Constant.getTitleFont2(3));
        UIManager.put("TableHeader.foreground", new Color(131, 133, 142));
        UIManager.put("TextArea.font", Constant.contentFont);

        changePassword = new JButton(Constant.verticalImageTitle("changePassword.png", "Đổi mật khẩu"));
        changePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                changePassword();
            }
        });
        editAccount = new JButton(Constant.verticalImageTitle("editAccount.png", "Sửa thông tin tài khoản"));
        editAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editAccount();
            }
        });
        signOut = new JButton(Constant.verticalImageTitle("signOut.png", "Đăng xuất"));
        signOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                signOut();
            }
        });

        addApartment = new JButton(Constant.verticalImageTitle("addOwner.png", "Thêm chủ căn hộ"));
        addApartment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addApartment();
            }
        });
        apartmentSearchBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == '\n') {
                    if (residentTabbedPane.getSelectedComponent() instanceof ApartmentDisplay)
                        ((ApartmentDisplay)residentTabbedPane.getSelectedComponent()).filter(apartmentSearchBox.getText());
                    else if (residentTabbedPane.getSelectedComponent() instanceof ResidentDisplay)
                        ((ResidentDisplay)residentTabbedPane.getSelectedComponent()).filter(apartmentSearchBox.getText());
                }
            }
        });
        apartmentSearchBox.setBackground(new Color(245, 245, 245));
        apartmentSearchBox.setFont(Constant.contentFont);
        apartmentSearchBox.setForeground(Color.BLACK);
        apartmentSearchBox.setPreferredSize(new Dimension(300, 30));
        apartmentSearchBox.setRadius(10);
        deleteApartment = new JButton(Constant.verticalImageTitle("deleteOwner.png", "Xóa chủ căn hộ"));
        deleteApartment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteApartment();
            }
        });
        editApartment = new JButton(Constant.verticalImageTitle("editOwner.png", "Sửa chủ căn hộ"));
        editApartment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editApartment();
            }
        });
        showApartment = new JButton(Constant.verticalImageTitle("showApartment.png", "Thống kê nhân khẩu"));
        showApartment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                showApartment();
            }
        });

        addResident = new JButton(Constant.verticalImageTitle("addResident.png", "Thêm cư dân"));
        addResident.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addResident();
            }
        });
        deleteResident = new JButton(Constant.verticalImageTitle("deleteResident.png", "Xóa cư dân"));
        deleteResident.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteResident();
            }
        });
        editResident = new JButton(Constant.verticalImageTitle("editResident.png", "Sửa thông tin cư dân"));
        editResident.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editResident();
            }
        });
        exchange = new JButton("Chuyển hộ khẩu", new ImageIcon(Constant.image + "exchange.png"));
        exchange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                exchange();
            }
        });
        getInTemp = new JButton("  Đăng ký tạm trú", new ImageIcon(Constant.image + "getInTemp.png"));
        getInTemp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getInTempStatus();
            }
        });
        getOutTemp = new JButton("  Đăng ký tạm vắng", new ImageIcon(Constant.image + "getOutTemp.png"));
        getOutTemp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getOutTempStatus();
            }
        });

        changeStatusPopupMenu.setBackground(new Color(0, 0, 0, 0));
        changeStatusPopupMenu.setLayout(new GridLayout(1, 3));
        changeStatusPopupMenu.add(exchange);
        changeStatusPopupMenu.add(getInTemp);
        changeStatusPopupMenu.add(getOutTemp);
        changeStatus = new JButton(Constant.verticalImageTitle("changeStatus.png", "Thay đổi nhân khẩu"));
        changeStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                changeStatusPopupMenu.show(changeStatus, 0, changeStatus.getHeight());
            }
        });
        historyStatus = new JButton(Constant.verticalImageTitle("historyStatus.png", "Lịch sử cư trú"));
        historyStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                historyStatus();
            }
        });

        addVehicle = new JButton(Constant.verticalImageTitle("addVehicle.png", "Thêm phương tiện"));
        addVehicle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addVehicle();
            }
        });
        deleteVehicle = new JButton(Constant.verticalImageTitle("deleteVehicle.png", "Xóa phương tiện"));
        deleteVehicle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteVehicle();
            }
        });
        editVehicle = new JButton(Constant.verticalImageTitle("editVehicle.png", "Sửa phương tiện"));
        editVehicle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editVehicle();
            }
        });

        addFee = new JButton(Constant.verticalImageTitle("addFee.png", "Thêm loại phí"));
        addFee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addFee();
            }
        });
        deleteFee = new JButton(Constant.verticalImageTitle("deleteFee.png", "Xóa loại phí"));
        deleteFee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteFee();
            }
        });
        editFee = new JButton(Constant.verticalImageTitle("editFee.png", "Sửa loại phí"));
        editFee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editFee();
            }
        });
        feeSearchBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() == '\n') {
                    ((FeeDisplay)feeTabbedPane.getSelectedComponent()).filter(feeSearchBox.getText());
                }
            }
        });
        feeSearchBox.setBackground(new Color(245, 245, 245));
        feeSearchBox.setFont(Constant.contentFont);
        feeSearchBox.setPreferredSize(new Dimension(300, 30));
        feeSearchBox.setRadius(10);

        pay = new JButton(Constant.verticalImageTitle("pay.png", "Nộp phí"));
        pay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addPayment();
            }
        });
        payList = new JButton(Constant.verticalImageTitle("payList.png", "Danh sách đã nộp phí"));
        payList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                showPaymentList();
            }
        });

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        getContentPane().setBackground(new Color(161, 225, 255)); // OldColor: 211, 255, 231
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(gb);
        setSize(1000, 500);
        setTitle("Quản lý chung cư BlueMoon");

        function();
        content();

        setVisible(true);
    };

    private void accountManage() {
        accountManagePanel.setBackground(Color.WHITE);
        accountManagePanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        accountManagePanel.add(editAccount);
        accountManagePanel.add(changePassword);
        accountManagePanel.add(signOut);
    }
    private void feeManage() {
        feeManagePanel.setBackground(Color.WHITE);
        feeManagePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        feeManagePanel.add(addFee);
        feeManagePanel.add(editFee);
        feeManagePanel.add(deleteFee);
        feeManagePanel.add(pay);
        feeManagePanel.add(payList);
    }
    private void residentManage() {
        residentManagePanel.setBackground(Color.WHITE);
        residentManagePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        residentManagePanel.removeAll();
        if (mode == 0) {
            residentManagePanel.add(addApartment);
            residentManagePanel.add(editApartment);
            residentManagePanel.add(deleteApartment);
            residentManagePanel.add(showApartment);
        } else if (mode == 1) {
            residentManagePanel.add(addResident);
            residentManagePanel.add(editResident);
            residentManagePanel.add(deleteResident);
            residentManagePanel.add(changeStatus);
            residentManagePanel.add(historyStatus);
        } else {
            residentManagePanel.add(addVehicle);
            residentManagePanel.add(editVehicle);
            residentManagePanel.add(deleteVehicle);
        }

        residentManagePanel.revalidate();
        residentManagePanel.repaint();
    }

    private void accountContent() {
        accountDisplay = new AccountDisplay(user);

        contentPanel.removeAll();
        contentPanel.add(accountDisplay);
    }
    private void feeContent() {
        JLabel label, label1, label2, label3;
        JPanel panel = new JPanel(new GridBagLayout());

        label = new JLabel("Danh sách loại phí", JLabel.LEFT);
        label.setBackground(Color.WHITE);
        label.setFont(Constant.getTitleFont2(2).deriveFont((float)22));
        label.setPreferredSize(new Dimension(100, 25));

        label1 = new JLabel("Một lần", JLabel.CENTER);
        label1.setPreferredSize(new Dimension(100, 30));
        label2 = new JLabel("Hàng tháng", JLabel.CENTER);
        label2.setPreferredSize(new Dimension(100, 30));
        label3 = new JLabel("Thường niên", JLabel.CENTER);
        label3.setPreferredSize(new Dimension(100, 30));

        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1; panel.add(label, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; panel.add(feeSearchBox, gbc);

        feeTabbedPane.addTab("Một lần", new FeeDisplay(0));
        feeTabbedPane.addTab("Hàng tháng", null);
        feeTabbedPane.addTab("Thường niên", null);
        feeTabbedPane.setTabComponentAt(0, label1);
        feeTabbedPane.setTabComponentAt(1, label2);
        feeTabbedPane.setTabComponentAt(2, label3);

        feeTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if (feeTabbedPane.getSelectedIndex() == 0) {
                    feeTabbedPane.setComponentAt(0, new FeeDisplay(0));
                    feeTabbedPane.setComponentAt(1, null);
                    feeTabbedPane.setComponentAt(2, null);
                } else if (feeTabbedPane.getSelectedIndex() == 1) {
                    feeTabbedPane.setComponentAt(0, null);
                    feeTabbedPane.setComponentAt(1, new FeeDisplay(1));
                    feeTabbedPane.setComponentAt(2, null);
                } else {
                    feeTabbedPane.setComponentAt(0, null);
                    feeTabbedPane.setComponentAt(1, null);
                    feeTabbedPane.setComponentAt(2, new FeeDisplay(2));
                }

                revalidate();
                repaint();
            }
        });

        feeContentPanel.setBackground(Color.WHITE);
        feeContentPanel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 1.0; feeContentPanel.add(panel, gbc);
        gbc.fill = GridBagConstraints.BOTH; gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1.0; gbc.weighty = 100.0; feeContentPanel.add(feeTabbedPane, gbc);
    }
    private void residentContent() {
        JLabel label, label1, label2, label3;
        JPanel panel = new JPanel(new GridBagLayout());

        label = new JLabel("Danh sách căn hộ", JLabel.LEFT);
        label.setBackground(Color.WHITE);
        label.setFont(Constant.getTitleFont2(2).deriveFont((float)22));
        label.setPreferredSize(new Dimension(100, 25));

        label1 = new JLabel("Căn hộ", JLabel.CENTER);
        label1.setPreferredSize(new Dimension(100, 30));
        label2 = new JLabel("Cư dân", JLabel.CENTER);
        label2.setPreferredSize(new Dimension(100, 30));
        label3 = new JLabel("Phương tiện", JLabel.CENTER);
        label3.setPreferredSize(new Dimension(100, 30));

        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1; panel.add(label, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; panel.add(apartmentSearchBox, gbc);

        residentTabbedPane.addTab("Căn hộ", new ApartmentDisplay());
        residentTabbedPane.addTab("Cư dân", null);
        residentTabbedPane.addTab("Phương tiện", null);
        residentTabbedPane.setTabComponentAt(0, label1);
        residentTabbedPane.setTabComponentAt(1, label2);
        residentTabbedPane.setTabComponentAt(2, label3);

        residentTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if (residentTabbedPane.getSelectedIndex() == 0) {
                    residentTabbedPane.setComponentAt(0, new ApartmentDisplay());
                    residentTabbedPane.setComponentAt(1, null);
                    residentTabbedPane.setComponentAt(2, null);

                    label.setText("Danh sách căn hộ");
                } else if (residentTabbedPane.getSelectedIndex() == 1) {
                    residentTabbedPane.setComponentAt(0, null);
                    residentTabbedPane.setComponentAt(1, new ResidentDisplay());
                    residentTabbedPane.setComponentAt(2, null);

                    label.setText("Danh sách cư dân");
                } else {
                    residentTabbedPane.setComponentAt(0, null);
                    residentTabbedPane.setComponentAt(1, null);
                    residentTabbedPane.setComponentAt(2, new VehicleDisplay());

                    label.setText("Danh sách phương tiện");
                }
            }
        });

        residentContentPanel.setBackground(Color.WHITE);
        residentContentPanel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH; gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 1.0; residentContentPanel.add(panel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1.0; gbc.weighty = 100.0; residentContentPanel.add(residentTabbedPane, gbc);
    }

    private void content() {
        contentPanel = new RoundedPanel(30);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        accountContent();
        feeContent();
        residentContent();

        residentTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                mode = residentTabbedPane.getSelectedIndex();
                residentManage();
            }
        });

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1; gbc.weighty = 1000; gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH; contentPanel.add(accountContentPanel, gbc);
        gbc.anchor = GridBagConstraints.NORTH; add(contentPanel, gbc);
    }

    private void function() {
        JLabel label1, label2, label3;

        label1 = new JLabel("Trang chủ", JLabel.CENTER);
        label1.setBackground(Color.WHITE);
        label1.setPreferredSize(new Dimension(75, 30));

        label2 = new JLabel("Quản lý cư dân", JLabel.CENTER);
        label2.setPreferredSize(new Dimension(100, 30));

        label3 = new JLabel("Quản lý phí thu", JLabel.CENTER);
        label3.setPreferredSize(new Dimension(100, 30));   

        functionPanel = new RoundedPanel(30);
        functionPanel.setBackground(Color.WHITE);
        functionPanel.setLayout(new BorderLayout());

        accountManage();
        feeManage();
        residentManage();

        functionTabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        functionTabbedPane.addTab("Trang chủ", accountManagePanel);
        functionTabbedPane.addTab("Quản lý cư dân", residentManagePanel);
        functionTabbedPane.addTab("Quản lý phí thu", feeManagePanel);
        functionTabbedPane.setTabComponentAt(0, label1);
        functionTabbedPane.setTabComponentAt(1, label2);
        functionTabbedPane.setTabComponentAt(2, label3);

        functionTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if (functionTabbedPane.getSelectedIndex() == 0) {
                    contentPanel.removeAll();
                    
                    accountContent();
                    gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.BOTH;
                    contentPanel.add(accountContentPanel, gbc);

                    revalidate();
                    repaint();
                } else if (functionTabbedPane.getSelectedIndex() == 1) {
                    contentPanel.removeAll();

                    gbc.anchor = GridBagConstraints.NORTH; gbc.fill = GridBagConstraints.BOTH;
                    contentPanel.add(residentContentPanel, gbc);
                    
                    revalidate();
                    repaint();
                } else {
                    contentPanel.removeAll();

                    gbc.anchor = GridBagConstraints.NORTH; gbc.fill = GridBagConstraints.BOTH;
                    contentPanel.add(feeContentPanel, gbc);
                    
                    revalidate();
                    repaint();
                }
            }
        });
        functionPanel.add(functionTabbedPane, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1; gbc.weighty = 100; gbc.insets = new Insets(10, 10, 10, 10); add(functionPanel, gbc);
    }

    public JFrame getFrame() {return this;}
    public JTabbedPane getFeeTabbedPane() {return feeTabbedPane;}

    public void setApartmentDisplay(ApartmentDisplay apartmentDisplay) {residentTabbedPane.setComponentAt(0, apartmentDisplay);}
    public void setResidentDisplay(ResidentDisplay residentDisplay) {residentTabbedPane.setComponentAt(1, residentDisplay);}
    public void setVehicleDisplay(VehicleDisplay vehicleDisplay) {residentTabbedPane.setComponentAt(2, vehicleDisplay);}

    private void addApartment() {
        setEnabled(false);
        new AddApartment(this, user);
    }
    private void deleteApartment() {
        if (ApartmentCtrl.deleteApartment(((ApartmentDisplay)residentTabbedPane.getSelectedComponent()).getSelections())) {
            new Dialog(this, 2, "Thành công");
            residentTabbedPane.setComponentAt(0, new ApartmentDisplay());
        } else {
            new Dialog(this, 0, "Thất bại");
        }
    }
    private void editApartment() {
        ArrayList<Integer> selections = ((ApartmentDisplay)residentTabbedPane.getSelectedComponent()).getSelections();
        if (selections.size() == 1) {
            setEnabled(false);
            new EditApartment(this, user, selections.get(0));
        } else {
            new Dialog(this, 0, "Chỉ được chọn 1 mục!");
        }
    }
    private void showApartment() {
        ArrayList<Integer> selections = ((ApartmentDisplay)residentTabbedPane.getSelectedComponent()).getSelections();
        if (selections.size() == 1) {
            setEnabled(false);
            new ShowApartment(this, selections.get(0));
        } else {
            new Dialog(this, 0, "Chỉ được chọn 1 mục!");
        }
    }

    private void addFee() {
        setEnabled(false);
        new AddFee(this, user);
    }
    private void deleteFee() {
        int selected = feeTabbedPane.getSelectedIndex();
        if (FeeCtrl.deleteFee(((FeeDisplay)feeTabbedPane.getSelectedComponent()).getSelections())) {
            new Dialog(this, 2, "Thành công");
            feeTabbedPane.setComponentAt(selected, new FeeDisplay(selected));
        } else {
            new Dialog(this, 0, "Thất bại");
        }
    }
    private void editFee() {
        ArrayList<Integer> selections = ((FeeDisplay)feeTabbedPane.getSelectedComponent()).getSelections();
        if (selections.size() == 1) {
            setEnabled(false);
            new EditFee(this, user, selections.get(0));
        } else {
            new Dialog(this, 0, "Chỉ được chọn 1 mục!");
        }
    }

    private void addPayment() {
        setEnabled(false);
        new AddPayment(this, user);
    }
    private void showPaymentList() {
        ArrayList<Integer> selections = ((FeeDisplay)feeTabbedPane.getSelectedComponent()).getSelections();
        if (selections.size() == 1) {
            setEnabled(false);
            new ShowPaymentList(this, selections.get(0));
        } else {
            new Dialog(this, 0, "Chỉ được chọn 1 mục!");
        }
    }

    private void addResident() {
        setEnabled(false);
        new AddResident(this, user);
    }
    private void deleteResident() {
        ArrayList<Resident> selections = ((ResidentDisplay)residentTabbedPane.getSelectedComponent()).getSelections();
        if (selections.size() > 0) {
            if (ResidentCtrl.deleteResident(selections)) {
                residentTabbedPane.setComponentAt(0, new ApartmentDisplay());
                residentTabbedPane.setComponentAt(1, new ResidentDisplay());
                new Dialog(this, 2, "Thành công");
            } else {
                new Dialog(this, 0, "Thất bại");
            }
        } else {
            new Dialog(this, 0, "Phải chọn ít nhất 1 mục");
        }
    }
    private void editResident() {
        ArrayList<Long> selections = ((ResidentDisplay)residentTabbedPane.getSelectedComponent()).getSelectionsId();
        if (selections.size() == 1) {
            setEnabled(false);
            new EditResident(this, user, selections.get(0));
        } else {
            new Dialog(this, 0, "Phải chọn 1 cư dân!");
        }
    }
    private void exchange() {
        setEnabled(false);
        new Exchange(this);
    }
    private void getInTempStatus() {
        setEnabled(false);
        new GetInTemp(this, user);
    }
    private void getOutTempStatus() {
        ArrayList<Long> selections = ((ResidentDisplay)residentTabbedPane.getSelectedComponent()).getSelectionsId();
        if (selections.size() == 1) {
            setEnabled(false);
            new GetOutTemp(this, user, selections.get(0));
        } else {
            new Dialog(this, 0, "Phải chọn 1 cư dân!");
        }
    }
    private void historyStatus() {
        ArrayList<Long> selections = ((ResidentDisplay)residentTabbedPane.getSelectedComponent()).getSelectionsId();
        if (selections.size() == 1) {
            setEnabled(false);
            new ShowHistory(this, selections.get(0));
        } else {
            new Dialog(this, 0, "Phải chọn 1 cư dân!");
        }
    }

    private void addVehicle() {
        setEnabled(false);
        new AddVehicle(this, user);
    }
    private void deleteVehicle() {
        ArrayList<String> selections = ((VehicleDisplay)residentTabbedPane.getSelectedComponent()).getSelections();

        if (selections.size() > 0) {
            if (ApartmentCtrl.deleteVehicle(selections)) {
                residentTabbedPane.setComponentAt(2, new VehicleDisplay());
                new Dialog(this, 2, "Thành công");
            } else {
                new Dialog(this, 0, "Thất bại");
            }
        } else {
            new Dialog(this, 0, "Phải chọn ít nhất 1 mục");
        }
    }
    private void editVehicle() {
        ArrayList<String> selections = ((VehicleDisplay)residentTabbedPane.getSelectedComponent()).getSelections();
        if (selections.size() == 1) {
            setEnabled(false);
            new EditVehicle(this, user, selections.get(0));
        } else {
            new Dialog(this, 0, "Chỉ được chọn 1 mục!");
        }
    }

    private void changePassword() {
        setEnabled(false);
        new ChangePassword(this, user);
    }
    private void editAccount() {
        accountDisplay.turnEditModeOn();
    }
    private void signOut() {AuthCtrl.signOut(this);}
}
