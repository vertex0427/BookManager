package org.neptune.BookManager.model;

import java.util.Date;

public class Ticket {

    private int id;
    private int userId;
    //ticketInfo为一串复杂字符串，交由UuidUtils生成
    private String ticketInfo;
    //expiredTime为过期时间
    private Date expiredTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(String ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
