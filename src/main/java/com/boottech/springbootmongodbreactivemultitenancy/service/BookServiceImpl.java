package com.boottech.springbootmongodbreactivemultitenancy.service;

import com.boottech.springbootmongodbreactivemultitenancy.domain.document.Book;
import com.boottech.springbootmongodbreactivemultitenancy.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Flux<Book> getAllBook() {
        return  bookRepository.findAll();
    }

    @Override
    public Mono<Book> addBook(Book book) {
        LOGGER.info("addBook : {} " , book );
        return bookRepository.save(book)
                .log();
    }

    @Override
    public Mono<Book> getBookById(String id) {
        return null;
    }

    @Override
    public Mono<Book> updateBook(Book book, String id) {
        return bookRepository.findById(id)
                .flatMap(book1 -> {
                    book1.setTitle(book.getTitle());
                    book1.setIsbn(book.getIsbn());
                    book1.setPublicationDate(book.getPublicationDate());
                    return bookRepository.save(book1);
                });
    }

    @Override
    public Mono<Void> deleteBookById(String id) {
        return bookRepository.deleteById(id);
    }
}
