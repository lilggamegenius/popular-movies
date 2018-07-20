package net.lilggamegenius.popularmovies.TMDB.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

enum Status {
    Rumored, Planned, InProduction, PostProduction, Released, Canceled
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
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
    public Videos videos;
    public Reviews reviews;

    public Movie() {
    }

    protected Movie(Parcel in) {
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        budget = in.readInt();
        homepage = in.readString();
        id = in.readInt();
        imdb_id = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        release_date = in.readString();
        revenue = in.readLong();
        runtime = in.readInt();
        tagline = in.readString();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        vote_count = in.readInt();
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeInt(budget);
        dest.writeString(homepage);
        dest.writeInt(id);
        dest.writeString(imdb_id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeLong(revenue);
        dest.writeInt(runtime);
        dest.writeString(tagline);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeDouble(vote_average);
    }
}

