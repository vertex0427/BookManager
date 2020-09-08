package org.neptune.BookManager.utils;

import org.neptune.BookManager.model.Ticket;
import org.joda.time.DateTime;

//生产Ticket
public class TicketUtils {

    public static Ticket next(int uid){

        Ticket ticket = new Ticket();
        ticket.setTicket(UuidUtils.next());
        ticket.setUserId(uid);
        //设置t票过期时间
        DateTime expiredTime = new DateTime();
        expiredTime = expiredTime.plusMonths(3);
        ticket.setExpiredTime(expiredTime.toDate());

        return ticket;
    }

}
