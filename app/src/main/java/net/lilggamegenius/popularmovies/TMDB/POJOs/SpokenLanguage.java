package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpokenLanguage {
    public String iso_639_1;
    public String name;

    public String getIso6391() {
        return this.iso_639_1;
    }

    public void setIso6391(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
