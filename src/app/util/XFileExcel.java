/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class XFileExcel {

    public static boolean exportToFile(DefaultTableModel model) throws IOException {
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;

        //Choose Location For Saving Excel File
        JFileChooser excelFileChooser = new JFileChooser("D:\\Downloads");
        //Change Dilog Box Title
        excelFileChooser.setDialogTitle("Save As");
        //Onliny filter files with these extensions "xls", "xlsx", "xlsm"
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);

        //Check if save button is clicked
        if (excelChooser == JFileChooser.APPROVE_OPTION) {

            try {
                //Import excel poi libraries to netbeans
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                //            Loop to get jtable columns and rows

                // Write the column names to the Excel sheet
                XSSFRow excelRow = excelSheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    XSSFCell excelCell = excelRow.createCell(i);
                    excelCell.setCellValue(model.getColumnName(i));
                }

                // Write the data to the Excel sheet
                for (int i = 0; i < model.getRowCount(); i++) {
                    XSSFRow row = excelSheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        XSSFCell cell = row.createCell(j);
                        cell.setCellValue(model.getValueAt(i, j).toString());
                    }
                }

                //Append xlsx file extensions to selected files. To create unique file names
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                return true;
            } catch (FileNotFoundException ex) {
                return false;
            } catch (IOException ex) {
                return false;
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                    return false;
                }
            }

        }
        return false;
    }

}
