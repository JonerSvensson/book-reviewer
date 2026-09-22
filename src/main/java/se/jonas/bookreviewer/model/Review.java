package se.jonas.bookreviewer.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Review {

    private Long id;

    @NotBlank
    private String user;

    @NotBlank
    private String book;

    @Min(1)
    @Max(10)
    private int rating;

    public Review() {
    }

    public Review(String user, String book, int rating) {
        this.user = user;
        this.book = book;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}