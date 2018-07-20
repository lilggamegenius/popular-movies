package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Videos {
    public int id;
    public List<Video> results;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return this.results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public boolean hasResults() {
        return !results.isEmpty();
    }
}
