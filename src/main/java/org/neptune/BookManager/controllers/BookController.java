package org.neptune.BookManager.controllers;

import org.neptune.BookManager.model.Book;
import org.neptune.BookManager.model.User;
import org.neptune.BookManager.service.BookService;
import org.neptune.BookManager.service.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private HostHolder hostHolder;

    //主页，显示所有书本
    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET})
    public String bookList(Model model) {
        //检查是否登录
        User host = hostHolder.getUser();
        if(host != null) {
            model.addAttribute("host", host);
        }
        loadAllBooksView(model);
        return "book/books";
    }

    //书本添加页面
    @RequestMapping(path = {"/books/add"}, method = {RequestMethod.GET})
    public String addBook() {
        return "book/addbook";
    }

    //新书本输入页面
    @RequestMapping(path = {"/books/add/do"}, method = {RequestMethod.POST})
    public String doAddBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") String price
    ) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        bookService.addBook(book);

        return "redirect:/index";
    }

    //书本删除页面
    @RequestMapping(path = {"/books/{bookId:[0-9]+}/delete"}, method = {RequestMethod.GET})
    public String deleteBook(@PathVariable("bookId") int bookId) {

        bookService.deleteBook(bookId);
        return "redirect:/index";
    }

    //书本恢复正常状态页面
    @RequestMapping(path = {"/books/{bookId:[0-9]+}/recover"}, method = {RequestMethod.GET})
    public String recoverBook(@PathVariable("bookId") int bookId) {

        bookService.recoverBook(bookId);
        return "redirect:/index";
    }

    //为model加载所有的book
    private void loadAllBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
    }
}
