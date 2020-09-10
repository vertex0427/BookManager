package org.neptune.BookManager.interceptor;

import org.neptune.BookManager.model.Ticket;
import org.neptune.BookManager.service.TicketService;
import org.neptune.BookManager.utils.CookieUtils;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//拦截器之二，使用AOP。对权限认证失败的用户重定向至登录页面
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object Handler
    ) throws Exception {

        //从Cookie中获得TicketInfo
        String ticketInfo = CookieUtils.getCookie("ticketInfo", request);

        //没有TicketInfo
        if(StringUtils.isEmpty(ticketInfo)) {
            response.sendRedirect("/users/login");
            return false;
        }

        //尝试获得Ticket
        Ticket ticket = ticketService.getTicket(ticketInfo);

        //无效TicketInfo，即找不到对应的合法Ticket
        if(ticket == null) {
            response.sendRedirect("/users/login");
            return false;
        }

        //或是有效TicketInfo，但是找到的Ticket显示已过期
        if(ticket.getExpiredTime().before(new Date())) {
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }
}
