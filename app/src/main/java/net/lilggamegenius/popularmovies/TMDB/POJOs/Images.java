package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Images {
    public List<Backdrop> backdrops;
    public List<Poster> posters;

    public List<Backdrop> getBackdrops() {
        return this.backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return this.posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }
}
