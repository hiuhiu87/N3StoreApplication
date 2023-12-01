/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.KhachHang;
import app.model.Order;
import app.model.Voucher;
import app.response.ProductBoughtCustomerResponse;
import app.service.KhachHang_Service;
import app.view.swing.EventPagination;
import app.view.swing.PaginationItemRenderStyle1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class CustomerPanel extends javax.swing.JPanel {
    
    private final KhachHang_Service service = new KhachHang_Service();
    int row = -1;
    private DefaultTableModel model = new DefaultTableModel();
    private final DefaultTableModel productBought = new DefaultTableModel();
    private List<KhachHang> listPhanTrangkh = new ArrayList<>();
    
    public CustomerPanel() {
        initComponents();
        tblDisplayProductBought.setModel(productBought);
        filltable(service.getAll());
        ph.setPaginationItemRender(new PaginationItemRenderStyle1());
        ph.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadDataTablePhanTrang(page);
            }
        });
        loadDataTablePhanTrang(1);
        addColumnsProductBought();
    }
    
    private void addColumnsProductBought() {
        productBought.addColumn("Mã HĐ");
        productBought.addColumn("Mã CTSP");
        productBought.addColumn("Tên Sản Phẩm");
        productBought.addColumn("Kích Cỡ");
        productBought.addColumn("Chất Liệu");
        productBought.addColumn("Màu Sắc");
        productBought.addColumn("Loại Đế");
    }
    
    private void fillTableProductBought(List<ProductBoughtCustomerResponse> list) {
        productBought.setRowCount(0);
        for (ProductBoughtCustomerResponse productBoughtCustomerResponse : list) {
            Object[] row = {
                productBoughtCustomerResponse.getOrderCode(),
                productBoughtCustomerResponse.getProductDetailCode(),
                productBoughtCustomerResponse.getNameProduct(),
                productBoughtCustomerResponse.getNameSize(),
                productBoughtCustomerResponse.getNameMaterial(),
                productBoughtCustomerResponse.getNameColor(),
                productBoughtCustomerResponse.getNameSole()
            };
            productBought.addRow(row);
        }
    }
    
    public void loadDataTablePhanTrang(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            model = (DefaultTableModel) tblListCustomer.getModel();
            model.setRowCount(0);
            filltable(service.getAll());
            int count = service.count();
            System.out.println(count);
            int sumPage = (int) Math.ceil((double) count / limit);
            System.out.println(sumPage);
            
            listPhanTrangkh = service.getListPhanTrang(offset, limit);
            filltable(listPhanTrangkh);
            ph.setPagegination(page, sumPage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void filltable(List<KhachHang> lst) {
        DefaultTableModel model = (DefaultTableModel) tblListCustomer.getModel();
        model.setRowCount(0);
        for (KhachHang khachHang : lst) {
            model.addRow(khachHang.toDaTaRow());
        }
        
    }
    
    void showdata(int index) {
        KhachHang kh = listPhanTrangkh.get(index);
        txtCode.setText(String.valueOf(kh.getCode()));
        txtFullname.setText(kh.getFullName());
        txtEmail.setText(kh.getEmail());
        txtPhoneNumber.setText(kh.getPhoneNumber());
        txtAddress.setText(kh.getAddress());
        txtngaysinh.setDate(kh.getBirthDate());
    }
    
    KhachHang reafrom() {
        KhachHang cd = new KhachHang();
        cd.setCode(service.generateNextModelCode());
        cd.setFullName(txtFullname.getText());
        cd.setEmail(txtEmail.getText());
        cd.setPhoneNumber(txtPhoneNumber.getText());
        cd.setAddress(txtAddress.getText());
        cd.setBirthDate(txtngaysinh.getDate());
        return cd;
    }
    
    boolean check() {
        if (txtFullname.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui long nhap ten");
            txtFullname.requestFocus();
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email");
            txtEmail.requestFocus();
            return false;
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        
        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(txtEmail.getText().trim()).matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email");
            txtEmail.requestFocus();
            return false;
        }
        
        if (txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui long nhap dia chi");
            txtAddress.requestFocus();
            return false;
        }
        if (txtPhoneNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại");
            txtPhoneNumber.requestFocus();
            return false;
        }
        
        String phoneRegex = "^(0[3|5|7|8|9])+([0-9]{8})$";
        
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(txtPhoneNumber.getText());
        
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải đúng định dạng");
            txtPhoneNumber.requestFocus();
            return false;
        }
        
        return true;
        
    }
    
    boolean checkTrung() {
        List<KhachHang> lstXM = listPhanTrangkh;
        for (int i = 0; i < lstXM.size(); i++) {
            if (txtPhoneNumber.getText().equalsIgnoreCase(lstXM.get(i).getPhoneNumber())) {
                JOptionPane.showMessageDialog(this, "Trùng số điện thoại");
                return false;
            }
        }
        return true;
    }
    
    boolean checkTrung1() {
        List<KhachHang> lstXM = listPhanTrangkh;
        for (int i = 0; i < lstXM.size(); i++) {
            if (txtEmail.getText().equalsIgnoreCase(lstXM.get(i).getEmail())) {
                JOptionPane.showMessageDialog(this, "Trùng gmail");
                return false;
            }
        }
        return true;
    }
    
    void reset() {
        txtAddress.setText("");
        txtCode.setText("");
        txtngaysinh.setDate(null);
        txtEmail.setText("");
        txtFullname.setText("");
        txtPhoneNumber.setText("");
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
        labelDateError = new javax.swing.JLabel();
        txtCode = new app.view.swing.TextField();
        labelNameError1 = new javax.swing.JLabel();
        txtngaysinh = new com.toedter.calendar.JDateChooser();
        panelCustomerBought = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayProductBought = new javax.swing.JTable();
        panelListCustomer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListCustomer = new javax.swing.JTable();
        ph = new app.view.swing.Pagination();
        txtSearchCustomer = new app.view.swing.TextField();
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

        labelDateError.setForeground(new java.awt.Color(255, 0, 51));
        labelDateError.setPreferredSize(new java.awt.Dimension(0, 14));

        txtCode.setEditable(false);
        txtCode.setLabelText("Mã");

        labelNameError1.setForeground(new java.awt.Color(255, 0, 51));
        labelNameError1.setPreferredSize(new java.awt.Dimension(0, 14));

        txtngaysinh.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout panelCustomerInforLayout = new javax.swing.GroupLayout(panelCustomerInfor);
        panelCustomerInfor.setLayout(panelCustomerInforLayout);
        panelCustomerInforLayout.setHorizontalGroup(
            panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtFullname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelNameError1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelDateError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addComponent(labelEmailError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addComponent(labelPhoneNumberError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerInforLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(labelAddressError, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelCustomerInforLayout.setVerticalGroup(
            panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
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
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNameError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelDateError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCustomerInforLayout.createSequentialGroup()
                        .addGroup(panelCustomerInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCustomerInforLayout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(70, 70, 70)
                        .addComponent(labelAddressError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
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
                "Mã", "Họ và Tên", "Email", "SĐT", "Địa Chỉ", "Ngày Sinh"
            }
        ));
        tblListCustomer.setRowHeight(35);
        tblListCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblListCustomer);

        txtSearchCustomer.setLabelText("Tìm Kiếm");
        txtSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCustomerActionPerformed(evt);
            }
        });
        txtSearchCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCustomerKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelListCustomerLayout = new javax.swing.GroupLayout(panelListCustomer);
        panelListCustomer.setLayout(panelListCustomerLayout);
        panelListCustomerLayout.setHorizontalGroup(
            panelListCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListCustomerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(495, 495, 495))
            .addGroup(panelListCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                    .addGroup(panelListCustomerLayout.createSequentialGroup()
                        .addComponent(txtSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelListCustomerLayout.setVerticalGroup(
            panelListCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListCustomerLayout.createSequentialGroup()
                .addComponent(txtSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        labelPanelCustomer.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelPanelCustomer.setForeground(new java.awt.Color(0, 0, 0));
        labelPanelCustomer.setText("Quản Lý Khách Hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCustomerInfor, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCustomerBought, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelPanelCustomer)
                .addGap(463, 463, 463))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPanelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCustomerBought, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCustomerInfor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        if (check() && checkTrung() && checkTrung1()) {
            int a = JOptionPane.showConfirmDialog(this, "bạn có muốn thêm nữa không?");
            if (a == 0) {
                KhachHang cd = reafrom();
                if (service.addSach(cd) > 0) {
                    JOptionPane.showMessageDialog(this, "thêm thành công");
                    filltable(service.getAll());
                    loadDataTablePhanTrang(1);
                    reset();
                } else {
                    JOptionPane.showMessageDialog(this, "thêm thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        
        row = tblListCustomer.getSelectedRow();
        KhachHang cd = reafrom();
        int ma = Integer.parseInt(tblListCustomer.getValueAt(row, 0).toString());
        if (check() && checkTrung() && checkTrung1()) {
            int a = JOptionPane.showConfirmDialog(this, "bạn có muốn update nữa không?");
            if (a == 0) {
                if (service.updateSV(cd, ma) > 0) {
                    JOptionPane.showMessageDialog(this, "update thành công");
                    filltable(service.getAll());
                    loadDataTablePhanTrang(1);
                    reset();
                } else {
                    JOptionPane.showMessageDialog(this, "update thất bại");
                }
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblListCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListCustomerMouseClicked
        row = tblListCustomer.getSelectedRow();
        showdata(row);
        int rowSel = tblListCustomer.getSelectedRow();
        if (rowSel >= 0) {
            String codeCustomer = (String) tblListCustomer.getValueAt(row, 0);
            fillTableProductBought(service.getAllProductBoughtByCustomer(codeCustomer));
        }
    }//GEN-LAST:event_tblListCustomerMouseClicked

    private void txtSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCustomerActionPerformed

    }//GEN-LAST:event_txtSearchCustomerActionPerformed

    private void txtSearchCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCustomerKeyReleased
        String textSearch = txtSearchCustomer.getText().toLowerCase().trim();
        System.out.println(textSearch);
        List<KhachHang> list = service.getAll();
        List<KhachHang> listSearch = new ArrayList<>();
        
        for (KhachHang khachhang : list) {
            String idString = String.valueOf(khachhang.getId());
            String fullName = khachhang.getFullName().toLowerCase();
            if (idString.contains(textSearch) || fullName.contains(textSearch)) {
                listSearch.add(khachhang);
            }
        }
        
        if (listSearch.isEmpty()) {
            filltable(service.getAll());
            loadDataTablePhanTrang(1);
            
        } else {
            if (txtSearchCustomer.getText().equals("")) {
                clearTable(model);
                filltable(list);
                loadDataTablePhanTrang(1);
            } else {
                clearTable(model);
                for (KhachHang khachhang : listSearch) {
                    model.addRow(khachhang.toDaTaRow());
                }
                filltable(listSearch);
                
            }
        }
    }//GEN-LAST:event_txtSearchCustomerKeyReleased
    private void clearTable(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

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
    private app.view.swing.Pagination ph;
    private javax.swing.JTable tblDisplayProductBought;
    private javax.swing.JTable tblListCustomer;
    private app.view.swing.TextField txtAddress;
    private app.view.swing.TextField txtCode;
    private app.view.swing.TextField txtEmail;
    private app.view.swing.TextField txtFullname;
    private app.view.swing.TextField txtPhoneNumber;
    private app.view.swing.TextField txtSearchCustomer;
    private com.toedter.calendar.JDateChooser txtngaysinh;
    // End of variables declaration//GEN-END:variables
}
