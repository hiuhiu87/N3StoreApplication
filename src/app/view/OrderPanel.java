/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.Order;
import app.model.Voucher;
import app.response.CartResponse;
import app.response.OrderDetailResponse;
import app.response.OrderResponse;
import app.response.ProductResponse;
import app.service.OrderDetailService;
import app.service.OrderService;
import app.service.SellService;
import app.service.VoucherService;
import app.view.swing.EventPagination;
import app.view.swing.PaginationItemRenderStyle1;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Admin
 */
public class OrderPanel extends javax.swing.JPanel {

    private final DefaultTableModel tableModelOrder = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final DefaultTableModel tableModelOrderDetail = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    };
    private final DefaultComboBoxModel<String> cbxPayment = new DefaultComboBoxModel<>();
    private final OrderService oderService = new OrderService();
    private final SellService sellService = new SellService();
    private final VoucherService voucherService = new VoucherService();
    private final OrderDetailService oderDetailService = new OrderDetailService();
    private String orderCodeChoose = "";

    public OrderPanel() {
        initComponents();
        tblDisplayOrder.setModel(tableModelOrder);
        tblDetailOrder.setModel(tableModelOrderDetail);
        addComlumnOrder();
        addComlumnOrderDetail();
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
                loadDataOrders(page);
            }
        });

        loadDataOrders(1);
        onChange();
        paginationOderDetail.setEnabled(false);
        paginationOderDetail.setVisible(false);
    }

    private void fillTableOrder(List<OrderResponse> listOder) {
        tableModelOrder.setRowCount(0);
        int stt = 0;
        for (OrderResponse orderResponse : listOder) {
            Object[] row = {
                ++stt,
                orderResponse.getCode(),
                orderResponse.getNameCustomer(),
                orderResponse.getNameEmployee(),
                orderResponse.getPhoneNumber(),
                String.valueOf(new BigDecimal(orderResponse.getTotalMoney())),
                orderResponse.getCreateDate(),
                returnNameStatus(orderResponse.getStatus())
            };
            tableModelOrder.addRow(row);
        }
    }

    private void onChange() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        loadDataOrders(1);
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        List<OrderResponse> listSearch = oderService.getAllOrderView();
                        List<OrderResponse> listRes = new ArrayList<>();
                        if (listSearch != null) {
                            for (OrderResponse orderResponse : listSearch) {
                                if (orderResponse.getCode().toLowerCase().contains(namePrdSearch.toLowerCase())) {
                                    listRes.add(orderResponse);
                                }
                            }
                            fillTableOrder(listRes);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadDataOrders(1);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        loadDataOrders(1);
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        List<OrderResponse> listSearch = oderService.getAllOrderView();
                        List<OrderResponse> listRes = new ArrayList<>();
                        if (listSearch != null) {
                            for (OrderResponse orderResponse : listSearch) {
                                if (orderResponse.getCode().toLowerCase().contains(namePrdSearch.toLowerCase())) {
                                    listRes.add(orderResponse);
                                }
                            }
                            fillTableOrder(listRes);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private String returnNameStatus(int status) {
        switch (status) {
            case 1 -> {
                return "Chờ Thanh Toán";
            }
            case 2 -> {
                return "Đã Thanh Toán";
            }
            case 3 -> {
                return "Hủy Thanh Toán";
            }
            default -> {
                return "Lỗi";
            }
        }
    }

    public void loadDataOrders(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            int rowCount = oderService.countOder();
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableOrder(oderService.getPaginatedOders(offset, limit));
            paginationOder.setPagegination(page, totalPages);
            System.out.println("Tổng số trang: " + totalPages);
            System.out.println("Trang hiện tại: " + page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataOdersDetail(int page) {
        int limit = 10;
        int offset = (page - 1) * limit;
        try {
            int rowCount = oderDetailService.countOderDetail(orderCodeChoose);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableOderDetail(oderDetailService.getPaginatedOders(offset, limit, orderCodeChoose));
            paginationOderDetail.setPagegination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTableOderDetail(List<OrderDetailResponse> listOrderDetails) {
        tableModelOrderDetail.setRowCount(0);
        int stt = 0;
        for (OrderDetailResponse orderDetailResponse : listOrderDetails) {
            Object[] row = {
                ++stt,
                orderDetailResponse.getOrderCode(),
                orderDetailResponse.getProductDetailCode(),
                orderDetailResponse.getNameProduct(),
                orderDetailResponse.getQuantity(),
                String.valueOf(new BigDecimal(orderDetailResponse.getPrice())),
                String.valueOf(new BigDecimal(orderDetailResponse.getTotalMoney()))
            };
            tableModelOrderDetail.addRow(row);
        }
    }

    private void addComlumnOrder() {
        tableModelOrder.addColumn("STT");
        tableModelOrder.addColumn("Mã Hóa Đơn");
        tableModelOrder.addColumn("Tên Khách Hàng");
        tableModelOrder.addColumn("Tên Nhân Viên");
        tableModelOrder.addColumn("Số Điện Thoại");
        tableModelOrder.addColumn("Tổng Tiền");
        tableModelOrder.addColumn("Ngày Tạo");
        tableModelOrder.addColumn("Trạng Thái");
    }

    private void addComlumnOrderDetail() {
        tableModelOrderDetail.addColumn("STT");
        tableModelOrderDetail.addColumn("Mã Hóa Đơn");
        tableModelOrderDetail.addColumn("Mã CTSP");
        tableModelOrderDetail.addColumn("Tên Sản Phẩm");
        tableModelOrderDetail.addColumn("Số Lượng");
        tableModelOrderDetail.addColumn("Đơn Giá");
        tableModelOrderDetail.addColumn("Tổng Tiền");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayOrder = new javax.swing.JTable();
        txtSearch = new app.view.swing.TextField();
        paginationOder = new app.view.swing.Pagination();
        btnPrint = new app.view.swing.Button();
        btnRefresh = new app.view.swing.Button();
        cbbOrderStatus = new app.view.swing.Combobox();
        panelOrderDetail = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailOrder = new javax.swing.JTable();
        paginationOderDetail = new app.view.swing.Pagination();

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        panelOrder.setBackground(new java.awt.Color(255, 255, 255));
        panelOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Hóa Đơn"));

        tblDisplayOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Hóa Đơn", "Tên Khách Hàng", "Tên Nhân Viên", "Số Điện Thoại", "Tổng Tiền", "Ngày Tạo", "Trạng Thái"
            }
        ));
        tblDisplayOrder.setRowHeight(45);
        tblDisplayOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDisplayOrder);
        if (tblDisplayOrder.getColumnModel().getColumnCount() > 0) {
            tblDisplayOrder.getColumnModel().getColumn(6).setResizable(false);
        }

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

        paginationOder.setBackground(new java.awt.Color(204, 204, 204));

        btnPrint.setBackground(new java.awt.Color(23, 35, 51));
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setText("In Hóa Đơn");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(23, 35, 51));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm Mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        cbbOrderStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chờ Thanh Toán", "Đã Thanh Toán", "Đã Hủy", "Tất Cả" }));
        cbbOrderStatus.setSelectedIndex(3);
        cbbOrderStatus.setLabeText("Tình Trạng Hóa Đơn");
        cbbOrderStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbOrderStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGap(506, 506, 506)
                .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(cbbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        tblDetailOrder.setRowHeight(35);
        jScrollPane2.setViewportView(tblDetailOrder);

        paginationOderDetail.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelOrderDetailLayout = new javax.swing.GroupLayout(panelOrderDetail);
        panelOrderDetail.setLayout(panelOrderDetailLayout);
        panelOrderDetailLayout.setHorizontalGroup(
            panelOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderDetailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(490, 490, 490))
        );
        panelOrderDetailLayout.setVerticalGroup(
            panelOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOrderDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOrderDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDisplayOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayOrderMouseClicked
        int row = tblDisplayOrder.getSelectedRow();
        if (row >= 0) {
            String orderCode = (String) tblDisplayOrder.getValueAt(row, 1);
            this.orderCodeChoose = orderCode;
            loadDataOdersDetail(1);
            paginationOderDetail.setEnabled(true);
            paginationOderDetail.setVisible(true);
        }
    }//GEN-LAST:event_tblDisplayOrderMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

    }//GEN-LAST:event_txtSearchKeyReleased

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if (tblDetailOrder.getRowCount() <= 0) {
            return;
        }

        int row = tblDisplayOrder.getSelectedRow();
        if (row >= 0) {
            String codeOrder = (String) tblDisplayOrder.getValueAt(row, 1);
            Order order = oderService.findByCode(codeOrder);
            if (order.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Chưa Được Thanh Toán !");
                return;
            }

            if (order.getStatus() == 3) {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Hủy!");
                return;
            }
            Voucher voucher = voucherService.findById(order.getIdVoucher());
            List<CartResponse> orderDetailRepsonse = sellService.getAllListCart(order.getCode());
            OrderResponse orderResponsePrint = new OrderResponse();
            List<OrderResponse> orderResponses = oderService.getAllOrderView();
            for (OrderResponse op : orderResponses) {
                if (op.getCode().equals(order.getCode())) {
                    orderResponsePrint.setCode(codeOrder);
                    orderResponsePrint.setCreateDate(op.getCreateDate());
                    orderResponsePrint.setNameCustomer(op.getNameCustomer());
                    orderResponsePrint.setNameEmployee(op.getNameEmployee());
                }
            }
            try {
                System.out.println(orderResponsePrint.getNameCustomer());
                System.out.println(orderResponsePrint.getCode());
                Map<String, Object> map = new HashMap<>();
                map.put("stt", "1");
                map.put("Custommer", orderResponsePrint.getNameCustomer());
                map.put("employee", orderResponsePrint.getNameEmployee());
                map.put("Code", orderResponsePrint.getCode());
                map.put("dateCreate", orderResponsePrint.getCreateDate() + "");
                map.put("ProductDataSource", new JRBeanCollectionDataSource(orderDetailRepsonse));
                map.put("totalMoney", new BigDecimal(order.getTotalMoney()) + "");
                if (voucher == null) {
                    map.put("totalMoneydiscount", new BigDecimal(order.getTotalMoney()) + "");
                } else {
                    map.put("totalMoneydiscount", new BigDecimal(order.getTotalMoney() - order.getMoneyReduce()) + "");
                    map.put("moneyReduce", new BigDecimal(voucher.getValues()) + "");
                }
                map.put("customerMoney", new BigDecimal(order.getCustomerMoney()) + "");
                map.put("payment", order.getPaymentMethod());
                JOptionPane.showMessageDialog(this, "In hoá đơn thành công");
                JasperReport rpt = JasperCompileManager.compileReport("src/app/jesport/JasportOder.jrxml");
                JasperPrint print = JasperFillManager.fillReport(rpt, map, new JREmptyDataSource());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String timestamp = dateFormat.format(new Date());

                String pdfFileName = "src/app/export/hoadon_" + timestamp + ".pdf";
                JasperExportManager.exportReportToPdfFile(print, pdfFileName);
                try {
                    Desktop.getDesktop().open(new File(pdfFileName));
                } catch (IOException ex) {
                    Logger.getLogger(OrderPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                JasperViewer.viewReport(print, false);
            } catch (JRException ex) {
                Logger.getLogger(OrderPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadDataOrders(1);
        tableModelOrderDetail.setRowCount(0);
        paginationOderDetail.setEnabled(false);
        paginationOderDetail.setVisible(false);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cbbOrderStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbOrderStatusActionPerformed
        int selectedIndx = cbbOrderStatus.getSelectedIndex();
        switch (selectedIndx) {
            case 0 -> {
                fillTableOrder(oderService.getAllOdersByStatus(1));
            }
            case 1 -> {
                fillTableOrder(oderService.getAllOdersByStatus(2));
            }
            case 2 -> {
                fillTableOrder(oderService.getAllOdersByStatus(3));
            }
            default ->
                loadDataOrders(1);
        }
    }//GEN-LAST:event_cbbOrderStatusActionPerformed

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
    private javax.swing.ButtonGroup btnGroupOrderStatus;
    private app.view.swing.Button btnPrint;
    private app.view.swing.Button btnRefresh;
    private app.view.swing.Combobox cbbOrderStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private app.view.swing.Pagination paginationOder;
    private app.view.swing.Pagination paginationOderDetail;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JPanel panelOrderDetail;
    private javax.swing.JTable tblDetailOrder;
    private javax.swing.JTable tblDisplayOrder;
    private app.view.swing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
