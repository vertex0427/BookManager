package org.neptune.BookManager.biz;

import org.neptune.BookManager.model.User;
import org.neptune.BookManager.model.Ticket;
import org.neptune.BookManager.model.exceptions.LoginRegisterException;
import org.neptune.BookManager.service.UserService;
import org.neptune.BookManager.service.TicketService;
import org.neptune.BookManager.utils.ConcurrentUtils;
import org.neptune.BookManager.utils.MD5;
import org.neptune.BookManager.utils.TicketUtils;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginBiz {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    //登录逻辑。通过Email和密码来登录，成功后返回TicketInfo
    public String login(String email, String password) throws Exception {
        //首先检查输入的Email
        User user = userService.getUser(email);
        //空email判定
        if(user == null)
            throw new LoginRegisterException("邮箱不存在");
        //Email对应的密码是否正确。先将输入的password用MD5方法加密，再和数据库中的正确密码比较
        if(!StringUtils.equals(MD5.next(password), user.getPassword()))
            throw new LoginRegisterException("密码不正确");

        //检查Ticket
        Ticket ticket = ticketService.getTicket(user.getId());
        //如果没有就生成一个
        if(ticket == null) {
            ticket = TicketUtils.next(user.getId());
            ticketService.addTicket(ticket);
            return ticket.getTicketInfo();
        }
        //检查Ticket是否过期
        if(ticket.getExpiredTime().before(new Date())) {
            //过期了就删除
            ticketService.deleteTicket(ticket.getId());

            ticket = TicketUtils.next(user.getId());
            ticketService.addTicket(ticket);
        }

        //将当前用户设为Host
        ConcurrentUtils.setHost(user);
        return ticket.getTicketInfo();
    }

    //登出逻辑。直接删除用户的Ticket
    public void logout(String ticketInfo) {
        ticketService.deleteTicket(ticketInfo);
    }

    //注册逻辑。成功后返回TicketInfo
    public String register(User user) throws Exception {

        //检查注册信息
        if(userService.getUser(user.getEmail()) != null) {
            throw new LoginRegisterException("该邮箱已注册");
        }

        //密码加密
        String plain = user.getPassword();
        String md5 = MD5.next(plain);
        user.setPassword(md5);

        //数据库添加用户
        userService.addUser(user);

        //生成用户Ticket，并添加入数据库
        Ticket ticket = TicketUtils.next(user.getId());
        ticketService.addTicket(ticket);

        //将注册成功的用户直接设为Host
        ConcurrentUtils.setHost(user);
        return ticket.getTicketInfo();
    }
}
