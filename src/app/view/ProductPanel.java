/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.Category;
import app.model.Color;
import app.model.Company;
import app.model.Material;
import app.model.Size;
import app.model.Sole;
import app.request.AddProductRequest;
import app.response.ProductResponse;
import app.service.CategoryService;
import app.service.ColorService;
import app.service.CompanyService;
import app.service.MaterialService;
import app.service.ProductService;
import app.service.SizeService;
import app.service.SoleService;
import app.util.XFileExcel;
import app.util.XGenerateQRCode;
import app.view.swing.Combobox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author Admin
 */
public class ProductPanel extends javax.swing.JPanel {

    private final CategoryService categoryService = new CategoryService();
    private final ProductService productService = new ProductService();
    private final ColorService colorService = new ColorService();
    private final CompanyService companyService = new CompanyService();
    private final SizeService sizeService = new SizeService();
    private final MaterialService materialService = new MaterialService();
    private final SoleService soleService = new SoleService();
    private final DefaultComboBoxModel comboBoxModelCategory;
    private final DefaultComboBoxModel comboBoxModelColor;
    private final DefaultComboBoxModel comboBoxModelMaterial;
    private final DefaultComboBoxModel comboBoxModelSize;
    private final DefaultComboBoxModel comboBoxModelSole;
    private final DefaultComboBoxModel comboBoxModelCompany;
    private final DefaultTableModel tableModelProduct;
    private final ColorDialog colorDialog;
    private final MaterialDialog materialDialog;
    private final SoleDialog soleDialog;
    private final CompanyDialog companyDialog;
    private final SizeDialog sizeDialog;

    public ProductPanel(JFrame parentFrame) {
        initComponents();
        comboBoxModelCategory = new DefaultComboBoxModel();
        comboBoxModelColor = new DefaultComboBoxModel();
        comboBoxModelMaterial = new DefaultComboBoxModel();
        comboBoxModelSize = new DefaultComboBoxModel();
        comboBoxModelSole = new DefaultComboBoxModel();
        comboBoxModelCompany = new DefaultComboBoxModel();
        tableModelProduct = new DefaultTableModel();
        colorDialog = new ColorDialog(parentFrame, true);
        materialDialog = new MaterialDialog(parentFrame, true);
        soleDialog = new SoleDialog(parentFrame, true);
        companyDialog = new CompanyDialog(parentFrame, true);
        sizeDialog = new SizeDialog(parentFrame, true);
        tblProductDisplay.setModel(tableModelProduct);
        cbbCategory.setModel(comboBoxModelCategory);
        cbbColor.setModel(comboBoxModelColor);
        cbbCompany.setModel(comboBoxModelCompany);
        cbbMaterial.setModel(comboBoxModelMaterial);
        cbbSize.setModel(comboBoxModelSize);
        cbbSole.setModel(comboBoxModelSole);
        addColumnTableProduct();
        fillComboBoxCategory(categoryService.getAll());
        fillComboBoxColor(colorService.getAllColors());
        fillComboBoxCompany(companyService.getAllCompany());
        fillComboBoxMaterial(materialService.getAllMaterials());
        fillComboBoxSize(sizeService.getAllSizes());
        fillComboBoxSole(soleService.getAllSoles());
        fillTableProduct(productService.getAllProductResponse());
    }

    private void fillComboBoxCategory(List<Category> categorys) {
        List<String> categoryName = new ArrayList<>();
        categoryName.add("Chọn Danh Mục Giày");
        for (Category category : categorys) {
            categoryName.add(category.getName());
        }
        comboBoxModelCategory.addAll(categoryName);
        cbbCategory.setSelectedIndex(0);
    }

    private void fillTableProduct(List<ProductResponse> list) {
        if (list != null) {
            tableModelProduct.setRowCount(0);
            int stt = 0;
            for (ProductResponse productResponse : list) {
                stt++;
                Object[] row = {
                    stt,
                    productResponse.getName(),
                    productResponse.getCategory(),
                    productResponse.getDeleted() ? "Ngừng Hoạt Động" : "Hoạt Động"
                };
                tableModelProduct.addRow(row);
            }
        }
    }

    private void fillComboBoxColor(List<Color> list) {
        List<String> colorList = new ArrayList<>();
        for (Color color : list) {
            colorList.add(color.getName());
        }
        comboBoxModelColor.removeAllElements();
        comboBoxModelColor.addAll(colorList);
        cbbColor.setSelectedIndex(0);
    }

    private void fillComboBoxMaterial(List<Material> list) {
        List<String> materialList = new ArrayList<>();
        for (Material material : list) {
            materialList.add(material.getName());
        }
        comboBoxModelMaterial.removeAllElements();
        comboBoxModelMaterial.addAll(materialList);
        cbbMaterial.setSelectedIndex(0);
    }

    private void fillComboBoxCompany(List<Company> list) {
        List<String> companyList = new ArrayList<>();
        for (Company company : list) {
            companyList.add(company.getName());
        }
        comboBoxModelCompany.removeAllElements();
        comboBoxModelCompany.addAll(companyList);
        cbbCompany.setSelectedIndex(0);
    }

    private void fillComboBoxSole(List<Sole> list) {
        List<String> soleList = new ArrayList<>();
        for (Sole sole : list) {
            soleList.add(sole.getName());
        }
        comboBoxModelSole.removeAllElements();
        comboBoxModelSole.addAll(soleList);
        cbbSole.setSelectedIndex(0);
    }

    private void fillComboBoxSize(List<Size> list) {
        List<String> sizeList = new ArrayList<>();
        for (Size size : list) {
            sizeList.add(size.getName());
        }
        comboBoxModelSize.removeAllElements();
        comboBoxModelSize.addAll(sizeList);
        cbbSize.setSelectedIndex(0);
    }

    private void addColumnTableProduct() {
        tableModelProduct.addColumn("STT");
        tableModelProduct.addColumn("Tên Sản Phẩm");
        tableModelProduct.addColumn("Danh Mục");
        tableModelProduct.addColumn("Trạng Thái");
    }

    private void onCloseJDialog(JDialog dialog, String nameFill) {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                switch (nameFill) {
                    case "Color" -> {
                        fillComboBoxColor(colorService.getAllColors());

                    }
                    case "Category" -> {
                        fillComboBoxCategory(categoryService.getAll());
                    }

                    case "Company" -> {
                        fillComboBoxCompany(companyService.getAllCompany());
                    }
                    case "Size" -> {
                        fillComboBoxSize(sizeService.getAllSizes());
                    }
                    case "Sole" -> {
                        fillComboBoxSole(soleService.getAllSoles());
                    }
                    case "Material" -> {
                        fillComboBoxMaterial(materialService.getAllMaterials());
                    }
                    default ->
                        throw new AssertionError();
                }
            }

        });
    }

    private void onChange() {
//            txtSearchProduct.getDocument().addDocumentListener(new DocumentListener() {
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    try {
//                        if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
//                            refreshTablePrdDetail();
//                        } else {
//                            String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
//                            ArrayList<ProductDetail> listSearch = serviceProductDetail.searchByName(namePrdSearch);
//                            if (listSearch != null) {
//                                fillTableProductDetail(listSearch);
//                            }
//                        }
//                    } catch (BadLocationException ex) {
//                        Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//    
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                    fillTableProductDetail(listProductDetail);
//                }
//    
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    try {
//                        if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
//                            refreshTablePrdDetail();
//                        } else {
//                            String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
//                            ArrayList<ProductDetail> listSearch = serviceProductDetail.searchByName(namePrdSearch);
//                            if (listSearch != null) {
//                                fillTableProductDetail(listSearch);
//                            }
//                        }
//                    } catch (BadLocationException ex) {
//                        Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDisplayList = new app.view.swing.TabbedPaneCustom();
        panelProduct = new javax.swing.JPanel();
        labelNameProduct = new javax.swing.JLabel();
        txtNameProduct = new app.view.swing.TextField();
        btnAdd = new app.view.swing.MyButton();
        btnUpdate = new app.view.swing.MyButton();
        labelNameProductSearch = new javax.swing.JLabel();
        txtSearchNameProduct = new app.view.swing.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductDisplay = new javax.swing.JTable();
        btnRefesh = new app.view.swing.MyButton();
        labelNamePrdError = new javax.swing.JLabel();
        btnNew = new app.view.swing.MyButton();
        labelCategory = new javax.swing.JLabel();
        cbbCategory = new app.view.swing.Combobox();
        btnAddCatefory = new app.view.swing.Button();
        panelDetailProduct = new javax.swing.JPanel();
        panelFunction = new javax.swing.JPanel();
        labelNameProductTab2 = new javax.swing.JLabel();
        cbbNameProductDetail = new app.view.swing.Combobox();
        labelPriceIn = new javax.swing.JLabel();
        txtPriceIn = new app.view.swing.TextField();
        labelPriceOut = new javax.swing.JLabel();
        txtPriceOut = new app.view.swing.TextField();
        btnAddDetailProduct = new app.view.swing.Button();
        btnRefresh = new app.view.swing.Button();
        btnUpdateDetailProduct = new app.view.swing.Button();
        labelDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        labelPriceInError = new javax.swing.JLabel();
        labelPriceOutError = new javax.swing.JLabel();
        labelQuantityError = new javax.swing.JLabel();
        btnStatusDetailProd = new app.view.swing.Button();
        panelAtribute = new javax.swing.JPanel();
        labelCompany = new javax.swing.JLabel();
        cbbCompany = new app.view.swing.Combobox();
        cbbColor = new app.view.swing.Combobox();
        labelCPU = new javax.swing.JLabel();
        btnAddCompany = new app.view.swing.Button();
        btnAddColor = new app.view.swing.Button();
        cbbSole = new app.view.swing.Combobox();
        btnAddSole = new app.view.swing.Button();
        labelRam = new javax.swing.JLabel();
        labelROM = new javax.swing.JLabel();
        btnAddMaterial = new app.view.swing.Button();
        cbbMaterial = new app.view.swing.Combobox();
        labelCPU1 = new javax.swing.JLabel();
        cbbSize = new app.view.swing.Combobox();
        btnAddSize = new app.view.swing.Button();
        labelRam1 = new javax.swing.JLabel();
        panelDisplayProduct = new javax.swing.JPanel();
        panelListProduct = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        txtSearchProduct = new app.view.swing.TextField();
        labelSearchByPrice = new javax.swing.JLabel();
        txtPriceMin = new app.view.swing.TextField();
        txtPriceMax = new app.view.swing.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisplayPrdDetail = new javax.swing.JTable();
        btnImport = new app.view.swing.Button();
        btnExport = new app.view.swing.Button();
        btnFilterPrice = new app.view.swing.Button();
        btnResetTable = new app.view.swing.Button();

        panelDisplayList.setForeground(new java.awt.Color(255, 255, 255));
        panelDisplayList.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        panelDisplayList.setSelectedColor(new java.awt.Color(23, 35, 51));
        panelDisplayList.setUnselectedColor(new java.awt.Color(23, 44, 76));

        panelProduct.setBackground(new java.awt.Color(255, 255, 255));

        labelNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelNameProduct.setText("Tên Sản Phẩm");

        txtNameProduct.setLabelText("Tên Sản Phẩm");

        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.setBorderColor(new java.awt.Color(23, 35, 51));
        btnAdd.setColor(new java.awt.Color(23, 35, 51));
        btnAdd.setColorClick(new java.awt.Color(23, 16, 71));
        btnAdd.setColorOver(new java.awt.Color(23, 11, 84));
        btnAdd.setRadius(10);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.setBorderColor(new java.awt.Color(23, 35, 51));
        btnUpdate.setColor(new java.awt.Color(23, 35, 51));
        btnUpdate.setColorClick(new java.awt.Color(23, 16, 71));
        btnUpdate.setColorOver(new java.awt.Color(23, 11, 84));
        btnUpdate.setRadius(10);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        labelNameProductSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelNameProductSearch.setText("Tìm Kiếm");

        txtSearchNameProduct.setLabelText("Tên Sản Phẩm");

        tblProductDisplay.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProductDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductDisplayMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductDisplay);

        btnRefesh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefesh.setText("Làm Mới");
        btnRefesh.setBorderColor(new java.awt.Color(23, 35, 51));
        btnRefesh.setColor(new java.awt.Color(23, 35, 51));
        btnRefesh.setColorClick(new java.awt.Color(23, 16, 71));
        btnRefesh.setColorOver(new java.awt.Color(23, 11, 84));
        btnRefesh.setRadius(10);
        btnRefesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshActionPerformed(evt);
            }
        });

        labelNamePrdError.setForeground(new java.awt.Color(255, 51, 51));

        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Tạo Mới");
        btnNew.setBorderColor(new java.awt.Color(23, 35, 51));
        btnNew.setColor(new java.awt.Color(23, 35, 51));
        btnNew.setColorClick(new java.awt.Color(23, 16, 71));
        btnNew.setColorOver(new java.awt.Color(23, 11, 84));
        btnNew.setRadius(10);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        labelCategory.setText("Chọn Danh Mục");

        cbbCategory.setLabeText("");

        btnAddCatefory.setBackground(new java.awt.Color(23, 35, 51));
        btnAddCatefory.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCatefory.setText("+");
        btnAddCatefory.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddCatefory.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddCatefory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCateforyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductLayout = new javax.swing.GroupLayout(panelProduct);
        panelProduct.setLayout(panelProductLayout);
        panelProductLayout.setHorizontalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addGap(22, 46, Short.MAX_VALUE)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelProductLayout.createSequentialGroup()
                                .addComponent(labelCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddCatefory, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelProductLayout.createSequentialGroup()
                                .addComponent(labelNameProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNamePrdError, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNameProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(212, 212, 212)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelProductLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(516, 516, 516))
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(labelNameProductSearch)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );
        panelProductLayout.setVerticalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddCatefory, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNamePrdError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelNameProductSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDisplayList.addTab("Sản Phẩm", panelProduct);

        panelDetailProduct.setBackground(new java.awt.Color(255, 255, 255));

        panelFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelFunction.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức Năng"));
        panelFunction.setPreferredSize(new java.awt.Dimension(1230, 430));

        labelNameProductTab2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNameProductTab2.setText("Tên Sản Phẩm");

        cbbNameProductDetail.setLabeText("Tên Sản Phẩm");
        cbbNameProductDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNameProductDetailActionPerformed(evt);
            }
        });

        labelPriceIn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceIn.setText("Giá Nhập");

        txtPriceIn.setLabelText("Giá Nhập");

        labelPriceOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceOut.setText("GIá Bán");

        txtPriceOut.setLabelText("Giá Bán");

        btnAddDetailProduct.setBackground(new java.awt.Color(23, 35, 51));
        btnAddDetailProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnAddDetailProduct.setText("Thêm");
        btnAddDetailProduct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnAddDetailProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDetailProductActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(23, 35, 51));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm Mới");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnUpdateDetailProduct.setBackground(new java.awt.Color(23, 35, 51));
        btnUpdateDetailProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateDetailProduct.setText("Sửa");
        btnUpdateDetailProduct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnUpdateDetailProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailProductActionPerformed(evt);
            }
        });

        labelDescription.setText("Mô Tả");

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setRows(5);
        jScrollPane3.setViewportView(txtAreaDescription);

        labelPriceInError.setForeground(new java.awt.Color(255, 51, 0));

        labelPriceOutError.setForeground(new java.awt.Color(255, 51, 0));

        labelQuantityError.setForeground(new java.awt.Color(255, 51, 0));

        btnStatusDetailProd.setBackground(new java.awt.Color(23, 35, 51));
        btnStatusDetailProd.setForeground(new java.awt.Color(255, 255, 255));
        btnStatusDetailProd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnStatusDetailProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatusDetailProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFunctionLayout = new javax.swing.GroupLayout(panelFunction);
        panelFunction.setLayout(panelFunctionLayout);
        panelFunctionLayout.setHorizontalGroup(
            panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFunctionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(labelPriceOutError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(5, 5, 5))
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addComponent(labelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addComponent(labelPriceInError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(261, 261, 261))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdateDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnStatusDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(107, 107, 107)))
                        .addComponent(labelQuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(174, Short.MAX_VALUE))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addComponent(labelNameProductTab2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbNameProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelFunctionLayout.setVerticalGroup(
            panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(labelNameProductTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cbbNameProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                        .addComponent(labelPriceInError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnStatusDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addComponent(labelPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(labelPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addComponent(txtPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(txtPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPriceOutError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(labelQuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57))))
        );

        panelAtribute.setBackground(new java.awt.Color(255, 255, 255));
        panelAtribute.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi Tiết Thuộc TÍnh"));

        labelCompany.setText("Hãng");

        cbbCompany.setLabeText("");

        cbbColor.setLabeText("");

        labelCPU.setText("Màu");

        btnAddCompany.setBackground(new java.awt.Color(23, 35, 51));
        btnAddCompany.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCompany.setText("+");
        btnAddCompany.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddCompany.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCompanyActionPerformed(evt);
            }
        });

        btnAddColor.setBackground(new java.awt.Color(23, 35, 51));
        btnAddColor.setForeground(new java.awt.Color(255, 255, 255));
        btnAddColor.setText("+");
        btnAddColor.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddColor.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddColorActionPerformed(evt);
            }
        });

        cbbSole.setLabeText("");

        btnAddSole.setBackground(new java.awt.Color(23, 35, 51));
        btnAddSole.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSole.setText("+");
        btnAddSole.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddSole.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddSole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSoleActionPerformed(evt);
            }
        });

        labelRam.setText("Đế Giày");

        btnAddMaterial.setBackground(new java.awt.Color(23, 35, 51));
        btnAddMaterial.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMaterial.setText("+");
        btnAddMaterial.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddMaterial.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMaterialActionPerformed(evt);
            }
        });

        cbbMaterial.setLabeText("");

        labelCPU1.setText("Chất Liệu");

        cbbSize.setLabeText("");

        btnAddSize.setBackground(new java.awt.Color(23, 35, 51));
        btnAddSize.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSize.setText("+");
        btnAddSize.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddSize.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSizeActionPerformed(evt);
            }
        });

        labelRam1.setText("Kính Cỡ");

        javax.swing.GroupLayout panelAtributeLayout = new javax.swing.GroupLayout(panelAtribute);
        panelAtribute.setLayout(panelAtributeLayout);
        panelAtributeLayout.setHorizontalGroup(
            panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCPU1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                        .addComponent(labelROM, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(162, Short.MAX_VALUE))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelRam, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbSole, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSole, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelRam1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddSize, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        panelAtributeLayout.setVerticalGroup(
            panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributeLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                            .addComponent(labelRam, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(3, 3, 3))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbSole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddSole, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSize, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRam1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddColor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                            .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelCPU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelROM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(3, 3, 3)))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAddMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                                .addComponent(labelCPU1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)))))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout panelDetailProductLayout = new javax.swing.GroupLayout(panelDetailProduct);
        panelDetailProduct.setLayout(panelDetailProductLayout);
        panelDetailProductLayout.setHorizontalGroup(
            panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailProductLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFunction, javax.swing.GroupLayout.DEFAULT_SIZE, 1158, Short.MAX_VALUE)
                    .addComponent(panelAtribute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        panelDetailProductLayout.setVerticalGroup(
            panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFunction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAtribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        panelDisplayList.addTab("Chi Tiết Sản Phẩm", panelDetailProduct);

        panelListProduct.setBackground(new java.awt.Color(255, 255, 255));
        panelListProduct.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        labelSearch.setText("Tìm Kiếm");

        txtSearchProduct.setLabelText("Tên Sản Phẩm");

        labelSearchByPrice.setText("Lọc Giá");

        txtPriceMin.setLabelText("Giá Thấp Nhất");

        txtPriceMax.setLabelText("Giá Cao Nhất");

        tblDisplayPrdDetail.setBackground(new java.awt.Color(255, 255, 255));
        tblDisplayPrdDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDisplayPrdDetail);

        btnImport.setBackground(new java.awt.Color(23, 35, 51));
        btnImport.setForeground(new java.awt.Color(255, 255, 255));
        btnImport.setText("Import");

        btnExport.setBackground(new java.awt.Color(23, 35, 51));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnFilterPrice.setBackground(new java.awt.Color(0, 0, 102));
        btnFilterPrice.setForeground(new java.awt.Color(255, 255, 255));
        btnFilterPrice.setText("Lọc");
        btnFilterPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterPriceActionPerformed(evt);
            }
        });

        btnResetTable.setBackground(new java.awt.Color(0, 0, 102));
        btnResetTable.setForeground(new java.awt.Color(255, 255, 255));
        btnResetTable.setText("Làm Mới");
        btnResetTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelListProductLayout = new javax.swing.GroupLayout(panelListProduct);
        panelListProduct.setLayout(panelListProductLayout);
        panelListProductLayout.setHorizontalGroup(
            panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addComponent(jScrollPane2)
            .addGroup(panelListProductLayout.createSequentialGroup()
                .addGap(259, 259, 259)
                .addComponent(labelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(labelSearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnResetTable, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        panelListProductLayout.setVerticalGroup(
            panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelListProductLayout.createSequentialGroup()
                            .addComponent(labelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)))
                    .addComponent(labelSearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnResetTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelDisplayProductLayout = new javax.swing.GroupLayout(panelDisplayProduct);
        panelDisplayProduct.setLayout(panelDisplayProductLayout);
        panelDisplayProductLayout.setHorizontalGroup(
            panelDisplayProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDisplayProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelListProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDisplayProductLayout.setVerticalGroup(
            panelDisplayProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDisplayProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelListProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDisplayList.addTab("Danh Sách Sản Phẩm", panelDisplayProduct);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDisplayList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDisplayList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int count = 0;

        if (txtNameProduct.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên Sản Phẩm Không Được Để Trống");
            count++;
        }

        if (cbbCategory.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui Lòng Chọn Danh Mục");
            count++;
        }

        if (count == 0) {
            String name = txtNameProduct.getText();
            String categoryName = (String) cbbCategory.getSelectedItem();
            System.out.println(categoryName);
            AddProductRequest addProductRequest = new AddProductRequest(name, categoryName);
            String res = productService.addProduct(addProductRequest);
            JOptionPane.showMessageDialog(this, res);
            fillTableProduct(productService.getAllProductResponse());
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblProductDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductDisplayMouseClicked

    }//GEN-LAST:event_tblProductDisplayMouseClicked

    private void btnRefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshActionPerformed

    }//GEN-LAST:event_btnRefeshActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed

    }//GEN-LAST:event_btnNewActionPerformed

    private void btnAddCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCompanyActionPerformed
        companyDialog.setVisible(true);
        onCloseJDialog(companyDialog, "Company");
    }//GEN-LAST:event_btnAddCompanyActionPerformed

    private void btnAddDetailProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDetailProductActionPerformed

    }//GEN-LAST:event_btnAddDetailProductActionPerformed

    private void btnAddColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddColorActionPerformed
        colorDialog.setVisible(true);
        onCloseJDialog(colorDialog, "Color");
    }//GEN-LAST:event_btnAddColorActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cbbNameProductDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNameProductDetailActionPerformed

    }//GEN-LAST:event_cbbNameProductDetailActionPerformed

    private void btnUpdateDetailProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailProductActionPerformed

    }//GEN-LAST:event_btnUpdateDetailProductActionPerformed

    private void btnStatusDetailProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatusDetailProdActionPerformed

    }//GEN-LAST:event_btnStatusDetailProdActionPerformed

    private void btnFilterPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterPriceActionPerformed

    }//GEN-LAST:event_btnFilterPriceActionPerformed

    private void btnResetTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTableActionPerformed

    }//GEN-LAST:event_btnResetTableActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed

    }//GEN-LAST:event_btnExportActionPerformed

    private void btnAddCateforyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCateforyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddCateforyActionPerformed

    private void btnAddMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMaterialActionPerformed
        materialDialog.setVisible(true);
        onCloseJDialog(materialDialog, "Material");
    }//GEN-LAST:event_btnAddMaterialActionPerformed

    private void btnAddSoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSoleActionPerformed
        soleDialog.setVisible(true);
        onCloseJDialog(soleDialog, "Sole");
    }//GEN-LAST:event_btnAddSoleActionPerformed

    private void btnAddSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSizeActionPerformed
        sizeDialog.setVisible(true);
        onCloseJDialog(sizeDialog, "Size");
    }//GEN-LAST:event_btnAddSizeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.MyButton btnAdd;
    private app.view.swing.Button btnAddCatefory;
    private app.view.swing.Button btnAddColor;
    private app.view.swing.Button btnAddCompany;
    private app.view.swing.Button btnAddDetailProduct;
    private app.view.swing.Button btnAddMaterial;
    private app.view.swing.Button btnAddSize;
    private app.view.swing.Button btnAddSole;
    private app.view.swing.Button btnExport;
    private app.view.swing.Button btnFilterPrice;
    private app.view.swing.Button btnImport;
    private app.view.swing.MyButton btnNew;
    private app.view.swing.MyButton btnRefesh;
    private app.view.swing.Button btnRefresh;
    private app.view.swing.Button btnResetTable;
    private app.view.swing.Button btnStatusDetailProd;
    private app.view.swing.MyButton btnUpdate;
    private app.view.swing.Button btnUpdateDetailProduct;
    private app.view.swing.Combobox cbbCategory;
    private app.view.swing.Combobox cbbColor;
    private app.view.swing.Combobox cbbCompany;
    private app.view.swing.Combobox cbbMaterial;
    private app.view.swing.Combobox cbbNameProductDetail;
    private app.view.swing.Combobox cbbSize;
    private app.view.swing.Combobox cbbSole;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelCPU;
    private javax.swing.JLabel labelCPU1;
    private javax.swing.JLabel labelCategory;
    private javax.swing.JLabel labelCompany;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelNamePrdError;
    private javax.swing.JLabel labelNameProduct;
    private javax.swing.JLabel labelNameProductSearch;
    private javax.swing.JLabel labelNameProductTab2;
    private javax.swing.JLabel labelPriceIn;
    private javax.swing.JLabel labelPriceInError;
    private javax.swing.JLabel labelPriceOut;
    private javax.swing.JLabel labelPriceOutError;
    private javax.swing.JLabel labelQuantityError;
    private javax.swing.JLabel labelROM;
    private javax.swing.JLabel labelRam;
    private javax.swing.JLabel labelRam1;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JLabel labelSearchByPrice;
    private javax.swing.JPanel panelAtribute;
    private javax.swing.JPanel panelDetailProduct;
    private app.view.swing.TabbedPaneCustom panelDisplayList;
    private javax.swing.JPanel panelDisplayProduct;
    private javax.swing.JPanel panelFunction;
    private javax.swing.JPanel panelListProduct;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JTable tblDisplayPrdDetail;
    private javax.swing.JTable tblProductDisplay;
    private javax.swing.JTextArea txtAreaDescription;
    private app.view.swing.TextField txtNameProduct;
    private app.view.swing.TextField txtPriceIn;
    private app.view.swing.TextField txtPriceMax;
    private app.view.swing.TextField txtPriceMin;
    private app.view.swing.TextField txtPriceOut;
    private app.view.swing.TextField txtSearchNameProduct;
    private app.view.swing.TextField txtSearchProduct;
    // End of variables declaration//GEN-END:variables
}
