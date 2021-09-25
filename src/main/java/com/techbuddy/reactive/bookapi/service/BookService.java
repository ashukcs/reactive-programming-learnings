package com.techbuddy.reactive.bookapi.service;

import com.techbuddy.reactive.bookapi.domain.Book;
import com.techbuddy.reactive.bookapi.domain.BookInfo;
import com.techbuddy.reactive.bookapi.domain.Review;
import com.techbuddy.reactive.bookapi.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
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
                })
                .onErrorMap(throwable -> {
                    log.error("Exception is : "+throwable);
                    return new BookException("Eception occurred while fetching books!!");
                })
                .log();
    }

    public Flux<Book> getBooksRetry(){
        Flux<BookInfo> books = bookInfoService.getBooks();

        return books.flatMap(
                        bookInfo -> {
                            Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                            return reviews.map(review -> new Book(bookInfo, review));
                        })
                .onErrorMap(throwable -> {
                    log.error("Exception is : "+throwable);
                    return new BookException("Eception occurred while fetching books!!");
                })
                .retry(3) // for infinite retry use retry()

                .log();
    }

    public Flux<Book> getBooksRetryWhen(){
        RetryBackoffSpec retryBackoffSpec = getRetryBackoffSpec();
        Flux<BookInfo> books = bookInfoService.getBooks();

        return books.flatMap(
                        bookInfo -> {
                            Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                            return reviews.map(review -> new Book(bookInfo, review));
                        })
                .onErrorMap(throwable -> {
                    log.error("Exception is : "+throwable);
                    return new BookException("Eception occurred while fetching books!!");
                })
                .retryWhen(retryBackoffSpec) // for infinite retry use retry()
                .log();
    }

    private RetryBackoffSpec getRetryBackoffSpec() {
        RetryBackoffSpec retryBackoffSpec = Retry.backoff(3, Duration.ofMillis(1000))
                .filter(throwable -> throwable instanceof BookException)
                .onRetryExhaustedThrow((retryBackoffSpec1, retrySignal) -> {
                    return Exceptions.propagate(retrySignal.failure());
                });
        return retryBackoffSpec;
    }

    public Mono<Book> getBookById(long bookId){
        Mono<BookInfo> book = bookInfoService.getBookById(bookId);
        Mono<List<Review>> reviews = reviewService.getReviews(bookId).collectList();

        return book.zipWith(reviews, (b,r)-> new Book(b,r));
    }
}
