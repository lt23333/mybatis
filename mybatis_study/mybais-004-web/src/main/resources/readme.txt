metadata-complete="true">
true表示只支持配置文件，不支持注释
fales表示支持配置文件，也支持注释



TheadLoacl:
一个线程一个SqlSession，SqlSession实例不是线程安全的，不建议共享
在业务层开始的时候，开启业务，将SqlSession绑定到当前线程中，
---->工具类 private  static  ThreadLocal<SqlSession> local = new ThreadLocal<>();
            public static SqlSession openSession(){
                SqlSession sqlSession = local.get();
                if(sqlSession==null){
                    sqlSession =sqlSessionFactory.openSession();
                    //将sql对象绑定到当前线程
                    local.set(sqlSession);
                }
                return sqlSession;
            }
---->private AccountDao accountDao = new AccountDaoimpl();业务类中
关闭的时候也需要特殊处理（解绑）:
public static void close(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
            local.remove();  -->删除该对象(从当前线程移除该对象)
            因为tomcat服务器支持线程池：用过的线程对象t1，使用之后会返回到线程池，下次使用t1线程的时候，没有解绑的话，获取的是同一个连接对象而且被关闭（SqlSession.close()）
        }
    }

在mybatis当中，mybatis提供了相关的机制，也可以动态为我们生成dao接口的实现类（代理类：dao接口的代理）
mybatis当中实际上采用了代理模式，在内存中生成dao接口的代理类，然后创建代理类的实例
使用mybatis的这种代理机制的前提，SqlMapper.xml文件中的namespace必须是dao接口的全限定名称，id必须是dao接口中的方法名
    private AccountDao accountDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);
SqlSessionUtil。opernSession（）-->Sqlsession -->Sqlsession.getMapper 获取映射器

#{}：底层使用PreparedStatement.特点：先进行SQL语句的编译，然后给SQL语句的占位符？传值，可以避免SQL注入的风险（优先使用,避免SQL注入的风险）

${}:底层使用Statement。特点：先进行SQL语句的拼接，然后再对SQL语句进行编译，存在SQL注入的风险。
  *如果需要SQL语句的关键字放到SQL语句中，只能使用${}，因为#{}是以值的形式放到SQL语句当中的。
  *向SQL语句当中拼接表名，就需要使用${}
  *批量删除使用${}
  *模糊查询建议使用${}，使用#{}时，需要进行concat函数（专门进行字符串拼接）进行拼接-->concat('%',#{},'%')
    concat('%','${}','%')
    "%"#{}"%" 这种方式比较多



resultType别名机制，alias可以省略，省略的话别名就是类的简名
com.powernode.bank.pojo.Account--->Account
    <typeAliases>
<!--        type:指定给哪个类型起别名-->
<!--        alias:指定别名-->
        <typeAlias type="com.powernode.bank.pojo.Account"  alias="Account"></typeAlias>
    </typeAliases>
 namespacke没有别名机制

 <package name="com.powernode.bank.pojo"/>---->将包下所有的类起别名-->简名(不区分大小）


mapper标签的属性可以有3个：
  resource：这种方式是从类的根路径下开始查找资源，采用这种方式，配置文件需要放到类路径才行。
  url：绝对路径
  class：Mapper接口的全限定接口名，带有包名。--->需要在reousrce建目录 com/powernode/bank/dao  将Mapper.xml放进去即可
  或者<packge name=""com/powernode/bank/dao" 也需要在resource建目录--->同级目录 名字一致


获取生成的主键：
<insert id="insertUseGeneratedKeys" useGeneratedKeys="true" keyProperty="id">
  insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>
useGeneratedKeys="true"--》使用自动生成的主键值
keyProperty="id"---》指定主键值赋值给对象的哪个属性，这个就表示将主键值赋值给Car对象的id属性

<!--  专门定义一个结果映射，在这个结果映射当中指定数据库的字段名和JAVA类的属性名的对应关系-->
<!--  type属性用来指定POJO类的类名 id属性用来指定resultType的唯一标识。这个id将来要在select标签中使用-->
  <resultMap id="carTrsultMap" type="Account">
<!--  如果数据库表中有主键，建议这里配置id标签，注意：这不是必须的，配置的话可以提高效率-->
<!--    property后面填写POJO类的属性名-->
    <id property="id" column="id"></id>
<!--    column后面填写数据库表的字段名-->
    <result property="" column=""></result>
    <result property="" column=""></result>
    <result property="" column=""></result>
  </resultMap>
  如果成员变量和字段名一样 可以不配