import controller.CharConversion;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Demo1 {

    public static void main(String[] args) throws Exception {

// 将元------->分
        /*String tx_amt="0.01";
        Double tx_amt1=Double.valueOf(tx_amt);
        System.out.println("转换后的金额="+tx_amt1);
        String tx_amt2 = BigDecimal.valueOf(tx_amt1)
                .multiply(new BigDecimal(100)).toString();

        System.out.println("转换后的金额="+tx_amt2);*/


       /* DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date star = dateFormat.parse("20180121");
        Date endDay = dateFormat.parse("20200101");
        Long startTime=star.getTime();
        Long endTime=endDay.getTime();
        Long num = endTime - startTime;
        System.out.println("相差天数为="+num/24/60/60/1000);
        System.out.println("相差天数为="+num/24/60/60/1000/30);*/

        /*String nucc_server_ipstr="192.168.2.239|192.168.2.236";
        String [] nucc_server_iplist =nucc_server_ipstr.split("\\|");
        for (int i = 0; i <nucc_server_iplist.length ; i++) {
            System.out.println(nucc_server_iplist[i]);
        }*/
        String st1="安付宝-上海百联集团—有限—公司（BL）";
        System.out.println(st1);
        st1= CharConversion.qj2bj(st1);
        System.out.println(st1);
        st1=st1.replaceAll("\\—","-");
        System.out.println(st1);

        String st2="安付宝-上海百联集团—有限—公司（BL）";
    }

}
