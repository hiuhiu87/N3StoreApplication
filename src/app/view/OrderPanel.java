/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.OderDetail;
import app.model.Oders;
import app.service.OderDetailService;
import app.service.OderService;
import app.view.swing.EventPagination;
import app.view.swing.PaginationItemRenderStyle1;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class OrderPanel extends javax.swing.JPanel {

    DefaultTableModel model;
    DefaultTableModel modelDetail;
    DefaultComboBoxModel<String> cbxPayment = new DefaultComboBoxModel<>();
    OderService oderService = new OderService();
    OderDetailService oderDetailService = new OderDetailService();
    List<Oders> listOders = new ArrayList<>();
    List<OderDetail> listOderDetails = new ArrayList<>();
    private int index = -1;

    public OrderPanel() {
        initComponents();
        setComboxPayment();
        paginationOderDetail.setPaginationItemRender(new PaginationItemRenderStyle1());
        paginationOderDetail.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadDataOdersDetail(page);
            }
        });
        paginationOder.setPaginationItemRender(new PaginationItemRenderStyle1());

        paginationOder.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                if (page < 1) {
                    page = 1;
                }
                loadDataOders(page);
            }
        });

        loadDataOders(1);
        loadDataOdersDetail(1);

    }

    void setComboxPayment() {
        String[] listItem = {"Vui lòng chọn...", "Transfer", "Cash",};
        for (String item : listItem) {
            cbxPayment.addElement(item);
        }
        cbxPaymentMethod.setModel(cbxPayment);
    }

    void fillTableOder(List<Oders> listOder) {
        model.setRowCount(0);
        for (Oders oders : listOder) {
            model.addRow(oders.toDataOrder());
        }
    }

    public void loadDataOders(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            model = (DefaultTableModel) tblDisplayOrder.getModel();
            model.setRowCount(0);
            fillTableOder(oderService.getAllOders());
            int rowCount = oderService.countOder();
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            listOders = oderService.getPaginatedOders(offset, limit);
            fillTableOder(listOders);
            paginationOder.setPagegination(page, totalPages);
            System.out.println("Tổng số trang: " + totalPages);
            System.out.println("Trang hiện tại: " + page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataOdersDetail(int page) {
        int limit = 10;
        int offset = (page - 1) * limit;
        try {
            modelDetail = (DefaultTableModel) tblDetailOrder.getModel();
            modelDetail.setRowCount(0);
            fillTableOderDetail(oderDetailService.getAllOderDetails());
            int rowCount = oderDetailService.countOderDetail();
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            listOderDetails = oderDetailService.getPaginatedOders(offset, limit);
            fillTableOderDetail(listOderDetails);
            paginationOderDetail.setPagegination(page, totalPages);
            System.out.println("Tổng số trang: " + totalPages);
            System.out.println("Trang hiện tại: " + page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillTableOderDetail(List<OderDetail> listOderDetails) {
//        modelDetail = (DefaultTableModel) tblDetailOrder.getModel();
        modelDetail.setRowCount(0);
        for (OderDetail item : listOderDetails) {
            modelDetail.addRow(item.toDataOderDetail());
        }
    }

    void showDataOder(int index) {
        Oders oders = listOders.get(index);
        txtCreateDate.setText(oders.getDateCreateDate() + "");
        txtCustomerMoney.setText(oders.getCustomerMoney() + "");
        txtNameCustomer.setText(oders.getNameCustomer());
        txtNameStaff.setText(oders.getNameEmployee());
        txtOrderCode.setText(oders.getCode());
        txtTotalMoney.setText(oders.getTotalMoney() + "");
        if (oders.getPaymentMethod().equals("Transfer")) {
            cbxPaymentMethod.setSelectedIndex(1);
        } else {
            cbxPaymentMethod.setSelectedIndex(2);
        }
        if (oders.getStatus() == 1) {
            txtPayStatus.setText("Chờ thanh toán");
        } else if (oders.getStatus() == 2) {
            txtPayStatus.setText("Đã thanh toán");
        } else {
            txtPayStatus.setText("Huỷ");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupOrderStatus = new javax.swing.ButtonGroup();
        panelOrder = new javax.swing.JPanel();
        rdWait = new javax.swing.JRadioButton();
        rdPaied = new javax.swing.JRadioButton();
        rdCancel = new javax.swing.JRadioButton();
        rdAll = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayOrder = new javax.swing.JTable();
        txtSearch = new app.view.swing.TextField();
        btnImport = new app.view.swing.Button();
        btnExport = new app.view.swing.Button();
        paginationOder = new app.view.swing.Pagination();
        panelOrderDetail = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailOrder = new javax.swing.JTable();
        paginationOderDetail = new app.view.swing.Pagination();
        panelInformation = new javax.swing.JPanel();
        txtNameStaff = new app.view.swing.TextField();
        txtOrderCode = new app.view.swing.TextField();
        txtNameCustomer = new app.view.swing.TextField();
        txtCreateDate = new app.view.swing.TextField();
        txtTotalMoney = new app.view.swing.TextField();
        txtPayStatus = new app.view.swing.TextField();
        txtCustomerMoney = new app.view.swing.TextField();
        cbxPaymentMethod = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        panelOrder.setBackground(new java.awt.Color(255, 255, 255));
        panelOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Hóa Đơn"));

        btnGroupOrderStatus.add(rdWait);
        rdWait.setText("Chờ Thanh Toán");
        rdWait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdWaitActionPerformed(evt);
            }
        });

        btnGroupOrderStatus.add(rdPaied);
        rdPaied.setText("Đã Thanh Toán");
        rdPaied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdPaiedActionPerformed(evt);
            }
        });

        btnGroupOrderStatus.add(rdCancel);
        rdCancel.setText("Hủy");
        rdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdCancelActionPerformed(evt);
            }
        });

        btnGroupOrderStatus.add(rdAll);
        rdAll.setSelected(true);
        rdAll.setText("Tất cả");
        rdAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAllActionPerformed(evt);
            }
        });

        tblDisplayOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Code", "Customer", "Employee", "Phone", "Payment", "Customer money", "Total money", "Money reduce", "Date create", "status", "note"
            }
        ));
        tblDisplayOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDisplayOrder);

        txtSearch.setLabelText("Tìm Kiếm");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnImport.setBackground(new java.awt.Color(23, 35, 51));
        btnImport.setForeground(new java.awt.Color(255, 255, 255));
        btnImport.setLabel("Import");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnExport.setBackground(new java.awt.Color(23, 35, 51));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setLabel("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        paginationOder.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdWait)
                            .addComponent(rdPaied))
                        .addGap(18, 18, 18)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOrderLayout.createSequentialGroup()
                                .addComponent(rdCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addGroup(panelOrderLayout.createSequentialGroup()
                                .addComponent(rdAll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)))))
                .addContainerGap())
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdWait)
                            .addComponent(rdCancel))
                        .addGap(18, 18, 18)
                        .addComponent(rdPaied))
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdAll)
                            .addGroup(panelOrderLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        panelOrderDetail.setBackground(new java.awt.Color(255, 255, 255));
        panelOrderDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn Chi Tiết"));

        tblDetailOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Customer", "Employee", "Name Product", "Payment", "Total money", "Price", "Quantity", "Date create", "Voucher"
            }
        ));
        jScrollPane2.setViewportView(tblDetailOrder);

        paginationOderDetail.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelOrderDetailLayout = new javax.swing.GroupLayout(panelOrderDetail);
        panelOrderDetail.setLayout(panelOrderDetailLayout);
        panelOrderDetailLayout.setHorizontalGroup(
            panelOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderDetailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
        );
        panelOrderDetailLayout.setVerticalGroup(
            panelOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelInformation.setBackground(new java.awt.Color(255, 255, 255));
        panelInformation.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Hóa Đơn"));

        txtNameStaff.setLabelText("Tên Nhân Viên");

        txtOrderCode.setLabelText("Mã Hóa Đơn");

        txtNameCustomer.setLabelText("Tên Khách Hàng");

        txtCreateDate.setLabelText("Ngày Tạo");

        txtTotalMoney.setLabelText("Tổng Tiền Hóa Đơn");

        txtPayStatus.setLabelText("Tình Trạng");

        txtCustomerMoney.setLabelText("Tiền Khách Đưa");

        cbxPaymentMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelInformationLayout = new javax.swing.GroupLayout(panelInformation);
        panelInformation.setLayout(panelInformationLayout);
        panelInformationLayout.setHorizontalGroup(
            panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrderCode, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotalMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(txtCreateDate, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(cbxPaymentMethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(99, 99, 99)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCustomerMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPayStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        panelInformationLayout.setVerticalGroup(
            panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformationLayout.createSequentialGroup()
                        .addComponent(txtPayStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCustomerMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelInformationLayout.createSequentialGroup()
                            .addComponent(txtCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(cbxPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelInformationLayout.createSequentialGroup()
                            .addComponent(txtOrderCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtNameStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelOrderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOrderDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdWaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdWaitActionPerformed
        fillTableOder(oderService.getAllOdersByStatus(1));
    }//GEN-LAST:event_rdWaitActionPerformed

    private void rdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdCancelActionPerformed
        fillTableOder(oderService.getAllOdersByStatus(3));
    }//GEN-LAST:event_rdCancelActionPerformed

    private void rdAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAllActionPerformed
        fillTableOder(oderService.getAllOders());
    }//GEN-LAST:event_rdAllActionPerformed

    private void rdPaiedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdPaiedActionPerformed
        fillTableOder(oderService.getAllOdersByStatus(2));
    }//GEN-LAST:event_rdPaiedActionPerformed

    private void tblDisplayOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayOrderMouseClicked
        index = tblDisplayOrder.getSelectedRow();
        showDataOder(index);
    }//GEN-LAST:event_tblDisplayOrderMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String textSearch = txtSearch.getText().toLowerCase().trim();
        System.out.println(textSearch);
        List<Oders> list = oderService.getAllOders();
        List<Oders> listSearch = new ArrayList<>();

        for (Oders oders : list) {
            if (oders.getNameCustomer().toLowerCase().contains(textSearch)) {
                listSearch.add(oders);
            }
        }

        if (listSearch.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy");
            fillTableOder(oderService.getAllOders());
        } else {
            if (txtSearch.getText().equals("")) {
                clearTable(model);
                for (Oders oders : listSearch) {
                    model.addRow(oders.toDataOrder());
                }
            }
            clearTable(model);
            for (Oders oders : listSearch) {
                model.addRow(oders.toDataOrder());
            }

        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        List<Oders> listOdersEx = oderService.getAllOders();

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("DataOder");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Code");
            headerRow.createCell(2).setCellValue("Customer");
            headerRow.createCell(3).setCellValue("Employee");
            headerRow.createCell(4).setCellValue("Phone");
            headerRow.createCell(5).setCellValue("Payment");
            headerRow.createCell(6).setCellValue("Customer money");
            headerRow.createCell(7).setCellValue("Total money");
            headerRow.createCell(8).setCellValue("Money reduce");
            headerRow.createCell(9).setCellValue("Date create");
            headerRow.createCell(10).setCellValue("Status");
            headerRow.createCell(11).setCellValue("Note");

            for (int i = 0; i < listOdersEx.size(); i++) {
                Oders oders = listOdersEx.get(i);
                Row excelRow = sheet.createRow(i + 1);

                Cell id = excelRow.createCell(0);
                id.setCellValue(oders.getIdOrder());

                Cell code = excelRow.createCell(1);
                code.setCellValue(oders.getCode());

                Cell customer = excelRow.createCell(2);
                customer.setCellValue(oders.getNameCustomer());

                Cell employee = excelRow.createCell(3);
                employee.setCellValue(oders.getNameEmployee());

                Cell phone = excelRow.createCell(4);
                phone.setCellValue(oders.getPhoneNumber());

                Cell payment = excelRow.createCell(5);
                payment.setCellValue(oders.getPaymentMethod());

                Cell customerMoney = excelRow.createCell(6);
                customerMoney.setCellValue(oders.getCustomerMoney() + "");

                Cell totalMoney = excelRow.createCell(7);
                totalMoney.setCellValue(oders.getTotalMoney() + "");

                Cell moneyReduce = excelRow.createCell(8);
                moneyReduce.setCellValue(oders.getMoneyReduce());

                CellStyle dateCellStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

                Cell dateCreate = excelRow.createCell(9);
                dateCreate.setCellValue(oders.getDateCreateDate());
                dateCreate.setCellStyle(dateCellStyle);

                Cell status = excelRow.createCell(10);
                status.setCellValue(oders.getStatus());

                Cell note = excelRow.createCell(11);
                note.setCellValue(oders.getNote());
            }
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String filePath = "dataOder_" + dateFormat.format(new Date()) + ".xlsx";

            try ( FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                JOptionPane.showMessageDialog(this, "Export Thành công");
                openExcelFile(filePath);
            }
        } catch (Exception e) {
            System.out.println("Lỗi");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(null);
            if ((returnValue == JFileChooser.APPROVE_OPTION)) {
                File selectedFile = fileChooser.getSelectedFile();
                String excelFilePath = selectedFile.getAbsolutePath();
                oderService.importDataFromExcel(excelFilePath);
                JOptionPane.showMessageDialog(null, "Import thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Import Lỗi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnImportActionPerformed

    private void openExcelFile(String filePath) {
        try {
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTable(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.Button btnExport;
    private javax.swing.ButtonGroup btnGroupOrderStatus;
    private app.view.swing.Button btnImport;
    private javax.swing.JComboBox<String> cbxPaymentMethod;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private app.view.swing.Pagination paginationOder;
    private app.view.swing.Pagination paginationOderDetail;
    private javax.swing.JPanel panelInformation;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JPanel panelOrderDetail;
    private javax.swing.JRadioButton rdAll;
    private javax.swing.JRadioButton rdCancel;
    private javax.swing.JRadioButton rdPaied;
    private javax.swing.JRadioButton rdWait;
    private javax.swing.JTable tblDetailOrder;
    private javax.swing.JTable tblDisplayOrder;
    private app.view.swing.TextField txtCreateDate;
    private app.view.swing.TextField txtCustomerMoney;
    private app.view.swing.TextField txtNameCustomer;
    private app.view.swing.TextField txtNameStaff;
    private app.view.swing.TextField txtOrderCode;
    private app.view.swing.TextField txtPayStatus;
    private app.view.swing.TextField txtSearch;
    private app.view.swing.TextField txtTotalMoney;
    // End of variables declaration//GEN-END:variables
}
