package com.boottech.springbootmongodbreactivemultitenancy.domain.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book {

    @Id
    private String id;

    private String title;

    private int page;

    private String isbn;

    private String description;

    private double price;

    private LocalDate publicationDate;

    private String language;
}
