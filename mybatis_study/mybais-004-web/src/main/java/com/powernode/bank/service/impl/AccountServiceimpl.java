package com.powernode.bank.service.impl;

import com.powernode.bank.dao.AccountDao;
import com.powernode.bank.dao.impl.AccountDaoimpl;
import com.powernode.bank.exception.TransferException;
import com.powernode.bank.exception.moneyNotEnougException;
import com.powernode.bank.pojo.Account;
import com.powernode.bank.service.AccountService;
import com.powernode.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceimpl implements AccountService {


    //自己封装的
//    private AccountDao accountDao = new AccountDaoimpl();

    private AccountDao accountDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);

    @Override
    public void transfer(String fromActno, String toActno, double money) throws moneyNotEnougException, TransferException {
//       1.判断转出账户余额是否充足。。。。。。
        SqlSession sqlSession = SqlSessionUtil.openSession();

        Account fromact = accountDao.seletByActno(fromActno);
        if(fromact.getBalance()<money){
           throw new moneyNotEnougException("对不起，余额不足!");
        }
//        余额充足
        Account toact = accountDao.seletByActno(toActno);
        fromact.setBalance(fromact.getBalance()-money);
        fromact.setBalance(toact.getBalance()+money);
//        以上代码只是更新内存
//        以下代码是更新数据库账户的信息
        int count=accountDao.UpdateByActno(fromact);
        count+=accountDao.UpdateByActno(toact);
        if(count!=2){
            throw new TransferException("转账失败");
        }
        sqlSession.commit();
        //提交事务，关闭事务
        SqlSessionUtil.close(sqlSession);
    }

}
