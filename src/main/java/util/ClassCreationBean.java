package util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Description 根据类对象名称获取类对象（非框架方法）--根据bean的唯一标识获取对象
 * @Author Sire
 * @Date 2020/12/9 17:35
 * @Version 1.0
 **/
public class ClassCreationBean {
    //获取properties文件文件名：bean.properties
    private static ResourceBundle bundle = ResourceBundle.getBundle("bean");
    //定义一个容器存储要使用的对象
    private static Map<String,Object> beans = new HashMap<String,Object>();
    //使用静态代码块初始化map
    static{
        try { //1、读取配置文件中所有的key
            Enumeration<String> keys = bundle.getKeys();
            //2、遍历key
            while (keys.hasMoreElements()){
                //3获取一个key
                String key= keys.nextElement();
                //4根据key获取bean的路径
                String beanPath = bundle.getString(key);
                //5、根据beanPath反射创建类对象
                Object value = Class.forName(beanPath).newInstance();
                //6、把key和value存入map中
                beans.put(key,value);
            }
        } catch (Exception e) {
            throw  new ExceptionInInitializerError("创建容器失败，程序停止执行");
        }
    }
    /**
     * 根据bean对象的名称创建类对象
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return beans.get(beanName);
    }
}
