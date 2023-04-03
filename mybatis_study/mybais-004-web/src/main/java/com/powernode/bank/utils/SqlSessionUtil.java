package com.powernode.bank.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


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

    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }
}
