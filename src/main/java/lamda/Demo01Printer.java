package lamda;

/**
 * @Description DOTO
 * @Author Sire
 * @Date 2021/6/10 17:37
 * @Version 1.0
 **/
public class Demo01Printer {

    public static void main(String[] args) {
        Runnable test = new Runnable() {
            @Override
            public void run() {
                System.out.println("多线程已经执行了");
            }
        };
        new Thread(test).start();


    }
}
