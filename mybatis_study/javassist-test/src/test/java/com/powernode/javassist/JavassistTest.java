package com.powernode.javassist;

import com.powernode.javassist.bank.dao.AccountDao;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.lang.reflect.Method;

public class JavassistTest {

    @Test
    public void testjavassist() throws Exception{
     //获取类池，这个类池就是用来给我生成class的
        ClassPool pool = ClassPool.getDefault();
        //制造类(需要告诉javassist,类名是啥
        CtClass ctClass = pool.makeClass("com.powernode.bank.dao.impl.AccountDaoimpl");
        //制造方法
//        CtMethod.make("方法的代码","放在那个类里面");
        String methodcode="public void insert(){System.out.println(12345);}";
        CtMethod method = CtMethod.make(methodcode, ctClass);
        //将方法添加到类中
        ctClass.addMethod(method);
        //在内存中生成class
        ctClass.toClass();

//          上面是javassist的代码，下面是jdk代码，通过字节码文件创建类，获取类的函数
        //类加载JVM，返回AccountDaoimpl类的字节码
        Class<?> aClass = Class.forName("com.powernode.bank.dao.impl.AccountDaoimpl");
        //创建对象
        Object obj = aClass.newInstance();
       //获取AccountDaoimpl中的insert方法
        Method insert = aClass.getDeclaredMethod("insert");
        //调用方法insert
        insert.invoke(obj);
    }

    @Test
    public void testAccountDao() throws  Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.powernode.bank.dao.impl.AccountDaoimpl");
        CtClass ctInterface = pool.makeInterface("com.powernode.javassist.bank.dao.AccountDao", ctClass);
        ctClass.addInterface(ctInterface);//让这个类实现接口
        CtMethod method = CtMethod.make("public void delete(){System.out.println(123);}", ctClass);
        ctClass.addMethod(method);
        //在内存中生成类，同时将生成的类加载到JVM当中
        Class<?> aClass = ctClass.toClass();
        AccountDao accountDao = (AccountDao)aClass.newInstance();
        accountDao.delete();
    }

}
