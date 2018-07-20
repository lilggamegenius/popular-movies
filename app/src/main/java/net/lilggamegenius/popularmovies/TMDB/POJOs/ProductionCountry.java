package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionCountry {
    public String iso_3166_1;
    public String name;

    public String getIso31661() {
        return this.iso_3166_1;
    }

    public void setIso31661(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
