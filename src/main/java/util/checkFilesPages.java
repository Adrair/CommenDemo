package util;


import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileInputStream;


/**
 * @Description DOTO:判断文件的页数
 * @Author Sire
 * @Date 2020/6/24 15:00
 * @Version 1.0
 **/
public class checkFilesPages {

    /**
     * 获取pdf文件的页数
     * @param filePath ：pdf文件的绝对路径
     * @return
     * @throws Exception
     */
    public static int pdfPages(File filePath) throws Exception {
        int pageNumber=0;
        PdfReader pdfReader;
        try{
            pdfReader = new PdfReader(new FileInputStream(filePath));
            pageNumber = pdfReader.getNumberOfPages();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageNumber;
    }
}
