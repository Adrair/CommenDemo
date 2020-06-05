import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.sun.org.apache.xpath.internal.functions.FuncSubstring;
import util.PageXofYTest;
import util.ZIPUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static util.ZIPUtil.compress;


/**
 * @Description TODO
 * @Author Gjl
 * @Date 2020/5/12 14:17
 * @Version 1.0
 **/
public class ProductPDF {

    public static void main(String[] args) throws Exception {
        //文字map
        Map<String, String> map1 = new HashMap<>();
        map1.put("ord_no", "张三");
        map1.put("date_time", "张三");
        map1.put("business_type", "张三");
        map1.put("ord_sts", "张三");
        map1.put("rcv_crd_no", "张三");
        map1.put("trn_crd_no", "张三");
        map1.put("rcv_crd_nm", "张三");
        map1.put("trn_crd_nm", "张三");
        map1.put("rcv_cap_corg", "张三");
        map1.put("trn_cap_corg", "张三");
        map1.put("trn_amt", "张三");
        map1.put("trn_amt_uppercase", "张三");

        //图片map
        Map<String, String> map2 = new HashMap<>();
        map2.put("img1", "D:\\MUrong\\AWORK\\MURONG\\开发\\45.jpeg");
        map2.put("img2", "D:\\MUrong\\AWORK\\MURONG\\开发\\48.jpg");

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("datemap", map1);
        objectMap.put("imgmap", map2);
        productPdfOut(objectMap);

    }

    /**
     * 根据模板进行生成pdf
     *
     * @param objectMap
     * @throws Exception
     */

    public static void productPdfOut(Map<String, Object> objectMap) throws Exception {
        //模板路径
        String templatePath = "F:\\pdf\\modulepdf1.pdf";
        //输出PDF文件
        String newParh = "F:/pdf";
        PdfReader reader;
        FileOutputStream outFile;
        ByteArrayOutputStream byteArrayOutputStream;
        PdfStamper pdfStamper;
        SimpleDateFormat simp;
        String times = "2020";
        try {
            simp = new SimpleDateFormat("yyyy-MM-dd");
            times = simp.format(new Date()).trim();
            //设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //输出流
            outFile = new FileOutputStream(newParh + "/" + times + ".pdf");
            //读取PDF模板
            reader = new PdfReader(templatePath);

            byteArrayOutputStream = new ByteArrayOutputStream();

            pdfStamper = new PdfStamper(reader, byteArrayOutputStream);
            AcroFields form = pdfStamper.getAcroFields();
            //文件类的内容处理
            Map<String, String> dateMap = (Map<String, String>) objectMap.get("datemap");
            form.addSubstitutionFont(bfChinese);
            for (String key : dateMap.keySet()) {
                String value = dateMap.get(key);
                form.setField(key, value);
            }
            //图片类的内容处理
            Map<String, String> imgMap = (Map<String, String>) objectMap.get("imgmap");
            for (String key : imgMap.keySet()) {
                //获取图片的路径
                String value = imgMap.get(key);
                String imgpath = value;
                int pageNo = form.getFieldPositions(key).get(0).page;
                Rectangle signRect = form.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                //根据路径读取图片
                Image image = Image.getInstance(imgpath);
                //获取图片的页面
                PdfContentByte under = pdfStamper.getOverContent(pageNo);
                //图片大小自适应
                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                //添加图片
                image.setAbsolutePosition(x, y);
                under.addImage(image);
            }
            pdfStamper.setFormFlattening(true);//如果为false，则生成的PDF可编辑，如果为true，生成的PDF不可编辑
            pdfStamper.close();
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, outFile);
            document.open();
            PdfImportedPage importedPage = copy.getImportedPage(new PdfReader(byteArrayOutputStream.toByteArray()), 1);
            copy.addPage(importedPage);
            document.close();

        } catch (Exception e) {
            System.out.println("报错信息：" + e.toString());
        }
        compress(newParh + "/" + times + ".pdf", newParh + "/" + times + ".zip");
    }


    /**
     * 无模板创建PDF
     *
     * @param objectMap
     * @throws Exception
     */
    public void createAllPdf(Map<String, Object> objectMap) throws Exception {
        String path = "";
        Integer paperId = 0;

        PdfReader reader = null;
        PdfStamper stamper = null;
        String examPath = "";
        try {
            reader = new PdfReader("模板路径");
            SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
            String times = simp.format(new Date()).trim();
            //创建文件
            Document document = new Document();
            //设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //文件路径
            examPath = path + "/exam-" + paperId;
            File files = new File(examPath);
            if (!files.exists() && !files.isDirectory()) {
                files.mkdirs();
            }
            //创建pdf
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(examPath + "/" + times + ".pdf"));
            //设置页面布局
            pdfWriter.setViewerPreferences(PdfWriter.PageLayoutOneColumn);
            //页面
            pdfWriter.setPageEvent(new PageXofYTest());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
