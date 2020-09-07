package org.neptune.BookManager.service;

import org.neptune.BookManager.model.Book;
import org.neptune.BookManager.model.enums.BookStatusEnum;
import org.neptune.BookManager.dao.BookDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    public List<Book> getAllBooks() {
        return bookDAO.selectAll();
    }

    public Book getBook(String name) {
        return bookDAO.selectBookByName(name);
    }

    public int addBook(Book book) {
        return bookDAO.addBook(book);
    }

    public void deleteBook(int id) {
        bookDAO.updateBookStatus(id, BookStatusEnum.DELETED.getValue());
    }

    public void recoverBook(int id) {
        bookDAO.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
    }

    public void recommendBook(int id) {
        bookDAO.updateBookStatus(id, BookStatusEnum.RECOMMENDED.getValue());
    }
}
