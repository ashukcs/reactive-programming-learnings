package com.techbuddy.reactive.bookapi.service;

import com.techbuddy.reactive.bookapi.domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class BookInfoService {
    public Flux<BookInfo> getBooks(){
        List<BookInfo> books = Arrays.asList(
            new BookInfo(1, "Book One", "Author One", "12121321"),
            new BookInfo(2, "Book Two", "Author Two", "45454556"),
            new BookInfo(3, "Book Three", "Author Three", "98123274")
        );
        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long bookId){
        BookInfo book = new BookInfo(1, "Book One", "Author One", "12121321");

        return Mono.just(book);
    }
}
