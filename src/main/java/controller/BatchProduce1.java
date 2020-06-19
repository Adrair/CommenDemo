package controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;

/**
 * @Description TODO
 * @Author Gjl
 * @Date 2019/10/31 17:05
 * @Version 1.0
 * <p>
 * 根据SQ模板快速生成SQL脚本
 **/
public class BatchProduce1 {


    public static void main(String[] args) throws Exception {

        //数据来源文件路径
        String excelPath = "F:\\用于批量脚本生成的excel.xls";
        //输出文件路径
        String filenameTemp = "F:\\batchProduce.txt";
        //脚本中间符号
        String insql = "','";
        //脚本结束符号
        String endsql = "','0',to_char(sysdate, 'yyyyMMddHH24miss'));";
        //插入脚本
        String insert = "insert into imerc.t_merc_mercbusstlcyc(merc_id,crdmerc_id,crd_stlcyc,stl_eff_dt,stl_exp_dt,flg,tm_smp)values('";
        //存储每一行数据
        ArrayList<String> arrayList = new ArrayList<String>();
        //输入流文件
        BufferedReader reader = null;
        //输出流文件
        BufferedWriter writer = null;
        //存储生成脚本的数组
        //String[] details = null;
        ArrayList<String> details = new ArrayList<String>();


        if (excelPath.endsWith("txt")) { //文件是txt 格式
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath), "UTF-8"));
                String tempString = null;
                while ((tempString = reader.readLine()) != null) {
                    arrayList.add(tempString);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            int j = 0;
            for (String ine : arrayList) {
                sb.append(insert);
                String[] content = ine.split("\t");
                for (int i = 0; i < content.length; i++) {
                    if (i == (content.length - 1)) {
                        sb.append(content[i].trim()).append(endsql);
                    } else {
                        sb.append(content[i].trim()).append(insql);
                    }
                }
                details.add(sb.toString());
                j++;
            }
        } else if (excelPath.endsWith("xls") || excelPath.endsWith("xlsx")) { //文件是excel 格式

            try {
                InputStream excelFile = new FileInputStream(new File(excelPath));
                Workbook wb = null;

                if (excelPath.endsWith("xls")) {
                    wb = new HSSFWorkbook(excelFile);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }


                //开始解析excel文件
                Sheet sheet = wb.getSheetAt(0);//读取sheet0
                for (int i = 1; i <= sheet.getLastRowNum() + 1; i++) { //遍历行
                    StringBuilder sb = new StringBuilder();
                    sb.append(insert);
                    Row row = sheet.getRow(i);//获取每一行的内容
                    if (row != null) {
                        for (int j = 0; j < row.getLastCellNum(); j++) {//遍历列
                            System.out.println("第" + i + "行数据，第" + j + "列数据");
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                if (j == row.getLastCellNum() - 1) {
                                    sb.append(cell.getStringCellValue().trim()).append(endsql);
                                } else {
                                    System.out.println("内容=" + cell.getStringCellValue());
                                    sb.append(cell.getStringCellValue().trim()).append(insql);
                                }
                            }
                        }
                        details.add(sb.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件类型错误!");
            return;
        }


        //输出文件
        if (details.size() != 0) {
            File file2 = new File(filenameTemp);
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameTemp), "GBK"));
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                for (String detail : details) {
                    if (detail != null) {
                        writer.write(detail.trim());
                        writer.newLine();//换行
                        writer.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
