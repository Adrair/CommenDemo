package controller;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;


/**
 * 图片压缩，不改变图片尺寸，只改变图片大小的方法
 */

public class ImgUtils{
    /**
     * 压缩图片（通过降低图片质量）
     * @explain 压缩图片,通过压缩图片质量，保持原图大小
     * @param sourceImageUrl 待压缩文件路径
     * @param targetImageUrl 压缩后文件路径
     * @param quality 图片质量（0-1）
     * @throws
     */
    public void compressPicByQuality(String sourceImageUrl,String targetImageUrl,float quality) throws FileNotFoundException, IOException{
        byte[] sourceData = null;
        byte[] targetData = null;
        FileImageInputStream input = null;
        input = new FileImageInputStream(new File(sourceImageUrl));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int numBytesRead = 0;
        while ((numBytesRead = input.read(buf)) != -1) {
            output.write(buf, 0, numBytesRead);
        }
        sourceData = output.toByteArray();
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(sourceData);
            BufferedImage image = ImageIO.read(byteInput);

            // 如果图片空，返回空
            if (image == null) {
                return;
            }
            // 得到指定Format图片的writer（迭代器）
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
            // 得到writer
            ImageWriter writer = (ImageWriter) iter.next();
            // 得到指定writer的输出参数设置(ImageWriteParam )
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            // 设置可否压缩
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // 设置压缩质量参数
            iwp.setCompressionQuality(quality);

            iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

            ColorModel colorModel = ColorModel.getRGBdefault();
            // 指定压缩时使用的色彩模式
            iwp.setDestinationType(
                    new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

            // 开始打包图片，写入byte[]
            // 取得内存输出流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IIOImage iIamge = new IIOImage(image, null, null);

            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
            writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
            writer.write(null, iIamge, iwp);
            targetData = byteArrayOutputStream.toByteArray();
            System.out.print(targetData.length);
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(targetImageUrl));
            imageOutput.write(targetData, 0, targetData.length);
            imageOutput.close();
        } catch (IOException e) {
            System.out.println("write errro");
            e.printStackTrace();
        }
    }
}
