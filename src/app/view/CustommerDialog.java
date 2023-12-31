/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package app.view;

import app.model.KhachHang;
import app.service.KhachHang_Service;
import app.service.SellService;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

/**
 *
 * @author H.Long
 */
public class CustommerDialog extends javax.swing.JDialog {

    DefaultTableModel model;
    private int index = -1;
    private final SellService sellService = new SellService();
    private final KhachHang_Service khachHangService = new KhachHang_Service();
    private KhachHang khachHang;

    /**
     * Creates new form CustommerDialog
     */
    public CustommerDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        fillTable(sellService.getAllKhachHang());
        onSearch();
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    private void onSearch() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        fillTable(khachHangService.getAll());
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        List<KhachHang> listSearch = khachHangService.getAll();
                        List<KhachHang> listRes = new ArrayList<>();
                        if (listSearch != null) {
                            for (KhachHang khachHang : listSearch) {
                                if ((khachHang.getFullName().toLowerCase().contains(namePrdSearch.toLowerCase()))
                                        || (khachHang.getPhoneNumber().toLowerCase().contains(namePrdSearch.toLowerCase()))) {
                                    listRes.add(khachHang);
                                }
                            }
                            fillTable(listRes);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTable(khachHangService.getAll());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        fillTable(khachHangService.getAll());
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        List<KhachHang> listSearch = khachHangService.getAll();
                        List<KhachHang> listRes = new ArrayList<>();
                        if (listSearch != null) {
                            for (KhachHang khachHang : listSearch) {
                                if ((khachHang.getFullName().toLowerCase().contains(namePrdSearch.toLowerCase()))
                                        || (khachHang.getPhoneNumber().toLowerCase().contains(namePrdSearch.toLowerCase()))) {
                                    listRes.add(khachHang);
                                }
                            }
                            fillTable(listRes);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        });
    }

    void fillTable(List<KhachHang> list) {
        model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        for (KhachHang khachHang : list) {
            model.addRow(khachHang.toDaTaRowLong());
        }
    }

    KhachHang readForm() {
        String nameCustommer = txtTenKhachHang.getText();

        boolean gender;
        gender = rdNam.isSelected();

        String phone = txtSoDienThoai.getText();
        String email = txtEmail.getText();
        Date ngaySinh = txtDate.getDate();
        boolean status;
        status = rdHD.isSelected();
        String address = txtDiaChi.getText();
        return new KhachHang(nameCustommer, email, gender, phone, address, ngaySinh, status, khachHangService.generateNextModelCode());
    }

    boolean validateFormCustommer() {
        if (txtTenKhachHang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
            return false;
        }
        String regex = "^[a-zA-ZÀ-ỹĐđ\\s]+$";
        String nameCustommer = txtTenKhachHang.getText();

        if (!nameCustommer.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng chỉ được chứa chữ cái");
            return false;
        }

        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(txtEmail.getText());

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Email không đúng định dạng");
            return false;
        }

        if (txtSoDienThoai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
            return false;
        }

        String phoneRegex = "0\\d{9,10}";
        if (!txtSoDienThoai.getText().matches(phoneRegex)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng định dạng");
            return false;
        }

        return true;

    }

    void showData(int index) {
        KhachHang kh = sellService.getAllKhachHang().get(index);
        txtTenKhachHang.setText(kh.getFullName());
        txtSoDienThoai.setText(kh.getPhoneNumber());
        txtDiaChi.setText(kh.getAddress());
        txtEmail.setText(kh.getEmail());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tabbedPaneCustom1 = new app.view.swing.TabbedPaneCustom();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnChonKH = new app.view.swing.ButtonGradient();
        jPanel2 = new javax.swing.JPanel();
        txtTenKhachHang = new app.view.swing.TextField();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        txtSoDienThoai = new app.view.swing.TextField();
        txtEmail = new app.view.swing.TextField();
        rdNgungHD = new javax.swing.JRadioButton();
        rdHD = new javax.swing.JRadioButton();
        btnThem = new app.view.swing.ButtonGradient();
        btnUpdate = new app.view.swing.ButtonGradient();
        btnLamMoi = new app.view.swing.ButtonGradient();
        txtDiaChi = new app.view.swing.TextField();
        txtDate = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabbedPaneCustom1.setBackground(new java.awt.Color(255, 255, 255));
        tabbedPaneCustom1.setSelectedColor(new java.awt.Color(23, 35, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm:");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Full name", "Email", "Gender", "Phone", "Address", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setRowHeight(35);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        btnChonKH.setText("Chọn");
        btnChonKH.setColor1(new java.awt.Color(23, 35, 51));
        btnChonKH.setColor2(new java.awt.Color(23, 35, 51));
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(33, 33, 33)
                .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        tabbedPaneCustom1.addTab("Danh sách khách hàng", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtTenKhachHang.setLabelText("Tên Khách Hàng");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        txtSoDienThoai.setLabelText("Số Điện Thoại");

        txtEmail.setLabelText("Email");

        buttonGroup2.add(rdNgungHD);
        rdNgungHD.setText("Ngừng Hoạt Động");

        buttonGroup2.add(rdHD);
        rdHD.setSelected(true);
        rdHD.setText("Đang hoạt động");

        btnThem.setText("Thêm");
        btnThem.setColor1(new java.awt.Color(23, 35, 51));
        btnThem.setColor2(new java.awt.Color(23, 35, 51));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.setColor1(new java.awt.Color(23, 35, 51));
        btnUpdate.setColor2(new java.awt.Color(23, 35, 51));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Reset");
        btnLamMoi.setColor1(new java.awt.Color(23, 35, 51));
        btnLamMoi.setColor2(new java.awt.Color(23, 35, 51));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtDiaChi.setLabelText("Địa Chỉ");

        txtDate.setToolTipText("Ngày Sinh");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdHD, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdNgungHD))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdNam, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50)
                                        .addComponent(rdNu, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(171, 171, 171)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(171, 171, 171))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdNam)
                    .addComponent(rdNu))
                .addGap(18, 18, 18)
                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdHD)
                    .addComponent(rdNgungHD))
                .addGap(44, 44, 44)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        tabbedPaneCustom1.addTab("Cập nhập khách hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        index = tblKhachHang.getSelectedRow();
        System.out.println(index);
        if (index >= 0) {
            if (validateFormCustommer()) {
                KhachHang khachHang = this.readForm();
                int id = Integer.parseInt(tblKhachHang.getValueAt(index, 0).toString());
                System.out.println(id);
                int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa ?", "Update", JOptionPane.YES_NO_OPTION);
                if (chon == 0) {
                    if (sellService.updateKhachHang(khachHang, id) != 0) {
                        JOptionPane.showMessageDialog(this, "Cập nhập thành công Khách Hàng : " + khachHang.getFullName());
                        fillTable(sellService.getAllKhachHang());
                    }
                }
            }
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateFormCustommer()) {
            KhachHang khachHangAdd = this.readForm();
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm ?", "Add", JOptionPane.YES_NO_OPTION);
            if (chon == 0) {
                if (sellService.addKhachHang(khachHangAdd) != 0) {
                    JOptionPane.showMessageDialog(this, "Thêm mới khách hàng thành công");
                    fillTable(sellService.getAllKhachHang());
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        index = tblKhachHang.getSelectedRow();
        showData(index);
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            String code = (String) tblKhachHang.getValueAt(row, 0);
            KhachHang khachHangChose = khachHangService.findByCode(code);
            this.khachHang = khachHangChose;
            this.dispose();
        }
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        txtTenKhachHang.setText("");
        rdHD.setSelected(true);
        rdNam.setSelected(true);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CustommerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CustommerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CustommerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CustommerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                CustommerDialog dialog = new CustommerDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.ButtonGradient btnChonKH;
    private app.view.swing.ButtonGradient btnLamMoi;
    private app.view.swing.ButtonGradient btnThem;
    private app.view.swing.ButtonGradient btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JRadioButton rdHD;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNgungHD;
    private javax.swing.JRadioButton rdNu;
    private app.view.swing.TabbedPaneCustom tabbedPaneCustom1;
    private javax.swing.JTable tblKhachHang;
    private com.toedter.calendar.JDateChooser txtDate;
    private app.view.swing.TextField txtDiaChi;
    private app.view.swing.TextField txtEmail;
    private javax.swing.JTextField txtSearch;
    private app.view.swing.TextField txtSoDienThoai;
    private app.view.swing.TextField txtTenKhachHang;
    // End of variables declaration//GEN-END:variables
}
