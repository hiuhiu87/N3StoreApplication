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
        txtTen = new app.view.swing.TextField();
        txtMaSo = new app.view.swing.TextField();
        txtSoLuong = new app.view.swing.TextField();
        txtNgayStar = new app.view.swing.TextField();
        txtDKGiaTriMin = new app.view.swing.TextField();
        txtKieu = new app.view.swing.TextField();
        txtGiaTri = new app.view.swing.TextField();
        txtGiaTriMax = new app.view.swing.TextField();
        txtNgayEnd = new app.view.swing.TextField();
        rdHoatDong = new javax.swing.JRadioButton();
        btnAdd2 = new app.view.swing.ButtonGradient();
        btnUpdate = new app.view.swing.ButtonGradient();
        btnRemove = new app.view.swing.ButtonGradient();
        txtTimKiem = new app.view.swing.TextField();
        btnSearch = new app.view.swing.ButtonGradient();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHienthiVoucher = new javax.swing.JTable();
        rdHetHan = new javax.swing.JRadioButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÝ VOUCHER");

        txtTen.setLabelText("Tên Voucher");

        txtMaSo.setLabelText("Mã Số");

        txtSoLuong.setLabelText("Số Lượng");

        txtNgayStar.setLabelText("Ngày Bắt Đầu");

        txtDKGiaTriMin.setLabelText("Điều Kiện Giá Trị Tối Thiểu");

        txtKieu.setLabelText("Kiểu");

        txtGiaTri.setLabelText("Giá Trị");

        txtGiaTriMax.setLabelText("Giá Trị Tối Đa");

        txtNgayEnd.setLabelText("Ngày Kết Thúc");

        buttonGroup1.add(rdHoatDong);
        rdHoatDong.setText("Hoạt động");

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

        txtTimKiem.setLabelText("Nhập Tên");

        btnSearch.setText("Tìm Kiếm");
        btnSearch.setColor2(new java.awt.Color(23, 35, 51));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

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

        buttonGroup1.add(rdHetHan);
        rdHetHan.setText("Đã hết hạn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
            .addGroup(layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSo, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayStar, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKieu, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTriMax, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDKGiaTriMin, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdHoatDong)
                                .addGap(38, 38, 38)
                                .addComponent(rdHetHan))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(200, 200, 200))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNgayStar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDKGiaTriMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaTriMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdHoatDong)
                    .addComponent(rdHetHan))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        String name = txtTimKiem.getText();
        vcs.getList(name);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.ButtonGradient btnAdd2;
    private app.view.swing.ButtonGradient btnRemove;
    private app.view.swing.ButtonGradient btnSearch;
    private app.view.swing.ButtonGradient btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
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
