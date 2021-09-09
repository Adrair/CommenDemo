package lamda;

import bean.LamdaVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description DOTO
 * @Author Sire
 * @Date 2021/6/9 14:22
 * @Version 1.0
 **/
public class LamdaTest {

    public static void main(String[] args) {
        List<LamdaVO> lamdaVOList = new ArrayList<>();
        LamdaVO user1 = new LamdaVO(1, "小明", "12324qq.com");
        LamdaVO user2 = new LamdaVO(2, "小芳", "12324qq.com");
        LamdaVO user3 = new LamdaVO(3, "小华", "12324qq.com1");
        LamdaVO user4 = new LamdaVO(4, "小华", "12324qq.com2");

        lamdaVOList.add(user1);
        lamdaVOList.add(user2);
        lamdaVOList.add(user3);
        lamdaVOList.add(user4);

        //lamda表达式 过滤加遍历
        System.out.println("lamda表达式 过滤");
        lamdaVOList.stream().
                forEach(u -> System.out.println(u.getId() + ":::" + u.getName()));
        System.out.println("lamda表达式 过滤加遍历");
        lamdaVOList.stream().filter(u -> u.getId() > 1).
                forEach(u -> System.out.println(u.getId() + ":::" + u.getName()));

        //lamda表达式对提取为map对象
        // (o, n) -> o表示的 如果出现重复key，则用 原来代替新的key,
        // 所以打印的小华的邮箱 为小华::12324qq.com1.
        // 同样可以使用  (o, n) -> n 这样当出现重复key时候，则会用新的key代替老的key了，
        // 此时打印的小华的邮箱 为小华::12324qq.com2
        System.out.println("lamda表达式对提取为map对象");
        Map<String, String> map = lamdaVOList.stream().
                collect(Collectors.toMap(LamdaVO::getName, LamdaVO::getEamil, (o, n) -> o));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"---"+entry.getValue());
        }

        //lamda 表达式对对象里面某个属性提取List对象
        System.out.println("lamda 表达式对对象里面某个属性提取List对象");
        List<String> list = lamdaVOList.stream().map(LamdaVO::getName).collect(Collectors.toList());
        list.stream().forEach(s-> System.out.println(s));

        List<String> list2 = lamdaVOList.stream().map(LamdaVO::getEamil).collect(Collectors.toList());
        list2.stream().forEach(s-> System.out.println(s));

        //lamda表达式变为map<String,List<String>>
        System.out.println("lamda表达式变为map<String,List<String>>");
        Map<String, List<LamdaVO>> map2 = lamdaVOList.stream().collect(Collectors.groupingBy(LamdaVO::getName));
        for (Map.Entry<String, List<LamdaVO>> entry : map2.entrySet()) {
            System.out.println(entry.getKey()+"::"+entry.getValue());
        }

    }
}
