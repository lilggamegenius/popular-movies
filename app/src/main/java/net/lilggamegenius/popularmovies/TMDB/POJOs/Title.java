package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Title {
    public String iso_3166_1;
    public String title;
    public String type;

    public String getIso31661() {
        return this.iso_3166_1;
    }

    public void setIso31661(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
