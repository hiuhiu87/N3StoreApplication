/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DownloadProductDetailTemplate {

    public static boolean ImportExcel() {
        boolean check = false;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Quản Lý Sản Phẩm");
            int rowNum = 0;
            Row firstRow = sheet.createRow(rowNum++);
            Cell firstCell1 = firstRow.createCell(0);
            Cell firstCell2 = firstRow.createCell(1);
            Cell firstCell3 = firstRow.createCell(2);
            Cell firstCell4 = firstRow.createCell(3);
            Cell firstCell5 = firstRow.createCell(4);
            Cell firstCell6 = firstRow.createCell(5);
            Cell firstCell7 = firstRow.createCell(6);
            Cell firstCell8 = firstRow.createCell(7);
            Cell firstCell9 = firstRow.createCell(8);
            Cell firstCell10 = firstRow.createCell(9);

            firstCell1.setCellValue("STT");
            firstCell2.setCellValue("Sản phẩm");
            firstCell3.setCellValue("Màu sắc");
            firstCell4.setCellValue("Kích thước");
            firstCell5.setCellValue("Chất liệu");
            firstCell6.setCellValue("Đế Giày");
            firstCell7.setCellValue("Mô tả");
            firstCell8.setCellValue("Số lượng tồn");
            firstCell9.setCellValue("Giá bán");
            firstCell10.setCellValue("Giá nhập");

            int index = 1;
            Row row = sheet.createRow(1);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);
            Cell cell3 = row.createCell(2);
            Cell cell4 = row.createCell(3);
            Cell cell5 = row.createCell(4);
            Cell cell6 = row.createCell(5);
            Cell cell7 = row.createCell(6);
            Cell cell8 = row.createCell(7);
            Cell cell9 = row.createCell(8);
            Cell cell10 = row.createCell(9);

            cell1.setCellValue(String.valueOf(1));
            cell2.setCellValue("Adidas");
            cell3.setCellValue("Blue");
            cell4.setCellValue("38");
            cell5.setCellValue("Da PU");
            cell6.setCellValue("Đế Cao");
            cell7.setCellValue("Chất Lượng Tuyệt Vời");
            cell8.setCellValue("10");
            cell9.setCellValue("120000");
            cell10.setCellValue("250000");

            try {
                String pathFile = "D:" + "\\" + "QuanLySanPhamTemplate.xlsx";
                File file = new File(pathFile);
                FileOutputStream outputStream = new FileOutputStream(pathFile);
                workbook.write(outputStream);
                workbook.close();
                if (!Desktop.isDesktopSupported()) {
                    return check;
                }
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            check = true;
        } catch (Exception e) {
        }
        return check;
    }
}
