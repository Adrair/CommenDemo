import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static util.RegularUtil.checkChnesEnglishReg;

public class Demo {

    public static void main(String[] args) {
        /*String red_crd_list = "";
        if(red_crd_list.length()!=0){
            red_crd_list = red_crd_list.substring(0, red_crd_list.length() - 1);
        }

        System.out.println("red_crd_list="+red_crd_list);
        System.out.println("red_crd_list.length()="+red_crd_list.length());*/

        String red_crd_list = "";
        red_crd_list = red_crd_list.replace("\r\n", ";"); //支持回车分隔
        String[] batch_cloning_param = red_crd_list.split(";");
        System.out.println("长度=" + batch_cloning_param.length);


        double a = 23.174;
        double b = Math.ceil(a * 100) / 100;
        double c = Math.floor(a * 100) / 100;
        double d = Math.round(a * 100) / 100;
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);





        /*if(red_crd_list.length()!=0){
            red_crd_list = red_crd_list.substring(0, red_crd_list.length() - 1);
        }

        System.out.println("red_crd_list="+red_crd_list);
        System.out.println("red_crd_list.length()="+red_crd_list.length());


        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String agr_eff_dt="20160801";
        try {

            agr_eff_dt = format1.format(format1.parse(agr_eff_dt));
        } catch (Exception e1) {
            e1.printStackTrace();
        }//日期格式转换
        String agr_exp_dt="20200801";
        try {
            agr_exp_dt = format1.format(format1.parse(agr_exp_dt));
        } catch (Exception e1) {
            e1.printStackTrace();
        }//日期格式转换

        System.out.println("agr_eff_dt="+agr_eff_dt);
        System.out.println("agr_exp_dt="+agr_exp_dt);

        *//*
          判断字符串的根据某个字符分割成数据
         *//*

        String str1 ="尊敬的客户：您的手机号码：18236573929，已为您重置登陆密码，新的密码为73713287，请妥善保管！";
        String[] s=str1.split("，");
        String[] s2={};
        String[] StrArr=s[0].split(",");
        System.out.println("长度s2="+s2.length);
        System.out.println("长度="+s[2]);
        System.out.println("长度="+s[2].substring(5));*/
        /*for (int i=0;i<StrArr.length;i++){
            StrArr[i]=String.valueOf(Integer.valueOf(StrArr[i]));

            System.out.println("第"+i+"条数据="+StrArr[i]);
        }

        System.out.println("Hello World!");

        /*
            字符串切割字符，取某段字符串
         */
        /*String str2="20180601145755";
        String opr_mbl=str2.substring(0,3);
        String usr_opr_mbl="210"+opr_mbl;
        System.out.println(opr_mbl);
        System.out.println(usr_opr_mbl.length());*/

        /*String str3=" ";
        if(StringUtils.isEmpty(str3)){
            System.out.println("字符串为空");
        }else if(StringUtils.isBlank(str3)){
            System.out.println("字符串为空格");
        }else{
            System.out.println("str3="+str3);
        }*/

        /*
            字符串切割字符，通过lastIndexOf 获取第几个数
         */

       /* String str3="乐高上海陆家嘴店（关）";
        String lastStr=str3.substring(str3.lastIndexOf("（"));
        String lastStrlength=str3.substring(0,str3.lastIndexOf("（"));
        System.out.println(lastStr);
        System.out.println(lastStrlength);*/


        /**
         * 判断是否包含某一字符串，如果包含则将该字符串去除
         */
        //String str4="1,2,15,25,S";
        //String str4="25,S";
        /*String str4="1,2,15,25,26,27,28,29,S";
        if(str4.contains("25,")){
            str4=str4.replace("25,","");
            System.out.println("str4="+str4);
        }else{
            System.out.println("判断错误！");
        }


        if(!str4.contains("20,")){
            str4=str4.replace("S","20,S");
            System.out.println("str4="+str4);
        }*/
        //String str4="1,2,15,25,S";
        //String str4="25,S";
        /*String str5="1113110001";
        String[] strArr5  = str5.split(",");
        System.out.println("长度2="+strArr5.length);

        String regString="1111111111111111111";
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9X]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

        boolean flg1=Pattern.compile(regularExpression).matcher(regString).matches();
        System.out.println("flg1="+flg1);

        if(!Pattern.compile(regularExpression).matcher(regString).matches()){
            System.out.println("证件类型不正确");
        }
        boolean flg2=checkIdCardReg(regString);
        System.out.println("flg2="+flg2);
        if(!checkIdCardReg(regString)){
            System.out.println("证件类型不正确2");
        }*/

        /*String regString1 = "中ed,z.中，那几幅啊。哈";
        boolean flg3 = checkChnesEnglishReg(regString1);
        System.out.println("flg3=" + flg3);
        if (!checkChnesEnglishReg(regString1)) {
            System.out.println("正则表达式不正确");
        }*/


    }


}
