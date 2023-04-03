package com.powernode.bank.service;

public interface AccountService {

//    业务层的业务接口
//    fromActno转出账号 toActno转入账号 money金额
    public void transfer(String fromActno,String toActno,double money);
}
