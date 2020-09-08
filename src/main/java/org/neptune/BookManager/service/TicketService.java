package org.neptune.BookManager.service;

import org.neptune.BookManager.model.Ticket;
import org.neptune.BookManager.dao.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketDAO ticketDAO;

    public void addTicket(Ticket ticket) {
        ticketDAO.addTicket(ticket);
    }

    public Ticket getTicket(int uid) {
        return ticketDAO.selectByUserId(uid);
    }

    public Ticket getTicket(String ticketInfo) {
        return ticketDAO.selectByTicket(ticketInfo);
    }

    public void deleteTicket(int tid) {
        ticketDAO.deleteTicketById(tid);
    }

    public void deleteTicket(String ticketInfo) {
        ticketDAO.deleteTicket(ticketInfo);
    }
}
