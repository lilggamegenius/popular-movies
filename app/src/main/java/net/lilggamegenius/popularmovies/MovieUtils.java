package net.lilggamegenius.popularmovies;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.tools.MovieDbException;

public /*static*/ class MovieUtils {
    public static final String language = "English"; // todo turn into setting or get from device language
    public static final String region = ""; // todo turn into setting or get from device language
    public static final String API_KEY = BuildConfig.TMDBAPIKey;
    @Nullable
    public static TmdbApi tmdbApi;
    @Nullable
    public static TmdbMovies tmdbMovies;

    @Nullable
    public static List<MovieDb> results = new LinkedList<>();
    public static int curPage;
    public static int pageCount;
    public static int spanCount;

    public static void connectToAPI() {
        try {
            tmdbApi = new TmdbApi(API_KEY);
            tmdbMovies = tmdbApi.getMovies();
        } catch (MovieDbException ignored) {
        } // No network
    }

    public static void fetchResults(MovieAdapter adapter) {
        fetchResults(adapter, false);
    }

    public static void fetchResults(MovieAdapter adapter, boolean newResult) {
        if (tmdbApi == null || tmdbMovies == null) connectToAPI(); // Try reconnecting
        if (tmdbApi == null || tmdbMovies == null) return; // Connection failed
        try {
            Thread thread = new Thread(() -> {
                int oldSize = results != null ? results.size() : 0;
                RecyclerView recyclerView = adapter.getRecyclerView();
                if (newResult || results == null) {
                    results = new LinkedList<>();
                    curPage = 0;
                    if (recyclerView != null)
                        recyclerView.post(() -> adapter.notifyItemRangeRemoved(0, oldSize));
                }
                try {
                    MovieResultsPage resultsPage;
                    switch (MovieAdapter.filter) {
                        case Popular:
                            resultsPage = tmdbMovies.getPopularMovies(language, ++curPage);
                            break;
                        case TopRated:
                            resultsPage = tmdbMovies.getTopRatedMovies(language, ++curPage);
                            break;
                        case Upcoming:
                            resultsPage = tmdbMovies.getUpcoming(language, ++curPage, region);
                            break;
                        case NowPlaying:
                            resultsPage = tmdbMovies.getNowPlayingMovies(language, ++curPage, region);
                            break;
                        default:
                            throw new RuntimeException("filter not part of Filter enum");
                    }
                    results.addAll(resultsPage.getResults());
                    if (newResult) {
                        if (recyclerView != null)
                            recyclerView.post(() -> adapter.notifyItemRangeInserted(oldSize + 1, 20));
                    }
                    System.out.printf("Page: %d/%d Item count: %d\n", resultsPage.getPage(), pageCount = resultsPage.getTotalPages(), results.size());
                } catch (MovieDbException ignored) {
                } // No network
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            results = null;
        }
    }
}
