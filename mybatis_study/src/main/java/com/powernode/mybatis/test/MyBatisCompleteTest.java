package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

//采用正规的方式，写一个完整版的Mybatis程序
public class MyBatisCompleteTest {

    public static void main(String[] args) {
        SqlSession sqlSession =null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            //执行sql语句 处理相关业务
            int count = sqlSession.insert("insertCar");
            sqlSession.commit();
            System.out.println("插入条数:"+count);
        } catch (Exception e) {
            if(sqlSession!=null){
                sqlSession.rollback();
            }
            e.printStackTrace();
        }finally{
          if(sqlSession!=null){
              sqlSession.close();
          }

        }

    }
}
