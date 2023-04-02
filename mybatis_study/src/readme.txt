开发我的第一个mybatis程序：

*关于流:指向内存的流可以不用关闭,指向网络/硬盘等外部资源的流一定要关闭
1.resource目录：
  放在这个目录当中的，一般都是资源文件，配置文件
  直接放到resource目录下的文件，等同于放到类的
  根路径下，Maven有特殊配置

2.开发步骤

*第一步
  打包方式 jar包
*第二步
  mybatis依赖
  mysql驱动依赖
*第三步
  从XML中创建sqlSessonFactory
  通过官方的这句话，你能想到什么呢？
    第一：在Mybatis中一定有一个很重要的对象，这个对象事SqlSessionFactory对象
    第二：SqlSessionFactory对象的创建需要XML


  XML是什么？
   他一定是一个配置文件
  *编写mybatis核心配置文件：mybatis-config.xml
    注意：
      第一：这个文件名不是必须叫做mybatis-config.xml可以用其他的名字，只是大多都采用这个名字
      第二：这个文件存放的位置也不是固定，可以随意，但一般情况下，会放到类的根路径下。

  mybatis-config.xml文件中的配置信息不理解没关系，先把连接数据库的信息修改即可
*第四步
 编写XxxxxMapper.xml文件
 在这个配置文件当中编写SQL语句：这个文件名也不是固定的，放的位置也不是固定的(把它暂时放到类的根路径下）

*第5步
  在mybatis-config.xml文件中指定XxxMapper.xml文件的路径
  <mapper resource="CarMapper.xml"/>
  注意:resouce属性会自动从类的根路径下开始查找信息.

*第六步
  编写mybatis程序
  (使用mabtis的类库,编写mybatis程序,连接数据库,做增删改查)
  在mybatis当中,负责执行SQL语句的那个对象叫做什么?
    SqlSession
  SqlSession是专门用来执行SQL语句的,是一个java程序和数据库之间的一次会话.
  要想获取SqlSession对象,需要先获取SqlSessionFactory对象,用过SqlSessionFactory工厂来生产SqlSession对象
  怎么获取SqlSessionFactory对象?
    需要首先获取SqlSessionFactoryBuilder对象
    通过SqlSessionFactoryBuilder对向build方法,来获取SqlSessionFactory对象

  mybaits的核心对象:
  SqlSessionFactoryBuilder
  SqlSeesionFactory
  SqlSession

SqlSessionFactoryBuilder-->SqlSeesionFactory-->SqlSession

3.mybatis中有两个主要的配置文件
   其中一个是mybatis-config.xml 这是核心配置文件，主要配置连接数据库的信息登。
   另一个事：XxxxxMapper.xml 这个文件是专门用来编写SQL语句的配置文件。（一个表一个）
   t_user表 一般会对应一个UserMapper.xml
   t_student表 一般会对应一个StudentMapper.xml （可以一个XxxxxMpper.xml文件对应多个文件，但是不建议使用）

4.关于第一个程序的小细节
  *mybatis中sql语句结尾的“;”可以省略
  *Resource。getResourceAsStream
    小技巧：以后凡是遇到resource这个单词，大部分情况下，这种加载资源的方式都是从类的根路径下开始加载（开始查找）
    ClassLoader.getSystemClassLoader() 获取系统的类加载器
    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
    系统类加载器有一个方法叫做getResourceAsStream，它就是从类路径当中加载资源的

5.关于mybatis的事务管理机制
  *在mybatis-config.xml文件中，可以通过以下的配置进行mybatis的事务管理
    <transactionManager type="JDBC"/>
  *type属性的值包括两个
     JDBC（jdbc）
     MANAGED（managed）
     type后面的值，只有以上两个值可选，不区分大小写
  *在mybatis中提供了两种事务管理机制
     JDBC事务管理器
     MANAGED事务管理器
  *JDBC事务管理器
    mybatis框架自己管理事务，自己采用原生的JDBC代码去管理事务
    conn.setAutoCommit(false)
            .....事务处理.....
    conn.commit();手动提交事务
    使用JDBC事务管理器的话，底层传教的事务管理器对象：JdbcTransaction对象
    如果编写下面的代码：
    SqlSession sqlSession = sqlSessionFactory.openSession();
    表示没有开启事务，因为这种方式压根不会执行 conn.setAutoCommit(false);
    在JDBC事务中，没有执行conn.setAutoCommit(false);那么autoCommit就是true
    如果autoCommit是true，就表示没有开启事务，只要执行任意一条DML语句就提交一次

  *MANAGED事务管理器
    mybatis不再负责事务的管理，事务管理交给其他容器来负责，例如：spring


  *重点
    autoCommit是true，就表示没有开启事务
    automCommit是false，就表示开启了事务

6.关于mybtis集成日志组件,让我们调试起来更加方便
   <setting name="logImpl" value="STDOUT_LOGGING"/>  mybatis内置 其他日志组件需要添加依赖
   <setting name="logImpl" value="SLF4J"/>  集成logback日志框架
   指定 MyBatis 所用日志的具体实现，未指定时将自动查找。:使用第三方日志框架,不需要配置,配置也没问题.

   *集成logback日志框架
    第一步:引入logback依赖
    第二步:引入logback所必须的xml配置文件
     这个配置文件的名字必须叫做:logback.xml或者logback-test.xml 不能是其他名字
     这个配置文件必须放到类的根路径下,不能是其他位置

7.程序的问题？
  值是写死到配置文件中的，这个在实际开发中是不存在的，一定是前端的from表单提交过来数据，然后将值传给sql语句
  *在JDBC当中占位符是？，在mybatis当中是什么呢？
  在mybatis当中不能使用？占位符，必须使用#{}来替代JDBC当中的？
  #{}和JDBC当中的？是等值的