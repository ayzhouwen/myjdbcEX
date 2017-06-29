package pool.spring;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/6/28.
 */
//在spring框架下使用druid
public class PoolDB {
    static {
        //如果在web中使用,不用执行此代码,
        try {
            ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

            ds=(DruidDataSource)ac.getBean("dataSource_base");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private    static DruidDataSource ds;


    Statement stmt=null;
    ResultSet rs=null;
    Connection conn=null;
    private PoolDB getConByPool(){
        Connection conn=null;
        try {
            conn= ds.getConnection();
            stmt=conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  this;
    }



    //下面的方法跟DBBean操作就一样了

    //关闭链接
    public void closeObject()
    {
        try {
            if (rs!=null){
                rs.close();
            }
            if (stmt!=null){
                stmt.close();
            }
            if (conn !=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getUpdate(String sql)
    {

        int i=0;
        try {
            if (conn ==null){
                getConByPool();
            }

            i=stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("更改错误");
            //	System.out.println(sql);
        }
        return i;
    }



    public ResultSet getQuery(String sql)
    {
        try {
            if (conn ==null){
                getConByPool();
            }
            rs=stmt.executeQuery(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("查询错误");
            //	System.out.println(sql);

        }
        return rs;
    }
}
