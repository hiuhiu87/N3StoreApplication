/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.Voucher;
import app.service.VoucherService;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class DiscountPanel extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private VoucherService vcs = new VoucherService();
    private Voucher v = new Voucher();

    /**
     * Creates new form discountPanel
     */
    public DiscountPanel() {
        initComponents();
        dtm = (DefaultTableModel) tbHienthiVoucher.getModel();
        loadTable();
    }

    public void loadTable() {
        try {
            String name = txtTimKiem.getText();
            ArrayList<Voucher> listV = vcs.getList(name);
            dtm.setRowCount(0);
            for (Voucher v : listV) {
                dtm.addRow(new Object[]{v.getId(), v.getTen(), v.getCode(), v.getQuantity(), v.getStart_Date(), v.getEnd_Date(), v.getMin_values_condition(), v.getType(), v.getValues(), v.getMax_values(), v.getDeleted() == 1 ? "Hoạt động" : "Đã hết hạn"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Voucher getform() {        
        String name = txtTen.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được bỏ trống!");
            return null;
        }
        if (name.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Tên phải là chữ!");
            return null;
        }
        String code = txtMaSo.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã không được bỏ trống!");
            return null;
        }
        if (name.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Mã bao gồm là chữ và số!");
            return null;
        }
        String quantity = txtSoLuong.getText().trim();
        if (quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "số lượng không được bỏ trống!");
            return null;
        }
        if (!quantity.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số!");
            return null;
        }
        String start_Date = txtNgayStar.getText().trim();
        if (start_Date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được bỏ trống!");
            return null;
        }
        String end_Date = txtNgayEnd.getText().trim();
        if (end_Date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được bỏ trống!");
            return null;
        }
        String min_values_condition = txtDKGiaTriMin.getText().trim();
        if (min_values_condition.isEmpty()) {
            JOptionPane.showMessageDialog(this, "điều kiện tối thiểu không được bỏ trống!");
            return null;
        }
        if (!min_values_condition.matches("[[0-9][.][0-9]]+")) {
            JOptionPane.showMessageDialog(this, "Điều kiện tối thiểu phải là số");
            return null;
        }
        String type = txtKieu.getText().trim();
        if (type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kiểu không được bỏ trống!");
            return null;
        }
        if (type.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Kiểu bao gồm là chữ và số!");
            return null;
        }
        String values = txtGiaTri.getText().trim();
        if (values.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị không được bỏ trống!");
            return null;
        }
        if (!values.matches("[[0-9][.][0-9]]+")) {
            JOptionPane.showMessageDialog(this, "Giá trị phải là số");
            return null;
        }
        String max_values = txtGiaTriMax.getText().trim();
        if (max_values.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị tối đa không được bỏ trống!");
            return null;
        }
        if (!values.matches("[[0-9][.][0-9]]+")) {
            JOptionPane.showMessageDialog(this, "Giá trị tối đa phải là số");
            return null;
        }
        int deleted = v.getDeleted();
        if (rdHoatDong.isSelected()) {
            deleted = 1;
        } else {
            deleted = 0;
        }
        Voucher vr = new Voucher(name, code, Integer.valueOf(quantity), java.sql.Date.valueOf(start_Date), java.sql.Date.valueOf(end_Date), Float.valueOf(min_values_condition), type, Float.valueOf(values), Float.valueOf(max_values), Integer.valueOf(deleted));
        return vr;
    }

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
        txtNgayEnd = new app.view.swing.TextField();
        rdHoatDong = new javax.swing.JRadioButton();
        txtTen = new app.view.swing.TextField();
        txtMaSo = new app.view.swing.TextField();
        txtSoLuong = new app.view.swing.TextField();
        txtNgayStar = new app.view.swing.TextField();
        txtDKGiaTriMin = new app.view.swing.TextField();
        txtKieu = new app.view.swing.TextField();
        txtGiaTri = new app.view.swing.TextField();
        rdHetHan = new javax.swing.JRadioButton();
        txtGiaTriMax = new app.view.swing.TextField();
        btnAdd2 = new app.view.swing.ButtonGradient();
        btnUpdate = new app.view.swing.ButtonGradient();
        btnRemove = new app.view.swing.ButtonGradient();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHienthiVoucher = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ VOUCHER");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtNgayEnd.setLabelText("Ngày Kết Thúc");

        buttonGroup1.add(rdHoatDong);
        rdHoatDong.setText("Hoạt động");

        txtTen.setLabelText("Tên Voucher");

        txtMaSo.setLabelText("Mã Số");

        txtSoLuong.setLabelText("Số Lượng");

        txtNgayStar.setLabelText("Ngày Bắt Đầu");

        txtDKGiaTriMin.setLabelText("Điều Kiện Giá Trị Tối Thiểu");

        txtKieu.setLabelText("Kiểu");

        txtGiaTri.setLabelText("Giá Trị");

        buttonGroup1.add(rdHetHan);
        rdHetHan.setText("Đã hết hạn");

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

        btnRemove.setText("Xóa");
        btnRemove.setColor2(new java.awt.Color(23, 35, 51));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaSo, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayStar, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKieu, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTriMax, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDKGiaTriMin, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdHoatDong)
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdHetHan)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNgayStar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdHoatDong)
                            .addComponent(rdHetHan)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDKGiaTriMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaTriMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Thông tin Voucher");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/view/poster-giay.jpg"))); // NOI18N

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

        tbHienthiVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên Voucher", "Mã Số", "Số Lượng", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Điều Kiện", "Kiểu", "Giá Trị", "Giá Trị Tối Đa", "Trạng thái"
            }
        ));
        tbHienthiVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHienthiVoucherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHienthiVoucher);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(510, 510, 510))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(48, 48, 48))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thêm?", "add", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            Voucher v = getform();
            if (vcs.add(v) > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadTable();
            }

        }

    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn cập nhật?", "update", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            int row = tbHienthiVoucher.getSelectedRow();
            String name = txtTimKiem.getText();
            int id = Integer.valueOf(tbHienthiVoucher.getValueAt(row, 0).toString());
            Voucher v = this.getform();
            vcs.update(v, id);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadTable();

        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa?", "remove", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            int row = tbHienthiVoucher.getSelectedRow();
            String name = txtTimKiem.getText();
            Voucher v = vcs.getList(name).get(row);
            vcs.remove(v);
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            loadTable();

        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String tk = txtTimKiem.getText().trim();
        if(tk.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin muốn tìm kiếm!");
            return;
        }
        vcs.getList(tk);
        loadTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tbHienthiVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHienthiVoucherMouseClicked
        int row = tbHienthiVoucher.getSelectedRow();
        txtTen.setText(tbHienthiVoucher.getValueAt(row, 1).toString());
        txtMaSo.setText(tbHienthiVoucher.getValueAt(row, 2).toString());
        txtSoLuong.setText(tbHienthiVoucher.getValueAt(row, 3).toString());
        txtNgayStar.setText(tbHienthiVoucher.getValueAt(row, 4).toString());
        txtNgayEnd.setText(tbHienthiVoucher.getValueAt(row, 5).toString());
        txtDKGiaTriMin.setText(tbHienthiVoucher.getValueAt(row, 6).toString());
        txtKieu.setText(tbHienthiVoucher.getValueAt(row, 7).toString());
        txtGiaTri.setText(tbHienthiVoucher.getValueAt(row, 8).toString());
        txtGiaTriMax.setText(tbHienthiVoucher.getValueAt(row, 9).toString());
        String deleted = tbHienthiVoucher.getValueAt(row, 10).toString();
        if (deleted.equals("Hoạt động")) {
            rdHoatDong.setSelected(true);
        } else {
            rdHetHan.setSelected(true);
        }
    }//GEN-LAST:event_tbHienthiVoucherMouseClicked

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        String tk = txtTimKiem.getText();
        if(tk.length() == 0){
            loadTable();
        }
    }//GEN-LAST:event_txtTimKiemCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.ButtonGradient btnAdd2;
    private app.view.swing.ButtonGradient btnRemove;
    private app.view.swing.ButtonGradient btnSearch;
    private app.view.swing.ButtonGradient btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdHetHan;
    private javax.swing.JRadioButton rdHoatDong;
    private javax.swing.JTable tbHienthiVoucher;
    private app.view.swing.TextField txtDKGiaTriMin;
    private app.view.swing.TextField txtGiaTri;
    private app.view.swing.TextField txtGiaTriMax;
    private app.view.swing.TextField txtKieu;
    private app.view.swing.TextField txtMaSo;
    private app.view.swing.TextField txtNgayEnd;
    private app.view.swing.TextField txtNgayStar;
    private app.view.swing.TextField txtSoLuong;
    private app.view.swing.TextField txtTen;
    private app.view.swing.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
