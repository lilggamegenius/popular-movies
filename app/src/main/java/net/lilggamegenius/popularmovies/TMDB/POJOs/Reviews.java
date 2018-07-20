package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reviews {
    private int id;
    private int page;
    private List<Review> Reviews;
    private int total_pages;
    private int total_Reviews;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getReviews() {
        return this.Reviews;
    }

    public void setReviews(List<Review> Reviews) {
        this.Reviews = Reviews;
    }

    public int getTotalPages() {
        return this.total_pages;
    }

    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotalReviews() {
        return this.total_Reviews;
    }

    public void setTotalReviews(int total_Reviews) {
        this.total_Reviews = total_Reviews;
    }
}
