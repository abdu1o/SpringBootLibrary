package com.example.library;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
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
                          @RequestParam String genreName) {

        Author author = new Author();
        author.setName(authorName);
        author = authorRepository.save(author);

        Genre genre = new Genre();
        genre.setName(genreName);
        genre = genreRepository.save(genre);

        Book book = new Book();
        book.setTitle(title);
        book.setPageAmount(pages);
        book.setYear(year);
        book.setDescription(description);
        book.setAuthor(author);
        book.setGenre(genre);

        bookRepository.save(book);

        return "redirect:/";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
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
            Model model) {

        List<Book> results = bookService.searchBooks(title, author, pages);
        model.addAttribute("books", results);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("pages", pages);
        return "books";
    }
}

