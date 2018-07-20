package net.lilggamegenius.popularmovies.TMDB.POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionCompany {
    public int id;
    public String logo_path;
    public String name;
    public String origin_country;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoPath() {
        return this.logo_path;
    }

    public void setLogoPath(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return this.origin_country;
    }

    public void setOriginCountry(String origin_country) {
        this.origin_country = origin_country;
    }
}
