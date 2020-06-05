import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTime {

    public static void main(String[] args) {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");

        /*Long currentTime=System.currentTimeMillis();

        System.out.println("当前时间="+currentTime);

        Long startTime=currentTime-(3 * 60 * 60 * 1000);

        System.out.println("三个小时前的时间："+startTime);

        System.out.println(new Date(currentTime));

        System.out.println(sdf.format(startTime));

        System.out.println("时间戳格式："+sdf1.format(startTime));*/

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        String push_dt=format3.format(calendar.getTime());

        System.out.println("日期是："+push_dt);


    }

}
