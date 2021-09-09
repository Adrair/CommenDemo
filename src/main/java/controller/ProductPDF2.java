package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import util.PageXofYTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * @Description 根据模板生成PDF文件
 * @Author Gjl
 * @Date 2020/5/12 14:17
 * @Version 1.0
 *
 **/
public class ProductPDF2 {

    public static void main(String[] args) throws Exception {
        //文字map
        Map<String, String> map1 = new HashMap<>();
        map1.put("receipt_id", "hd2018071700800167880");
        map1.put("field_nm1", "交易时间");
        map1.put("data1", "2020/7/27 14:24");
        map1.put("field_nm2", "记账流水号");
        map1.put("data2", "2018071700800167880");
        map1.put("field_nm3", "业务类型");
        map1.put("data3", "充值");
        map1.put("field_nm4", "收付款标志");
        map1.put("data4", "收入");
        map1.put("field_nm5", "付款企业账号");
        map1.put("data5", "打款银行账号");
        map1.put("field_nm6", "收款企业编号");
        map1.put("data6", "82000000000414");
        map1.put("field_nm7", "付款企业名称");
        map1.put("data7", "付款企业名称");
        map1.put("field_nm8", "收款企业名称");
        map1.put("data8", "上海市天龙生物科技有限公司");
        map1.put("field_nm9", "支付总额");
        map1.put("data9", "510.00元");
        map1.put("field_nm10", "订单状态");
        map1.put("data10", "交易成功");
        map1.put("field_nm11", "备注");
        map1.put("data11", "充值一笔钱");
        map1.put("field_nm12", "");
        map1.put("data12", "");



        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("datemap", map1);
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
        String templatePath = "F:\\pdf\\6modulepdf.pdf.pdf";
        //输出PDF文件
        String newParh = "F://pdf";
        //家水印之后的文件
        String outputFile="F:\\pdf\\waterPDF.pdf";
        //水印图片路径
        String waterMarkName="D:\\MUrong\\AWORK\\MURONG\\开发\\logo.png";
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
        newParh=newParh+ "//" + times + ".pdf";
        waterMark(newParh,outputFile,waterMarkName,1);
        //compress(newParh + "/" + times + ".pdf", newParh + "/" + times + ".zip");
    }





    /**
     *
     * @param inputFile 要加水印的原pdf文件路径
     * @param outputFile 加了水印后要输出的路径
     * @param waterMarkName 水印图片路径
     */
    public static void waterMark(String inputFile,String outputFile, String waterMarkName,int pageSize) {
        try {
            PdfReader reader = new PdfReader(inputFile, "PDF".getBytes());
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outputFile));
            Image img = Image.getInstance(waterMarkName);// 插入水印
            //设置图片水印的位置。
            img.setAbsolutePosition(535, 245);
            //开始水印
            for(int i = 1; i <= pageSize; i++) {
                PdfContentByte under = stamp.getUnderContent(i);
                under.addImage(img);
            }
            stamp.close();// 关闭
            System.out.println("文件路径："+inputFile);
            File tempfile = new File(inputFile);
            //删除原文件。
            if(tempfile.exists()) {
                System.out.println("文件存在");
                tempfile.delete();
                if (tempfile.delete()) {
                    System.out.println("删除单个文件" + inputFile + "成功！");
                } else {
                    System.out.println("删除单个文件" + inputFile + "失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
