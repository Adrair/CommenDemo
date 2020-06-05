package util;

import java.math.BigDecimal;

/**
 * @Description 金额小写转大写
 * @Author Sire
 * @Date 2020/6/5 14:59
 * @Version 1.0
 **/
public class AmtToUppder {

    /*
     *汉字中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    /*
     *汉语中货币单位大写
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = {"分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "佰", "仟"};

    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;


    public static void main(String[] args) {

        double amtStr = 4455336644.235;
        BigDecimal bigDecimal = new BigDecimal(amtStr);
        String amtUpStr = amtToUp(bigDecimal);
        System.out.println("输入的金额=" + amtStr + "      转换后的大写金额=" + amtUpStr);
    }


    /**
     * 输入的金额转换成大写金额
     *
     * @param numberAmt
     * @return
     */
    public static String amtToUp(BigDecimal numberAmt) {

        StringBuffer stringBuffer = new StringBuffer();
        int sigNum = numberAmt.signum();
        //零元整的情况
        if (sigNum == 0) {
            return CN_ZEOR_FULL;
        }
        //将金额进行四舍五入
        long number = numberAmt.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
        //得到小数点后两位数据
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        //判断最后两位数，一共有四种情形：00 = 0，01=1，10,11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            //每次获取到最后一位数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                stringBuffer.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    stringBuffer.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            //将number的最后一位数去掉
            number = number / 10;
            ++numIndex;
        }
        //如果signum==-1则说明输入的数字是负数，就在前面追加特殊字符：负
        if (sigNum == -1) {
            stringBuffer.insert(0, CN_NEGATIVE);
        }
        //输入的数字小数点最后的两位为“00”的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            stringBuffer.insert(0, CN_FULL);
        }
        return stringBuffer.toString();
    }


}
