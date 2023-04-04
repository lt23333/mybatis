package com.powernode.bank.web;

import com.powernode.bank.exception.TransferException;
import com.powernode.bank.exception.moneyNotEnougException;
import com.powernode.bank.service.AccountService;
import com.powernode.bank.service.impl.AccountServiceimpl;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {
    AccountService accountService =new AccountServiceimpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String  fromActno =request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
//        servlet作为控制器，不处理业务，业务放到service处理
//        调用业务层的转账方式完成转账
        try {
            accountService.transfer(fromActno,toActno,money);
//        调用view视图层进行展示结果
            //成功
//            response.sendRedirect(request.getContextPath()+"/页面路径");
            response.sendRedirect(request.getContextPath()+"/success.html");
        }  catch (TransferException e) {
            //转账失败
            response.sendRedirect(request.getContextPath()+"/error1.html");
        } catch (moneyNotEnougException e) {
            //余额不足
            response.sendRedirect(request.getContextPath()+"/error2.html");
        }
    }
}
