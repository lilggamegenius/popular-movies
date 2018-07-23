package net.lilggamegenius.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import net.lilggamegenius.popularmovies.TMDB.POJOs.Movie;
import net.lilggamegenius.popularmovies.TMDB.POJOs.Review;
import net.lilggamegenius.popularmovies.TMDB.POJOs.Reviews;
import net.lilggamegenius.popularmovies.TMDB.POJOs.Video;
import net.lilggamegenius.popularmovies.TMDB.POJOs.Videos;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity {
    Movie movie;

    @BindView(R.id.movie_detail_title)
    TextView title;
    @BindView(R.id.movie_detail_image)
    ImageView poster;
    @BindView(R.id.movie_detail_overview)
    TextView overview;
    @BindView(R.id.movie_detail_release_date)
    TextView releaseDate;
    @BindView(R.id.movie_detail_rating)
    RatingBar rating;
    @BindView(R.id.content_movie_detail_trailers)
    RecyclerView trailers;
    @BindView(R.id.content_movie_detail_reviews)
    RecyclerView reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            int ret = MainActivity.favoritesDbHelper.deleteFavorite(movie.id);
            if (ret != 0) {
                Toast.makeText(getApplicationContext(), "Removed '" + movie.getTitle() + "' to your favorites",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (MainActivity.favoritesDbHelper.insertFavorite(movie)) {
                Toast.makeText(getApplicationContext(), "Added '" + movie.getTitle() + "' to your favorites",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "Some kind of error occurred. Unable to debug",
                    Toast.LENGTH_SHORT).show();
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        trailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Thread thread = new Thread(() -> {
            Intent intent = getIntent();
            if (!intent.getBooleanExtra("favorite", false)) {
                fillUI(getIntent());
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            movie = MovieUtils.getMovie(intent.getIntExtra("movie", 0), true);
            fillUI(movie);
        });
        thread.start();
    }

    private void fillUI(final Intent intent) {
        Thread.currentThread().setName(String.format("%s: %s", "FillUI", movie));
        movie = MovieUtils.getMovie(intent.getIntExtra("movie", 0), false);
        fillUI(movie);
    }

    private void fillUI(final Movie movie) {
        trailers.post(() -> trailers.setAdapter(new TrailerList(movie.videos)));
        reviews.post(() -> reviews.setAdapter(new ReviewList(movie.reviews)));

        // todo: Decide if these should use one post or stay separate
        title.post(() -> title.setText(movie.getTitle()));
        MainActivity.Size size = MainActivity.Size.w780;
        poster.post(() -> Picasso
                .with(poster.getContext())
                .load(String.format("%s%s%s", MainActivity.API_IMAGE_URL, size, movie.getPosterPath()))
                //.resize(movieImage.getWidth(), movieImage.getHeight())
                //.centerCrop()
                .into(poster));
        overview.post(() -> overview.setText(movie.getOverview()));
        releaseDate.post(() ->
                releaseDate.setText(
                        String.format(
                                getString(R.string.movie_detail_release_date_format),
                                movie.getReleaseDate()
                        )
                ));
        rating.post(() -> rating.setRating((float) (movie.getVoteAverage() / 2)));
    }

    private class TrailerList extends RecyclerView.Adapter<TrailerList.ViewHolder> {
        Videos videos;

        public TrailerList(Videos videos) {
            this.videos = videos;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int listItemId = R.layout.trailer_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(listItemId, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                Video video = videos.getResults().get(position);
                holder.bind(video);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return videos.getResults().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Button button;
            Video video;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            public void bind(final Video video) {
                this.video = video;
                button = itemView.findViewById(R.id.trailer_list_item_btn);
                button.setText(video.getName());
                button.setOnClickListener(v ->
                        button.post(() ->
                                startActivity(
                                        new Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()
                                                )
                                        )
                                )
                        )
                );
            }
        }
    }

    private class ReviewList extends RecyclerView.Adapter<ReviewList.ViewHolder> {
        Reviews reviews;

        public ReviewList(Reviews reviews) {
            this.reviews = reviews;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int listItemId = R.layout.review_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(listItemId, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                Review review = reviews.getReviews().get(position);
                holder.bind(review);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            List<Review> reviewList = reviews.getReviews();
            if (reviewList == null) return 0;
            return reviewList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView username, text;
            Review review;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            public void bind(final Review review) {
                this.review = review;
                username.setText(review.getAuthor());
                text.setText(review.getContent());
            }
        }
    }

}
