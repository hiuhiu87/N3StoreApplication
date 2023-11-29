/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.OrderDetail;
import app.model.Order;
import app.response.OrderDetailResponse;
import app.response.OrderResponse;
import app.service.OrderDetailService;
import app.service.OrderService;
import app.view.swing.EventPagination;
import app.view.swing.PaginationItemRenderStyle1;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
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
    
    private final DefaultTableModel tableModelOrder = new DefaultTableModel();
    private final DefaultTableModel tableModelOrderDetail = new DefaultTableModel();
    private final DefaultComboBoxModel<String> cbxPayment = new DefaultComboBoxModel<>();
    private final OrderService oderService = new OrderService();
    private final OrderDetailService oderDetailService = new OrderDetailService();
    private String orderCodeChoose = "";
    
    public OrderPanel() {
        initComponents();
        setComboxPayment();
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
    
    void setComboxPayment() {
        String[] listItem = {"Vui lòng chọn...", "Transfer", "Cash",};
        for (String item : listItem) {
            cbxPayment.addElement(item);
        }
        cbxPaymentMethod.setModel(cbxPayment);
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
                orderResponse.getTotalMoney(),
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
                orderDetailResponse.getPrice(),
                orderDetailResponse.getTotalMoney()
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

//    void showDataOder(int index) {
//        Order oders = listOders.get(index);
//        txtCreateDate.setText(oders.getCreateDate()+ "");
//        txtCustomerMoney.setText(oders.getCustomerMoney() + "");
//        txtNameCustomer.setText(oders.getNameCustomer());
//        txtNameStaff.setText(oders.getNameEmployee());
//        txtOrderCode.setText(oders.getCode());
//        txtTotalMoney.setText(oders.getTotalMoney() + "");
//        if (oders.getPaymentMethod().equals("Transfer")) {
//            cbxPaymentMethod.setSelectedIndex(1);
//        } else {
//            cbxPaymentMethod.setSelectedIndex(2);
//        }
//        if (oders.getStatus() == 1) {
//            txtPayStatus.setText("Chờ thanh toán");
//        } else if (oders.getStatus() == 2) {
//            txtPayStatus.setText("Đã thanh toán");
//        } else {
//            txtPayStatus.setText("Huỷ");
//        }
//    }
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
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderDetailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        panelOrderDetailLayout.setVerticalGroup(
            panelOrderDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paginationOderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdWait)
                            .addComponent(rdPaied))
                        .addGap(18, 18, 18)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdCancel)
                            .addComponent(rdAll))
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOrderLayout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(75, 75, 75))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                                        .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(181, 181, 181)))))
                        .addComponent(panelOrderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                        .addGap(57, 57, 57)
                        .addComponent(rdAll))
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paginationOder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addComponent(panelOrderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(txtTotalMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCreateDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxPaymentMethod, 0, 256, Short.MAX_VALUE))
                .addGap(99, 99, 99)
                .addGroup(panelInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCustomerMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPayStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
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
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(panelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
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
//        showDataOder(index);
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
//        if (tblDetailOrder.getRowCount() <= 0) {
//            return;
//        }

//        if (index >= 0) {
//            try {
//                index = tblDisplayOrder.getSelectedRow();
//                Order oders = oderService.getAllListPrintOders().get(index);
//                System.out.println(oders.getNameCustomer());
//                System.out.println(oders.getCode());
//                Map<String, Object> map = new HashMap<>();
//                map.put("stt", "1");
//                map.put("Custommer", oders.getNameCustomer());
//                map.put("employee", oders.getNameEmployee());
//                map.put("Code", oders.getCode());
//                map.put("dateCreate", oders.getDateCreateDate() + "");
//                map.put("productName", oders.getNameProduct());
//                map.put("quantity", oders.getQuantityProduct() + "");
//                map.put("price", oders.getSellProduct() + "");
//                map.put("totalMoney", oders.getTotalMoney() + "");
//                map.put("moneyReduce", oders.getMoneyReduce() + "");
//                map.put("totalMoneydiscount", "450,000");
//                map.put("customerMoney", oders.getCustomerMoney() + "");
//                map.put("payment", oders.getPaymentMethod());
//                JOptionPane.showMessageDialog(this, "In hoá đơn thành công");
//                JasperReport rpt = JasperCompileManager.compileReport("src/app/jesport/JasportOder.jrxml");
//                JasperPrint print = JasperFillManager.fillReport(rpt, map, new JREmptyDataSource());
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
//                String timestamp = dateFormat.format(new Date());
//
//                String pdfFileName = "src/app/export/hoadon_" + timestamp + ".pdf";
//                JasperExportManager.exportReportToPdfFile(print, pdfFileName);
//                try {
//                    Desktop.getDesktop().open(new File(pdfFileName));
//                } catch (IOException ex) {
//                    Logger.getLogger(OrderPanel.class.getName()).log(Level.SEVERE, null, ex);
//                }
////                JasperViewer.viewReport(print, false);
//            } catch (JRException ex) {
//                Logger.getLogger(OrderPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }//GEN-LAST:event_btnPrintActionPerformed
    
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
