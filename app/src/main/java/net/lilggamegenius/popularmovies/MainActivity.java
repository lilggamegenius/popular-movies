package net.lilggamegenius.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import info.movito.themoviedbapi.model.MovieDb;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public MovieAdapter movieAdapter;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "TODO: make this button do something", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = findViewById(R.id.movie_lists);
        final int spanCount = ((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount();

        //GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //recyclerView.setLayoutManager(layoutManager);

        try {
            Thread thread = new Thread(() -> {
                movieAdapter = new MovieAdapter(spanCount, clickedItem -> new Thread(() -> {
                    MovieDb movieDb = movieAdapter.getResults().get(clickedItem);
                    Intent intent = new Intent(this, MovieDetail.class);
                    intent.putExtra("movie", movieDb.getId());
                    startActivity(intent);
                }).start());
                recyclerView.setAdapter(movieAdapter);
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_popular);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_popular:
                MovieAdapter.filter = Filter.Popular;
                recreate();
                break;
            case R.id.nav_top_rated:
                MovieAdapter.filter = Filter.TopRated;
                recreate();
                break;
            case R.id.nav_upcoming:
                Toast.makeText(getApplicationContext(), "Not yet implemented",
                        Toast.LENGTH_SHORT).show();
                MovieAdapter.filter = Filter.Upcoming;
                break;
            case R.id.nav_now_playing:
                Toast.makeText(getApplicationContext(), "Not yet implemented",
                        Toast.LENGTH_SHORT).show();
                MovieAdapter.filter = Filter.NowPlaying;
                break;
            case R.id.nav_share:
                Toast.makeText(getApplicationContext(), "Not yet implemented",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(getApplicationContext(), "Not yet implemented",
                        Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    enum Filter {
        Popular, TopRated, Upcoming, NowPlaying
    }

    public static final String API_URL = "http://image.tmdb.org/t/p/";

    public enum Size {
        w92,
        w154,
        w185,
        w342,
        w500,
        w780
    }
}
