package com.techbuddy.reactive.bookapi.service;

import com.techbuddy.reactive.bookapi.domain.Book;
import com.techbuddy.reactive.bookapi.domain.BookInfo;
import com.techbuddy.reactive.bookapi.domain.Review;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {
    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks(){
        Flux<BookInfo> books = bookInfoService.getBooks();

        return books.flatMap(
                bookInfo -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review -> new Book(bookInfo, review));
                }
        ).log();
    }
}
