import controller.CharConversion;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static util.RegularUtil.checkChnesEnglishReg;
import static util.RegularUtil.checkNumLetReg;

public class Demo {

    public static void main(String[] args) throws Exception {

        String str="安付宝—上海迪信电子通信技术有限公司(BL)";
        String str1=CharConversion.qj2bj(str);
        str1=str1.replaceAll("\\(","\\（").replaceAll("\\)","\\）");
        System.out.println(str1);
        String str2=str1.replaceAll("\\—","\\-");
        System.out.println(str2);

        String str3="安付宝－上海迪信电子通信技术有限公司(BL)";
        String str4=CharConversion.qj2bj(str3);
        str4=str4.replaceAll("\\(","\\（").replaceAll("\\)","\\）");
        System.out.println(str4);

    }

    public static String encodeData(String str) throws Exception {
        if (StringUtils.isEmpty(str.trim())) {
            return "";
        }

        String bTemp = "";
        try {
            bTemp = new String(Base64.encodeBase64(str.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bTemp;
    }
}
