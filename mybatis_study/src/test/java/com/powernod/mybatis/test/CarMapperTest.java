package com.powernod.mybatis.test;

import com.powernod.mybatis.utils.SqlSessionUtil;
import com.powernode.mybatis.POJO.Car;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
   @Test
   public void testInsertCar1(){
       Map<String,Object> map = new HashMap<>();
       map.put("k1","1111");
       map.put("k2","比亚迪汉");
       map.put("k3",10.0);
       map.put("k4","2020-11-11");
       map.put("k5","新能源");
       SqlSession sqlSession = SqlSessionUtil.openSession();
//       int cont = sqlSession.insert("insertCar1","封装数据的对象");
       int cont = sqlSession.insert("insertCar1",map);
       sqlSession.commit();
       sqlSession.close();
   }


   @Test
   public void testPOJO(){
       Car car = new Car(null, "3333", "比亚迪秦", 30.0, "2020-11-11", "新能源");
       SqlSession sqlSession = SqlSessionUtil.openSession();
       int cont = sqlSession.insert("insertCar2",car);
       sqlSession.commit();
       sqlSession.close();

   }
}

