package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.InputStream;

public class MyBatisIntroductionTest  {
    public static void main(String[] args) throws  Exception {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//        InputStream is  = new FileInputStream("mybatis-config.xml文件的路径");
//        使用matbis的工具类
//        Resources.getResourceAsStream默认从类的根路径下查找资源
//        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();//底层 conn.setAutoCommit(false);开启事务
        int count = sqlSession.insert("insertCar");
        System.out.println("插入了几条记录："+count);
        is.close();
        sqlSession.commit();//底层conn.Commit();
    }
}
