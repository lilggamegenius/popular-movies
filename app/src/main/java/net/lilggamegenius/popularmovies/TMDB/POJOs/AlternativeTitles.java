package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlternativeTitles {
    public List<Title> titles;

    public List<Title> getTitles() {
        return this.titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
}
