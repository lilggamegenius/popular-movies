package net.lilggamegenius.popularmovies.TMDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

enum Status {
    Rumored, Planned, InProduction, PostProduction, Released, Canceled
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    public boolean adult;
    public String backdrop_path;
    public BelongsToCollection belongs_to_collection;
    public int budget;
    public List<Genre> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public List<ProductionCompany> production_companies;
    public List<ProductionCountry> production_countries;
    public String release_date;
    public long revenue;
    public int runtime;
    public List<SpokenLanguage> spoken_languages;
    public Status status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public Images images;
    public AlternativeTitles alternative_titles;

    public boolean getAdult() {
        return this.adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return this.backdrop_path;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public BelongsToCollection getBelongsToCollection() {
        return this.belongs_to_collection;
    }

    public void setBelongsToCollection(BelongsToCollection belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public int getBudget() {
        return this.budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return this.genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return this.imdb_id;
    }

    public void setImdbId(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginalLanguage() {
        return this.original_language;
    }

    public void setOriginalLanguage(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginalTitle() {
        return this.original_title;
    }

    public void setOriginalTitle(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return this.poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return this.production_companies;
    }

    public void setProductionCompanies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return this.production_countries;
    }

    public void setProductionCountries(List<ProductionCountry> production_countries) {
        this.production_countries = production_countries;
    }

    public String getReleaseDate() {
        return this.release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public long getRevenue() {
        return this.revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return this.spoken_languages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    @JsonIgnore
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTagline() {
        return this.tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getVideo() {
        return this.video;
    }

    public void setVideo(boolean video) {
        this.video = video;
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

    public Images getImages() {
        return this.images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public AlternativeTitles getAlternativeTitles() {
        return this.alternative_titles;
    }

    public void setAlternativeTitles(AlternativeTitles alternative_titles) {
        this.alternative_titles = alternative_titles;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BelongsToCollection {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class Genre {
    public int id;
    public String name;

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
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductionCompany {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductionCountry {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class SpokenLanguage {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class Backdrop {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class Poster {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class Images {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class Title {
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

@JsonIgnoreProperties(ignoreUnknown = true)
class AlternativeTitles {
    public List<Title> titles;

    public List<Title> getTitles() {
        return this.titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
}