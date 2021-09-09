package lamda;

/**
 * @Description DOTO
 * @Author Sire
 * @Date 2021/6/10 18:18
 * @Version 1.0
 **/
public class Demo02Lamda {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("线程执行了！")).start();
    }
}
