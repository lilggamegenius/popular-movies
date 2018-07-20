package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Poster {
    public double aspect_ratio;
    public String file_path;
    public int height;
    public String iso_639_1;
    public double vote_average;
    public int vote_count;
    public int width;

    public double getAspectRatio() {
        return this.aspect_ratio;
    }

    public void setAspectRatio(double aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFilePath() {
        return this.file_path;
    }

    public void setFilePath(String file_path) {
        this.file_path = file_path;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getIso6391() {
        return this.iso_639_1;
    }

    public void setIso6391(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public double getVoteAverage() {
        return this.vote_average;
    }

    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVoteCount() {
        return this.vote_count;
    }

    public void setVoteCount(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
