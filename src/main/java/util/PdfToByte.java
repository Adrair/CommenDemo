package util;

/**
 * @Description DOTO
 * @Author Sire
 * @Date 2020/7/2 15:36
 * @Version 1.0
 **/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PdfToByte {

    public static void main(String[] args) throws Exception {
        String fromPath = "F://fileupload//aaa.docx";
        String toPath = "C://Users//Desktop//aaaa.docx";
        String fileStr = PdfToByte.fileToString(fromPath);
        PdfToByte.stringSaveAsFile(fileStr, toPath);
    }

    /**
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String fileBase64ToString(String filePath) throws Exception {
        if(StringUtils.isEmpty(filePath.trim())){
            return "";
        }
        File  file = new File(filePath);
        FileInputStream inputFile=null;
        byte[] buffer = new byte[(int)file.length()];
        try {
            inputFile = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inputFile.read(buffer);
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BASE64Encoder().encode(buffer);
    }


    /**
     * summary:将字符串存储为文件 采用Base64解码
     *
     * @param fileStr
     * @param outfile
     *
     */
    public static void streamSaveAsFile(InputStream is, String outFileStr) {
        FileOutputStream fos = null;
        try {
            File file = new File(outFileStr);
            BASE64Decoder decoder = new BASE64Decoder();
            fos = new FileOutputStream(file);
            byte[] buffer = decoder.decodeBuffer(is);
            fos.write(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
    }

    /**
     *
     *
     * summary:将字符串存储为文件
     *
     * @param fileStr
     * @param outfile
     *
     */
    public static void stringSaveAsFile(String fileStr, String outFilePath) {
        InputStream out = new ByteArrayInputStream(fileStr.getBytes());
        PdfToByte.streamSaveAsFile(out, outFilePath);
    }

    /**
     * 将流转换成字符串 使用Base64加密
     *
     * @param in输入流
     * @return
     * @throws IOException
     */
    public static String streamToString(InputStream inputStream) throws IOException {
        byte[] bt = toByteArray(inputStream);
        inputStream.close();
        String out = new BASE64Encoder().encodeBuffer(bt);
        return out;
    }

    /**
     * 将流转换成字符串
     *
     * @param in输入流
     * @return
     * @throws IOException
     */
    public static String fileToString(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);
        String fileStr = PdfToByte.streamToString(is);
        return fileStr;
    }

    /**
     *
     * summary:将流转化为字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     *
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        byte[] result = null;
        try {
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
        return result;
    }


}
