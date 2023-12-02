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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
    
    private final DefaultTableModel tableModelOrder = new DefaultTableModel();
    private final DefaultTableModel tableModelOrderDetail = new DefaultTableModel();
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
            int rowCount = oderDetailService.countOderDetail();
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableOderDetail(oderDetailService.getPaginatedOders(offset, limit, orderCodeChoose));
            paginationOderDetail.setPagegination(page, totalPages);
            System.out.println("Tổng số trang: " + totalPages);
            System.out.println("Trang hiện tại: " + page);
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
        rdWait = new javax.swing.JRadioButton();
        rdPaied = new javax.swing.JRadioButton();
        rdCancel = new javax.swing.JRadioButton();
        rdAll = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayOrder = new javax.swing.JTable();
        txtSearch = new app.view.swing.TextField();
        paginationOder = new app.view.swing.Pagination();
        btnPrint = new app.view.swing.Button();
        btnRefresh = new app.view.swing.Button();
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

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGap(506, 506, 506)
                .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdWait)
                    .addComponent(rdPaied))
                .addGap(18, 18, 18)
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdCancel)
                    .addComponent(rdAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelOrderLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(81, 81, 81))
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addGap(57, 57, 57)
                                .addComponent(rdAll)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(489, 489, 489))
        );
        panelOrderDetailLayout.setVerticalGroup(
            panelOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelOrderDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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

    private void rdWaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdWaitActionPerformed
        fillTableOrder(oderService.getAllOdersByStatus(1));
    }//GEN-LAST:event_rdWaitActionPerformed

    private void rdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdCancelActionPerformed
        fillTableOrder(oderService.getAllOdersByStatus(3));
    }//GEN-LAST:event_rdCancelActionPerformed

    private void rdAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAllActionPerformed
        loadDataOrders(1);
    }//GEN-LAST:event_rdAllActionPerformed

    private void rdPaiedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdPaiedActionPerformed
        fillTableOrder(oderService.getAllOdersByStatus(2));
    }//GEN-LAST:event_rdPaiedActionPerformed

    private void tblDisplayOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayOrderMouseClicked
        int row = tblDisplayOrder.getSelectedRow();
        if (row >= 0) {
            String orderCode = (String) tblDisplayOrder.getValueAt(row, 1);
            this.orderCodeChoose = orderCode;
            loadDataOdersDetail(1);
        }
    }//GEN-LAST:event_tblDisplayOrderMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
//        String textSearch = txtSearch.getText().toLowerCase().trim();
//        System.out.println(textSearch);
//        List<Oders> list = oderService.getAllOders();
//        List<Oders> listSearch = new ArrayList<>();
//
//        for (Order oders : list) {
//            if (oders.getNameCustomer().toLowerCase().contains(textSearch)) {
//                listSearch.add(oders);
//            }
//        }
//
//        if (listSearch.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy");
//            fillTableOrder(oderService.getAllOders());
//        } else {
//            if (txtSearch.getText().equals("")) {
//                clearTable(tableModelOrder);
//                for (Order oders : listSearch) {
//                    tableModelOrder.addRow(oders.toDataOrder());
//                }
//            }
//            clearTable(tableModelOrder);
//            for (Order oders : listSearch) {
//                tableModelOrder.addRow(oders.toDataOrder());
//            }
//
//        }
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
                map.put("totalMoneydiscount", new BigDecimal(order.getTotalMoney() - voucher.getValues()) + "");
                map.put("moneyReduce", new BigDecimal(voucher.getValues()) + "");
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
    }//GEN-LAST:event_btnRefreshActionPerformed
    
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private app.view.swing.Pagination paginationOder;
    private app.view.swing.Pagination paginationOderDetail;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JPanel panelOrderDetail;
    private javax.swing.JRadioButton rdAll;
    private javax.swing.JRadioButton rdCancel;
    private javax.swing.JRadioButton rdPaied;
    private javax.swing.JRadioButton rdWait;
    private javax.swing.JTable tblDetailOrder;
    private javax.swing.JTable tblDisplayOrder;
    private app.view.swing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
