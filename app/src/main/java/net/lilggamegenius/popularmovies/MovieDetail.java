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

import info.movito.themoviedbapi.model.MovieDb;

public class MovieDetail extends AppCompatActivity {
    MovieDb movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        TextView title = findViewById(R.id.movie_detail_title);
        ImageView poster = findViewById(R.id.movie_detail_image);
        TextView overview = findViewById(R.id.movie_detail_overview);
        TextView releaseDate = findViewById(R.id.movie_detail_release_date);
        TextView rating = findViewById(R.id.movie_detail_rating);

        try {
            Intent intent = getIntent();
            Thread thread = new Thread(() -> movie = MovieAdapter.tmdbApi.getMovies().getMovie(intent.getIntExtra("movie", 0), MovieAdapter.language));
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            finish();
        }

        title.setText(movie.getTitle());
        MainActivity.Size size = MainActivity.Size.w780;
        Picasso
                .with(poster.getContext())
                .load(String.format("%s%s%s", MainActivity.API_URL, size, movie.getPosterPath()))
                //.resize(movieImage.getWidth(), movieImage.getHeight())
                //.centerCrop()
                .into(poster);
        overview.setText(movie.getOverview());
        releaseDate.setText(
                String.format(
                        getString(R.string.movie_detail_release_date_format),
                        movie.getReleaseDate()
                )
        );
        rating.setText(
                String.format(
                        getString(R.string.movie_detail_rating_format),
                        movie.getVoteAverage()
                )
        );
    }

}
