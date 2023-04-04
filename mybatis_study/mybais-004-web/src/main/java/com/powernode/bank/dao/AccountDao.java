package com.powernode.bank.dao;

import com.powernode.bank.pojo.Account;

//DAO对象中的任务一个方法都和业务不挂钩，没有任何业务逻辑在里面
//负责t_act表中数据的CRUD
public interface AccountDao {
  //查询账户信息
   Account seletByActno(String actno);
   //更新账户信息
    int UpdateByActno(Account act);

}
