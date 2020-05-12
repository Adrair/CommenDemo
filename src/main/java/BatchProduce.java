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
public class BatchProduce {


    public static void main(String[] args) throws Exception {

        //数据来源文件路径
        String excelPath = "F:\\OK费率\\15store_cnm.xls";
        //输出文件路径
        String filenameTemp = "F:\\OK费率\\okstore1crdmfee.sql";
        //脚本中间符号
        String insql1 = "','";
        String insql2 = "','";
        //String insql3= "','";
        //脚本结束符号
        //String endsql = "','01','00001','安付宝会员卡','1',to_char(sysdate, 'yyyyMMddHH24miss'));";//门店开通卡权限-统一商户管理
        String endsql = "','2','00001','2','1',to_char(sysdate, 'yyyyMMddHH24miss'),to_char(sysdate, 'yyyyMMddHH24miss'));";//门店开通卡权限-互联网方向
        //插入脚本
        //String startsql = "insert into imerc.t_merc_crdinfo(store_id,crdstore_id,crd_typ,main_body,body_nm,eff_flg,tm_smp)values('";//门店开通卡权限-统一商户管理
        String startsql = "insert into ipay.t_urm_mercrdinfo (merc_id,merc_nm,out_merc_id,merc_level,main_body,pre_crd_typ,eff_flg,lst_opr_tm,tm_smp)values('";//门店开通卡权限-互联网方向

        String [] sql={startsql,insql1,endsql};

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
                StringBuilder sb = new StringBuilder();
                sb.append(sql[0]);
                String[] content = ine.split("\t");
                for (int i = 0; i < content.length; i++) {
                    if (i == (content.length - 1)) {
                        sb.append(content[i].trim()).append(sql[2]);
                    } else {
                        sb.append(content[i].trim()).append(sql[1]);
                    }
                }
                details.add(sb.toString());
                j++;
            }
        }
        else if (excelPath.endsWith("xls") || excelPath.endsWith("xlsx")) { //文件是excel 格式

            try {
                InputStream excelFile = new FileInputStream(new File(excelPath));
                Workbook wb = null;

                if(excelPath.endsWith("xls")){
                    wb = new HSSFWorkbook(excelFile);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }


                //开始解析excel文件
                Sheet sheet = wb.getSheetAt(0);//读取sheet0
                for(int i = 1;i<= sheet.getLastRowNum()+1;i++){ //遍历行
                    StringBuilder sb = new StringBuilder();
                    sb.append(sql[0]);
                    Row row= sheet.getRow(i);//获取每一行的内容
                    if(row != null){
                        for (int j = 0; j < row.getLastCellNum() ; j++){//遍历列
                            Cell cell=row.getCell(j);
                            if(cell != null){
                                cell.setCellType(CellType.STRING);//定义单元格的数据类型，转换成字符串类型
                                if(j == row.getLastCellNum()-1){
                                    sb.append(cell.getStringCellValue().trim()).append(sql[2]);
                                }else{
                                    sb.append(cell.getStringCellValue().trim()).append(sql[1]);
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
        if(details.size() != 0){
            File file2 = new File(filenameTemp);
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameTemp), "GBK"));
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                for (String detail : details) {
                    if(detail!=null){
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
