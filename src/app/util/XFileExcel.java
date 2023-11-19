/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

                for (int x = 0; x < excelSheet.getRow(0).getPhysicalNumberOfCells(); x++) {
                    excelSheet.autoSizeColumn(x);
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

    public void importExcelToJtableJava(DefaultTableModel model) {

        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath = "D:\\Downloads";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Select Excel File");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = excelFileChooser.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelImportToJTable = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);

                for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    XSSFCell excelName = excelRow.getCell(0);
                    XSSFCell excelGender = excelRow.getCell(1);
                    XSSFCell excelProgrammingLanguage = excelRow.getCell(2);
                    XSSFCell excelSubject = excelRow.getCell(3);

                    Object[] rowData = {
                        (excelName != null) ? excelName.toString() : null,
                        (excelGender != null) ? excelGender.toString() : null,
                        (excelProgrammingLanguage != null) ? excelProgrammingLanguage.toString() : null,
                        (excelSubject != null) ? excelSubject.toString() : null
                    };

                    model.addRow(rowData);
                }
            } catch (IOException iOException) {
                iOException.printStackTrace();
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
        }
    }

}
