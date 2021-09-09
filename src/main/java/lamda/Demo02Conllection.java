package lamda;

import java.util.ArrayList;

/**
 * @Description TODO
 * @Author gaojl
 * @Date 2021/6/16 18:02
 * @Version 1.0
 **/
public class Demo02Conllection {

    /*public static void main(String[] args) {
        ArrayList<String > list = new ArrayList<>();
        list.add("赵丽颖");
        list.add("迪丽热巴");
        list.add("张三");
        list.add("张三丰");
        list.add("张无忌");
        list.add("赵敏");
        list.stream().filter(s -> s.length() != 3 ).forEach(System.out::println);
    }*/

    public static void main(String[] args) {
        ArrayList<String > list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        long count = list.stream().filter(s -> s.equals(7+"")).count();
        System.out.println(count);
    }


}
