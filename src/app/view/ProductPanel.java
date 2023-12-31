/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.view.propertiesproductview.SoleDialog;
import app.view.propertiesproductview.SizeDialog;
import app.view.propertiesproductview.MaterialDialog;
import app.view.propertiesproductview.CompanyDialog;
import app.view.propertiesproductview.ColorDialog;
import app.view.propertiesproductview.CategoryDialog;
import app.model.Category;
import app.model.Color;
import app.model.Company;
import app.model.Material;
import app.model.Product;
import app.model.ProductDetail;
import app.model.Size;
import app.model.Sole;
import app.request.AddProductDetailRequest;
import app.request.AddProductRequest;
import app.request.UpdateProductDetailRequest;
import app.response.ProductDetailResponse;
import app.response.ProductResponse;
import app.service.CategoryService;
import app.service.ColorService;
import app.service.CompanyService;
import app.service.MaterialService;
import app.service.ProductDetailService;
import app.service.ProductService;
import app.service.SizeService;
import app.service.SoleService;
import app.util.DownloadProductDetailTemplate;
import app.util.ImportExcelProductDetail;
import app.util.XFileExcel;
import app.util.XGenerateQRCode;
import app.view.swing.Combobox;
import app.view.swing.EventPagination;
import app.view.swing.PaginationItemRenderStyle1;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private final ProductDetailService productDetailService = new ProductDetailService();
    private final DefaultComboBoxModel comboBoxModelCategory;
    private final DefaultComboBoxModel comboBoxModelColor;
    private final DefaultComboBoxModel comboBoxModelMaterial;
    private final DefaultComboBoxModel comboBoxModelSize;
    private final DefaultComboBoxModel comboBoxModelSole;
    private final DefaultComboBoxModel comboBoxModelCompany;
    private final DefaultComboBoxModel comboBoxModelProduct;
    private final DefaultTableModel tableModelProduct;
    private final DefaultTableModel tableModelProductDetail;
    private final DefaultTableModel tableModelProductDetailAll;
    private final ColorDialog colorDialog;
    private final MaterialDialog materialDialog;
    private final SoleDialog soleDialog;
    private final CompanyDialog companyDialog;
    private final SizeDialog sizeDialog;
    private final CategoryDialog categoryDialog;
    private String nameProduct = "";
    private final JFrame parentFrame;

    public ProductPanel(JFrame parentFrame) {
        initComponents();
        this.parentFrame = parentFrame;
        btnShowDetail.setVisible(false);
        comboBoxModelCategory = new DefaultComboBoxModel();
        comboBoxModelColor = new DefaultComboBoxModel();
        comboBoxModelProduct = new DefaultComboBoxModel();
        comboBoxModelMaterial = new DefaultComboBoxModel();
        comboBoxModelSize = new DefaultComboBoxModel();
        comboBoxModelSole = new DefaultComboBoxModel();
        comboBoxModelCompany = new DefaultComboBoxModel();
        tableModelProduct = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModelProductDetailAll = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModelProductDetail = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        colorDialog = new ColorDialog(parentFrame, true);
        materialDialog = new MaterialDialog(parentFrame, true);
        categoryDialog = new CategoryDialog(parentFrame, true);
        soleDialog = new SoleDialog(parentFrame, true);
        companyDialog = new CompanyDialog(parentFrame, true);
        sizeDialog = new SizeDialog(parentFrame, true);
        tblProductDisplay.setModel(tableModelProduct);
        tblDisplayPrdDetail.setModel(tableModelProductDetail);
        tblDisplayProductDetailAll1.setModel(tableModelProductDetailAll);
        cbbCategory.setModel(comboBoxModelCategory);
        cbbColor.setModel(comboBoxModelColor);
        cbbCompany.setModel(comboBoxModelCompany);
        cbbMaterial.setModel(comboBoxModelMaterial);
        cbbSize.setModel(comboBoxModelSize);
        cbbSole.setModel(comboBoxModelSole);
        addColumnTableProduct();
        addColumnTableProductDetail();
        addColumnTableProductDetailAll();
        fillComboBoxCategory(categoryService.getAll());
        fillComboBoxColor(colorService.getAllColors());
        fillComboBoxCompany(companyService.getAllCompany());
        fillComboBoxMaterial(materialService.getAllMaterials());
        fillComboBoxSize(sizeService.getAllSizes());
        fillComboBoxSole(soleService.getAllSoles());
        paginationProducts.setPaginationItemRender(new PaginationItemRenderStyle1());
        paginationProductDetail.setPaginationItemRender(new PaginationItemRenderStyle1());
        paginationListAllDetail.setPaginationItemRender(new PaginationItemRenderStyle1());
        paginationListAllDetail.addEventPagination((page) -> {
            if (page < 1) {
                page = 1;
            }
            loadDataProductDetailsAll(page);
        });
        paginationProducts.addEventPagination((int page) -> {
            if (page < 1) {
                page = 1;
            }
            loadDataProducts(page);
        });
        if ("".equals(nameProduct)) {
            paginationProductDetail.addEventPagination((int page) -> {
                if (page < 1) {
                    page = 1;
                }
                loadDataProductDetails(page);
            });
        } else {
            paginationProductDetail.addEventPagination((int page) -> {
                if (page < 1) {
                    page = 1;
                }
                loadDataProductDetailsWithNameProduct(page);
            });
        }
        loadDataProducts(1);
        loadDataProductDetailsAll(1);
        onChange();
    }

    private void fillComboBoxCategory(List<Category> categorys) {
        List<String> categoryName = new ArrayList<>();
        for (Category category : categorys) {
            if (category.getDeleted() == false) {
                categoryName.add(category.getName());
            }
        }
        comboBoxModelCategory.removeAllElements();
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
                    productResponse.getCode(),
                    productResponse.getName(),
                    productResponse.getQuantity(),
                    productResponse.getCategory(),
                    productResponse.getCompany(),
                    productResponse.getDeleted() ? "Ngừng Hoạt Động" : "Hoạt Động"
                };
                tableModelProduct.addRow(row);
            }
        }
    }

    private void fillComboBoxColor(List<Color> list) {
        List<String> colorList = new ArrayList<>();
        for (Color color : list) {
            if (color.getDeleted() == false) {
                colorList.add(color.getName());
            }
        }
        comboBoxModelColor.removeAllElements();
        comboBoxModelColor.addAll(colorList);
        cbbColor.setSelectedIndex(0);
    }

//    private void fillComboBoxProduct(List<Product> list) {
//        List<String> productNameList = new ArrayList<>();
//        for (Product product : list) {
//            if (product.getDeleted() == false) {
//                productNameList.add(product.getName());
//            }
//        }
//        comboBoxModelProduct.removeAllElements();
//        comboBoxModelProduct.addAll(productNameList);
//        cbbNameProduct.setSelectedIndex(0);
//    }
    private void fillComboBoxMaterial(List<Material> list) {
        List<String> materialList = new ArrayList<>();
        for (Material material : list) {
            if (material.getDeleted() == false) {
                materialList.add(material.getName());
            }
        }
        comboBoxModelMaterial.removeAllElements();
        comboBoxModelMaterial.addAll(materialList);
        cbbMaterial.setSelectedIndex(0);
    }

    private void fillComboBoxCompany(List<Company> list) {
        List<String> companyList = new ArrayList<>();
        for (Company company : list) {
            if (company.getDeleted() == false) {
                companyList.add(company.getName());
            }
        }
        comboBoxModelCompany.removeAllElements();
        comboBoxModelCompany.addAll(companyList);
        cbbCompany.setSelectedIndex(0);
    }

    private void fillComboBoxSole(List<Sole> list) {
        List<String> soleList = new ArrayList<>();
        for (Sole sole : list) {
            if (sole.getDeleted() == false) {
                soleList.add(sole.getName());
            }
        }
        comboBoxModelSole.removeAllElements();
        comboBoxModelSole.addAll(soleList);
        cbbSole.setSelectedIndex(0);
    }

    private void fillComboBoxSize(List<Size> list) {
        List<String> sizeList = new ArrayList<>();
        for (Size size : list) {
            if (size.getDeleted() == false) {
                sizeList.add(size.getName());
            }
        }
        comboBoxModelSize.removeAllElements();
        comboBoxModelSize.addAll(sizeList);
        cbbSize.setSelectedIndex(0);
    }

    private void fillTableProductDetail(List<ProductDetailResponse> list) {
        if (list != null) {
            int stt = 0;
            tableModelProductDetail.setRowCount(0);
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
                tableModelProductDetail.addRow(row);
            }
        }
    }

    private void fillTableProductDetailAll(List<ProductDetailResponse> list) {
        if (list != null) {
            int stt = 0;
            tableModelProductDetailAll.setRowCount(0);
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
                tableModelProductDetailAll.addRow(row);
            }
        }
    }

    private void addColumnTableProduct() {
        tableModelProduct.addColumn("STT");
        tableModelProduct.addColumn("Mã Sản Phẩm");
        tableModelProduct.addColumn("Tên Sản Phẩm");
        tableModelProduct.addColumn("Số Lượng Tồn");
        tableModelProduct.addColumn("Danh Mục");
        tableModelProduct.addColumn("Thương Hiệu");
        tableModelProduct.addColumn("Trạng Thái");
    }

    private void addColumnTableProductDetail() {
        tableModelProductDetail.addColumn("STT");
        tableModelProductDetail.addColumn("Mã CTSP");
        tableModelProductDetail.addColumn("Tên Sản Phẩm");
        tableModelProductDetail.addColumn("Kích Cỡ");
        tableModelProductDetail.addColumn("Chất Liệu");
        tableModelProductDetail.addColumn("Màu Sắc");
        tableModelProductDetail.addColumn("Loại Đế");
        tableModelProductDetail.addColumn("Giá Bán");
        tableModelProductDetail.addColumn("Giá Nhập");
        tableModelProductDetail.addColumn("Số Lượng Tồn");
        tableModelProductDetail.addColumn("Trạng Thái");
    }

    private void addColumnTableProductDetailAll() {
        tableModelProductDetailAll.addColumn("STT");
        tableModelProductDetailAll.addColumn("Mã CTSP");
        tableModelProductDetailAll.addColumn("Tên Sản Phẩm");
        tableModelProductDetailAll.addColumn("Kích Cỡ");
        tableModelProductDetailAll.addColumn("Chất Liệu");
        tableModelProductDetailAll.addColumn("Màu Sắc");
        tableModelProductDetailAll.addColumn("Loại Đế");
        tableModelProductDetailAll.addColumn("Giá Bán");
        tableModelProductDetailAll.addColumn("Giá Nhập");
        tableModelProductDetailAll.addColumn("Số Lượng Tồn");
        tableModelProductDetailAll.addColumn("Trạng Thái");
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

    private void resetFormProductDetail() {
        txtAreaDescription.setText("");
        txtPriceSell.setText("");
        txtPriceOrigin.setText("");
        txtQuantity.setText("");

    }

    private void loadDataProducts(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            int rowCount = productService.countProductRecord();
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableProduct(productService.getAllProductPaging(offset, limit));
            paginationProducts.setPagegination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataProductDetails(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            int rowCount = productDetailService.countProductRecord();
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableProductDetail(productDetailService.getListProductDetailViewPagntion(offset, limit));
            paginationProductDetail.setPagegination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataProductDetailsAll(int page) {
        int limit = 10;
        int offset = (page - 1) * limit;
        try {
            int rowCount = productDetailService.countProductRecord();
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableProductDetailAll(productDetailService.getListProductDetailViewPagntion(offset, limit));
            paginationListAllDetail.setPagegination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataProductDetailsWithNameProduct(int page) {
        int limit = 5;
        int offset = (page - 1) * limit;
        try {
            int rowCount = productDetailService.countProductRecordWithNameProduct(nameProduct);
            System.out.println(rowCount);
            int totalPages = (int) Math.ceil((double) rowCount / limit);
            fillTableProductDetail(productDetailService.getListProductDetailViewPagntionWithNameProduct(offset, limit, nameProduct));
            paginationProducts.setPagegination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onChange() {
        txtSearchNameProduct.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        loadDataProducts(1);
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        List<ProductResponse> listSearch = productService.getAllProductResponse();
                        List<ProductResponse> listRes = new ArrayList<>();
                        if (listSearch != null) {
                            for (ProductResponse productResponse : listSearch) {
                                if (productResponse.getName().toLowerCase().contains(namePrdSearch.toLowerCase())) {
                                    listRes.add(productResponse);
                                }
                            }
                            fillTableProduct(listRes);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadDataProducts(1);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        loadDataProducts(1);
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        List<ProductResponse> listSearch = productService.getAllProductResponse();
                        List<ProductResponse> listRes = new ArrayList<>();
                        if (listSearch != null) {
                            for (ProductResponse productResponse : listSearch) {
                                if (productResponse.getName().toLowerCase().contains(namePrdSearch.toLowerCase())) {
                                    listRes.add(productResponse);
                                }
                            }
                            fillTableProduct(listRes);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        productDetailDialog = new javax.swing.JDialog();
        panelDetailProduct = new javax.swing.JPanel();
        panelFunction = new javax.swing.JPanel();
        labelNameProductTab2 = new javax.swing.JLabel();
        labelPriceIn = new javax.swing.JLabel();
        txtPriceOrigin = new app.view.swing.TextField();
        labelPriceOut = new javax.swing.JLabel();
        txtPriceSell = new app.view.swing.TextField();
        labelDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        labelPriceOutError = new javax.swing.JLabel();
        labelQuantityError = new javax.swing.JLabel();
        labelPriceOut1 = new javax.swing.JLabel();
        txtQuantity = new app.view.swing.TextField();
        panelAtribute = new javax.swing.JPanel();
        cbbColor = new app.view.swing.Combobox();
        labelCPU = new javax.swing.JLabel();
        btnAddColor = new app.view.swing.Button();
        cbbSole = new app.view.swing.Combobox();
        btnAddSole = new app.view.swing.Button();
        labelRam = new javax.swing.JLabel();
        btnAddMaterial = new app.view.swing.Button();
        cbbMaterial = new app.view.swing.Combobox();
        labelCPU1 = new javax.swing.JLabel();
        cbbSize = new app.view.swing.Combobox();
        btnAddSize = new app.view.swing.Button();
        labelRam1 = new javax.swing.JLabel();
        btnAddDetailProduct = new app.view.swing.Button();
        btnRefresh = new app.view.swing.Button();
        btnUpdateDetailProduct = new app.view.swing.Button();
        btnImport = new app.view.swing.Button();
        btnDownloadMau = new app.view.swing.Button();
        btnExport = new app.view.swing.Button();
        labelDisplayNameProduct = new javax.swing.JLabel();
        btnUpdateProductDetail = new app.view.swing.Button();
        btnDownLoadQR = new app.view.swing.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisplayPrdDetail = new javax.swing.JTable();
        paginationProductDetail = new app.view.swing.Pagination();
        tableProductDetailDialog = new javax.swing.JDialog();
        panelDetal = new javax.swing.JPanel();
        btnImport1 = new app.view.swing.Button();
        btnDownload1 = new app.view.swing.Button();
        btnExport1 = new app.view.swing.Button();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDisplayProductDetailAll = new javax.swing.JTable();
        btnExportQr = new app.view.swing.Button();
        panelDisplayList = new app.view.swing.TabbedPaneCustom();
        panelProduct = new javax.swing.JPanel();
        txtSearchNameProduct = new app.view.swing.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductDisplay = new javax.swing.JTable();
        btnRefesh = new app.view.swing.MyButton();
        paginationProducts = new app.view.swing.Pagination();
        jPanel1 = new javax.swing.JPanel();
        btnNew = new app.view.swing.MyButton();
        btnAdd = new app.view.swing.MyButton();
        btnUpdate = new app.view.swing.MyButton();
        btnShowDetail = new app.view.swing.MyButton();
        labelNamePrdError = new javax.swing.JLabel();
        labelCategory = new javax.swing.JLabel();
        cbbCategory = new app.view.swing.Combobox();
        btnAddCatefory = new app.view.swing.Button();
        labelCompany1 = new javax.swing.JLabel();
        cbbCompany = new app.view.swing.Combobox();
        btnAddCompany1 = new app.view.swing.Button();
        labelNameProduct = new javax.swing.JLabel();
        txtNameProduct = new app.view.swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelDetal1 = new javax.swing.JPanel();
        btnImport2 = new app.view.swing.Button();
        btnDownload2 = new app.view.swing.Button();
        btnExport2 = new app.view.swing.Button();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDisplayProductDetailAll1 = new javax.swing.JTable();
        btnExportQr1 = new app.view.swing.Button();
        paginationListAllDetail = new app.view.swing.Pagination();

        productDetailDialog.setModal(true);
        productDetailDialog.setResizable(false);

        panelDetailProduct.setBackground(new java.awt.Color(255, 255, 255));

        panelFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelFunction.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức Năng"));
        panelFunction.setPreferredSize(new java.awt.Dimension(1230, 430));

        labelNameProductTab2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNameProductTab2.setText("Tên Sản Phẩm");

        labelPriceIn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceIn.setText("Giá Nhập");

        txtPriceOrigin.setLabelText("Giá Nhập");

        labelPriceOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceOut.setText("Giá Bán");

        txtPriceSell.setLabelText("Giá Bán");

        labelDescription.setText("Mô Tả");

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setRows(5);
        jScrollPane3.setViewportView(txtAreaDescription);

        labelPriceOutError.setForeground(new java.awt.Color(255, 51, 0));

        labelQuantityError.setForeground(new java.awt.Color(255, 51, 0));

        labelPriceOut1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceOut1.setText("Số Lượng");

        txtQuantity.setLabelText("Số Lượng");

        panelAtribute.setBackground(new java.awt.Color(255, 255, 255));
        panelAtribute.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi Tiết Thuộc Tính"));

        cbbColor.setLabeText("");

        labelCPU.setText("Màu");

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
                .addGap(22, 22, 22)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelAtributeLayout.createSequentialGroup()
                                .addComponent(labelCPU1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAtributeLayout.createSequentialGroup()
                                .addComponent(labelRam1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddSize, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelAtributeLayout.createSequentialGroup()
                            .addComponent(labelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddColor, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelAtributeLayout.createSequentialGroup()
                            .addComponent(labelRam, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbbSole, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddSole, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        panelAtributeLayout.setVerticalGroup(
            panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributeLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelRam, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbSole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddSole, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddColor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(49, 49, 49)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSize, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRam1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCPU1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(30, 30, 30))
        );

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
        btnUpdateDetailProduct.setText("Đổi Trạng Thái");
        btnUpdateDetailProduct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnUpdateDetailProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailProductActionPerformed(evt);
            }
        });

        btnImport.setBackground(new java.awt.Color(23, 35, 51));
        btnImport.setForeground(new java.awt.Color(255, 255, 255));
        btnImport.setText("Import");
        btnImport.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnDownloadMau.setBackground(new java.awt.Color(23, 35, 51));
        btnDownloadMau.setForeground(new java.awt.Color(255, 255, 255));
        btnDownloadMau.setText("Tải Mẫu Excel");
        btnDownloadMau.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnDownloadMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadMauActionPerformed(evt);
            }
        });

        btnExport.setBackground(new java.awt.Color(23, 35, 51));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Export");
        btnExport.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        labelDisplayNameProduct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        btnUpdateProductDetail.setBackground(new java.awt.Color(23, 35, 51));
        btnUpdateProductDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateProductDetail.setText("Sửa");
        btnUpdateProductDetail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnUpdateProductDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProductDetailActionPerformed(evt);
            }
        });

        btnDownLoadQR.setBackground(new java.awt.Color(23, 35, 51));
        btnDownLoadQR.setForeground(new java.awt.Color(255, 255, 255));
        btnDownLoadQR.setText("Download QR");
        btnDownLoadQR.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnDownLoadQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownLoadQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFunctionLayout = new javax.swing.GroupLayout(panelFunction);
        panelFunction.setLayout(panelFunctionLayout);
        panelFunctionLayout.setHorizontalGroup(
            panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFunctionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addComponent(labelNameProductTab2)
                        .addGap(18, 18, 18)
                        .addComponent(labelDisplayNameProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addComponent(labelPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(txtPriceOrigin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelPriceOut1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelPriceOut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPriceSell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)))
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(txtQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(89, 89, 89)
                .addComponent(panelAtribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPriceOutError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDownloadMau, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(901, 901, 901)
                        .addComponent(labelQuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDownLoadQR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFunctionLayout.setVerticalGroup(
            panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFunctionLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelNameProductTab2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(labelDisplayNameProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPriceOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPriceSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPriceOut1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelFunctionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelQuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71))
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAddDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdateProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdateDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDownloadMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDownLoadQR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(labelPriceOutError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addComponent(panelAtribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

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
        tblDisplayPrdDetail.setRowHeight(40);
        tblDisplayPrdDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayPrdDetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDisplayPrdDetail);

        paginationProductDetail.setBackground(new java.awt.Color(23, 35, 51));

        javax.swing.GroupLayout panelDetailProductLayout = new javax.swing.GroupLayout(panelDetailProduct);
        panelDetailProduct.setLayout(panelDetailProductLayout);
        panelDetailProductLayout.setHorizontalGroup(
            panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailProductLayout.createSequentialGroup()
                .addGroup(panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailProductLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 1158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2)))
                    .addGroup(panelDetailProductLayout.createSequentialGroup()
                        .addGap(516, 516, 516)
                        .addComponent(paginationProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        panelDetailProductLayout.setVerticalGroup(
            panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFunction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paginationProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout productDetailDialogLayout = new javax.swing.GroupLayout(productDetailDialog.getContentPane());
        productDetailDialog.getContentPane().setLayout(productDetailDialogLayout);
        productDetailDialogLayout.setHorizontalGroup(
            productDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1217, Short.MAX_VALUE)
            .addGroup(productDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(productDetailDialogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        productDetailDialogLayout.setVerticalGroup(
            productDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 751, Short.MAX_VALUE)
            .addGroup(productDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(productDetailDialogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tableProductDetailDialog.setModal(true);
        tableProductDetailDialog.setResizable(false);

        panelDetal.setBackground(new java.awt.Color(255, 255, 255));

        btnImport1.setBackground(new java.awt.Color(23, 35, 51));
        btnImport1.setForeground(new java.awt.Color(255, 255, 255));
        btnImport1.setText("Import");
        btnImport1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnImport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImport1ActionPerformed(evt);
            }
        });

        btnDownload1.setBackground(new java.awt.Color(23, 35, 51));
        btnDownload1.setForeground(new java.awt.Color(255, 255, 255));
        btnDownload1.setText("Tải Mẫu Excel");
        btnDownload1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnDownload1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownload1ActionPerformed(evt);
            }
        });

        btnExport1.setBackground(new java.awt.Color(23, 35, 51));
        btnExport1.setForeground(new java.awt.Color(255, 255, 255));
        btnExport1.setText("Export");
        btnExport1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExport1ActionPerformed(evt);
            }
        });

        tblDisplayProductDetailAll.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDisplayProductDetailAll.setRowHeight(35);
        jScrollPane4.setViewportView(tblDisplayProductDetailAll);

        btnExportQr.setBackground(new java.awt.Color(23, 35, 51));
        btnExportQr.setForeground(new java.awt.Color(255, 255, 255));
        btnExportQr.setText("Tải Danh Sách QR CTSP");
        btnExportQr.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExportQr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportQrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDetalLayout = new javax.swing.GroupLayout(panelDetal);
        panelDetal.setLayout(panelDetalLayout);
        panelDetalLayout.setHorizontalGroup(
            panelDetalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDownload1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImport1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExportQr, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
        );
        panelDetalLayout.setVerticalGroup(
            panelDetalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelDetalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDownload1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportQr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tableProductDetailDialogLayout = new javax.swing.GroupLayout(tableProductDetailDialog.getContentPane());
        tableProductDetailDialog.getContentPane().setLayout(tableProductDetailDialogLayout);
        tableProductDetailDialogLayout.setHorizontalGroup(
            tableProductDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tableProductDetailDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDetal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tableProductDetailDialogLayout.setVerticalGroup(
            tableProductDetailDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tableProductDetailDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDetal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDisplayList.setForeground(new java.awt.Color(255, 255, 255));
        panelDisplayList.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        panelDisplayList.setSelectedColor(new java.awt.Color(23, 35, 51));
        panelDisplayList.setUnselectedColor(new java.awt.Color(23, 44, 76));

        panelProduct.setBackground(new java.awt.Color(255, 255, 255));

        txtSearchNameProduct.setLabelText("Tìm kiếm Theo Tên Sản Phẩm");

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
        tblProductDisplay.setRowHeight(45);
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

        paginationProducts.setBackground(new java.awt.Color(23, 35, 51));
        paginationProducts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        btnUpdate.setText("Đổi Trạng Thái");
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

        btnShowDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnShowDetail.setText("Xem Chi Tiết");
        btnShowDetail.setBorderColor(new java.awt.Color(23, 35, 51));
        btnShowDetail.setColor(new java.awt.Color(23, 35, 51));
        btnShowDetail.setColorClick(new java.awt.Color(23, 16, 71));
        btnShowDetail.setColorOver(new java.awt.Color(23, 11, 84));
        btnShowDetail.setRadius(10);
        btnShowDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowDetailActionPerformed(evt);
            }
        });

        labelNamePrdError.setForeground(new java.awt.Color(255, 51, 51));

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

        labelCompany1.setText("Hãng");

        cbbCompany.setLabeText("");

        btnAddCompany1.setBackground(new java.awt.Color(23, 35, 51));
        btnAddCompany1.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCompany1.setText("+");
        btnAddCompany1.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddCompany1.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddCompany1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCompany1ActionPerformed(evt);
            }
        });

        labelNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelNameProduct.setText("Tên Sản Phẩm");

        txtNameProduct.setLabelText("Tên Sản Phẩm");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/logo_Shop.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNameProduct)
                            .addComponent(labelCompany1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelNamePrdError, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 79, Short.MAX_VALUE))
                            .addComponent(txtNameProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbCompany, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddCompany1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddCatefory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnShowDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAddCatefory, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(btnAddCompany1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelCompany1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNamePrdError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnShowDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelProductLayout = new javax.swing.GroupLayout(panelProduct);
        panelProduct.setLayout(panelProductLayout);
        panelProductLayout.setHorizontalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                        .addComponent(txtSearchNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paginationProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(508, 508, 508))
        );
        panelProductLayout.setVerticalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paginationProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        panelDisplayList.addTab("Sản Phẩm", panelProduct);

        panelDetal1.setBackground(new java.awt.Color(255, 255, 255));

        btnImport2.setBackground(new java.awt.Color(23, 35, 51));
        btnImport2.setForeground(new java.awt.Color(255, 255, 255));
        btnImport2.setText("Import");
        btnImport2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnImport2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImport2ActionPerformed(evt);
            }
        });

        btnDownload2.setBackground(new java.awt.Color(23, 35, 51));
        btnDownload2.setForeground(new java.awt.Color(255, 255, 255));
        btnDownload2.setText("Tải Mẫu Excel");
        btnDownload2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnDownload2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownload2ActionPerformed(evt);
            }
        });

        btnExport2.setBackground(new java.awt.Color(23, 35, 51));
        btnExport2.setForeground(new java.awt.Color(255, 255, 255));
        btnExport2.setText("Export");
        btnExport2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExport2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExport2ActionPerformed(evt);
            }
        });

        tblDisplayProductDetailAll1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDisplayProductDetailAll1.setRowHeight(45);
        jScrollPane5.setViewportView(tblDisplayProductDetailAll1);

        btnExportQr1.setBackground(new java.awt.Color(23, 35, 51));
        btnExportQr1.setForeground(new java.awt.Color(255, 255, 255));
        btnExportQr1.setText("Tải Danh Sách QR CTSP");
        btnExportQr1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExportQr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportQr1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDetal1Layout = new javax.swing.GroupLayout(panelDetal1);
        panelDetal1.setLayout(panelDetal1Layout);
        panelDetal1Layout.setHorizontalGroup(
            panelDetal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1205, Short.MAX_VALUE)
            .addGroup(panelDetal1Layout.createSequentialGroup()
                .addGap(482, 482, 482)
                .addComponent(paginationListAllDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(554, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetal1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDownload2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImport2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExportQr1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(295, 295, 295))
        );
        panelDetal1Layout.setVerticalGroup(
            panelDetal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetal1Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(panelDetal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDownload2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportQr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paginationListAllDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDetal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDetal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDisplayList.addTab("Danh Sách Chi Tiết Sản Phẩm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDisplayList, javax.swing.GroupLayout.PREFERRED_SIZE, 1222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDisplayList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        boolean resExport = false;
        try {
            resExport = XFileExcel.exportToFile(tableModelProductDetail);
        } catch (IOException ex) {
            Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resExport) {
            JOptionPane.showMessageDialog(this, "Xuất File Thành Công");
        } else {
            JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi Vui Lòng Thử Lại sau!");
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnDownloadMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadMauActionPerformed
        boolean res = DownloadProductDetailTemplate.ImportExcel();
        if (res) {
            JOptionPane.showMessageDialog(this, "Tải Mẫu Excel Thành Công");
        } else {
            JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi");
        }
    }//GEN-LAST:event_btnDownloadMauActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        try {
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Exel File", "xlsx");
            fc.setFileFilter(filter);
            int check = fc.showOpenDialog(null);
            File file = null;
            if (check == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }
            if (file == null) {
                return;
            }
            ImportExcelProductDetail excelCTSP = new ImportExcelProductDetail();
            excelCTSP.ImportFile(file.getAbsolutePath());
            loadDataProductDetails(1);
            loadDataProducts(1);
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnUpdateDetailProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailProductActionPerformed
        int row = tblDisplayPrdDetail.getSelectedRow();
        if (row >= 0) {
            int columnCode = 1;
            String codeProductDetail = (String) tblDisplayPrdDetail.getValueAt(row, columnCode);
            boolean res = productDetailService.updateStatus(codeProductDetail);
            if (res) {
                JOptionPane.showMessageDialog(this, "Thay Đổi Trạng Thái Thành Công !");
                loadDataProductDetails(1);
            } else {
                JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa Chọn Chi Tiết Sản Phẩm !");
        }
    }//GEN-LAST:event_btnUpdateDetailProductActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        clearFormAtri();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnAddDetailProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDetailProductActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Xác Nhận Thêm Sản Phẩm Chi Tiết");
        if (confirm == JOptionPane.YES_OPTION) {
            String product = (String) labelDisplayNameProduct.getText();
            String color = (String) cbbColor.getSelectedItem();
            String sole = (String) cbbSole.getSelectedItem();
            String size = (String) cbbSize.getSelectedItem();
            String material = (String) cbbMaterial.getSelectedItem();
            ProductDetail productDetail = productDetailService.findByAtribute(product, size, material, sole, color);
            if (productDetail.getCode() != null) {
                JOptionPane.showMessageDialog(this, "Sản Phẩm Chi Tiết Đã Tồn Tại");
                return;
            }
            String description = txtAreaDescription.getText();
            Integer quantity = null;
            Double sellPrice = null;
            Double originPrice = null;

            try {
                quantity = Integer.valueOf(txtQuantity.getText());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Số Lượng Phải Lớn Hơn 0 !");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số");
                return;
            }

            try {
                sellPrice = Double.valueOf(txtPriceSell.getText());
                if (sellPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Giá Bán Phải Lớn Hơn 0 !");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá Bán Phải Là Số");
                return;

            }

            try {
                originPrice = Double.valueOf(txtPriceOrigin.getText());
                if (originPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Giá Nhập Phải Lớn Hơn 0 !");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá Nhập Phải Là Số");
                return;
            }

            if (sellPrice < originPrice) {
                JOptionPane.showMessageDialog(this, "Giá Bán Phải Lớn Hơn Giá Nhập");
                return;
            }

            AddProductDetailRequest addProductDetailRequest = new AddProductDetailRequest(product, sellPrice, originPrice, color, sole, material, size, quantity, description);
            String message = productDetailService.addProductDetail(addProductDetailRequest);
            JOptionPane.showMessageDialog(this, message);
            loadDataProductDetailsWithNameProduct(1);
            fillTableProductDetailAll(productDetailService.getAllListProducts());
        }
    }//GEN-LAST:event_btnAddDetailProductActionPerformed

    private void btnAddSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSizeActionPerformed
        sizeDialog.setVisible(true);
        onCloseJDialog(sizeDialog, "Size");
    }//GEN-LAST:event_btnAddSizeActionPerformed

    private void btnAddMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMaterialActionPerformed
        materialDialog.setVisible(true);
        onCloseJDialog(materialDialog, "Material");
    }//GEN-LAST:event_btnAddMaterialActionPerformed

    private void btnAddSoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSoleActionPerformed
        soleDialog.setVisible(true);
        onCloseJDialog(soleDialog, "Sole");
    }//GEN-LAST:event_btnAddSoleActionPerformed

    private void btnAddColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddColorActionPerformed
        colorDialog.setVisible(true);
        onCloseJDialog(colorDialog, "Color");
    }//GEN-LAST:event_btnAddColorActionPerformed

    private void btnShowDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowDetailActionPerformed
        List<ProductDetailResponse> listByName = productDetailService.selectListDetailByNameProduct(nameProduct);
        productDetailDialog = new JDialog(parentFrame);
        productDetailDialog.getContentPane().add(panelDetailProduct);
        productDetailDialog.pack();
        productDetailDialog.setLocationRelativeTo(null);
        productDetailDialog.setVisible(true);
        labelDisplayNameProduct.setText(nameProduct);
        fillTableProductDetail(listByName);
    }//GEN-LAST:event_btnShowDetailActionPerformed

    private void btnAddCompany1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCompany1ActionPerformed
        companyDialog.setVisible(true);
        onCloseJDialog(companyDialog, "Company");
    }//GEN-LAST:event_btnAddCompany1ActionPerformed

    private void btnAddCateforyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCateforyActionPerformed
        categoryDialog.setVisible(true);
        onCloseJDialog(categoryDialog, "Category");
    }//GEN-LAST:event_btnAddCateforyActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        tblProductDisplay.clearSelection();
        txtNameProduct.setText("");
        cbbCategory.setSelectedIndex(0);
        txtNameProduct.setEnabled(true);
        cbbCategory.setEnabled(true);
        cbbCompany.setEnabled(true);
        btnShowDetail.setVisible(false);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnRefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshActionPerformed
        tblProductDisplay.clearSelection();
        txtNameProduct.setText("");
        cbbCategory.setSelectedIndex(0);
        txtNameProduct.setEnabled(true);
        cbbCategory.setEnabled(true);
        cbbCompany.setEnabled(true);
        btnShowDetail.setVisible(false);
        panelDisplayList.setSelectedIndex(0);
        nameProduct = "";
        loadDataProductDetails(1);
        loadDataProducts(1);
    }//GEN-LAST:event_btnRefeshActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblProductDisplay.getSelectedRow();
        if (row >= 0) {
            int column = 2;
            String nameCategory = (String) tblProductDisplay.getValueAt(row, column);
            boolean res = productService.changeStatus(nameCategory);
            if (res) {
                JOptionPane.showMessageDialog(this, "Đổi Trạng Thái Thành Công");
                loadDataProducts(1);
            } else {
                JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Xác Nhận Thêm Sản Phẩm");
        if (confirm == JOptionPane.YES_OPTION) {
            int count = 0;

            if (txtNameProduct.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên Sản Phẩm Không Được Để Trống");
                count++;
            }

            if (count == 0) {
                String name = txtNameProduct.getText();
                String companyName = (String) cbbCompany.getSelectedItem();
                String categoryName = (String) cbbCategory.getSelectedItem();
                System.out.println(categoryName);
                AddProductRequest addProductRequest = new AddProductRequest(name, categoryName, companyName);
                String res = productService.addProduct(addProductRequest);
                JOptionPane.showMessageDialog(this, res);
                loadDataProducts(1);
                resetFormProductDetail();
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblProductDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductDisplayMouseClicked
        int row = tblProductDisplay.getSelectedRow();
        if (row >= 0) {
            String status = (String) tblProductDisplay.getValueAt(row, 6);
            nameProduct = (String) tblProductDisplay.getValueAt(row, 2);
            btnShowDetail.setVisible(true);
        }
    }//GEN-LAST:event_tblProductDisplayMouseClicked

    private void btnImport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImport1ActionPerformed
        try {
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Exel File", "xlsx");
            fc.setFileFilter(filter);
            int check = fc.showOpenDialog(null);
            File file = null;
            if (check == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }
            if (file == null) {
                return;
            }
            ImportExcelProductDetail excelCTSP = new ImportExcelProductDetail();
            excelCTSP.ImportFile(file.getAbsolutePath());
            loadDataProductDetails(1);
            loadDataProducts(1);
            fillTableProductDetailAll(productDetailService.getAllListProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImport1ActionPerformed

    private void btnDownload1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownload1ActionPerformed
        boolean res = DownloadProductDetailTemplate.ImportExcel();
        if (res) {
            JOptionPane.showMessageDialog(this, "Tải Mẫu Excel Thành Công");
        } else {
            JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi");
        }
    }//GEN-LAST:event_btnDownload1ActionPerformed

    private void btnExport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExport1ActionPerformed
        boolean resExport = false;
        try {
            resExport = XFileExcel.exportToFile(tableModelProductDetailAll);
        } catch (IOException ex) {
            Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resExport) {
            JOptionPane.showMessageDialog(this, "Xuất File Thành Công");
        } else {
            JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi Vui Lòng Thử Lại sau!");
        }
    }//GEN-LAST:event_btnExport1ActionPerformed

    private void btnExportQrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportQrActionPerformed
        for (ProductDetailResponse pd : productDetailService.getAllListProducts()) {
            XGenerateQRCode.doGenerate(pd.getCode(), pd.getProduct());
        }
    }//GEN-LAST:event_btnExportQrActionPerformed

    private void btnImport2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImport2ActionPerformed
        try {
            JFileChooser fc = new JFileChooser("D:\\Downloads");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Exel File", "xlsx");
            fc.setFileFilter(filter);
            int check = fc.showOpenDialog(null);
            File file = null;
            if (check == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }
            if (file == null) {
                return;
            }
            ImportExcelProductDetail excelCTSP = new ImportExcelProductDetail();
            excelCTSP.ImportFile(file.getAbsolutePath());
            loadDataProductDetails(1);
            loadDataProducts(1);
            loadDataProductDetailsAll(1);
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImport2ActionPerformed

    private void btnDownload2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownload2ActionPerformed
        boolean res = DownloadProductDetailTemplate.ImportExcel();
        if (res) {
            JOptionPane.showMessageDialog(this, "Tải Mẫu Excel Thành Công");
        } else {
            JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi");
        }
    }//GEN-LAST:event_btnDownload2ActionPerformed

    private void btnExport2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExport2ActionPerformed
        boolean resExport = false;
        try {
            resExport = XFileExcel.exportToFile(tableModelProductDetailAll);
        } catch (IOException ex) {
            Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resExport) {
            JOptionPane.showMessageDialog(this, "Xuất File Thành Công");
        } else {
            JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi Vui Lòng Thử Lại sau!");
        }
    }//GEN-LAST:event_btnExport2ActionPerformed

    private void btnExportQr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportQr1ActionPerformed
        for (ProductDetailResponse pd : productDetailService.getAllListProducts()) {
            XGenerateQRCode.doGenerate(pd.getCode(), pd.getProduct());
        }
    }//GEN-LAST:event_btnExportQr1ActionPerformed

    private void btnUpdateProductDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProductDetailActionPerformed
        int row = tblDisplayPrdDetail.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Sửa Sản Phẩm Này ?");
            if (confirm == JOptionPane.YES_OPTION) {
                String code = (String) tblDisplayPrdDetail.getValueAt(row, 1);
                String description = txtAreaDescription.getText();
                Integer quantity = null;
                Double sellPrice = null;
                Double originPrice = null;

                try {
                    quantity = Integer.valueOf(txtQuantity.getText());
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(this, "Số Lượng Phải Lớn Hơn 0 !");
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số Lượng Phải Là Số");
                    return;
                }

                try {
                    sellPrice = Double.valueOf(txtPriceSell.getText());
                    if (sellPrice <= 0) {
                        JOptionPane.showMessageDialog(this, "Giá Bán Phải Lớn Hơn 0 !");
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Giá Bán Phải Là Số");
                    return;

                }

                try {
                    originPrice = Double.valueOf(txtPriceOrigin.getText());
                    if (originPrice <= 0) {
                        JOptionPane.showMessageDialog(this, "Giá Nhập Phải Lớn Hơn 0 !");
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Giá Nhập Phải Là Số");
                    return;
                }

                if (sellPrice < originPrice) {
                    JOptionPane.showMessageDialog(this, "Giá Bán Phải Lớn Hơn Giá Nhập");
                    return;
                }

                UpdateProductDetailRequest updateProductDetailRequest = new UpdateProductDetailRequest(description, quantity, sellPrice, originPrice);
                boolean res = productDetailService.updateProductDetail(code, updateProductDetailRequest);
                if (res) {
                    JOptionPane.showMessageDialog(this, "Cập Nhật Thành Công !");
                    clearFormAtri();
                } else {
                    JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi !");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm !");
        }
    }//GEN-LAST:event_btnUpdateProductDetailActionPerformed

    private void tblDisplayPrdDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayPrdDetailMouseClicked
        int row = tblDisplayPrdDetail.getSelectedRow();
        if (row >= 0) {
            String code = (String) tblDisplayPrdDetail.getValueAt(row, 1);
            ProductDetail productDetail = productDetailService.findByCode(code);
            showDataProductDetail(productDetail);
        }
    }//GEN-LAST:event_tblDisplayPrdDetailMouseClicked

    private void btnDownLoadQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownLoadQRActionPerformed
        String nameProductDown = labelDisplayNameProduct.getText();
        List<ProductDetailResponse> list = productDetailService.selectListDetailByNameProduct(nameProductDown);
        for (ProductDetailResponse productDetailResponse : list) {
            XGenerateQRCode.doGenerate(productDetailResponse.getCode(), productDetailResponse.getProduct());
        }
        JOptionPane.showMessageDialog(this, "Tải QR Sản Phẩm " + nameProductDown + " Thành Công");
    }//GEN-LAST:event_btnDownLoadQRActionPerformed

    private void showDataProductDetail(ProductDetail productDetail) {
        cbbSole.setEnabled(false);
        cbbColor.setEnabled(false);
        cbbMaterial.setEnabled(false);
        cbbSize.setEnabled(false);
        txtPriceSell.setText(String.valueOf(new BigDecimal(productDetail.getSellPrice())));
        txtPriceOrigin.setText(String.valueOf(new BigDecimal(productDetail.getOriginPrice())));
        txtQuantity.setText(String.valueOf(productDetail.getQuantity()));
        Sole sole = soleService.findById(productDetail.getIdSole());
        Size size = sizeService.findById(productDetail.getIdSize());
        Material material = materialService.findById(productDetail.getIdMaterial());
        Color color = colorService.findById(productDetail.getIdColor());
        cbbSole.setSelectedItem(String.valueOf(sole.getName()));
        cbbColor.setSelectedItem(String.valueOf(color.getName()));
        cbbMaterial.setSelectedItem(String.valueOf(material.getName()));
        cbbSize.setSelectedItem(String.valueOf(size.getName()));
        txtAreaDescription.setText(productDetail.getDescription());
    }

    private void clearFormAtri() {
        txtQuantity.setText("");
        txtPriceOrigin.setText("");
        txtPriceSell.setText("");
        txtAreaDescription.setText("");
        loadDataProductDetailsWithNameProduct(1);
        cbbSole.setEnabled(true);
        cbbColor.setEnabled(true);
        cbbMaterial.setEnabled(true);
        cbbSize.setEnabled(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.MyButton btnAdd;
    private app.view.swing.Button btnAddCatefory;
    private app.view.swing.Button btnAddColor;
    private app.view.swing.Button btnAddCompany1;
    private app.view.swing.Button btnAddDetailProduct;
    private app.view.swing.Button btnAddMaterial;
    private app.view.swing.Button btnAddSize;
    private app.view.swing.Button btnAddSole;
    private app.view.swing.Button btnDownLoadQR;
    private app.view.swing.Button btnDownload1;
    private app.view.swing.Button btnDownload2;
    private app.view.swing.Button btnDownloadMau;
    private app.view.swing.Button btnExport;
    private app.view.swing.Button btnExport1;
    private app.view.swing.Button btnExport2;
    private app.view.swing.Button btnExportQr;
    private app.view.swing.Button btnExportQr1;
    private app.view.swing.Button btnImport;
    private app.view.swing.Button btnImport1;
    private app.view.swing.Button btnImport2;
    private app.view.swing.MyButton btnNew;
    private app.view.swing.MyButton btnRefesh;
    private app.view.swing.Button btnRefresh;
    private app.view.swing.MyButton btnShowDetail;
    private app.view.swing.MyButton btnUpdate;
    private app.view.swing.Button btnUpdateDetailProduct;
    private app.view.swing.Button btnUpdateProductDetail;
    private app.view.swing.Combobox cbbCategory;
    private app.view.swing.Combobox cbbColor;
    private app.view.swing.Combobox cbbCompany;
    private app.view.swing.Combobox cbbMaterial;
    private app.view.swing.Combobox cbbSize;
    private app.view.swing.Combobox cbbSole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelCPU;
    private javax.swing.JLabel labelCPU1;
    private javax.swing.JLabel labelCategory;
    private javax.swing.JLabel labelCompany1;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelDisplayNameProduct;
    private javax.swing.JLabel labelNamePrdError;
    private javax.swing.JLabel labelNameProduct;
    private javax.swing.JLabel labelNameProductTab2;
    private javax.swing.JLabel labelPriceIn;
    private javax.swing.JLabel labelPriceOut;
    private javax.swing.JLabel labelPriceOut1;
    private javax.swing.JLabel labelPriceOutError;
    private javax.swing.JLabel labelQuantityError;
    private javax.swing.JLabel labelRam;
    private javax.swing.JLabel labelRam1;
    private app.view.swing.Pagination paginationListAllDetail;
    private app.view.swing.Pagination paginationProductDetail;
    private app.view.swing.Pagination paginationProducts;
    private javax.swing.JPanel panelAtribute;
    private javax.swing.JPanel panelDetailProduct;
    private javax.swing.JPanel panelDetal;
    private javax.swing.JPanel panelDetal1;
    private app.view.swing.TabbedPaneCustom panelDisplayList;
    private javax.swing.JPanel panelFunction;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JDialog productDetailDialog;
    private javax.swing.JDialog tableProductDetailDialog;
    private javax.swing.JTable tblDisplayPrdDetail;
    private javax.swing.JTable tblDisplayProductDetailAll;
    private javax.swing.JTable tblDisplayProductDetailAll1;
    private javax.swing.JTable tblProductDisplay;
    private javax.swing.JTextArea txtAreaDescription;
    private app.view.swing.TextField txtNameProduct;
    private app.view.swing.TextField txtPriceOrigin;
    private app.view.swing.TextField txtPriceSell;
    private app.view.swing.TextField txtQuantity;
    private app.view.swing.TextField txtSearchNameProduct;
    // End of variables declaration//GEN-END:variables
}
