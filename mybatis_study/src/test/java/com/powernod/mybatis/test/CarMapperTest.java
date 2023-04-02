package com.powernod.mybatis.test;

import com.powernod.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class CarMapperTest {

    @Test
    public void testInsertCar(){
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
    @Test
    public void testSqlSessionUtil(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int cont = sqlSession.insert("insertCar");
        sqlSession.commit();
        sqlSession.close();
    }


}

