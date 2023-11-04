/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.KhachHang;
import app.service.KhachHang_Service;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class CustomerPanel extends javax.swing.JPanel {

    KhachHang_Service service = new KhachHang_Service();
    int row = -1;

    public CustomerPanel() {
        initComponents();
        filltable(service.getAll());

    }

    void filltable(List<KhachHang> lst) {
        DefaultTableModel model = (DefaultTableModel) tblListCustomer.getModel();
        model.setRowCount(0);
        for (KhachHang khachHang : lst) {
            model.addRow(khachHang.toDaTaRow());
        }

    }

    void showdata(int index) {
        KhachHang kh = service.getAll().get(index);
         txtCode.setText(String.valueOf(kh.getId()));
        txtFullname.setText(kh.getFULLNAME());
        txtEmail.setText(kh.getEMAIL());
       txtPhoneNumber.setText(kh.getPHONE_NUMBER());
        txtAddress.setText(kh.getAddress());
        txtDate.setText(String.valueOf(kh.getBIRTHDATE()));
    }

    KhachHang reafrom() {
        KhachHang cd = new KhachHang();
        cd.setFULLNAME(txtFullname.getText());
        cd.setEMAIL(txtEmail.getText());
        cd.setPHONE_NUMBER(txtPhoneNumber.getText());
        cd.setAddress(txtAddress.getText());
cd.setBIRTHDATE(txtDate.getText());
        return cd;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCustomerInfor = new javax.swing.JPanel();
        txtPhoneNumber = new app.view.swing.TextField();
        txtEmail = new app.view.swing.TextField();
        txtAddress = new app.view.swing.TextField();
        btnUpdate = new app.view.swing.ButtonGradient();
        btnAdd = new app.view.swing.ButtonGradient();
        txtFullname = new app.view.swing.TextField();
        labelNameError = new javax.swing.JLabel();
        labelPhoneNumberError = new javax.swing.JLabel();
        labelEmailError = new javax.swing.JLabel();
        labelAddressError = new javax.swing.JLabel();
        txtDate = new app.view.swing.TextField();
        labelDateError = new javax.swing.JLabel();
        txtCode = new app.view.swing.TextField();
        labelNameError1 = new javax.swing.JLabel();
        panelCustomerBought = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayProductBought = new javax.swing.JTable();
        panelListCustomer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListCustomer = new javax.swing.JTable();
        labelPanelCustomer = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        panelCustomerInfor.setBackground(new java.awt.Color(255, 255, 255));
        panelCustomerInfor.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));

        txtPhoneNumber.setLabelText("Số Điện Thoại");

        txtEmail.setLabelText("Email");

        txtAddress.setLabelText("Địa Chỉ");

        btnUpdate.setText("Sửa");
        btnUpdate.setColor2(new java.awt.Color(23, 35, 51));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnAdd.setText("Thêm");
        btnAdd.setColor2(new java.awt.Color(23, 35, 51));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtFullname.setLabelText("Họ Và Tên");

        labelNameError.setForeground(new java.awt.Color(255, 0, 51));
        labelNameError.setPreferredSize(new java.awt.Dimension(0, 14));

        labelPhoneNumberError.setForeground(new java.awt.Color(255, 0, 51));
        labelPhoneNumberError.setPreferredSize(new java.awt.Dimension(0, 14));

        labelEmailError.setForeground(new java.awt.Color(255, 0, 51));
        labelEmailError.setPreferredSize(new java.awt.Dimension(0, 14));

        labelAddressError.setForeground(new java.awt.Color(255, 0, 0));
        labelAddressError.setPreferredSize(new java.awt.Dimension(0, 14));

        txtDate.setLabelText("Ngày Sinh");

        labelDateError.setForeground(new java.awt.Color(255, 0, 51));
        labelDateError.setPreferredSize(new java.awt.Dimension(0, 14));

        txtCode.setEditable(false);
        txtCode.setLabelText("Mã");

        labelNameError1.setForeground(new java.awt.Color(255, 0, 51));
        labelNameError1.setPreferredSize(new java.awt.Dimension(0, 14));

        javax.swing.GroupLayout panelCustomerInforLayout = new javax.swing.GroupLayout(panelCustomerInfor);
        panelCustomerInfor.setLayout(panelCustomerInforLayout);
        panelCustomerInforLayout.setHorizontalGroup(
            panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtFullname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelDateError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelNameError1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
                        .addComponent(labelAddressError, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createSequentialGroup()
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPhoneNumberError, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))))
        );
        panelCustomerInforLayout.setVerticalGroup(
            panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createSequentialGroup()
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(labelPhoneNumberError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNameError1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDateError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNameError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelAddressError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        panelCustomerBought.setBackground(new java.awt.Color(255, 255, 255));
        panelCustomerBought.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản Phẩm Khách Hàng Đã Mua"));

        tblDisplayProductBought.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDisplayProductBought);

        javax.swing.GroupLayout panelCustomerBoughtLayout = new javax.swing.GroupLayout(panelCustomerBought);
        panelCustomerBought.setLayout(panelCustomerBoughtLayout);
        panelCustomerBoughtLayout.setHorizontalGroup(
            panelCustomerBoughtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerBoughtLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        panelCustomerBoughtLayout.setVerticalGroup(
            panelCustomerBoughtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerBoughtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelListCustomer.setBackground(new java.awt.Color(255, 255, 255));
        panelListCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Khách Hàng"));

        tblListCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Họ và tên", "email", "sdt", "địa chỉ", "ngày sinh"
            }
        ));
        tblListCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblListCustomer);

        javax.swing.GroupLayout panelListCustomerLayout = new javax.swing.GroupLayout(panelListCustomer);
        panelListCustomer.setLayout(panelListCustomerLayout);
        panelListCustomerLayout.setHorizontalGroup(
            panelListCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelListCustomerLayout.setVerticalGroup(
            panelListCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        labelPanelCustomer.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        labelPanelCustomer.setText("Quản Lý Khách Hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(580, 580, 580)
                .addComponent(labelPanelCustomer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(panelCustomerInfor, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCustomerBought, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPanelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCustomerInfor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCustomerBought, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        KhachHang cd = reafrom();
        if (service.addSach(cd) > 0) {
            JOptionPane.showMessageDialog(this, "them thanh cong");
            filltable(service.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "them that bai");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        row = tblListCustomer.getSelectedRow();
        KhachHang cd = reafrom();
        int ma = Integer.parseInt(tblListCustomer.getValueAt(row, 0).toString());
        if (service.updateSV(cd, ma) > 0) {
            JOptionPane.showMessageDialog(this, "update thanh cong");
            filltable(service.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "update that bai");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblListCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListCustomerMouseClicked
        row = tblListCustomer.getSelectedRow();
        showdata(row);
    }//GEN-LAST:event_tblListCustomerMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.ButtonGradient btnAdd;
    private app.view.swing.ButtonGradient btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAddressError;
    private javax.swing.JLabel labelDateError;
    private javax.swing.JLabel labelEmailError;
    private javax.swing.JLabel labelNameError;
    private javax.swing.JLabel labelNameError1;
    private javax.swing.JLabel labelPanelCustomer;
    private javax.swing.JLabel labelPhoneNumberError;
    private javax.swing.JPanel panelCustomerBought;
    private javax.swing.JPanel panelCustomerInfor;
    private javax.swing.JPanel panelListCustomer;
    private javax.swing.JTable tblDisplayProductBought;
    private javax.swing.JTable tblListCustomer;
    private app.view.swing.TextField txtAddress;
    private app.view.swing.TextField txtCode;
    private app.view.swing.TextField txtDate;
    private app.view.swing.TextField txtEmail;
    private app.view.swing.TextField txtFullname;
    private app.view.swing.TextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
