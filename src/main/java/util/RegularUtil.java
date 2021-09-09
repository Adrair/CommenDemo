package util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @Description 正则校验
 * @Author Gjl
 * @Date 2020/4/29 11:04
 * @Version 1.0
 **/
public class RegularUtil {


    /**
     * 只允许含数字
     *
     * @param regString
     * @return
     */
    public static boolean checkNumLetReg(String regString) {
        String regEx = "^[0-9]+$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }


    /**
     * 只允许含数字字母汉字
     *
     * @param regString
     * @return
     */
    public static boolean checkNumLetChraReg(String regString) {
        String regEx = "^[\u4E00-\u9FA5A-Za-z0-9]+$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 金额表达式
     *
     * @param regString
     * @return
     */
    public static boolean checkAmtReg(String regString) {
        String regEx = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 14到19位数字
     *
     * @param regString
     * @return
     */
    public static boolean checkNumDigReg(String regString) {
        String regEx = "^([1-9]{1})(\\d{14,18})$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 10到30位数字(对公帐户)
     *
     * @param regString
     * @return
     */
    public static boolean checkNumDig1Reg(String regString) {
        String regEx = "^([1-9]{1})(\\d{9,29})$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 汉字的正则
     *
     * @param regString
     * @return
     */
    public static boolean checkNameReg(String regString) {
        String regEx = "^[\u4e00-\u9fa5]+$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 手机号
     *
     * @param regString
     * @return
     */
    public static boolean checkMblReg(String regString) {
        String regEx = "^(1[3|4|5|6|7|8|9]\\d{9})$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 座机号正则
     */
    public static boolean checkTelReg(String regString) {
        String regEx = "^(0\\d{2,3}\\d{8})$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 邮编
     *
     * @param regString
     * @return
     */
    public static boolean checkPostReg(String regString) {
        String regEx = "^[1-9][0-9]{5}$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 身份证
     *
     * @param regString
     * @return
     */
    public static boolean checkIdCardReg(String regString) {
        String regEx = "(^[1-9]\\\\d{5}(18|19|20)\\\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\\\d{3}[0-9X]$)|\" +\n" +
                "                \"(^[1-9]\\\\d{5}\\\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\\\d{3}$)";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 邮箱
     *
     * @param regString
     * @return
     */
    public static boolean checkEmailReg(String regString) {
        String regEx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * ip
     *
     * @param regString
     * @return
     */
    public static boolean checkIpReg(String regString) {
        String regEx = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * Mac
     *
     * @param regString
     * @return
     */
    public static boolean checkMacReg(String regString) {
        String regEx = "^[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 大于0数字
     *
     * @param regString
     * @return
     */
    public static boolean checkNumReg(String regString) {
        String regEx = "^[0-9]\\d*$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 三位数字
     *
     * @param regString
     * @return
     */
    public static boolean checkNoReg(String regString) {
        String regEx = "^[0-9]{3}$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    /**
     * 支持中文，英文，数字，中英文的逗号，句号，感叹号（兼容win10输入法）
     *
     * @param regString
     * @return
     */
    public static boolean checkChnesEnglishReg(String regString) {
        String regEx = "^([\\u4e00-\\u9fa5\\u3002\\uff0ca-zA-Z0-9,。，！!\\.]$)|(^[\\u4e00-\\u9fa5\\u3002\\uff0ca-zA-Z0-9,。！!，\\.]$)";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;

    }

    private static boolean checkInteger(String regString){
        String regEx = "^[1-9]\\d*$";
        boolean result = Pattern.compile(regEx).matcher(regString).matches();
        return result;
    }

    public static void main(String[] args) {
        BigDecimal ratio = new BigDecimal(23413.213);

        if(checkInteger(ratio.toString())){
            System.out.println("是整数");
        }else{
            System.out.println("不是整数");
        }

    }

}
