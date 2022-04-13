package util;

import java.sql.*;

/**
 * @Description JDBC工具类
 * @Author Sire
 * @Date 2020/11/14 15:02
 * @Version 1.0
 **/
public class JDBCUtils {
    private static final String driverClassName;
    private static final String url;
    private static final String userName;
    private static final String password;

    static {
        driverClassName="com.mysql.cj.jdbc.Driver";
        url="jdbc:mysql://localhost:3306/gao_import?serverTimezone=GMT%2B8";
        userName="root";
        password="root";
    }


    /**
     * 注册驱动
     */
    public static void loadDrive(){
        try{
            Class.forName(driverClassName);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    /**
     *获得连接
     */
    public static Connection getConnection(){
        Connection connection= null;

        try{
            loadDrive();
            connection=DriverManager.getConnection(url,userName,password);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * 释放资源
     */
    public static void release (Statement statement,Connection connection){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }
    public static void release (ResultSet resultSet,Statement statement, Connection connection){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSet = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

}
