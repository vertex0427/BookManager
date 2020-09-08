package org.neptune.BookManager.dao;

import org.apache.ibatis.annotations.*;
import org.neptune.BookManager.model.Ticket;

@Mapper
public interface TicketDAO {

    String tableName = " ticket ";
    String insertField = " user_id, ticket, expired_time ";
    String selectField = " id, " + insertField;

    @Insert({"insert into", tableName, "(", insertField,
        ") values (#{userId}, #{ticket}, #{expired_time})"})
    int addTicket(Ticket ticket);

    @Select({"select", selectField, "from", tableName, "where user_id=#{uid}"})
    Ticket selectByUserId(int uid);

    @Select({"select", selectField, "from", tableName, "where ticket=#{t}"})
    Ticket selectByTicket(String t);

    @Delete({"delete from", tableName, "where id=#{tid}"})
    void deleteTicketById(int tid);

    @Delete({"delete from", tableName, "where ticket=#{t}"})
    void deleteTicket(String t);
}
