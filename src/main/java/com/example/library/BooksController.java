package com.example.library;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
import com.example.library.entities.Publisher;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BooksController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/books/add")
    public String addBook(@RequestParam String title,
                          @RequestParam int pages,
                          @RequestParam int year,
                          @RequestParam String description,
                          @RequestParam String authorName,
                          @RequestParam String genreName,
                          @RequestParam String publisherName,
                          @RequestParam int copiesSold) {

        Book book = new Book();
        book.setTitle(title);
        book.setPageAmount(pages);
        book.setYear(year);
        book.setDescription(description);
        book.setCopiesSold(copiesSold);

        Author author = new Author();
        author.setName(authorName);
        book.setAuthor(author);

        Genre genre = new Genre();
        genre.setName(genreName);
        book.setGenre(genre);

        Publisher publisher = new Publisher();
        publisher.setName(publisherName);
        book.setPublisher(publisher);

        bookService.saveBook(book);

        return "redirect:/";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);

        if (book.getAuthor() == null) book.setAuthor(new Author());
        if (book.getGenre() == null) book.setGenre(new Genre());
        if (book.getPublisher() == null) book.setPublisher(new Publisher());

        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/books/search")
    public String searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer pages,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String description,
            Model model) {

        List<Book> results = bookService.searchBooks(title, author, pages, year, publisher, description);
        model.addAttribute("books", results);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("pages", pages);
        model.addAttribute("year", year);
        model.addAttribute("publisher", publisher);
        model.addAttribute("description", description);
        return "books";
    }

    @GetMapping("/books/best-sellers")
    public String showBestSellers(Model model) {
        List<Book> bestSellers = bookService.getBestSellers();
        model.addAttribute("books", bestSellers);
        return "books";
    }

}

