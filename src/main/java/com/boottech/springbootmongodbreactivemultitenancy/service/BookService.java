package com.boottech.springbootmongodbreactivemultitenancy.service;

import com.boottech.springbootmongodbreactivemultitenancy.domain.document.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Flux<Book> getAllBook();

    Mono<Book> addBook(Book book);

    Mono<Book> getBookById(String id);

    Mono<Book> updateBook(Book book, String id);

    Mono<Void> deleteBookById(String id);
}
