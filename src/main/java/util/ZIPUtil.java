package util;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件zip
 */
public class ZIPUtil {

    private static Logger logger = LoggerFactory.getLogger(ZIPUtil.class);

    /**
     * @param srcFilePath  压缩源路径
     * @param destFilePath 压缩目的路径
     */
    public static void compress(String srcFilePath, String destFilePath) {

        logger.info("压缩源路径=" + srcFilePath);
        File src = new File(srcFilePath);

        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }
        File zipFile = new File(destFilePath);
        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            String baseDir = "";
            compressbyType(src, zos, baseDir);
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 按照原路径的类型就行压缩，文件路径直接把文件压缩
     *
     * @param src
     * @param zos
     * @param baseDir
     */
    private static void compressbyType(File src, ZipOutputStream zos, String baseDir) {
        if (!src.exists()) {
            return;
        }
        logger.info("压缩路径：" + baseDir + src.getName());
        if (src.isFile()) {
            //src是文件
            compressFile(src, zos, baseDir);
        } else if (src.isDirectory()) {
            //src是文件夹
            compressDire(src, zos, baseDir);
        }


    }

    /**
     * 压缩文件
     *
     * @param file
     * @param zos
     * @param baseDir
     */
    private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bus = new BufferedInputStream(new FileInputStream(file));
            ZipEntry zipEntry = new ZipEntry(baseDir + file.getName());
            zos.putNextEntry(zipEntry);
            int count = 0;
            byte[] buf = new byte[1024];
            while ((count = bus.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
            bus.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件夹
     *
     * @param dir
     * @param zos
     * @param baseDir
     */
    private static void compressDire(File dir, ZipOutputStream zos, String baseDir) {


        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files.length == 0) {
            try {
                zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (File file : files) {
            compressbyType(file, zos, dir.getName() + File.separator);
        }

    }


}
