package com.powernod.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

//工具类
public class SqlSessionUtil {

    private static SqlSessionFactory sqlSessionFactory =null;
    private SqlSessionUtil(){

    }
    static {

        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public  static  SqlSession openSession(){
//        try {
//            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
//            SqlSession sqlSession = sqlSessionFactory.openSession();
//            return sqlSession;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//    获取会话对象,返回会话对象
    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }
}
