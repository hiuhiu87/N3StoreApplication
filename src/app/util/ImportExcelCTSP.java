/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import app.model.Color;
import app.model.Material;
import app.model.Product;
import app.model.ProductDetail;
import app.model.Size;
import app.model.Sole;
import app.repository.ColorRepository;
import app.repository.MaterialRepository;
import app.repository.ProductDetailRepository;
import app.repository.ProductRepository;
import app.repository.SizeRepository;
import app.repository.SoleRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author HP
 */
public class ImportExcelCTSP {

    private final ProductDetailRepository productDetailRepository = new ProductDetailRepository();
    ;
    private final ProductRepository productRepository = new ProductRepository();
    private final ColorRepository colorRepository = new ColorRepository();
    private final MaterialRepository materialRepository = new MaterialRepository();
    private final SizeRepository sizeRepository = new SizeRepository();
    private final SoleRepository soleRepository = new SoleRepository();

    private ConcurrentMap<String, Material> mapCL = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Color> mapMS = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Size> mapKT = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Product> mapSP = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Sole> mapDG = new ConcurrentHashMap<>();

    public void ImportFile(String path) {
        try {
            List<ProductDetail> listProductDetails = new ArrayList<>();
            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            int mactsp = productDetailRepository.generateNextModelCodeNumber();
            addDataInMapCL(mapCL);
            addDataInMapKT(mapKT);
            addDataInMapMS(mapMS);
            addDataInMapSP(mapSP);
            addDataInMapDG(mapDG);
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                String sanPhamStr = String.valueOf(getCellValue(currentRow.getCell(1))).trim();
                String mauSacStr = String.valueOf(getCellValue(currentRow.getCell(2))).trim();
                String tenkichThuoc = String.valueOf(getCellValue(currentRow.getCell(3))).trim();
                String chatLieuStr = String.valueOf(getCellValue(currentRow.getCell(4))).trim();
                String deGiayStr = String.valueOf(getCellValue(currentRow.getCell(5))).trim();
                String moTa = String.valueOf(getCellValue(currentRow.getCell(6))).trim();
                String soLuongTon = String.valueOf(getCellValue(currentRow.getCell(7))).trim();
                String giaNhap = String.valueOf(getCellValue(currentRow.getCell(8))).trim();
                String giaBan = String.valueOf(getCellValue(currentRow.getCell(9))).trim();
                if (mauSacStr.isEmpty() && sanPhamStr.isEmpty() && tenkichThuoc.isEmpty()
                        && chatLieuStr.isEmpty() && moTa.isEmpty() && soLuongTon.isEmpty() && giaNhap.isEmpty() && giaBan.isEmpty() && deGiayStr.isEmpty()) {
                    continue;
                }
                if (mauSacStr.isEmpty() || sanPhamStr.isEmpty() || tenkichThuoc.isEmpty()
                        || chatLieuStr.isEmpty() || moTa.isEmpty() || soLuongTon.isEmpty() || giaNhap.isEmpty() || giaBan.isEmpty() && deGiayStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không để trống");
                    return;
                }
                try {
                    double soLuongTonSo = 0;
                    soLuongTonSo = Double.parseDouble(soLuongTon);
                    if (soLuongTonSo <= 0) {
                        JOptionPane.showMessageDialog(null, "Số lượng tồn lớn hơn 0");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Số lượng tồn phải là số");
                    return;
                }
                try {
                    double giaBanSo = 0;
                    giaBanSo = Double.parseDouble(giaNhap);
                    if (giaBanSo <= 0) {
                        JOptionPane.showMessageDialog(null, "Giá bán lớn hơn 0");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Giá bán phải là số");
                    return;
                }

                Product product = new Product();
                Color color = new Color();
                Material material = new Material();
                Size size = new Size();
                Sole sole = new Sole();

                if (mapSP.get(sanPhamStr) == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm");
                    return;
                } else {
                    product = mapSP.get(sanPhamStr);
                }

                if (mapMS.get(mauSacStr) == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy màu sắc");
                    return;
                } else {
                    color = mapMS.get(mauSacStr);
                }

                if (mapKT.get(String.valueOf((int) Double.parseDouble(tenkichThuoc))) == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy kích thước");
                    return;
                } else {
                    size = mapKT.get(String.valueOf((int) Double.parseDouble(tenkichThuoc)));
                }

                if (mapCL.get(chatLieuStr) == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy chất liệu");
                    return;
                } else {
                    material = mapCL.get(chatLieuStr);
                }

                if (mapDG.get(deGiayStr) == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy đế giày");
                    return;
                } else {
                    sole = mapDG.get(deGiayStr);
                }

                ProductDetail chiTietSP = new ProductDetail();
                ProductDetail chiTietSPcheck = productDetailRepository.findByAtribute(product.getName(), size.getName(), material.getName(), sole.getName(), color.getName());
//                String nameProduct, String nameSize, String nameMaterial, String nameSole, String nameColor
                if (chiTietSPcheck.getCode() == null) {
                    chiTietSP.setCode("CTSP" + mactsp++);
                    chiTietSP.setIdProduct(product.getId());
                    chiTietSP.setIdColor(color.getId());
                    chiTietSP.setIdSole(sole.getId());
                    chiTietSP.setIdSize(size.getId());
                    chiTietSP.setIdMaterial(material.getId());
                    chiTietSP.setDescription(moTa);
                    chiTietSP.setQuantity((int) Double.parseDouble(soLuongTon));
                    chiTietSP.setSellPrice(Double.valueOf(giaBan));
                    chiTietSP.setOriginPrice(Double.valueOf(giaNhap));
                    listProductDetails.add(chiTietSP);
                } else {
                    chiTietSPcheck.setSellPrice(Double.valueOf(giaBan));
                    chiTietSPcheck.setOriginPrice(Double.valueOf(giaNhap));
                    chiTietSPcheck.setDescription(moTa);
                    if (chiTietSPcheck.getQuantity() != null) {
                        chiTietSPcheck.setQuantity((int) Double.parseDouble(soLuongTon));
                    } else {
                        chiTietSPcheck.setQuantity((int) Double.parseDouble(soLuongTon));
                    }
                    listProductDetails.add(chiTietSPcheck);
                }
            }
            productDetailRepository.addAll(listProductDetails);
            JOptionPane.showMessageDialog(null, "Import file excel thành công");
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDataInMapSP(ConcurrentMap<String, Product> mapSimple) {
        List<Product> listSP = productRepository.getAll();
        getALlPutMapCheckSP(mapSimple, listSP);
    }

    public void addDataInMapMS(ConcurrentMap<String, Color> mapSimple) {
        List<Color> listSP = colorRepository.getAll();
        getALlPutMapCheckMS(mapSimple, listSP);
    }

    public void addDataInMapKT(ConcurrentMap<String, Size> mapSimple) {
        List<Size> listSP = sizeRepository.getAll();
        getALlPutMapCheckKT(mapSimple, listSP);
    }

    public void addDataInMapCL(ConcurrentMap<String, Material> mapSimple) {
        List<Material> listSP = materialRepository.getAll();
        getALlPutMapCheckCL(mapSimple, listSP);
    }

    public void addDataInMapDG(ConcurrentMap<String, Sole> mapSimple) {
        List<Sole> listSP = soleRepository.getAll();
        getALlPutMapCheckDG(mapSimple, listSP);
    }

    public void getALlPutMapCheckSP(ConcurrentMap<String, Product> mapSimple, List<Product> list) {
        for (Product xx : list) {
            mapSimple.put(xx.getName(), xx);
        }
    }

    public void getALlPutMapCheckKT(ConcurrentMap<String, Size> mapSimple, List<Size> list) {
        for (Size xx : list) {
            mapSimple.put(xx.getName(), xx);
        }
    }

    public void getALlPutMapCheckMS(ConcurrentMap<String, Color> mapSimple, List<Color> list) {
        for (Color xx : list) {
            mapSimple.put(xx.getName(), xx);
        }
    }

    public void getALlPutMapCheckCL(ConcurrentMap<String, Material> mapSimple, List<Material> list) {
        for (Material xx : list) {
            mapSimple.put(xx.getName(), xx);
        }
    }

    public void getALlPutMapCheckDG(ConcurrentMap<String, Sole> mapSimple, List<Sole> list) {
        for (Sole xx : list) {
            mapSimple.put(xx.getName(), xx);
        }
    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> {
                    return cell.getNumericCellValue();
                }
                case BOOLEAN -> {
                    return cell.getBooleanCellValue();
                }
                default -> {
                    return cell.getStringCellValue();
                }
            }
        } catch (Exception e) {
            return "";
        }
    }
}
