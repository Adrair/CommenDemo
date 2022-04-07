import controller.CharConversion;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Demo1 {

    public static void main(String[] args) throws Exception {
        int x=2;

        int[] arr1 ={1,2,'a'};

        System.out.println(Arrays.toString(arr1));
        /*int[] arr2 =new int[3];
        int[] arr3 =new int[]{1,2,3};
       // int[] arr4 =new int[3]{1,2,3};
        System.out.println(arr1.toString());
        System.out.println(arr2.toString());
        System.out.println(arr3.toString());
        //System.out.println(arr4);
        String s,t,c,e;
        if(s.equals(t)){
            System.out.println(1);
        }*/


















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
        /*String st1="安付宝-上海百联集团—有限—公司（BL）";
        System.out.println(st1);
        st1= CharConversion.qj2bj(st1);
        System.out.println(st1);
        st1=st1.replaceAll("\\—","-");
        System.out.println(st1);

        String st2="安付宝-上海百联集团—有限—公司（BL）";*/


       /* Long a = 1L;
        String b =null;
        String c="1";


        if (a.toString().equals(c)) {
            System.out.println("不能通知存在");
        }else{
            System.out.println("能通知存在");
        }*/


        //StringUtils.isNotEmpty(c) && StringUtils.isEmpty(b) && a == null
        //StringUtils.isEmpty(c) && StringUtils.isNotEmpty(b) && a == null
        //StringUtils.isEmpty(c) && StringUtils.isEmpty(b) && a != null
        //(StringUtils.isEmpty(c) || StringUtils.isEmpty(b) || a == null)
    }

}
