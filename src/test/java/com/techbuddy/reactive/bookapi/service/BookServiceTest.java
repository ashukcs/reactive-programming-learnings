package com.techbuddy.reactive.bookapi.service;

import com.techbuddy.reactive.bookapi.domain.Book;
import com.techbuddy.reactive.bookapi.domain.BookInfo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookInfoService bookInfoService = new BookInfoService();
    private ReviewService reviewService = new ReviewService();
    private BookService bookService = new BookService(bookInfoService, reviewService);

    @Test
    void getBooks() {
        Flux<Book> books = bookService.getBooks();

        StepVerifier.create(books)
                .assertNext(book -> {
                    assertEquals("Book One", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book Two", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book Three", book.getBookInfo().getTitle());
                    assertEquals(2, book.getReviews().size());
                })
                .verifyComplete();
    }
}