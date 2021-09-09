package util;

import java.io.*;

/**
 * @Description 将文件UTF-8转换成GBK
 * @Author Sire
 * @Date 2021/2/3 16:05
 * @Version 1.0
 **/
public class fileCodeConvert {


    public static void main(String[] args) {
        String inPath="C:\\Users\\gaojinlei\\Desktop\\PRD\\B202011290023_20_01.txt";
        fileCodeConvert(inPath);
    }

    public static void fileCodeConvert(String inPath) {
        try {
            File inFile = new File(inPath);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inFile), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }

            String outPath = inFile.getParent()+File.separator+"tmp."+inFile.getName().substring(inFile.getName().lastIndexOf(".") + 1);
            File outFile = new File(outPath);
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outFile), "GBK"));
            bw.write(sb.toString());
            bw.flush();
            bw.close();
            br.close();

            inFile.delete();
            outFile.renameTo(new File(inPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
