package net.lilggamegenius.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.lilggamegenius.popularmovies.TMDB.POJOs.Movie;

import java.util.List;

import static net.lilggamegenius.popularmovies.MovieUtils.fetchResults;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static MainActivity.Filter filter; // TODO: come up with more descriptive name
    public static boolean favoritesChanged = false;

    private ListItemClickListener clickListener;
    private RecyclerView recyclerView = null;

    MovieAdapter(int spanCount, ListItemClickListener listItemClickListener) {
        MovieUtils.spanCount = spanCount;
        clickListener = listItemClickListener;
        //connectToAPI();
        if (filter == null) filter = MainActivity.Filter.Popular;
        //fetchResults(this, true);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        fetchResults(this, true);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if (recyclerView == this.recyclerView) this.recyclerView = null;
        notifyDataSetChanged();
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
        if (MovieUtils.results == null) return;
        /*if (position == results.size() - (spanCount*2)) {
            fetchResults(false);
            System.out.printf("Results new size is %d", results.size());
        }*/
        holder.bind(MovieUtils.results.get(position));
    }

    @Override
    public int getItemCount() {
        return MovieUtils.results == null ? 0 : MovieUtils.results.size();
    }

    @Nullable
    public List<Movie> getResults() {
        return MovieUtils.results;
    }

    public int getPageCount() {
        return MovieUtils.curPage;
    }

    public int getTotalPages() {
        return MovieUtils.pageCount;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void checkForRefresh() {
        if (favoritesChanged) {
            recyclerView.setAdapter(this);
            favoritesChanged = false;
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItem);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;
        TextView movieInfo;

        MovieViewHolder(View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image_item_number);
            movieInfo = itemView.findViewById(R.id.movie_name_item_number);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            if (movie == null) {
                System.err.println("Movie is null");
                return;
            }
            if (movie.getPosterPath() != null) {
                MainActivity.Size size = MainActivity.Size.w185;
                Picasso
                        .with(this.itemView.getContext())
                        .load(String.format("%s%s%s", MainActivity.API_IMAGE_URL, size, movie.getPosterPath()))
                        //.resize(movieImage.getWidth(), movieImage.getHeight())
                        //.centerCrop()
                        .into(movieImage);
            }
            movieInfo.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onListItemClick(getAdapterPosition());
            }
        }

    }
}
