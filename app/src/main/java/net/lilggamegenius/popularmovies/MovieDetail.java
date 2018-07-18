package net.lilggamegenius.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.lilggamegenius.popularmovies.TMDB.Movie;

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
    TextView rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        Thread thread = new Thread(() -> fillUI(getIntent()));
        thread.start();
    }

    private void fillUI(Intent intent) {
        Thread.currentThread().setName(String.format("%s: %s", "FillUI", movie));
        movie = intent.getParcelableExtra("movie");

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
        rating.post(() ->
                rating.setText(
                        String.format(
                                getString(R.string.movie_detail_rating_format),
                                movie.getVoteAverage()
                        )
                ));
    }


}
