package net.lilggamegenius.popularmovies;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.lilggamegenius.popularmovies.TMDB.ApiUrl;
import net.lilggamegenius.popularmovies.TMDB.POJOs.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public /*static*/ class MovieUtils {
    public static final String language = Locale.getDefault().getLanguage();
    public static final String region = Locale.getDefault().getCountry();

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
            @SuppressLint("DefaultLocale") Thread thread = new Thread(() -> {
                Thread.currentThread().setName(String.format("%s-%d", "FetchResults", Thread.currentThread().getId()));
                int oldSize = adapter.results != null ? adapter.results.size() : 0;
                //noinspection StatementWithEmptyBody
                if (adapter == null) return;
                RecyclerView recyclerView = adapter.getRecyclerView();
                if (newResult || adapter.results == null) {
                    adapter.results = new LinkedList<>();
                    curPage = 0;
                    if (recyclerView != null)
                        recyclerView.post(() -> adapter.notifyItemRangeRemoved(0, oldSize));
                }
                try {
                    List<Movie> movies = getMovies(adapter, MovieAdapter.filter, ++curPage);
                    if (MovieAdapter.filter != MainActivity.Filter.Favorites) {
                        if (movies != null)
                        /*for (int i = 0, moviesSize = movies.size(); i < moviesSize; i++) {
                            Movie movie = getMovie(movies.get(i).getId());
                            results.add(movie);
                        }*/
                            adapter.results.addAll(movies);
                        if (recyclerView != null)
                            recyclerView.post(() -> adapter.notifyItemRangeInserted(oldSize, 20));
                    } else {
                        if (recyclerView != null)
                            recyclerView.post(adapter::notifyDataSetChanged);
                    }
                    System.out.printf("Page: %d/%d Item count: %d\n", curPage, 1000, adapter.results.size());
                } catch (Exception ignored) {
                } // No network
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            adapter.results = null;
        }
    }


    public static List<Movie> getMovies(MovieAdapter adapter, MainActivity.Filter filter) {
        return getMovies(adapter, filter, 1);
    }

    public static List<Movie> getMovies(MovieAdapter adapter, MainActivity.Filter filter, int page) {
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
            if (filter != MainActivity.Filter.Favorites) {
                URL url = new URL(apiUrl.toString());
                MovieResponse results = mapper.readValue(url, MovieResponse.class);
                pageCount = results.total_pages;
                return results.results;
            }
            adapter.results = MainActivity.favoritesDbHelper.getAllFavoritess();
            pageCount = (adapter.results.size() % 20) + 1;
            return adapter.results;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Movie getMovie(int id, boolean favorites) {
        if (!favorites) {
            ApiUrl apiUrl = new ApiUrl("/movie/" + id);
            apiUrl.getMap().put("append_to_response", "images,alternative_titles,videos,reviews");
            ObjectMapper mapper = new ObjectMapper();
            String value;
            try (Scanner scanner = new Scanner(new URL(apiUrl.toString()).openStream())) {
                scanner.useDelimiter("\\A");
                value = scanner.hasNext() ? scanner.next() : "";
            /*if (true) { // todo for debugging
                Object json = mapper.readValue(value, Object.class);
                value = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            }*/
                return mapper.readValue(value, Movie.class);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            List<Movie> movies = MainActivity.favoritesDbHelper.getAllFavoritess();
            for (Movie movie : movies) {
                if (movie.id == id) return movie;
            }
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