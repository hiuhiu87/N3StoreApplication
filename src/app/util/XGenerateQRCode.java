/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 *
 * @author Admin
 */
public class XGenerateQRCode {

    private static String outputPath = ""; // Biến lưu đường dẫn đã chọn

    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public static boolean doGenerate(String code, String nameProduct) {
        try {
            if (outputPath.isEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn thư mục lưu trữ QR Code");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    outputPath = selectedDirectory.getAbsolutePath(); // Lưu đường dẫn được chọn
                } else {
                    System.out.println("Hủy bỏ việc chọn thư mục lưu trữ.");
                    return false;
                }
            }

            String path = outputPath + File.separator + nameProduct + "_QR" + ".png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            generateQRcode(code, path, charset, hashMap, 200, 200);
            return true;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
