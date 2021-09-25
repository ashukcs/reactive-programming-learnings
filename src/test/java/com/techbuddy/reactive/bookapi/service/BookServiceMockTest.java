package com.techbuddy.reactive.bookapi.service;

import com.techbuddy.reactive.bookapi.domain.Book;
import com.techbuddy.reactive.bookapi.exception.BookException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {

    @Mock
    private BookInfoService bookInfoService;
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private BookService bookService;

    @Test
    void getBooksMock() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong())).thenCallRealMethod();

        Flux<Book> bookFlux = bookService.getBooks();
        StepVerifier.create(bookFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getBooksMockOnError() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("Exception occurred using Test!"));

        Flux<Book> bookFlux = bookService.getBooks();
        StepVerifier.create(bookFlux)
                .expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksMockOnErrorRetry() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("Exception occurred using Test!"));

        Flux<Book> bookFlux = bookService.getBooksRetry();
        StepVerifier.create(bookFlux)
                .expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksMockOnErrorRetryWhen() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("Exception occurred using Test!"));

        Flux<Book> bookFlux = bookService.getBooksRetryWhen();
        StepVerifier.create(bookFlux)
                .expectError(BookException.class)
                .verify();
    }

    @Test
    void getBookById() {
    }
}