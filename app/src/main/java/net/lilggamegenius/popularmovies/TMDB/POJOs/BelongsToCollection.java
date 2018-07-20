package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BelongsToCollection {
    public int id;
    public String name;
    public String poster_path;
    public String backdrop_path;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return this.poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdropPath() {
        return this.backdrop_path;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
