package com.techbuddy.reactive.bookapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private BookInfo bookInfo;
    private List<Review> reviews;
}
