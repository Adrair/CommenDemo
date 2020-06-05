package util;

import java.sql.*;
import java.util.Properties;

/**
 * 获取数据库连接的JDBC
 */
public class JdbcConnect {

    public static void main(String[] args) {
        Connection con = getJdbcConnect();

        System.out.println(con);
    }


    public static Connection getJdbcConnect() {

        Connection connection = null;

        try {
            //1、创建Driver实现的对象
            Driver driver = new com.mysql.cj.jdbc.Driver();
            String url = "jdbc:mysql://localhost:3306/gjlsql?&useSSL=false&serverTimezone=UTC";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "root");

            connection = driver.connect(url, info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭数据库的连接
     *
     * @param rs
     * @param preparedStatement
     * @param connection
     * @throws SQLException
     */
    public static void close(ResultSet rs, Statement preparedStatement, Connection connection) throws SQLException {
        //7.关闭
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
