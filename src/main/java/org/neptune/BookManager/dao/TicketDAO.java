package org.neptune.BookManager.dao;

import org.apache.ibatis.annotations.*;
import org.neptune.BookManager.model.Ticket;

@Mapper
public interface TicketDAO {

    String tableName = " ticket ";
    String insertField = " user_id, ticket_info, expired_time ";
    String selectField = " id, " + insertField;

    @Insert({"insert into", tableName, "(", insertField,
        ") values (#{userId}, #{ticketInfo}, #{expiredTime})"})
    int addTicket(Ticket ticket);

    @Select({"select", selectField, "from", tableName, "where user_id=#{uid}"})
    Ticket selectByUserId(int uid);

    @Select({"select", selectField, "from", tableName, "where ticket_info=#{ticketInfo}"})
    Ticket selectByTicket(String ticketInfo);

    @Delete({"delete from", tableName, "where id=#{tid}"})
    void deleteTicketById(int tid);

    @Delete({"delete from", tableName, "where ticket_info=#{ticketInfo}"})
    void deleteTicket(String ticketInfo);
}
