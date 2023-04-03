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
  <insert id="insertCar1">
          insert  into t_car(id,car_num,brand,guide_price,produce_time,car_type)
          values (null,#{k1},#{k2},#{k3},#{k4},#{k5})
  </insert>
  k1,k2过于生草，见名知意，与数据表的字段名相同。
  *java程序中使用POJO
    <insert id="insertCar2">
           insert  into t_car(id,car_num,brand,guide_price,produce_time,car_type)
           values (null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{catType})
    </insert>
    底层去找Car类中getXXX方法
    #{carNum}-->#{xyz}
    public String getxyz(){
     return carNum;
    }
    也能运行通过.严格意义上来说：如果使用POJO对象传递值的话，#{}这个大括号到底写什么？
    写的是get方法的方法名去掉get，然后将剩下的单词首字母小写，然后放进去
    例如：getUsename（）--》usename

  *如果#{}只有一个 那么占位符可以随便写，但是最好见名直意
  *POJO封装类的成员变量与数据库字段名对不上的时候，可以使用起别名解决，将数据库字段名起别名与成员变量对应。

  *查所有

8.<mapper namespace="fasfasd">
   List<Car> selectALL = sqlSession.selectList("fasfasd.selectALL");
   在执行sql时候，namespace.id是完整正确的写法，fasfasd.selectALL
   namespace是为了防止id重复。
9.
  sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
//            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"),"环境的id");

10.数据源
   <dataSource type="POOLED"> ：dataSource配置
   1.dataSource被称为数据源
   2.dataSource作用是什么？为程序提供Connection对象（凡是给程序提供Connecton对象的，都叫做数据源）
   3.数据源实际上一套规范，JDK中有这套规范:javax.sql.DataSource
   4.只要实现JavaX.sql.DataSource接口，自己也可以编写自己的数据源
   5 type只有3个 必须是3选1.
      [UNPOOLED|POOLED|JNDI]
      UNPOOLED– 这个数据源的实现会每次请求时打开和关闭连接。虽然有点慢，但对那些数据库连接可用性要求不高的简单应用程序来说，是一个很好的选择。 性能表现则依赖于使用的数据库，对某些数据库来说，使用连接池并不重要，这个配置就很适合这种情形。UNPOOLED 类型的数据源仅仅需要配置以下 5 种属性：

      driver – 这是 JDBC 驱动的 Java 类全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
      url – 这是数据库的 JDBC URL 地址。
      username – 登录数据库的用户名。
      password – 登录数据库的密码。
      defaultTransactionIsolationLevel – 默认的连接事务隔离级别。
      defaultNetworkTimeout – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。查看 java.sql.Connection#setNetworkTimeout() 的 API 文档以获取更多信息。
      作为可选项，你也可以传递属性给数据库驱动。只需在属性名加上“driver.”前缀即可，例如：

      driver.encoding=UTF8
      这将通过 DriverManager.getConnection(url, driverProperties) 方法传递值为 UTF8 的 encoding 属性给数据库驱动。

      POOLED– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这种处理方式很流行，能使并发 Web 应用快速响应请求。

      除了上述提到 UNPOOLED 下的属性外，还有更多属性用来配置 POOLED 的数据源：

      poolMaximumActiveConnections – 在任意时间可存在的活动（正在使用）连接数量，默认值：10
      poolMaximumIdleConnections – 任意时间可能存在的空闲连接数。
      poolMaximumCheckoutTime – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
      poolTimeToWait – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。
      poolMaximumLocalBadConnectionTolerance – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 poolMaximumIdleConnections 与 poolMaximumLocalBadConnectionTolerance 之和。 默认值：3（新增于 3.4.5）
      poolPingQuery – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动出错时返回恰当的错误消息。
      poolPingEnabled – 是否启用侦测查询。若开启，需要设置 poolPingQuery 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
      poolPingConnectionsNotUsedFor – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。
      JNDI – 这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的数据源引用。这种数据源配置只需要两个属性：

      initial_context – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。
      data_source – 这是引用数据源实例位置的上下文路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。
      和其他数据源配置类似，可以通过添加前缀“env.”直接把属性传递给 InitialContext。比如：

      env.encoding=UTF8
      这就会在 InitialContext 实例化时往它的构造方法传递值为 UTF8 的 encoding 属性。

   *JNDI是一套规范，谁实现了这套规范？大部分的web容器都实现了JNDI规范
     JNDI是：java命名目录接口、tomcat服务器实现了这个规范