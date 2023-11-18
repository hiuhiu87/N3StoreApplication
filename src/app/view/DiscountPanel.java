/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.Voucher;
import app.service.VoucherService;
import app.view.swing.EventPagination;
import app.view.swing.PaginationItemRenderStyle1;
import com.google.zxing.common.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.util.StringUtil;

/**
 *
 * @author Admin
 */
public class DiscountPanel extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private VoucherService vcs = new VoucherService();
    private Voucher v = new Voucher();
    private List<Voucher> listPhanTrangVoucher = new ArrayList<>();
    private int currentpage = 1;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Creates new form discountPanel
     */
    public DiscountPanel() {
        initComponents();
        dtm = (DefaultTableModel) tbHienthiVoucher.getModel();
        loadTable();
        pn.setPaginationItemRender(new PaginationItemRenderStyle1());
        pn.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                loadDataTablePhanTrang(page);
            }
        });
        loadDataTablePhanTrang(1);
//        DiscountPanel panel = new DiscountPanel();
//        panel.scheduleUpdateTask();
        updateNgayHH();
    }

    public void loadTable() {
        try {
            String name = txtTimKiem.getText();
            ArrayList<Voucher> listV = vcs.getList(name);
            dtm.setRowCount(0);
            for (Voucher v : listV) {
                dtm.addRow(new Object[]{v.getId(), v.getCode(), v.getTen()
                        , v.getQuantity(), v.getStart_Date(), v.getEnd_Date()
                        , v.getMin_values_condition(), v.getType(), v.getValues()
                        , v.getMax_values()
                        , v.getDeleted() == 1 ? "Hoạt động" : "Đã hết hạn"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillTable(List<Voucher> list) {
        dtm.setRowCount(0);
        for (Voucher v : list) {
            dtm.addRow(new Object[]{v.getId(), v.getCode(), v.getTen()
                    , v.getQuantity(), v.getStart_Date(), v.getEnd_Date()
                    , v.getMin_values_condition(), v.getType(), v.getValues()
                    , v.getMax_values()
                    , v.getDeleted() == 1 ? "Hoạt động" : "Đã hết hạn"});
        }

    }

    public void loadDataTablePhanTrang(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            dtm = (DefaultTableModel) tbHienthiVoucher.getModel();
            dtm.setRowCount(0);
            fillTable(vcs.getListAll());
            currentpage = page;
            int count = vcs.count();
            int sumPage = (int) Math.ceil((double) count / limit);
            listPhanTrangVoucher = vcs.getListPhanTrang(offset, limit);
            fillTable(listPhanTrangVoucher);
            pn.setPagegination(page, sumPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Voucher getform() {
        String name = txtTen.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this
                    , "Tên không được bỏ trống!");
            return null;
        }
        if (name.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this
                    , "Tên phải là chữ!");
            return null;
        }
        String code = txtMaSo.getText().trim();

        String quantity = txtSoLuong.getText().trim();
        if (quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this
                    , "số lượng không được bỏ trống!");
            return null;
        }
        if (!quantity.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this
                    , "Số lượng phải là số!");
            return null;
        }
        Date start_Date = jDateNgayBatDau.getDate();
        if (start_Date == null) {
            JOptionPane.showMessageDialog(this
                    , "Ngày bắt đầu không được bỏ trống!");
            return null;
        }
        Date end_Date = jDateNgayKetThuc.getDate();
        if (end_Date == null) {
            JOptionPane.showMessageDialog(this
                    , "Ngày kết thúc không được bỏ trống!");
            return null;
        }
        if (end_Date.getTime() < start_Date.getTime()) {
            JOptionPane.showMessageDialog(this
                    , "Ngày kết thúc phải sau ngày bắt đầu!");
            return null;
        }
        //        --------------------
        Date currentDate = new Date();
        try {
            if (end_Date.before(currentDate)) {
                JOptionPane.showMessageDialog(this
                        , "Ngày kết thúc không hợp lệ!");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        --------------------
        String min_values_condition = txtDKGiaTriMin.getText().trim();
        if (min_values_condition.isEmpty()) {
            JOptionPane.showMessageDialog(this
                    , "điều kiện tối thiểu không được bỏ trống!");
            return null;
        }
        if (!min_values_condition.matches("[[0-9][.][0-9]]+")) {
            JOptionPane.showMessageDialog(this
                    , "Điều kiện tối thiểu phải là số");
            return null;
        }
        String type = cbbKieu.getSelectedItem().toString();

        String values = txtGiaTri.getText().trim();
        if (values.isEmpty()) {
            JOptionPane.showMessageDialog(this
                    , "Giá trị không được bỏ trống!");
            return null;
        }

        //-----------------
        if (type.equals("Percentage")) {
            if (Integer.parseInt(values) > 100) {
                JOptionPane.showMessageDialog(this
                        , "Giá trị không được vượt quá 100%");
                return null;
            }
        }
        if (!values.matches("[[0-9][.][0-9]]+")) {
            JOptionPane.showMessageDialog(this
                    , "Giá trị phải là số");
            return null;
        }
        String max_values = txtGiaTriMax.getText().trim();
        if (max_values.isEmpty()) {
            JOptionPane.showMessageDialog(this
                    , "Giá trị tối đa không được bỏ trống!");
            return null;
        }
        if (!values.matches("[[0-9][.][0-9]]+")) {
            JOptionPane.showMessageDialog(this
                    , "Giá trị tối đa phải là số");
            return null;
        }
        Voucher vr = new Voucher(name, code
                , Integer.valueOf(quantity)
                , start_Date, end_Date
                , Float.valueOf(min_values_condition)
                , type, Float.valueOf(values)
                , Float.valueOf(max_values));
        return vr;
    }

    public void updateNgayHH() {
        List<Date> listDate = new ArrayList<>();
        Date ngayHT = new Date();
        for (Voucher v : vcs.checkNgay()) {
            if (ngayHT.after(v.getEnd_Date())) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = dateFormat.format(v.getEnd_Date());
                vcs.updateDeleted(false, v.getId());
                loadDataTablePhanTrang(1);
            }

            if (!(ngayHT.after(v.getEnd_Date()))) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = dateFormat.format(v.getEnd_Date());
                vcs.updateDeleted(true, v.getId());
                loadDataTablePhanTrang(1);
            }
        }
    }
    
//    public void scheduleUpdateTask() {
//        scheduler.scheduleAtFixedRate(() -> {
//            updateNgayHH();
//        }, 0, 30, TimeUnit.SECONDS);
//    }
//    public void shutdownScheduler() {
//        scheduler.shutdown();
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtTimKiem = new app.view.swing.TextField();
        btnSearch = new app.view.swing.ButtonGradient();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtMaSo = new app.view.swing.TextField();
        txtSoLuong = new app.view.swing.TextField();
        txtDKGiaTriMin = new app.view.swing.TextField();
        txtGiaTri = new app.view.swing.TextField();
        txtGiaTriMax = new app.view.swing.TextField();
        btnAdd2 = new app.view.swing.ButtonGradient();
        btnUpdate = new app.view.swing.ButtonGradient();
        btnRemove = new app.view.swing.ButtonGradient();
        jDateNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jDateNgayBatDau = new com.toedter.calendar.JDateChooser();
        cbbKieu = new javax.swing.JComboBox<>();
        txtTen = new app.view.swing.TextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pn = new app.view.swing.Pagination();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHienthiVoucher = new javax.swing.JTable();
        btnClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ VOUCHER");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtTimKiem.setLabelText("Nhập Tên");
        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });

        btnSearch.setText("Tìm Kiếm");
        btnSearch.setColor2(new java.awt.Color(23, 35, 51));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tìm kiếm");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtMaSo.setEditable(false);
        txtMaSo.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSo.setLabelText("Mã Số");

        txtSoLuong.setLabelText("Số Lượng");

        txtDKGiaTriMin.setLabelText("Điều Kiện Giá Trị Tối Thiểu");

        txtGiaTri.setLabelText("Giá Trị");

        txtGiaTriMax.setLabelText("Giá Trị Tối Đa");

        btnAdd2.setText("Thêm");
        btnAdd2.setColor2(new java.awt.Color(23, 35, 51));
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.setColor2(new java.awt.Color(23, 35, 51));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRemove.setText("Cập thật trạng thái");
        btnRemove.setColor2(new java.awt.Color(23, 35, 51));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jDateNgayKetThuc.setDateFormatString("yyyy-MM-dd");

        jDateNgayBatDau.setDateFormatString("yyyy-MM-dd");

        cbbKieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fixed Amount", "Percentage" }));

        txtTen.setLabelText("Tên Voucher");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaSo, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(jDateNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGiaTri, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(txtGiaTriMax, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(txtDKGiaTriMin, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(cbbKieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDKGiaTriMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbbKieu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateNgayKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Thông tin Voucher");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/poster-giay.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Danh sách Voucher");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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
        jScrollPane1.setViewportView(tbHienthiVoucher);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnClear.setText("Clear Form");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(510, 510, 510))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(21, 21, 21))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 416, Short.MAX_VALUE)
                                            .addComponent(btnClear))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
            .addGroup(layout.createSequentialGroup()
                .addGap(487, 487, 487)
                .addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed

        int a = JOptionPane.showConfirmDialog(this
                , "Bạn chắc chắn muốn thêm?", "add"
                , JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            Voucher v = getform();
            v.setCode(vcs.generateNextModelCode());
            if (vcs.add(v) > 0) {
                JOptionPane.showMessageDialog(this
                        , "Thêm thành công");
                loadDataTablePhanTrang(currentpage);
                updateNgayHH();
            }

        }

    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int a = JOptionPane.showConfirmDialog(this
                , "Bạn chắc chắn muốn cập nhật?", "update"
                , JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            int row = tbHienthiVoucher.getSelectedRow();
            String name = txtTimKiem.getText();
            int id = Integer.valueOf(tbHienthiVoucher.getValueAt(row, 0)
                    .toString());
            Voucher v = this.getform();
            if (vcs.update(v, id) > 0) {
                JOptionPane.showMessageDialog(this
                        , "Cập nhật thành công");
                loadDataTablePhanTrang(currentpage);
                updateNgayHH();

            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int a = JOptionPane.showConfirmDialog(this
                , "Bạn chắc chắn muốn cập nhật trạng thái?"
                , "update trạng thái", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            int row = tbHienthiVoucher.getSelectedRow();
            String name = txtTimKiem.getText();
            Voucher v = vcs.getList(name).get(row);
            if (vcs.remove(v) > 0) {
                loadTable();
                JOptionPane.showMessageDialog(this
                        , "cập nhật thành công");
            }

        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String tk = txtTimKiem.getText().trim();
        if (tk.isEmpty()) {
            JOptionPane.showMessageDialog(this
                    , "Vui lòng nhập thông tin muốn tìm kiếm!");
            return;
        }
        vcs.getList(tk);
        loadTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tbHienthiVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHienthiVoucherMouseClicked
        int row = tbHienthiVoucher.getSelectedRow();
        txtTen.setText(tbHienthiVoucher.getValueAt(row, 2).toString());
        txtMaSo.setText((String) tbHienthiVoucher.getValueAt(row, 1));
        txtSoLuong.setText(tbHienthiVoucher.getValueAt(row, 3).toString());
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(tbHienthiVoucher.getValueAt(row, 4).toString());
            jDateNgayBatDau.setDate(date1);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(tbHienthiVoucher.getValueAt(row, 5).toString());
            jDateNgayKetThuc.setDate(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtDKGiaTriMin.setText(tbHienthiVoucher.getValueAt(row, 6).toString());
        cbbKieu.setSelectedItem(tbHienthiVoucher.getValueAt(row, 7).toString());
        txtGiaTri.setText(tbHienthiVoucher.getValueAt(row, 8).toString());
        txtGiaTriMax.setText(tbHienthiVoucher.getValueAt(row, 9).toString());
        String deleted = tbHienthiVoucher.getValueAt(row, 10).toString();
//        if (deleted.equals("Hoạt động")) {
//            rdHoatDong.setSelected(true);
//        } else {
//            rdHetHan.setSelected(true);
//        }
    }//GEN-LAST:event_tbHienthiVoucherMouseClicked

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        String tk = txtTimKiem.getText();
        if (tk.length() == 0) {
            loadTable();
        }
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtTen.setText("");
        txtMaSo.setText("");
        txtSoLuong.setText("");
        cbbKieu.setSelectedIndex(0);
        txtGiaTri.setText("");
        txtGiaTriMax.setText("");
        txtDKGiaTriMin.setText("");
        jDateNgayBatDau.setDate(null);
        jDateNgayKetThuc.setDate(null);
//        rdHoatDong.setSelected(true);
//        rdHetHan.setSelected(false);

    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.ButtonGradient btnAdd2;
    private javax.swing.JButton btnClear;
    private app.view.swing.ButtonGradient btnRemove;
    private app.view.swing.ButtonGradient btnSearch;
    private app.view.swing.ButtonGradient btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbKieu;
    private com.toedter.calendar.JDateChooser jDateNgayBatDau;
    private com.toedter.calendar.JDateChooser jDateNgayKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private app.view.swing.Pagination pn;
    private javax.swing.JTable tbHienthiVoucher;
    private app.view.swing.TextField txtDKGiaTriMin;
    private app.view.swing.TextField txtGiaTri;
    private app.view.swing.TextField txtGiaTriMax;
    private app.view.swing.TextField txtMaSo;
    private app.view.swing.TextField txtSoLuong;
    private app.view.swing.TextField txtTen;
    private app.view.swing.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
