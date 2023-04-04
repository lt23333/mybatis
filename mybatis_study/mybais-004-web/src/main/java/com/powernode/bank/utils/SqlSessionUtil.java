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
    private  static  ThreadLocal<SqlSession> local = new ThreadLocal<>();
    public static SqlSession openSession(){
        SqlSession sqlSession = local.get();
        if(sqlSession==null){
            sqlSession =sqlSessionFactory.openSession();
            //将sql对象绑定到当前线程
            local.set(sqlSession);
        }
        return sqlSession;
    }
    public static void close(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
            local.remove();
        }
    }
}
