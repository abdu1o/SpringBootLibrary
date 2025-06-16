package com.example.library;

import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
import com.example.library.entities.Publisher;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.GenreRepository;
import com.example.library.repositories.PublisherRepository;
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

    @Autowired
    private PublisherRepository publisherRepository;

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
        if (book.getPublisher() == null || book.getPublisher().getName() == null || book.getPublisher().getName().isBlank()) {
            throw new IllegalArgumentException("Publisher must be specified");
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

        Publisher publisher = publisherRepository.findByName(book.getPublisher().getName());
        if (publisher == null) {
            publisher = new Publisher();
            publisher.setName(book.getPublisher().getName());
            publisher = publisherRepository.save(publisher);
        }
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String title, String authorName, String genre, Integer pages, Integer year, String publisher, String description) {
        return bookRepository.findAll().stream()
                .filter(b -> title == null || title.isEmpty() || b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(b -> authorName == null || authorName.isEmpty() ||
                        (b.getAuthor() != null && b.getAuthor().getName().toLowerCase().contains(authorName.toLowerCase())))
                .filter(b -> genre == null || genre.isEmpty() ||
                        (b.getGenre() != null && b.getGenre().getName().toLowerCase().contains(genre.toLowerCase())))
                .filter(b -> pages == null || b.getPageAmount() == pages)
                .filter(b -> year == null || b.getYear() == year)
                .filter(b -> publisher == null || publisher.isEmpty() ||
                        (b.getPublisher() != null && b.getPublisher().getName().toLowerCase().contains(publisher.toLowerCase())))
                .filter(b -> description == null || description.isEmpty() ||
                        (b.getDescription() != null && b.getDescription().toLowerCase().contains(description.toLowerCase())))
                .toList();
    }


    public List<Book> getBestSellers() {
        return bookRepository.findByCopiesSoldGreaterThanEqual(50000);
    }

}

