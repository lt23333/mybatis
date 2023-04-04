package com.powernode.bank.dao.impl;

import com.powernode.bank.dao.AccountDao;
import com.powernode.bank.pojo.Account;
import com.powernode.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountDaoimpl implements AccountDao {
    @Override
    public Account seletByActno(String actno) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Account account = (Account) sqlSession.selectOne("account.selectByActno",actno);
        return  account;
    }

    @Override
    public int UpdateByActno(Account act) {
       SqlSession sqlSession = SqlSessionUtil.openSession();
       int count =sqlSession.update("account.updateByActno",act);
       return count;
    }
}
