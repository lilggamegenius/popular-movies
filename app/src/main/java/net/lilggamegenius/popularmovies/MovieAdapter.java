package net.lilggamegenius.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static final String language = "English"; // todo turn into setting or get from device language
    private static final String region = ""; // todo turn into setting or get from device language
    private static final String API_KEY = BuildConfig.TMDBAPIKey;
    public static TmdbApi tmdbApi;
    public static MainActivity.Filter filter; // TODO: come up with more descriptive name
    private TmdbMovies tmdbMovies;

    private List<MovieDb> results = new LinkedList<>();
    private int curPage;
    private int spanCount;
    private ListItemClickListener clickListener;

    MovieAdapter(int spanCount, ListItemClickListener listItemClickListener) {
        this.spanCount = spanCount;
        clickListener = listItemClickListener;
        tmdbApi = new TmdbApi(API_KEY);
        tmdbMovies = tmdbApi.getMovies();
        if(filter == null) filter = MainActivity.Filter.Popular;
        fetchResults(true);
    }

    private void fetchResults(boolean newResult) {
        try {
            Thread thread = new Thread(() -> {
                if(newResult || results == null) {
                    results = new LinkedList<>();
                    curPage = 0;
                }
                MovieResultsPage resultsPage;
                switch (filter){
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
                System.out.printf("Page: %d Item count: %d\n", resultsPage.getPage(), results.size());
            });
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            results = null;
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(listItemId, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position == results.size() - (spanCount+1)) {
            fetchResults(false);
            System.out.printf("Results new size is %d", results.size());
        }
        holder.bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public List<MovieDb> getResults() {
        return results;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView movieImage;
        TextView movieInfo;

        MovieViewHolder(View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image_item_number);
            movieInfo = itemView.findViewById(R.id.movie_name_item_number);
            itemView.setOnClickListener(this);
        }

        void bind(MovieDb movie) {
            MainActivity.Size size = MainActivity.Size.w185;
            Picasso
                    .with(this.itemView.getContext())
                    .load(String.format("%s%s%s", MainActivity.API_URL, size, movie.getPosterPath()))
                    //.resize(movieImage.getWidth(), movieImage.getHeight())
                    //.centerCrop()
                    .into(movieImage);
            movieInfo.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onListItemClick(getAdapterPosition());
            }
        }
    }
    public interface ListItemClickListener{
        void onListItemClick(int clickedItem);
    }
}
