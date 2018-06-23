package net.lilggamegenius.popularmovies;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.lilggamegenius.popularmovies.TMDB.ApiUrl;
import net.lilggamegenius.popularmovies.TMDB.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public /*static*/ class MovieUtils {
    public static final String language = Locale.getDefault().getLanguage();
    public static final String region = Locale.getDefault().getCountry();

    @Nullable
    public static List<Movie> results = new LinkedList<>();
    public static int curPage;
    public static int pageCount;
    public static int spanCount;

    private MovieUtils() {
    }

    public static void fetchResults(MovieAdapter adapter) {
        fetchResults(adapter, false);
    }

    public static void fetchResults(MovieAdapter adapter, boolean newResult) {
        try {
            Thread thread = new Thread(() -> {
                Thread.currentThread().setName(String.format("%s-%d", "FetchResults", Thread.currentThread().getId()));
                int oldSize = results != null ? results.size() : 0;
                RecyclerView recyclerView = adapter.getRecyclerView();
                if (newResult || results == null) {
                    results = new LinkedList<>();
                    curPage = 0;
                    if (recyclerView != null)
                        recyclerView.post(() -> adapter.notifyItemRangeRemoved(0, oldSize));
                }
                try {
                    List<Movie> movies = getMovies(MovieAdapter.filter, ++curPage);
                    if (movies != null)
                        /*for (int i = 0, moviesSize = movies.size(); i < moviesSize; i++) {
                            Movie movie = getMovie(movies.get(i).getId());
                            results.add(movie);
                        }*/
                        results.addAll(movies);
                    if (!newResult) {
                        if (recyclerView != null)
                            recyclerView.post(() -> adapter.notifyItemRangeInserted(oldSize - 1, 20));
                    }
                    System.out.printf("Page: %d/%d Item count: %d\n", curPage, 1000, results.size());
                } catch (Exception ignored) {
                } // No network
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            results = null;
        }
    }


    public static List<Movie> getMovies(MainActivity.Filter filter) {
        return getMovies(filter, 1);
    }

    public static List<Movie> getMovies(MainActivity.Filter filter, int page) {
        ApiUrl apiUrl = new ApiUrl("/movie/", (short) page);
        switch (filter) {
            case Popular:
                apiUrl.setApiPath(apiUrl.getApiPath() + "popular");
                break;
            case TopRated:
                apiUrl.setApiPath(apiUrl.getApiPath() + "top_rated");
                break;
            case Upcoming:
                break;
            case NowPlaying:
                break;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL(apiUrl.toString());
            MovieResponse results = mapper.readValue(url, MovieResponse.class);
            pageCount = results.total_pages;
            return results.results;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Movie getMovie(int id) {
        ApiUrl apiUrl = new ApiUrl("/movie/" + id);
        apiUrl.getMap().put("append_to_response", "images,alternative_titles");
        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try (Scanner scanner = new Scanner(new URL(apiUrl.toString()).openStream())) {
            scanner.useDelimiter("\\A");
            value = scanner.hasNext() ? scanner.next() : "";
            /*if(true){ // todo for debugging
                Object json = mapper.readValue(value, Object.class);
                value = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            }*/
            return mapper.readValue(value, Movie.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MovieResponse {
    int page;
    List<Movie> results;
    int total_results;
    int total_pages;
}