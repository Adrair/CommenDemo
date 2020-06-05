package db.dao;

import bean.AccountPswBean;
import util.JdbcConnect;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 账户数据入表
 */
public class AcountPswDao {



    public static void main(String[] args) throws Exception {
        AccountPswBean accountPswBean = new AccountPswBean();
        accountPswBean.setAcount_inf("拉钩");
        accountPswBean.setAcount_inf_no(" ");
        accountPswBean.setAcount_nm("主QQ邮箱");
        accountPswBean.setAcount_psw("主QQ密码");

        try {
            insertAcountInfo(accountPswBean);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 数据插入t_account_info 账户表
     * @param userBean
     * @throws SQLException
     */
    public static void insertAcountInfo(AccountPswBean userBean)throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");

        String insertSql="insert into t_account_info(acount_inf,acount_inf_no,acount_nm,acount_psw,tr_dt,tr_tm,tm_smp)values(?,?,?,?,?,?,?)";
        //驱动连接
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        try{
            connection= JdbcConnect.getJdbcConnect();
            preparedStatement=connection.prepareStatement(insertSql);
            preparedStatement.setString(1,userBean.getAcount_inf());
            preparedStatement.setString(2,userBean.getAcount_inf_no());
            preparedStatement.setString(3,userBean.getAcount_nm());
            preparedStatement.setString(4,userBean.getAcount_psw());
            preparedStatement.setString(5,format1.format(new Date()));
            preparedStatement.setString(6,format2.format(new Date()));
            preparedStatement.setString(7,format.format(new Date()));
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("数据插入成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcConnect.close(rs,preparedStatement,connection);
            System.out.println("关闭成功");
        }

    }

    /**
     * 更新数据库表数据
     * @param accountBean
     * @throws Exception
     */

    public static void updateAcountInfo(AccountPswBean accountBean)throws Exception{



    }


}
