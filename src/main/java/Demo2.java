import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Description TODO
 * @Author g.jl
 * @Date 2021/7/28 9:30
 * @Version 1.0
 **/
public class Demo2 {

    public static void main(String[] args) {
        List list=null;
        if(list.size()>0){
            System.out.println("输出1");
        }else{
            System.out.println("输出0");
        }

        Integer i =1;
        if(i == null){
            System.out.println("输出2");
        }else{
            System.out.println("输出3");
        }
    }

}
