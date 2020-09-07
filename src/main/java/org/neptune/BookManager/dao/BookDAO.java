package org.neptune.BookManager.dao;

import org.neptune.BookManager.model.Book;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookDAO {

    String tableName = " book ";
    String insertField = " name, author, price ";
    String selectField = " id, status, " + insertField;

    @Select({"select", selectField, "from", tableName})
    List<Book> selectAll();

    @Select({"select", selectField, "from", tableName, "where name=#{name}"})
    Book selectBookByName(String name);

    @Insert({"insert into", tableName, "(", insertField,
            ") values (#{name},#{author},#{price})"})
    int addBook(Book book);

    @Update({"update ", tableName, " set status=#{status} where id=#{id}"})
    void updateBookStatus(@Param("id") int id, @Param("status") int status);

}
