package com.techbuddy.reactive.bookapi.service;

import com.techbuddy.reactive.bookapi.domain.Review;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class ReviewService {
    public Flux<Review> getReviews(long bookId){
        List<Review> reviewList = Arrays.asList(
            new Review(1, bookId, 9.1, "Good Book"),
            new Review(2, bookId, 8.3, "Worth Reading")
        );
        return Flux.fromIterable(reviewList);
    }
}
