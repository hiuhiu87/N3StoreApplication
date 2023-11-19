/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.KhachHang;
import app.model.Order;
import app.model.OrderDetail;
import app.model.ProductDetail;
import app.request.NewOrderRequest;
import app.response.CartResponse;
import app.response.OrderResponse;
import app.response.ProductDetailResponse;
import app.response.ProductResponse;
import app.service.KhachHang_Service;
import app.service.NhanVienService;
import app.service.OrderDetailService;
import app.service.OrderService;
import app.service.ProductDetailService;
import app.service.ProductService;
import app.service.SellService;
import app.util.XDate;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SellPanel extends javax.swing.JPanel {

    private DefaultTableModel tblModelOrder = new DefaultTableModel();
    private DefaultTableModel tblModelOrderDetail = new DefaultTableModel();
    private DefaultTableModel tblModelProductDetail = new DefaultTableModel();
    private DefaultTableModel tblModelProduct = new DefaultTableModel();
    private DefaultComboBoxModel ccbModelPayment = new DefaultComboBoxModel();
    private KhachHang khachHang;
    private Order orderChose;
    private final int TYPE_PAIED = 1;
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private final CustommerDialog custommerDialog;
    private final SellService sellService;
    private final KhachHang_Service khachHangService = new KhachHang_Service();
    private final NhanVienService nhanVienService = new NhanVienService();
    private final OrderService orderService = new OrderService();
    private final OrderDetailService orderDetailService = new OrderDetailService();
    private final ProductDetailService productDetailService = new ProductDetailService();
    private final ProductService productService = new ProductService();
    private final JFrame parentFrame;

    public SellPanel(JFrame parentFrame) {
        initComponents();
        sellService = new SellService();
        custommerDialog = new CustommerDialog(parentFrame, true);
//        Thread threadCam = new Thread(() -> {
//            initWebcam();
//        });
//        threadCam.start();
//        Thread threadScan = new Thread(() -> {
//            try {
//                Thread.sleep(5000);
//                scan();
//            } catch (InterruptedException ex) {
//                
//            }
//        });
//        threadScan.start();
        this.parentFrame = parentFrame;
        tblDisplayProduct.setModel(tblModelProduct);
        tblDisplayProductDetail.setModel(tblModelProductDetail);
        tblDisplayOrder.setModel(tblModelOrder);
        tblDisplayCart.setModel(tblModelOrderDetail);
        cbbTypePayment.setModel(ccbModelPayment);
        addComlumnOrder();
        addColumnOrderDetail();
        addColumnTableProductDetailAll();
        addColumnTableProduct();
        setKhachHangBanLe();
        addPaymentMethod();
        onCloseJDialog(custommerDialog);
        fillTableOrder(orderService.getAllOrderView());
        fillTableProduct(productService.getAllProductResponse());
    }

    private void setKhachHangBanLe() {
        txtNameCustomer.setText("Khách lẻ");
        if (txtNameCustomer.getText().equals("Khách lẻ")) {
            txtPhoneNumber.setText("");
            txtPhoneNumber.setEnabled(false);
            txtNameCustomer.setEnabled(false);
            txtPhoneNumber.setForeground(Color.red);
            txtPhoneNumber.setFont(txtPhoneNumber.getFont().deriveFont(Font.BOLD));
            this.khachHang = khachHangService.findKhachHangLe();
        }
    }

    private void showCustomerInfor(KhachHang khachHang) {
        txtNameCustomer.setText(khachHang.getFullName() != null ? khachHang.getFullName() : "");
        txtPhoneNumber.setText(khachHang.getPhoneNumber() != null ? khachHang.getPhoneNumber() : "");
    }

    private void initWebcam() {
//        Dimension size = WebcamResolution.QVGA.getSize();
//        try {
//            webcam = Webcam.getWebcams().get(0);
//            webcam.setViewSize(size);
//
//            panel = new WebcamPanel(webcam);
//            panel.setPreferredSize(size);
//
//            panelBarcode.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));
//            panelBarcode.revalidate();
//        } catch (WebcamException e) {
//            if (this.isEnabled()) {
//                JOptionPane.showMessageDialog(this, "Lỗi Cam");
//            }
//        }
    }

    private void scan() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(binaryBitmap);
            } catch (NotFoundException ex) {
                System.out.println(ex.getMessage());
            }

            if (result != null) {

            }

        } while (true);
    }

    private void fillTableProduct(List<ProductResponse> list) {
        if (list != null) {
            tblModelProduct.setRowCount(0);
            int stt = 0;
            for (ProductResponse productResponse : list) {
                if (productResponse.getDeleted() == false && productResponse.getQuantity() > 0) {
                    stt++;
                    Object[] row = {
                        stt,
                        productResponse.getCode(),
                        productResponse.getName(),
                        productResponse.getQuantity(),
                        productResponse.getCategory(),
                        productResponse.getCompany(),
                        productResponse.getDeleted() ? "Ngừng Hoạt Động" : "Hoạt Động"
                    };
                    tblModelProduct.addRow(row);
                }
            }
        }
    }

    private void fillTableCart(List<CartResponse> list) {
        tblModelOrderDetail.setRowCount(0);
        for (CartResponse cartResponse : list) {
            Object[] row = {
                cartResponse.getProductDetailCode(),
                cartResponse.getNameProduct(),
                cartResponse.getPrice(),
                cartResponse.getQuantity(),
                cartResponse.getTotaMoney()
            };
            tblModelOrderDetail.addRow(row);
        }
    }

//    private void fillTableOrderDetail(List)
    private void addColumnTableProduct() {
        tblModelProduct.addColumn("STT");
        tblModelProduct.addColumn("Mã Sản Phẩm");
        tblModelProduct.addColumn("Tên Sản Phẩm");
        tblModelProduct.addColumn("Số Lượng Tồn");
        tblModelProduct.addColumn("Danh Mục");
        tblModelProduct.addColumn("Thương Hiệu");
        tblModelProduct.addColumn("Trạng Thái");
    }

    private void addColumnTableProductDetailAll() {
        tblModelProductDetail.addColumn("STT");
        tblModelProductDetail.addColumn("Mã CTSP");
        tblModelProductDetail.addColumn("Tên Sản Phẩm");
        tblModelProductDetail.addColumn("Kích Cỡ");
        tblModelProductDetail.addColumn("Chất Liệu");
        tblModelProductDetail.addColumn("Màu Sắc");
        tblModelProductDetail.addColumn("Loại Đế");
        tblModelProductDetail.addColumn("Giá Bán");
        tblModelProductDetail.addColumn("Giá Nhập");
        tblModelProductDetail.addColumn("Số Lượng Tồn");
        tblModelProductDetail.addColumn("Trạng Thái");
    }

    private void onCloseJDialog(CustommerDialog dialog) {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                khachHang = dialog.getKhachHang();
                if (khachHang != null) {
                    showCustomerInfor(khachHang);
                } else {
                    setKhachHangBanLe();
                }
            }
        });
    }

    private void clearFormInforSell() {
        txtBanking.setText("");
        txtTotalMoney.setText("");
        txtNameStaff.setText("");
        txtCash.setText("");
        txtCodeOrder.setText("");
        txtCreateDate.setText("");
        txtPayBack.setText("");
        cbbVoucher.setSelectedItem("");
        cbbTypePayment.setSelectedIndex(0);
    }

    private void fillTableOrder(List<OrderResponse> listOder) {
        tblModelOrder.setRowCount(0);
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
            tblModelOrder.addRow(row);
        }
    }

    private void fillTableProductDetail(List<ProductDetailResponse> list) {
        if (list != null) {
            int stt = 0;
            tblModelProductDetail.setRowCount(0);
            for (ProductDetailResponse productDetailResponse : list) {
                stt++;
                Object[] row = {
                    stt,
                    productDetailResponse.getCode(),
                    productDetailResponse.getProduct(),
                    productDetailResponse.getSize(),
                    productDetailResponse.getMaterial(),
                    productDetailResponse.getColor(),
                    productDetailResponse.getSole(),
                    productDetailResponse.getSellPrice(),
                    productDetailResponse.getOriginPrice(),
                    productDetailResponse.getQuantity(),
                    productDetailResponse.getDeleted() ? "Ngừng Bán" : "Đang Bán"
                };
                tblModelProductDetail.addRow(row);
            }
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

    private void addComlumnOrder() {
        tblModelOrder.addColumn("STT");
        tblModelOrder.addColumn("Mã Hóa Đơn");
        tblModelOrder.addColumn("Tên Khách Hàng");
        tblModelOrder.addColumn("Tên Nhân Viên");
        tblModelOrder.addColumn("Số Điện Thoại");
        tblModelOrder.addColumn("Tổng Tiền");
        tblModelOrder.addColumn("Ngày Tạo");
        tblModelOrder.addColumn("Trạng Thái");
    }

    private void addColumnOrderDetail() {
        tblModelOrderDetail.addColumn("Mã CTSP");
        tblModelOrderDetail.addColumn("Tên Sản Phẩm");
        tblModelOrderDetail.addColumn("Đơn Giá");
        tblModelOrderDetail.addColumn("Số Lượng");
        tblModelOrderDetail.addColumn("Thành Tièn");
    }

    private void addPaymentMethod() {
        List<String> listPaymentS = new ArrayList<>();
        listPaymentS.add("Tiền Mặt");
        listPaymentS.add("Chuyển Khoản");
        listPaymentS.add("Kết Hợp");
        ccbModelPayment.addAll(listPaymentS);
    }

    private void showInforOrder(Order order) {
        String nameEmployee = nhanVienService.findById(order.getIdEmployee()).getTen();
        showCustomerInfor(khachHangService.findById(order.getIdCustomer()));
        fillTableCart(sellService.getAllListCart(order.getCode()));
        if (order.getStatus() == 1) {
            txtCodeOrder.setText(order.getCode());
            txtCreateDate.setText(XDate.parseToString(order.getCreateDate()));
            txtNameStaff.setText(nameEmployee);
            btnDeleteCart.setEnabled(true);
        } else if (order.getStatus() == 2) {
            txtCodeOrder.setText(order.getCode());
            txtCreateDate.setText(XDate.parseToString(order.getCreateDate()));
            txtNameStaff.setText(nameEmployee);
            txtTotalMoney.setText(String.valueOf(order.getTotalMoney()));
            cbbTypePayment.setSelectedItem(order.getPaymentMethod());
            btnDeleteCart.setEnabled(false);
        } else {
            btnDeleteCart.setEnabled(false);
        }
    }

    private void createOrder() {
        NewOrderRequest newOrderRequest = new NewOrderRequest();
        String codeOrder = sellService.generateCodeOrder();
        newOrderRequest.setIdCustomer(this.khachHang.getId());
        newOrderRequest.setIdEmployee(1);
        newOrderRequest.setCode(codeOrder);
        boolean res = sellService.createNewOrder(newOrderRequest);
        if (res) {
            JOptionPane.showMessageDialog(this, "Tạo Hóa Đơn Thành Công");
            Order o = orderService.findByCode(codeOrder);
            showInforOrder(o);
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

        btnGroupStatusOrder = new javax.swing.ButtonGroup();
        productDetailDialog = new javax.swing.JDialog();
        panelProductDetail = new javax.swing.JPanel();
        txtSearchCodeProductDetail = new app.view.swing.TextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDisplayProductDetail = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        panelOrder = new javax.swing.JPanel();
        btnAdd = new app.view.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayOrder = new javax.swing.JTable();
        rdAll = new javax.swing.JRadioButton();
        rdPaied = new javax.swing.JRadioButton();
        rdWaitPay = new javax.swing.JRadioButton();
        rdCancel = new javax.swing.JRadioButton();
        panelBarcodeContainer = new javax.swing.JPanel();
        panelBarcode = new javax.swing.JPanel();
        panelCart = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisplayCart = new javax.swing.JTable();
        btnDeleteCart = new app.view.swing.Button();
        panelProduct = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDisplayProduct = new javax.swing.JTable();
        txtSearchProduct = new app.view.swing.TextField();
        btnRefresh = new app.view.swing.MyButton();
        panelPay = new javax.swing.JPanel();
        panelCustomer = new javax.swing.JPanel();
        txtPhoneNumber = new app.view.swing.TextField();
        txtNameCustomer = new app.view.swing.TextField();
        btnChoose = new app.view.swing.Button();
        btnChoose1 = new app.view.swing.Button();
        txtNameStaff = new app.view.swing.TextField();
        txtCreateDate = new app.view.swing.TextField();
        txtTotalMoney = new app.view.swing.TextField();
        txtCash = new app.view.swing.TextField();
        txtBanking = new app.view.swing.TextField();
        txtPayBack = new app.view.swing.TextField();
        cbbTypePayment = new app.view.swing.Combobox();
        btnPay = new app.view.swing.Button();
        txtCodeOrder = new app.view.swing.TextField();
        labelCashError = new javax.swing.JLabel();
        labelBankingError = new javax.swing.JLabel();
        cbbVoucher = new app.view.swing.Combobox();

        productDetailDialog.setBackground(new java.awt.Color(255, 255, 255));

        panelProductDetail.setBackground(new java.awt.Color(255, 255, 255));

        txtSearchCodeProductDetail.setLabelText("Mã Chi Tiết Sản Phẩm");

        tblDisplayProductDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDisplayProductDetail.setRowHeight(40);
        tblDisplayProductDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayProductDetailMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDisplayProductDetail);

        javax.swing.GroupLayout panelProductDetailLayout = new javax.swing.GroupLayout(panelProductDetail);
        panelProductDetail.setLayout(panelProductDetailLayout);
        panelProductDetailLayout.setHorizontalGroup(
            panelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductDetailLayout.createSequentialGroup()
                        .addComponent(txtSearchCodeProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelProductDetailLayout.setVerticalGroup(
            panelProductDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductDetailLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(txtSearchCodeProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout productDetailDialogLayout = new javax.swing.GroupLayout(productDetailDialog.getContentPane());
        productDetailDialog.getContentPane().setLayout(productDetailDialogLayout);
        productDetailDialogLayout.setHorizontalGroup(
            productDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelProductDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        productDetailDialogLayout.setVerticalGroup(
            productDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelProductDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1082, 615));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        panelOrder.setBackground(new java.awt.Color(255, 255, 255));
        panelOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn"));

        btnAdd.setBackground(new java.awt.Color(23, 35, 51));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tạo Hóa Đơn");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tblDisplayOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Hoá Đơn", "Ngày Tạo", "Nhân Viên Tạo", "Tên Khách Hàng", "Trạng thái hoá đơn"
            }
        ));
        tblDisplayOrder.setRowHeight(35);
        tblDisplayOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDisplayOrder);

        btnGroupStatusOrder.add(rdAll);
        rdAll.setSelected(true);
        rdAll.setText("Tất Cả");
        rdAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAllActionPerformed(evt);
            }
        });

        btnGroupStatusOrder.add(rdPaied);
        rdPaied.setText("Đã Thanh Toán");
        rdPaied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdPaiedActionPerformed(evt);
            }
        });

        btnGroupStatusOrder.add(rdWaitPay);
        rdWaitPay.setText("Chờ Thanh Toán");
        rdWaitPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdWaitPayActionPerformed(evt);
            }
        });

        btnGroupStatusOrder.add(rdCancel);
        rdCancel.setText("Hủy");
        rdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(rdAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdPaied)
                        .addGap(12, 12, 12)
                        .addComponent(rdWaitPay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdCancel)
                        .addGap(0, 42, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdAll)
                    .addComponent(rdPaied)
                    .addComponent(rdWaitPay)
                    .addComponent(rdCancel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        panelBarcodeContainer.setBackground(new java.awt.Color(255, 255, 255));
        panelBarcodeContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Barcode"));
        panelBarcodeContainer.setPreferredSize(new java.awt.Dimension(0, 10));

        javax.swing.GroupLayout panelBarcodeLayout = new javax.swing.GroupLayout(panelBarcode);
        panelBarcode.setLayout(panelBarcodeLayout);
        panelBarcodeLayout.setHorizontalGroup(
            panelBarcodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelBarcodeLayout.setVerticalGroup(
            panelBarcodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBarcodeContainerLayout = new javax.swing.GroupLayout(panelBarcodeContainer);
        panelBarcodeContainer.setLayout(panelBarcodeContainerLayout);
        panelBarcodeContainerLayout.setHorizontalGroup(
            panelBarcodeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarcodeContainerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(panelBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBarcodeContainerLayout.setVerticalGroup(
            panelBarcodeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarcodeContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelCart.setBackground(new java.awt.Color(255, 255, 255));
        panelCart.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ Hàng"));

        tblDisplayCart.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDisplayCart);

        btnDeleteCart.setBackground(new java.awt.Color(23, 35, 51));
        btnDeleteCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/dustbin.png"))); // NOI18N
        btnDeleteCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCartLayout = new javax.swing.GroupLayout(panelCart);
        panelCart.setLayout(panelCartLayout);
        panelCartLayout.setHorizontalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeleteCart, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelCartLayout.setVerticalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                .addComponent(btnDeleteCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelProduct.setBackground(new java.awt.Color(255, 255, 255));
        panelProduct.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        tblDisplayProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDisplayProduct.setRowHeight(35);
        tblDisplayProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayProductMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDisplayProduct);

        txtSearchProduct.setLabelText("Tên Sản Phẩm");

        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm Mới");
        btnRefresh.setBorderColor(new java.awt.Color(23, 35, 51));
        btnRefresh.setColor(new java.awt.Color(23, 35, 51));
        btnRefresh.setColorClick(new java.awt.Color(23, 16, 71));
        btnRefresh.setColorOver(new java.awt.Color(23, 11, 84));
        btnRefresh.setRadius(10);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductLayout = new javax.swing.GroupLayout(panelProduct);
        panelProduct.setLayout(panelProductLayout);
        panelProductLayout.setHorizontalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(466, 466, 466)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelProductLayout.setVerticalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelPay.setBackground(new java.awt.Color(255, 255, 255));
        panelPay.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn Hàng - Thanh Toán"));

        panelCustomer.setBackground(new java.awt.Color(255, 255, 255));
        panelCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));

        txtPhoneNumber.setLabelText("Số điện thoại");

        txtNameCustomer.setLabelText("Tên Khách Hàng");

        btnChoose.setBackground(new java.awt.Color(23, 35, 51));
        btnChoose.setForeground(new java.awt.Color(255, 255, 255));
        btnChoose.setText("Chọn");
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        btnChoose1.setBackground(new java.awt.Color(23, 35, 51));
        btnChoose1.setForeground(new java.awt.Color(255, 255, 255));
        btnChoose1.setText("Khách Lẻ");
        btnChoose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoose1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCustomerLayout = new javax.swing.GroupLayout(panelCustomer);
        panelCustomer.setLayout(panelCustomerLayout);
        panelCustomerLayout.setHorizontalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnChoose1, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btnChoose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
        panelCustomerLayout.setVerticalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtNameStaff.setEditable(false);
        txtNameStaff.setLabelText("Tên Nhân Viên");

        txtCreateDate.setEditable(false);
        txtCreateDate.setLabelText("Ngày Tạo");

        txtTotalMoney.setEditable(false);
        txtTotalMoney.setLabelText("Tổng Tiền");

        txtCash.setLabelText("Tiền Mặt");
        txtCash.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCashFocusLost(evt);
            }
        });

        txtBanking.setLabelText("Tiền Chuyển Khoản");
        txtBanking.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBankingFocusLost(evt);
            }
        });

        txtPayBack.setEditable(false);
        txtPayBack.setLabelText("Tiền Trả Lại");

        cbbTypePayment.setLabeText("Hình Thức Thanh Toán");
        cbbTypePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTypePaymentActionPerformed(evt);
            }
        });

        btnPay.setBackground(new java.awt.Color(23, 35, 51));
        btnPay.setForeground(new java.awt.Color(255, 255, 255));
        btnPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/hand.png"))); // NOI18N
        btnPay.setText("Thanh Toán");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        txtCodeOrder.setEditable(false);
        txtCodeOrder.setLabelText("Mã Hóa Đơn");

        labelCashError.setForeground(new java.awt.Color(255, 51, 0));
        labelCashError.setPreferredSize(new java.awt.Dimension(0, 12));

        labelBankingError.setForeground(new java.awt.Color(255, 51, 0));
        labelBankingError.setPreferredSize(new java.awt.Dimension(0, 12));

        cbbVoucher.setLabeText("Voucher");
        cbbVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbVoucherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPayLayout = new javax.swing.GroupLayout(panelPay);
        panelPay.setLayout(panelPayLayout);
        panelPayLayout.setHorizontalGroup(
            panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPayLayout.createSequentialGroup()
                .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCodeOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                .addComponent(txtNameStaff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCreateDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTotalMoney, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbbTypePayment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbbVoucher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtPayBack, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPayLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBanking, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(32, 32, 32)
                        .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBankingError, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCashError, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPayLayout.setVerticalGroup(
            panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(labelCashError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(labelBankingError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNameStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(cbbVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(cbbTypePayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBanking, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPayBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelCart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBarcodeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPay, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBarcodeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        custommerDialog.setVisible(true);
    }//GEN-LAST:event_btnChooseActionPerformed

    private void tblDisplayProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayProductMouseClicked
        int row = tblDisplayProduct.getSelectedRow();
        if (row >= 0) {
            String nameProduct = (String) tblDisplayProduct.getValueAt(row, 2);
            fillTableProductDetail(productDetailService.selectListDetailByNameProduct(nameProduct));
            productDetailDialog = new JDialog(parentFrame);
            productDetailDialog.getContentPane().add(panelProductDetail);
            productDetailDialog.pack();
            productDetailDialog.setLocationRelativeTo(null);
            productDetailDialog.setVisible(true);
        }

    }//GEN-LAST:event_tblDisplayProductMouseClicked

    private void tblDisplayOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayOrderMouseClicked
        int row = tblDisplayOrder.getSelectedRow();
        if (row >= 0) {
            String codeOrder = (String) tblDisplayOrder.getValueAt(row, 1);
            Order order = orderService.findByCode(codeOrder);
            this.orderChose = order;
            showInforOrder(orderChose);
        }
    }//GEN-LAST:event_tblDisplayOrderMouseClicked

    private void btnDeleteCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCartActionPerformed
        int row = tblDisplayCart.getSelectedRow();
        if (row >= 0) {
            int chose = JOptionPane.showConfirmDialog(parentFrame, "Bạn có muốn xóa ra khỏi giỏ hàng ?");
            if (chose == JOptionPane.YES_OPTION) {
                String productDetailCode = (String) tblDisplayCart.getValueAt(row, 0);
                ProductDetail productDetail = productDetailService.findByCode(productDetailCode);
                boolean res = sellService.deleteFromCart(productDetail.getId());
                JOptionPane.showMessageDialog(parentFrame, "Xóa Thành Công");
                fillTableCart(sellService.getAllListCart(this.orderChose.getCode()));
            }

        }
    }//GEN-LAST:event_btnDeleteCartActionPerformed

    private void rdPaiedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdPaiedActionPerformed
    }//GEN-LAST:event_rdPaiedActionPerformed

    private void rdWaitPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdWaitPayActionPerformed

    }//GEN-LAST:event_rdWaitPayActionPerformed

    private void rdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdCancelActionPerformed

    }//GEN-LAST:event_rdCancelActionPerformed

    private void rdAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAllActionPerformed

    }//GEN-LAST:event_rdAllActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed

    }//GEN-LAST:event_btnPayActionPerformed

    private void cbbTypePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTypePaymentActionPerformed
        int typePamentChoose = cbbTypePayment.getSelectedIndex();
        switch (typePamentChoose) {
            case -1 -> {
                txtBanking.setEditable(false);
                txtCash.setEditable(false);
                labelCashError.setVisible(false);
                labelBankingError.setVisible(false);
            }

            case 0 -> {
                txtBanking.setEditable(false);
                txtCash.setEditable(true);
                labelCashError.setVisible(true);
            }

            case 1 -> {
                txtBanking.setEditable(true);
                txtCash.setEditable(false);
                labelCashError.setVisible(false);
                labelBankingError.setVisible(true);
            }

            case 2 -> {
                txtBanking.setVisible(true);
                txtCash.setEditable(true);
                labelCashError.setVisible(true);
                labelBankingError.setVisible(true);
            }

            default ->
                throw new AssertionError();
        }
    }//GEN-LAST:event_cbbTypePaymentActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.grabFocus();
    }//GEN-LAST:event_formMouseClicked

    private void txtCashFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashFocusLost


    }//GEN-LAST:event_txtCashFocusLost

    private void txtBankingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBankingFocusLost

    }//GEN-LAST:event_txtBankingFocusLost

    private void btnChoose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoose1ActionPerformed
        setKhachHangBanLe();
    }//GEN-LAST:event_btnChoose1ActionPerformed

    private void cbbVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbVoucherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbVoucherActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        fillTableProduct(productService.getAllProductResponse());
        fillTableProductDetail(productDetailService.getAllListProducts());
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tblDisplayProductDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayProductDetailMouseClicked
        int row = tblDisplayProductDetail.getSelectedRow();
        if (row >= 0) {
            String productDetailCode = (String) tblDisplayProductDetail.getValueAt(row, 1);
            ProductDetail productDetail = productDetailService.findByCode(productDetailCode);
            String quantity = JOptionPane.showInputDialog("Nhập Vào Số Lượng");
            int quantityOrderDetail = 0;
            if (!quantity.isEmpty()) {
                try {
                    quantityOrderDetail = Integer.parseInt(quantity);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số");
                }
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setIdOrder(this.orderChose.getId());
                orderDetail.setIdProductdetail(productDetail.getId());
                orderDetail.setPrice(productDetail.getSellPrice());
                orderDetail.setQuantity(quantityOrderDetail);
                orderDetail.setTotalMoney(productDetail.getSellPrice() * quantityOrderDetail);
                boolean res = sellService.addToCart(orderDetail);
                if (res) {
                    JOptionPane.showMessageDialog(this, "Thêm Thành Công");
                    productDetailDialog.dispose();
                    fillTableCart(sellService.getAllListCart(orderChose.getCode()));
                } else {
                    JOptionPane.showMessageDialog(this, "Đã Xảy ra Lỗi");
                }
            }
        }
    }//GEN-LAST:event_tblDisplayProductDetailMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.Button btnAdd;
    private app.view.swing.Button btnChoose;
    private app.view.swing.Button btnChoose1;
    private app.view.swing.Button btnDeleteCart;
    private javax.swing.ButtonGroup btnGroupStatusOrder;
    private app.view.swing.Button btnPay;
    private app.view.swing.MyButton btnRefresh;
    private app.view.swing.Combobox cbbTypePayment;
    private app.view.swing.Combobox cbbVoucher;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelBankingError;
    private javax.swing.JLabel labelCashError;
    private javax.swing.JPanel panelBarcode;
    private javax.swing.JPanel panelBarcodeContainer;
    private javax.swing.JPanel panelCart;
    private javax.swing.JPanel panelCustomer;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JPanel panelPay;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JPanel panelProductDetail;
    private javax.swing.JDialog productDetailDialog;
    private javax.swing.JRadioButton rdAll;
    private javax.swing.JRadioButton rdCancel;
    private javax.swing.JRadioButton rdPaied;
    private javax.swing.JRadioButton rdWaitPay;
    private javax.swing.JTable tblDisplayCart;
    private javax.swing.JTable tblDisplayOrder;
    private javax.swing.JTable tblDisplayProduct;
    private javax.swing.JTable tblDisplayProductDetail;
    private app.view.swing.TextField txtBanking;
    private app.view.swing.TextField txtCash;
    private app.view.swing.TextField txtCodeOrder;
    private app.view.swing.TextField txtCreateDate;
    private app.view.swing.TextField txtNameCustomer;
    private app.view.swing.TextField txtNameStaff;
    private app.view.swing.TextField txtPayBack;
    private app.view.swing.TextField txtPhoneNumber;
    private app.view.swing.TextField txtSearchCodeProductDetail;
    private app.view.swing.TextField txtSearchProduct;
    private app.view.swing.TextField txtTotalMoney;
    // End of variables declaration//GEN-END:variables
}
