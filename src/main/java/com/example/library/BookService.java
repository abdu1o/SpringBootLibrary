package com.example.library;

import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
    }

    public Book saveBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().getName() == null || book.getAuthor().getName().isBlank()) {
            throw new IllegalArgumentException("Author must be specified");
        }
        if (book.getGenre() == null || book.getGenre().getName() == null || book.getGenre().getName().isBlank()) {
            throw new IllegalArgumentException("Genre must be specified");
        }

        Author author = authorRepository.findByName(book.getAuthor().getName());
        if (author == null) {
            author = new Author();
            author.setName(book.getAuthor().getName());
            author = authorRepository.save(author);
        }
        book.setAuthor(author);

        Genre genre = genreRepository.findByName(book.getGenre().getName());
        if (genre == null) {
            genre = new Genre();
            genre.setName(book.getGenre().getName());
            genre = genreRepository.save(genre);
        }
        book.setGenre(genre);

        return bookRepository.save(book);
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String title, String authorName, Integer pages) {
        return bookRepository.findAll().stream()
                .filter(b -> (title == null || title.isEmpty() || b.getTitle().toLowerCase().contains(title.toLowerCase())))
                .filter(b -> (authorName == null || authorName.isEmpty() || b.getAuthor().getName().toLowerCase().contains(authorName.toLowerCase())))
                .filter(b -> (pages == null || b.getPageAmount() == pages))
                .toList();
    }
}

