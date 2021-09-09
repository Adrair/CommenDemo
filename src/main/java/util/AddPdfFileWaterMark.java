package util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;

/**
 * @Description DOTO  向pdf 文档中添加水印
 * @Author Sire
 * @Date 2020/7/1 13:31
 * @Version 1.0
 **/
public class AddPdfFileWaterMark {

    private static int interval = -5;
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String inputFile = "F:\\total3.pdf";
        String outputFile = "F:\\total4.pdf";
        String content = "BOSS okcard";
        waterMark(inputFile, outputFile, content);
    }

    //给PDF添加水印
    //inputFile 文件路径+名称
    //outputFile 添加水印后输出文件保存的路径+名称
    //waterMarkName 添加水印的内容
    public static void waterMark(String inputFile,String outputFile, String waterMarkName) {
        try {
            PdfReader reader = new PdfReader(inputFile);  //切记这里的参数是文件的路径 ，路径必须是双斜杠的如F:\\123.pdf，不能是F:/123.pdf 或者F:\123.pdf
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outputFile));

            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   BaseFont.EMBEDDED);

            Rectangle pageRect = null;
            // 设置水印透明度
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.3f);
            gs.setStrokeOpacity(0.4f);
            int total = reader.getNumberOfPages() + 1;

            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH = 0;
            int textW = 0;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());

            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                under = stamper.getOverContent(i);//在内容上方添加水印
                // under = stamper.getUnderContent(i);//在内容下方加水印
                under.saveState();
                under.setGState(gs);
                // 设置字体和字体大小
                under.beginText();
                under.setFontAndSize(base, 20);
                under.setTextMatrix(70, 200);
               // 水印文字成30度角倾斜展示多个
                for (int height = interval + textH; height < pageRect.getHeight();height = height + textH*4) {
                    for (int width = interval + textW; width < pageRect.getWidth() + textW;width = width + textW*2) {
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW,height - textH, 30);
                    }
                }


              /*Image image = Image.getInstance("G:/2.jpeg");
              img.setAlignment(Image.LEFT | Image.TEXTWRAP);
              img.setBorder(Image.BOX); img.setBorderWidth(10);
              img.setBorderColor(BaseColor.WHITE); img.scaleToFit(100072);//大小
              img.setRotationDegrees(-30);//旋转

                image.setAbsolutePosition(200, 206); // set the first background
                // image of the absolute
                image.scaleToFit(200, 200);
                under.addImage(image);*/

                //只展示一条
                //under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, 300, 350, 55);



                // 添加水印文字
                under.endText();
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
