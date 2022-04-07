package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description TODO
 * @Author gao_jl
 * @Date 2022/4/7 10:42
 * @Version 1.0
 **/
public class GetClass {

    /**
     * 在配置文件中获取对象-通过反射的方式获取
     *
     * @param name
     * @param properFile 对象参数文件
     * @return 返回获取的对象
     * @throws IOException
     */
    public Object getObject (String name,String properFile) throws IOException {
        InputStream io = ClassLoader.getSystemClassLoader().getResourceAsStream(properFile);
        Properties properties = new Properties();
        properties.load(io);
        Object o = properties.get(name);
        return o;
    }
}
