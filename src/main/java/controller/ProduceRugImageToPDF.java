package controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description DOTO
 * @Author Sire
 * @Date 2020/6/17 18:01
 * @Version 1.0
 **/
public class ProduceRugImageToPDF {

    /**
     * 图片路径
     */
    private static String IMAGE_PATH = "";
    /**
     * 营业执照
     */
    private static String ID_CARD_ZHE = "";
    /**
     * 营业执照-临时
     */
    private static String TEMP_ID_CARD_ZHE_NAME = "temp_id_card_zhe.jpg";
    /**
     * 缩放比例（ < 1.0时是等比例缩小图片，= 1.0时图片大小不变，> 1.0时是等比例放大图片）
     * 先设置为1.0，不改变图片的大小，如果原始图片合成的pdf文件即可满足大小限制则就不需要再缩放图片了
     */
    private static double SCALE = 1.0;
    /**
     * 目标pdf文件大小限制为 <= 256KB
     */
    private static int LENGTH = 256 * 1024;

    public static void main(String[] args) throws Exception {

        /**
         * TODO:原始加密图片的解密工作，并将解密后的图片存放到指定目录下（需在服务器上新建一个文件夹，权限；775）
         */

        // TODO:整合到代码中时，只需要修改这几个参数即可
        // 解密后的图片存放路径
        IMAGE_PATH = "D:\\MUrong\\AWORK\\MURONG\\开发\\";
        // 营业执照
        ID_CARD_ZHE = "123.jpg";

        // 调用一次主交易
        String newPDFPath = method();

        File file = new File(newPDFPath);
        // 判断文件大小是否满足规则
        while (file.length() > LENGTH) {
            System.out.println("文件大于256KB，缩放比例减0.1，程序继续...");

            // 缩放比例减0.1
            SCALE -= 0.1;

            // TODO:再执行一次缩放程序即可
            newPDFPath = method();
            file = new File(newPDFPath);
        }

        System.out.println("转化成功，文件路径：" + file.getAbsolutePath());

        // TODO:执行上报任务


        // TODO:上报成功后删除临时文件
        // deleteFile("filePath");


    }

    /**
     * @return 目标pdf文件的完整路径
     * @throws Exception
     */
    private static String method() throws Exception {
        // 原图1-身份证正面
        String fromFilePath1 = IMAGE_PATH + ID_CARD_ZHE;
        // 临时-身份证正面
        String TempFilePath1 = IMAGE_PATH + TEMP_ID_CARD_ZHE_NAME;

        // Step0：等比例缩放图片(可减小文件大小，且防失真)
        cutImage(fromFilePath1, TempFilePath1, SCALE);

        // Step1：利用 图片 和 pdf模板生成新的pdf文件
        Map<String, String> map = new HashMap();
        map.put("id_card_zheng", TempFilePath1);
        // 生成新的pdf文件
        String newPDFPath = imageAndTemplatePdf2NewPdf(map);
        return newPDFPath;
    }


    /**
     * 根据模板pdf 和 map数据（存放身份证正反面）来生成新的pdf文件
     *
     * @param imgMap
     */
    private static String imageAndTemplatePdf2NewPdf(Map<String, String> imgMap) {
        // 模板pdf文件路径
        String templatePath = "F:\\pdf\\img_template1.pdf";
        // 生成的新文件路径
        String newPDFPath = "F:\\reg_num.pdf";

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            //图片类的内容处理
            for (String key : imgMap.keySet()) {
                String value = imgMap.get(key);
                String imgpath = value;
                int pageNo = form.getFieldPositions(key).get(0).page;
                Rectangle signRect = form.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                //根据路径读取图片
                Image image = Image.getInstance(imgpath);
                //获取图片页面
                PdfContentByte under = stamper.getOverContent(pageNo);
                //图片大小自适应
                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                //添加图片
                image.setAbsolutePosition(x, y);
                under.addImage(image);
            }

            // 如果为false，则生成的PDF文件可以编辑，如果为true，则生成的PDF文件不可以编辑
            stamper.setFormFlattening(true);
            stamper.close();

            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return newPDFPath;
    }

    /**
     * @param fromFilePath   原始图片完整路径
     * @param saveToFilePath 缩略图片保存路径
     * @param scale          缩放比例（长宽等比压缩）
     * @throws Exception
     */
    public static void cutImage(String fromFilePath, String saveToFilePath, double scale) throws Exception {
        // 校验原始图片
        File file = new File(fromFilePath);
        if (!file.isFile()) {
            throw new Exception(file + " is not image file error in cutImage!");
        }

        BufferedImage buffer = ImageIO.read(file);
        /*
         * width和height为压缩后图片的宽和高
         */
        int width = (int) (buffer.getWidth() * scale);
        int height = (int) (buffer.getHeight() * scale);

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);
        buffer = op.filter(buffer, null);
        buffer = buffer.getSubimage(0, 0, width, height);
        try {
            ImageIO.write(buffer, "jpg", new File(saveToFilePath));
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: " + ex.getMessage());
        }
    }

    /**
     * 临时文件删除
     */
    private static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

}
