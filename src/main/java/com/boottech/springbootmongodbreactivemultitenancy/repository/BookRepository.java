package com.boottech.springbootmongodbreactivemultitenancy.repository;

import com.boottech.springbootmongodbreactivemultitenancy.domain.document.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
