/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.KhachHang;
import app.model.NhanVien;
import app.model.Order;
import app.model.OrderDetail;
import app.model.ProductDetail;
import app.model.Voucher;
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
import app.service.VoucherService;
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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
public class SellPanel extends javax.swing.JPanel {
    
    private WebcamPanel panel = null;
    private NhanVien nhanVien;
    private Webcam webcam = null;
    private DefaultTableModel tblModelOrder = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ;
    private DefaultTableModel tblModelOrderDetail = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ;
    private DefaultTableModel tblModelProductDetail = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ;
    private DefaultTableModel tblModelProduct = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ;
    private DefaultTableModel tblModelVoucher = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ;
    private DefaultComboBoxModel ccbModelPayment = new DefaultComboBoxModel();
    private DefaultComboBoxModel comboBoxModelVoucher = new DefaultComboBoxModel();
    
    private KhachHang khachHang;
    private Order orderChose;
    private Voucher voucher;
    
    private final int TYPE_PAIED = 1;
    private Double totalMoneyLast = null;
    
    private final CustommerDialog custommerDialog;
    private final JFrame parentFrame;
    
    private final SellService sellService = new SellService();
    private final KhachHang_Service khachHangService = new KhachHang_Service();
    private final NhanVienService nhanVienService = new NhanVienService();
    private final OrderService orderService = new OrderService();
    private final OrderDetailService orderDetailService = new OrderDetailService();
    private final ProductDetailService productDetailService = new ProductDetailService();
    private final ProductService productService = new ProductService();
    private final VoucherService voucherService = new VoucherService();
    
    public SellPanel(JFrame parentFrame, NhanVien nhanVien) {
        initComponents();
        this.btnAddProductCart.setVisible(false);
        this.nhanVien = nhanVien;
        btnChoose.setEnabled(false);
        btnChoose1.setEnabled(false);
        btnCancel.setEnabled(false);
        custommerDialog = new CustommerDialog(parentFrame, true);
        Thread threadCam = new Thread(() -> {
            initWebcam();
        });
        threadCam.start();
        Thread threadScan = new Thread(() -> {
            try {
                Thread.sleep(5000);
                scan();
            } catch (InterruptedException ex) {
                
            }
        });
        threadScan.start();
        this.parentFrame = parentFrame;
        tblDisplayProduct.setModel(tblModelProductDetail);
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
        fillTableOrder(orderService.getAllOdersByStatus(1));
        fillTableProductDetail(productDetailService.getAllListProducts());
        fillCbbVoucher(voucherService.getListAll());
        listenCashChange();
        onChangeSearchProductSell();
    }
    
    private void setKhachHangBanLe() {
        txtNameCustomer.setText("Khách lẻ");
        if (txtNameCustomer.getText().equals("Khách lẻ")) {
            txtPhoneNumber.setText("");
            txtPhoneNumber.setEnabled(false);
            txtNameCustomer.setEnabled(false);
            txtPhoneNumber.setForeground(Color.red);
            txtPhoneNumber.setFont(txtPhoneNumber.getFont().deriveFont(Font.BOLD));
            if (khachHangService.findKhachHangLe() == null) {
                khachHangService.createKhachLe();
                khachHang = khachHangService.findKhachHangLe();
            } else {
                khachHang = khachHangService.findKhachHangLe();
            }
        }
    }
    
    private void showCustomerInfor(KhachHang khachHang) {
        txtNameCustomer.setText(khachHang.getFullName() != null ? khachHang.getFullName() : "");
        txtPhoneNumber.setText(khachHang.getPhoneNumber() != null ? khachHang.getPhoneNumber() : "");
    }
    
    private void initWebcam() {
        try {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getWebcams().get(0);
            
            webcam.setViewSize(size);
            
            panel = new WebcamPanel(webcam);
            panel.setPreferredSize(size);
            
            panelBarcode.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));
            panelBarcode.revalidate();
        } catch (WebcamException e) {
            if (this.isEnabled()) {
                JOptionPane.showMessageDialog(this, "Lỗi Cam");
            } else {
                JOptionPane.showMessageDialog(this, "Chưa Kết Nối Được Camera");
            }
        }
    }
    
    private void listenCashChange() {
        txtCash.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                try {
                    Double cash = null;
                    Double total = null;
                    Double back = null;
                    Double bank = null;
                    
                    if (!txtTotalMoneyAfterMinus.getText().isEmpty()) {
                        total = Double.valueOf(txtTotalMoneyAfterMinus.getText());
                    } else {
                        total = Double.valueOf(txtTotalMoney.getText());
                    }
                    
                    if (cbbTypePayment.getSelectedIndex() == 2) {
                        try {
                            cash = Double.valueOf(txtCash.getText());
                            if (cash > total) {
                                JOptionPane.showMessageDialog(null, "Tiền mặt đang vượt quá tổng tiền !");
                                txtBanking.setText("");
//                            txtCash.setText("");
                                return;
                            }
                            if (cash == total) {
                                txtBanking.setText("");
                            }
                            bank = total - cash;
                            txtBanking.setText(String.valueOf(new BigDecimal(bank)));
                            
                            txtPayBack.setText("");
                            txtPayBack.setEditable(false);
                        } catch (NumberFormatException e) {
                            if (txtCash.getText().isEmpty()) {
                                txtBanking.setText("");
                                return;
                            }
                            JOptionPane.showMessageDialog(null, "Tiền là số");
                            return;
                        }
                    } else {
                        try {
                            cash = Double.valueOf(txtCash.getText());
                            if (cash > total) {
                                back = cash - total;
                                txtPayBack.setText(String.valueOf(new BigDecimal(back)));
                            } else {
                                txtPayBack.setText("");
                            }
                        } catch (NumberFormatException e) {
                            if (txtCash.getText().isEmpty()) {
                                txtPayBack.setText("");
                                return;
                            }
                            txtPayBack.setText("");
                            JOptionPane.showMessageDialog(null, "Tiền phải là số");
                        }
                    }
                } catch (HeadlessException | NumberFormatException e) {
                    e.printStackTrace(System.out);
                }
            }
        });
    }
    
    private void scan() {
        try {
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
                    if (this.orderChose != null) {
                        String productDetailCode = String.valueOf(result);
                        ProductDetail productDetail = productDetailService.findByCode(productDetailCode);
                        Double sellPrice = productDetail.getSellPrice();
                        int quantityInUse = productDetailService.getQuantityProductDetailByCode(productDetailCode);
                        String quantity = JOptionPane.showInputDialog("Nhập Vào Số Lượng");
                        int quantityOrderDetail = 0;
                        boolean resUpdateQuantity = false;
                        if (quantity != null) {
                            if (!quantity.isEmpty()) {
                                try {
                                    quantityOrderDetail = Integer.parseInt(quantity);
                                    if (quantityOrderDetail > quantityInUse) {
                                        JOptionPane.showMessageDialog(this, "Só Lượng Bán Đang Lớn Hơn Số Lượng Tồn");
                                        return;
                                    } else if (quantityOrderDetail <= 0) {
                                        JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số Dương");
                                        return;
                                    } else {
                                        int quantityNew = quantityInUse - quantityOrderDetail;
                                        resUpdateQuantity = productDetailService.updateQuantityInStore(productDetailCode, quantityNew);
                                    }
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Số");
                                    return;
                                }
                                
                                int quantityInOrderDetail = orderDetailService.getQuantityOrderDetail(orderChose.getCode(), productDetailCode);
                                if (quantityInOrderDetail > 0) {
                                    boolean resUp = productDetailService.updateQuantityInStore(productDetailCode, productDetail.getQuantity() - quantityOrderDetail);
                                    boolean resUpOrderDetail = sellService.updateQuantityInCart(orderChose.getCode(), productDetailCode, quantityInOrderDetail + quantityOrderDetail, (quantityInOrderDetail + quantityOrderDetail) * sellPrice);
                                    fillTableCart(sellService.getAllListCart(orderChose.getCode()));
                                    fillTableProduct(productService.getAllProductResponse());
                                    fillTableProductDetail(productDetailService.getAllListProducts());
                                    checkOrderTotalMoney(orderChose);
                                } else {
                                    OrderDetail orderDetail = new OrderDetail();
                                    orderDetail.setIdOrder(this.orderChose.getId());
                                    orderDetail.setIdProductdetail(productDetail.getId());
                                    orderDetail.setPrice(productDetail.getSellPrice());
                                    orderDetail.setQuantity(quantityOrderDetail);
                                    orderDetail.setTotalMoney(productDetail.getSellPrice() * quantityOrderDetail);
                                    boolean res = sellService.addToCart(orderDetail);
                                    if (res || resUpdateQuantity) {
                                        JOptionPane.showMessageDialog(this, "Thêm Thành Công");
                                        productDetailDialog.dispose();
                                        fillTableCart(sellService.getAllListCart(orderChose.getCode()));
                                        fillTableProduct(productService.getAllProductResponse());
                                        fillTableProductDetail(productDetailService.getAllListProducts());
                                        checkOrderTotalMoney(orderChose);
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Đã Xảy ra Lỗi");
                                    }
                                }
                            }
                        } else {
                            tblDisplayProduct.clearSelection();
                        }
                    }
                }
                
            } while (true);
        } catch (Exception e) {
            System.out.println("Tắt Webcam");
        }
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
    
    private void fillCbbVoucher(List<Voucher> list) {
        tblModelVoucher = (DefaultTableModel) tbHienthiVoucher.getModel();
        tblModelVoucher.setRowCount(0);
        for (Voucher v : list) {
            Object[] row = {
                v.getId(),
                v.getCode(),
                v.getTen(),
                v.getQuantity(),
                v.getStart_Date(),
                v.getEnd_Date(),
                v.getMin_values_condition(),
                v.getType(),
                v.getValues(),
                v.getMax_values(),
                v.getDeleted() == 1 ? "Ngừng Hoạt Động" : "Hoạt Động"
            };
            tblModelVoucher.addRow(row);
        }
    }
    
    private void fillTableCart(List<CartResponse> list) {
        tblModelOrderDetail.setRowCount(0);
        for (CartResponse cartResponse : list) {
            Object[] row = {
                cartResponse.getProductDetailCode(),
                cartResponse.getProductName(),
                cartResponse.getNameSize(),
                cartResponse.getNameMaterial(),
                cartResponse.getNameColor(),
                cartResponse.getNameSole(),
                String.valueOf(new BigDecimal(cartResponse.getPrice())),
                cartResponse.getQuantity(),
                cartResponse.getTotalMoney()
            };
            tblModelOrderDetail.addRow(row);
        }
    }
    
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
                    boolean resUpdateCustomer = sellService.updateCustomerForOrder(khachHang.getId(), khachHang.getPhoneNumber(), orderChose.getCode());
                    fillTableOrder(orderService.getAllOdersByStatus(1));
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
        txtTotalMoneyAfterMinus.setText("");
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
                String.valueOf(new BigDecimal(orderResponse.getTotalMoney())),
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
                if (Integer.parseInt(productDetailResponse.getQuantity()) > 0) {
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
        tblModelOrderDetail.addColumn("Kích Cỡ");
        tblModelOrderDetail.addColumn("Chất Liệu");
        tblModelOrderDetail.addColumn("Màu Sắc");
        tblModelOrderDetail.addColumn("Loại Đế");
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
        if (null == order.getStatus()) {
            btnDeleteCart.setEnabled(false);
        } else {
            switch (order.getStatus()) {
                case 1 -> {
                    txtCodeOrder.setText(order.getCode());
                    txtCreateDate.setText(XDate.parseToString(order.getCreateDate()));
                    txtNameStaff.setText(nameEmployee);
                    if (order.getTotalMoney() != null) {
                        txtTotalMoney.setText(String.valueOf(new BigDecimal(order.getTotalMoney())));
                    }
                    btnDeleteCart.setEnabled(true);
                }
                case 2 -> {
                    txtCodeOrder.setText(order.getCode());
                    txtCreateDate.setText(XDate.parseToString(order.getCreateDate()));
                    txtNameStaff.setText(nameEmployee);
                    txtTotalMoney.setText(String.valueOf(order.getTotalMoney()));
                    cbbTypePayment.setSelectedItem(order.getPaymentMethod());
                    btnDeleteCart.setEnabled(false);
                }
                default ->
                    btnDeleteCart.setEnabled(false);
            }
        }
    }
    
    private void createOrder() {
        NewOrderRequest newOrderRequest = new NewOrderRequest();
        String codeOrder = sellService.generateCodeOrder();
        newOrderRequest.setIdCustomer(this.khachHang.getId());
        newOrderRequest.setIdEmployee(nhanVien.getID());
        newOrderRequest.setCode(codeOrder);
        boolean res = sellService.createNewOrder(newOrderRequest);
        if (res) {
            JOptionPane.showMessageDialog(this, "Tạo Hóa Đơn Thành Công");
            Order o = orderService.findByCode(codeOrder);
            this.orderChose = o;
            showInforOrder(o);
        }
    }
    
    private void checkOrderTotalMoney(Order order) {
        Double totalMoney = sellService.getTotalMoney(order.getCode());
        txtTotalMoney.setText(String.valueOf(new BigDecimal(totalMoney)));
        orderService.updateTotalMoney(totalMoney, orderChose.getId());
    }
    
    private void checkTotalMoney() {
        if (this.voucher != null) {
            if (voucher.getType().equals("Percentage")) {
                double totalMoneyLast = orderChose.getTotalMoney() * voucher.getValues() / 100;
                double roundedTotalMoneyLast = Math.round(totalMoneyLast * 100.0) / 100.0;
                txtTotalMoneyAfterMinus.setText(String.valueOf(roundedTotalMoneyLast));
            } else {
                totalMoneyLast = orderChose.getTotalMoney() - voucher.getValues();
                txtTotalMoneyAfterMinus.setText(String.valueOf(totalMoneyLast));
            }
        }
    }
    
    private void onChangeSearchProductSell() {
        txtSearchProduct.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        fillTableProductDetail(productDetailService.getAllListProducts());
                    } else {
                        String nameProduct = e.getDocument().getText(0, e.getDocument().getLength());
                        List<ProductDetailResponse> detailResponses = productDetailService.getAllListProducts();
                        List<ProductDetailResponse> resultList = new ArrayList<>();
                        if (detailResponses != null) {
                            for (ProductDetailResponse productDetailResponse : detailResponses) {
                                if (productDetailResponse.getProduct().toLowerCase().contains(nameProduct.toLowerCase())) {
                                    resultList.add(productDetailResponse);
                                }
                            }
                            fillTableProductDetail(resultList);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(SellPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTableProductDetail(productDetailService.getAllListProducts());
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        fillTableProductDetail(productDetailService.getAllListProducts());
                    } else {
                        String nameProduct = e.getDocument().getText(0, e.getDocument().getLength());
                        List<ProductDetailResponse> detailResponses = productDetailService.getAllListProducts();
                        List<ProductDetailResponse> resultList = new ArrayList<>();
                        if (detailResponses != null) {
                            for (ProductDetailResponse productDetailResponse : detailResponses) {
                                if (productDetailResponse.getProduct().toLowerCase().contains(nameProduct.toLowerCase())) {
                                    resultList.add(productDetailResponse);
                                }
                            }
                            fillTableProductDetail(resultList);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(SellPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        voucherDialog = new javax.swing.JDialog();
        panelVoucherContainer = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbHienthiVoucher = new javax.swing.JTable();
        txtSearchVoucher = new app.view.swing.TextField();
        jPanel1 = new javax.swing.JPanel();
        panelOrder = new javax.swing.JPanel();
        btnAdd = new app.view.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayOrder = new javax.swing.JTable();
        btnRefresh = new app.view.swing.MyButton();
        btnVoucher = new app.view.swing.MyButton();
        panelBarcodeContainer = new javax.swing.JPanel();
        panelBarcode = new javax.swing.JPanel();
        panelCart = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisplayCart = new javax.swing.JTable();
        btnDeleteCart = new app.view.swing.Button();
        btnCancel = new app.view.swing.MyButton();
        btnAddProductCart = new app.view.swing.Button();
        panelProduct = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDisplayProduct = new javax.swing.JTable();
        txtSearchProduct = new app.view.swing.TextField();
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
        txtTotalMoneyAfterMinus = new app.view.swing.TextField();

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

        tbHienthiVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã Số", "Tên Voucher", "Số Lượng", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Điều Kiện", "Kiểu", "Giá Trị", "Giá Trị Tối Đa", "Trạng thái"
            }
        ));
        tbHienthiVoucher.setRowHeight(25);
        tbHienthiVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHienthiVoucherMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbHienthiVoucher);

        txtSearchVoucher.setLabelText("Tìm kiếm voucher");

        javax.swing.GroupLayout panelVoucherContainerLayout = new javax.swing.GroupLayout(panelVoucherContainer);
        panelVoucherContainer.setLayout(panelVoucherContainerLayout);
        panelVoucherContainerLayout.setHorizontalGroup(
            panelVoucherContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addGroup(panelVoucherContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelVoucherContainerLayout.setVerticalGroup(
            panelVoucherContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVoucherContainerLayout.createSequentialGroup()
                .addGap(0, 53, Short.MAX_VALUE)
                .addComponent(txtSearchVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout voucherDialogLayout = new javax.swing.GroupLayout(voucherDialog.getContentPane());
        voucherDialog.getContentPane().setLayout(voucherDialogLayout);
        voucherDialogLayout.setHorizontalGroup(
            voucherDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVoucherContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        voucherDialogLayout.setVerticalGroup(
            voucherDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(voucherDialogLayout.createSequentialGroup()
                .addComponent(panelVoucherContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
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
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/plus.png"))); // NOI18N
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

        btnVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnVoucher.setText("Chọn Voucher");
        btnVoucher.setBorderColor(new java.awt.Color(23, 35, 51));
        btnVoucher.setColor(new java.awt.Color(23, 35, 51));
        btnVoucher.setColorClick(new java.awt.Color(23, 16, 71));
        btnVoucher.setColorOver(new java.awt.Color(23, 11, 84));
        btnVoucher.setRadius(10);
        btnVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoucherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelBarcodeContainer.setBackground(new java.awt.Color(255, 255, 255));
        panelBarcodeContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Barcode"));
        panelBarcodeContainer.setPreferredSize(new java.awt.Dimension(0, 10));
        panelBarcodeContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBarcode.setLayout(new javax.swing.OverlayLayout(panelBarcode));
        panelBarcodeContainer.add(panelBarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 240, 200));

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
        tblDisplayCart.setRowHeight(35);
        tblDisplayCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayCartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDisplayCart);

        btnDeleteCart.setBackground(new java.awt.Color(23, 35, 51));
        btnDeleteCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/dustbin.png"))); // NOI18N
        btnDeleteCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCartActionPerformed(evt);
            }
        });

        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy Hóa Đơn");
        btnCancel.setBorderColor(new java.awt.Color(23, 35, 51));
        btnCancel.setColor(new java.awt.Color(23, 35, 51));
        btnCancel.setColorClick(new java.awt.Color(23, 16, 71));
        btnCancel.setColorOver(new java.awt.Color(23, 11, 84));
        btnCancel.setRadius(10);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnAddProductCart.setBackground(new java.awt.Color(23, 35, 51));
        btnAddProductCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/plus.png"))); // NOI18N
        btnAddProductCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCartLayout = new javax.swing.GroupLayout(panelCart);
        panelCart.setLayout(panelCartLayout);
        panelCartLayout.setHorizontalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCartLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddProductCart, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteCart, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        panelCartLayout.setVerticalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddProductCart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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

        javax.swing.GroupLayout panelProductLayout = new javax.swing.GroupLayout(panelProduct);
        panelProduct.setLayout(panelProductLayout);
        panelProductLayout.setHorizontalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        panelProductLayout.setVerticalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        panelPay.setBackground(new java.awt.Color(255, 255, 255));
        panelPay.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn Hàng - Thanh Toán"));

        panelCustomer.setBackground(new java.awt.Color(255, 255, 255));
        panelCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));

        txtPhoneNumber.setEditable(false);
        txtPhoneNumber.setLabelText("Số điện thoại");

        txtNameCustomer.setEditable(false);
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
                    .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtNameStaff.setEditable(false);
        txtNameStaff.setLabelText("Tên Nhân Viên");

        txtCreateDate.setEditable(false);
        txtCreateDate.setLabelText("Ngày Tạo");

        txtTotalMoney.setEditable(false);
        txtTotalMoney.setLabelText("Tổng Tiền");

        txtCash.setLabelText("Tiền Mặt");

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

        txtTotalMoneyAfterMinus.setEditable(false);
        txtTotalMoneyAfterMinus.setLabelText("Tổng Tiền Sau Giảm Giá");

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
                        .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPayLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCodeOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                        .addComponent(txtNameStaff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCreateDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTotalMoney, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbTypePayment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTotalMoneyAfterMinus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelPayLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtBanking, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPayBack, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelPayLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBankingError, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCashError, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNameStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalMoneyAfterMinus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbTypePayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBanking, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPayBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBarcodeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
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

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        custommerDialog.setVisible(true);
    }//GEN-LAST:event_btnChooseActionPerformed

    private void tblDisplayProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayProductMouseClicked
        if (orderChose != null) {
            if (!(orderChose.getStatus() == 2 || orderChose.getStatus() == 3)) {
                int rowTbal = tblDisplayProduct.getSelectedRow();
                if (rowTbal >= 0) {
                    String productDetailCode = (String) tblDisplayProductDetail.getValueAt(rowTbal, 1);
                    Double sellPrice = Double.valueOf((String) tblDisplayProductDetail.getValueAt(rowTbal, 7));
                    int quantityInUse = productDetailService.getQuantityProductDetailByCode(productDetailCode);
                    ProductDetail productDetail = productDetailService.findByCode(productDetailCode);
                    String quantity = JOptionPane.showInputDialog("Nhập Vào Số Lượng");
                    int quantityOrderDetail = 0;
                    boolean resUpdateQuantity = false;
                    if (quantity != null) {
                        if (!quantity.isEmpty()) {
                            try {
                                quantityOrderDetail = Integer.parseInt(quantity);
                                if (quantityOrderDetail > quantityInUse) {
                                    JOptionPane.showMessageDialog(this, "Só Lượng Bán Đang Lớn Hơn Số Lượng Tồn");
                                    return;
                                } else if (quantityOrderDetail <= 0) {
                                    JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số Dương");
                                    return;
                                } else {
                                    int quantityNew = quantityInUse - quantityOrderDetail;
                                    resUpdateQuantity = productDetailService.updateQuantityInStore(productDetailCode, quantityNew);
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Số");
                                return;
                            }
                            
                            int quantityInOrderDetail = orderDetailService.getQuantityOrderDetail(orderChose.getCode(), productDetailCode);
                            if (quantityInOrderDetail > 0) {
                                boolean resUp = productDetailService.updateQuantityInStore(productDetailCode, productDetail.getQuantity() - quantityOrderDetail);
                                boolean resUpOrderDetail = sellService.updateQuantityInCart(orderChose.getCode(), productDetailCode, quantityInOrderDetail + quantityOrderDetail, (quantityInOrderDetail + quantityOrderDetail) * sellPrice);
                                fillTableCart(sellService.getAllListCart(orderChose.getCode()));
                                fillTableProduct(productService.getAllProductResponse());
                                fillTableProductDetail(productDetailService.getAllListProducts());
                                checkOrderTotalMoney(orderChose);
                            } else {
                                OrderDetail orderDetail = new OrderDetail();
                                orderDetail.setIdOrder(this.orderChose.getId());
                                orderDetail.setIdProductdetail(productDetail.getId());
                                orderDetail.setPrice(productDetail.getSellPrice());
                                orderDetail.setQuantity(quantityOrderDetail);
                                orderDetail.setTotalMoney(productDetail.getSellPrice() * quantityOrderDetail);
                                boolean res = sellService.addToCart(orderDetail);
                                if (res || resUpdateQuantity) {
                                    JOptionPane.showMessageDialog(this, "Thêm Thành Công");
                                    productDetailDialog.dispose();
                                    fillTableCart(sellService.getAllListCart(orderChose.getCode()));
                                    fillTableProduct(productService.getAllProductResponse());
                                    fillTableProductDetail(productDetailService.getAllListProducts());
                                    checkOrderTotalMoney(orderChose);
                                } else {
                                    JOptionPane.showMessageDialog(this, "Đã Xảy ra Lỗi");
                                }
                            }
                        }
                    } else {
                        tblDisplayProduct.clearSelection();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Thanh Toán");
                tblDisplayProduct.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Hóa Đơn Trước");
            tblDisplayProduct.clearSelection();
        }

    }//GEN-LAST:event_tblDisplayProductMouseClicked

    private void btnDeleteCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCartActionPerformed
        int row = tblDisplayCart.getSelectedRow();
        if (row >= 0) {
            int chose = JOptionPane.showConfirmDialog(parentFrame, "Bạn có muốn xóa ra khỏi giỏ hàng ?");
            if (chose == JOptionPane.YES_OPTION) {
                String productDetailCode = (String) tblDisplayCart.getValueAt(row, 0);
                ProductDetail productDetail = productDetailService.findByCode(productDetailCode);
                int quantityInStore = productDetailService.getQuantityProductDetailByCode(productDetailCode);
                int quantityInCart = sellService.getQuantityInCart(productDetailCode);
                boolean res = sellService.deleteFromCart(productDetail.getId());
                boolean resUpdateStore = productDetailService.updateQuantityInStore(productDetailCode, quantityInCart + quantityInStore);
                if (res && resUpdateStore) {
                    JOptionPane.showMessageDialog(parentFrame, "Xóa Thành Công");
                    fillTableCart(sellService.getAllListCart(this.orderChose.getCode()));
                    fillTableProduct(productService.getAllProductResponse());
                    fillTableProductDetail(productDetailService.getAllListProducts());
                    checkOrderTotalMoney(orderChose);
                }
            }
            
        }
    }//GEN-LAST:event_btnDeleteCartActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        if (this.orderChose == null) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Đơn Hàng Không Thể Thanh Toán");
            return;
        }
        
        if (txtTotalMoney.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Đơn Hàng Trống Không Thể Thanh Toán");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thanh toán hóa đơn này không ? ");
        if (confirm == JOptionPane.YES_OPTION) {
            String payMethod = (String) cbbTypePayment.getSelectedItem();
            Double cash = null;
            Double bank = null;
            Double customerMoney = null;
            if (payMethod.equalsIgnoreCase("Tiền Mặt")) {
                if (txtCash.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi !");
                    return;
                }
                customerMoney = Double.valueOf(txtCash.getText());
            } else if (payMethod.equalsIgnoreCase("Chuyển Khoản")) {
                customerMoney = Double.valueOf(txtBanking.getText());
            } else {
                if (txtCash.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi !");
                    return;
                }
                customerMoney = Double.valueOf(txtCash.getText()) + Double.valueOf(txtBanking.getText());
            }
            
            if (customerMoney < this.orderChose.getTotalMoney()) {
                JOptionPane.showMessageDialog(this, "Số Tiền Khách Đưa Phải Lớn Hơn Hoặc Bằng Tiền ");
                return;
            }
            
            if (this.voucher != null) {
                boolean resVoucher = orderService.addVoucher(voucher.getId(), orderChose.getTotalMoney() - Double.valueOf(txtTotalMoneyAfterMinus.getText()), orderChose.getCode());
            }
            boolean res = orderService.payOrder(payMethod, customerMoney, orderChose.getId());
            if (res) {
                JOptionPane.showMessageDialog(this, "Thanh Toán Thành Công");
                int conPrint = JOptionPane.showConfirmDialog(null, "Bạn có muốn in hóa đơn không");
                if (conPrint == JOptionPane.YES_OPTION) {
                    if (tblDisplayCart.getRowCount() <= 0) {
                        return;
                    }
                    
                    int row = tblDisplayOrder.getSelectedRow();
                    if (row >= 0) {
                        String codeOrder = (String) tblDisplayOrder.getValueAt(row, 1);
                        Order order = orderService.findByCode(codeOrder);
                        List<CartResponse> orderDetailRepsonse = sellService.getAllListCart(order.getCode());
                        OrderResponse orderResponsePrint = new OrderResponse();
                        List<OrderResponse> orderResponses = orderService.getAllOrderView();
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
                            if (txtTotalMoneyAfterMinus.getText().isEmpty()) {
                                map.put("totalMoneydiscount", new BigDecimal(txtTotalMoney.getText()) + "");
                            } else {
                                map.put("totalMoneydiscount", new BigDecimal(Double.parseDouble(txtTotalMoneyAfterMinus.getText())) + "");
                                map.put("moneyReduce", new BigDecimal(order.getTotalMoney() - Double.valueOf(txtTotalMoneyAfterMinus.getText())) + "");
                            }
                            map.put("totalMoney", new BigDecimal(order.getTotalMoney()) + "");
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
                    
                }
                fillTableOrder(orderService.getAllOdersByStatus(1));
                tblModelOrderDetail.setRowCount(0);
                this.orderChose = null;
                clearFormInforSell();
            } else {
                
            }
        }
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
                txtBanking.setText("");
                txtCash.setEditable(true);
                labelCashError.setVisible(true);
            }
            
            case 1 -> {
                txtBanking.setEditable(true);
                txtCash.setText("");
                txtCash.setEditable(false);
                labelCashError.setVisible(false);
                labelBankingError.setVisible(true);
                
                if (!txtTotalMoneyAfterMinus.getText().isEmpty()) {
                    txtBanking.setText(String.valueOf(txtTotalMoneyAfterMinus.getText()));
                } else {
                    if (orderChose.getTotalMoney() != null) {
                        txtBanking.setText(String.valueOf(txtTotalMoney.getText()));
                    }
                }
            }
            
            case 2 -> {
                txtBanking.setVisible(true);
                txtCash.setEditable(true);
                labelCashError.setVisible(true);
                labelBankingError.setVisible(true);
                txtBanking.setText("");
            }
            
            default ->
                throw new AssertionError();
        }
    }//GEN-LAST:event_cbbTypePaymentActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.grabFocus();
    }//GEN-LAST:event_formMouseClicked

    private void txtBankingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBankingFocusLost

    }//GEN-LAST:event_txtBankingFocusLost

    private void btnChoose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoose1ActionPerformed
        setKhachHangBanLe();
        boolean resUpdateCustomer = sellService.updateCustomerForOrder(khachHang.getId(), khachHang.getPhoneNumber(), orderChose.getCode());
        fillTableOrder(orderService.getAllOdersByStatus(1));
    }//GEN-LAST:event_btnChoose1ActionPerformed

    private void tblDisplayProductDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayProductDetailMouseClicked

    }//GEN-LAST:event_tblDisplayProductDetailMouseClicked

    private void tblDisplayCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayCartMouseClicked
        int row = tblDisplayCart.getSelectedRow();
        if (row >= 0) {
            String productDetailCode = (String) tblDisplayCart.getValueAt(row, 0);
            ProductDetail pd = productDetailService.findByCode(productDetailCode);
            if (pd.getCode() != null) {
                this.btnAddProductCart.setVisible(true);
            } else {
                this.btnAddProductCart.setVisible(false);
            }
        }
    }//GEN-LAST:event_tblDisplayCartMouseClicked

    private void tbHienthiVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHienthiVoucherMouseClicked
        int row = tbHienthiVoucher.getSelectedRow();
        if (row >= 0) {
            int id = (int) tbHienthiVoucher.getValueAt(row, 0);
            this.voucher = voucherService.findById(id);
            System.out.println(voucher.getValues() + "/" + voucher.getType());
            if (voucher.getType().equalsIgnoreCase("Fixed Amount")) {
                Double totalMoneyBefore = Double.valueOf(txtTotalMoney.getText());
                Double moneyReduce = Double.valueOf(voucher.getValues());
                txtTotalMoneyAfterMinus.setText(String.valueOf(new BigDecimal(totalMoneyBefore - moneyReduce)));
            }
            
            if (voucher.getType().equalsIgnoreCase("Percentage")) {
                Double totalMoneyBefore = Double.valueOf(txtTotalMoney.getText());
                Double totalAfter = totalMoneyBefore * voucher.getValues() / 100;
                BigDecimal totalMoneyAfterMinus = new BigDecimal(String.valueOf(totalMoneyBefore - totalAfter));
                totalMoneyAfterMinus = totalMoneyAfterMinus.setScale(2, RoundingMode.HALF_UP);
                txtTotalMoneyAfterMinus.setText(totalMoneyAfterMinus.toString());
            }
            
            voucherDialog.dispose();
        }
    }//GEN-LAST:event_tbHienthiVoucherMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (this.orderChose == null) {
            JOptionPane.showMessageDialog(this, "Chưa Chọn Hóa Đơn !");
            return;
        }
        
        if (this.orderChose.getStatus() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa Đơn Đã Được Thanh Toán !");
            return;
        }
        
        if (this.orderChose.getStatus() == 1) {
            int conf = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Hủy Hóa Đơn " + orderChose.getCode());
            if (conf == JOptionPane.YES_OPTION) {
                boolean res = orderService.cancelOrder(this.orderChose.getCode());
                if (res) {
                    JOptionPane.showMessageDialog(this, "Hóa Đơn Đã Được Hủy!");
                    fillTableOrder(orderService.getAllOdersByStatus(3));
                } else {
                    JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi Vui Lòng Thử Lại !");
                }
            }
        }

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddProductCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductCartActionPerformed
        int row = tblDisplayCart.getSelectedRow();
        if (row >= 0) {
            String productDetailCode = (String) tblDisplayCart.getValueAt(row, 0);
            int quantityInCart = (int) tblDisplayCart.getValueAt(row, 7);
            double sellPrice = Double.parseDouble((String) tblDisplayCart.getValueAt(row, 6));
            int quantityInStore = productDetailService.getQuantityProductDetailByCode(productDetailCode);
            String quatityInput = JOptionPane.showInputDialog("Nhập Vào Số Lượng");
            int quantityNew = 0;
            try {
                quantityNew = Integer.parseInt(quatityInput);
                if (quantityNew <= 0) {
                    JOptionPane.showMessageDialog(this, "Bạn Phải Nhập Số Lượng Lên Hơn 0 !");
                    return;
                }
                if (quantityNew > quantityInStore) {
                    JOptionPane.showMessageDialog(this, "Số Lượng Vượt Quá Số Lượng Tôn !");
                    return;
                }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi (Số Lượng Phải Là Số)!");
                return;
            }
            
            double newTotalMoneyOrderDetail = sellPrice * quantityNew;
            int sumQuatityOld = quantityInCart + quantityInStore;
            int newQuantityInStore = sumQuatityOld - quantityNew;
            boolean resUpdateStore = productDetailService.updateQuantityInStore(productDetailCode, newQuantityInStore);
            boolean updateQuatityInCart = sellService.updateQuantityInCart(orderChose.getCode(), productDetailCode, quantityNew, newTotalMoneyOrderDetail);
            if (resUpdateStore && updateQuatityInCart) {
                JOptionPane.showMessageDialog(this, "Sửa Số Lượng Thành Công");
                fillTableProductDetail(productDetailService.getAllListProducts());
                fillTableCart(sellService.getAllListCart(this.orderChose.getCode()));
                checkOrderTotalMoney(orderChose);
            }
            
        }
    }//GEN-LAST:event_btnAddProductCartActionPerformed

    private void btnVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoucherActionPerformed
        if (this.orderChose == null) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn");
            return;
        }
        
        if (this.orderChose.getStatus() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Được Thanh Toán !");
            return;
        }
        
        if (this.orderChose.getStatus() == 3) {
            JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Được Hủy !");
            return;
        }
        
        if (this.orderChose.getTotalMoney() == 0) {
            JOptionPane.showMessageDialog(this, "Hóa Đơn Này Chưa Có Sản Phẩm Nào !");
            return;
        }
        
        voucherDialog = new JDialog(parentFrame);
        voucherDialog.getContentPane().add(panelVoucherContainer);
        voucherDialog.pack();
        voucherDialog.setLocationRelativeTo(null);
        voucherDialog.setVisible(true);
        if (this.orderChose == null) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn !");
        } else {
            try {
                double totalMoney = Double.parseDouble(txtTotalMoney.getText());
                fillCbbVoucher(voucherService.getListAllByMinValue(totalMoney));
            } catch (NumberFormatException e) {
                
            }
        }
    }//GEN-LAST:event_btnVoucherActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        this.btnAddProductCart.setVisible(false);
        tblDisplayOrder.clearSelection();
        btnCancel.setEnabled(false);
        tblDisplayProductDetail.clearSelection();
        tblDisplayCart.clearSelection();
        tblModelOrderDetail.setRowCount(0);
        btnChoose.setEnabled(false);
        btnChoose1.setEnabled(false);
        this.orderChose = null;
        this.voucher = null;
        clearFormInforSell();
        btnPay.setEnabled(true);
        setKhachHangBanLe();
        fillTableOrder(orderService.getAllOdersByStatus(1));
        fillTableProduct(productService.getAllProductResponse());
        fillTableProductDetail(productDetailService.getAllListProducts());
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tblDisplayOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayOrderMouseClicked
        int row = tblDisplayOrder.getSelectedRow();
        if (row >= 0) {
            clearFormInforSell();
            btnChoose.setEnabled(true);
            btnChoose1.setEnabled(true);
            String codeOrder = (String) tblDisplayOrder.getValueAt(row, 1);
            Order order = orderService.findByCode(codeOrder);
            this.orderChose = order;
            showInforOrder(orderChose);
            if (order.getStatus() == 2) {
                btnPay.setEnabled(false);
            } else {
                btnPay.setEnabled(true);
            }
            if (order.getStatus() == 1) {
                btnCancel.setEnabled(true);
            } else {
                btnCancel.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tblDisplayOrderMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        clearFormInforSell();
        createOrder();
        fillTableOrder(orderService.getAllOdersByStatus(1));
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.Button btnAdd;
    private app.view.swing.Button btnAddProductCart;
    private app.view.swing.MyButton btnCancel;
    private app.view.swing.Button btnChoose;
    private app.view.swing.Button btnChoose1;
    private app.view.swing.Button btnDeleteCart;
    private javax.swing.ButtonGroup btnGroupStatusOrder;
    private app.view.swing.Button btnPay;
    private app.view.swing.MyButton btnRefresh;
    private app.view.swing.MyButton btnVoucher;
    private app.view.swing.Combobox cbbTypePayment;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
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
    private javax.swing.JPanel panelVoucherContainer;
    private javax.swing.JDialog productDetailDialog;
    private javax.swing.JTable tbHienthiVoucher;
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
    private app.view.swing.TextField txtSearchVoucher;
    private app.view.swing.TextField txtTotalMoney;
    private app.view.swing.TextField txtTotalMoneyAfterMinus;
    private javax.swing.JDialog voucherDialog;
    // End of variables declaration//GEN-END:variables
}
