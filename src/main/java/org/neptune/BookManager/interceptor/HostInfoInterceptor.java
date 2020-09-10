package org.neptune.BookManager.interceptor;

import org.neptune.BookManager.model.Ticket;
import org.neptune.BookManager.model.User;
import org.neptune.BookManager.service.TicketService;
import org.neptune.BookManager.service.UserService;
import org.neptune.BookManager.utils.ConcurrentUtils;
import org.neptune.BookManager.utils.CookieUtils;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


//拦截器之一，使用AOP。检查用户身份，然后放行，即总是返回True
@Component
public class HostInfoInterceptor implements HandlerInterceptor{

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        //从Cookie中获得TicketInfo
        String ticketInfo = CookieUtils.getCookie("ticketInfo", request);

        if(!StringUtils.isEmpty(ticketInfo)) {

            //将此TicketInfo在Ticket数据库中寻找对应
            Ticket ticket = ticketService.getTicket(ticketInfo);

            //成功的话就将用户设为Host
            if(ticket != null && ticket.getExpiredTime().after(new Date())) {
                User host = userService.getUser(ticket.getUserId());
                ConcurrentUtils.setHost(host);
            }
        }

        return true;
    }
}
