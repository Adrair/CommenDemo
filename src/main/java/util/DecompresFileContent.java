package util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @Description 将 （使用 DEFLATE 压缩算法压缩后， Base64 编码的方式传输经压缩编码的文件内容）进行压缩和解压成文件
 * @Author Sire
 * @Date 2020/11/16 16:41
 * @Version 1.0
 **/
public class DecompresFileContent {

    public static void main(String[] args) {
        //生成的文件地址
        String filePath="F:\\1605514505915.txt";
       //传入压缩后的数据流（将文件转压缩，然后转换成数据流）
        String baseFileContent ="eJylzjEOQiEQBND+J/8qZHbZgaW29Ep7IWNhZ2XsLL2TJCqKhc2firA7D1wxQjAUKhA4pIelsT2vsmhI31ELIO9jFzX8q2tucT2cj5fT/RbrMo1654et4h8Wjd6febFFkLiBZsFEV9bx44oNsNAmmFbeMFUkJ7F/+qiOgwUzgVRsXR4bFWeq";
        denCodeFileContent(filePath,baseFileContent,"gbk");
    }

    /**
     *   Base64 编码的方式 传输经压缩编码的文件内容
     * @param filePath  文件路径
     * @param encoding 转换成的编码格式
     * @return
     */
    public static String enCodeFileContent(String filePath, String encoding) {
        String baseFileContent = "";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var17) {
                var17.printStackTrace();
            }
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            int fl = in.available();
            if (in != null) {
                byte[] s = new byte[fl];
                in.read(s, 0, fl);
                s = (new String(s, "GBK")).getBytes(encoding);
                baseFileContent = new String( Base64.encodeBase64(deflater(s)), encoding);
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }
        return baseFileContent;
    }

    /**
     *
     * @param filePath 生成的文件路径
     * @param baseFileContent 需要解压转换的文件流
     *  @param encoding 需要解压转换的文件流 需要转换的编码格式
     */
    public static void denCodeFileContent(String filePath,String baseFileContent,String encoding){
        byte[] fileArray = new byte[0];
        File file = null;
        FileOutputStream out = null;
        try {
            fileArray = inflater(Base64.decodeBase64(baseFileContent.getBytes(encoding)));
            file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            out.write(fileArray, 0, fileArray.length);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 解压缩.
     *
     * @param inputByte
     *            byte[]数组类型的数据
     * @return 解压缩后的数据
     * @throws IOException
     */
    public static byte[] inflater(byte[] inputByte) throws IOException {
        int compressedDataLength = 0;
        Inflater compresser = new Inflater(false);
        compresser.setInput(inputByte, 0, inputByte.length);
        ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.inflate(result);
                if (compressedDataLength == 0) {
                    break;
                }
                o.write(result, 0, compressedDataLength);
            }
        } catch (Exception ex) {
            System.err.println("Data format error!\n");
            ex.printStackTrace();
        } finally {
            o.close();
        }
        compresser.end();
        return o.toByteArray();
    }

    /**
     * 压缩数据
     * @param inputByte byte[]数组类型的数据
     * @return 压缩后的数据
     * @throws IOException
     */

    public static byte[] deflater(byte[] inputByte) throws IOException {
        int compressedDataLength = 0;
        Deflater compresser = new Deflater();
        compresser.setInput(inputByte);
        compresser.finish();
        ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        byte[] result = new byte[1024];
        try {
            while (!compresser.finished()) {
                compressedDataLength = compresser.deflate(result);
                o.write(result, 0, compressedDataLength);
            }
        } finally {
            o.close();
        }
        compresser.end();
        return o.toByteArray();
    }
}
