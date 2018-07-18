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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.lilggamegenius.popularmovies.TMDB.Movie;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public MovieAdapter movieAdapter;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(this::setupMainUI).start();
    }

    public static final String API_IMAGE_URL = "http://image.tmdb.org/t/p/";

    private void setupMainUI() {
        Thread.currentThread().setName("setupMainUI");
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.post(() -> navigationView.setCheckedItem(R.id.nav_popular));

        createAdapter();
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
                break;
            case R.id.nav_top_rated:
                MovieAdapter.filter = Filter.TopRated;
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
        refreshData();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void refreshData() {
        MovieUtils.fetchResults(movieAdapter, true);
    }

    private void createAdapter() {
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        final int spanCount = layoutManager.getSpanCount();

        //GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(spanCount, clickedItem -> new Thread(() -> {
            Thread.currentThread().setName("ClickedItemHandler");
            List<Movie> movies;
            if ((movies = movieAdapter.getResults()) != null) {
                Movie movie = movies.get(clickedItem);
                Intent intent = new Intent(this, MovieDetail.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connectivity",
                        Toast.LENGTH_LONG).show();
            }
        }).start());
        recyclerView.post(() -> {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(movieAdapter);
            recyclerView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
            recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
                @Override
                protected void loadMoreItems() {
                    MovieUtils.fetchResults(movieAdapter);
                }

                @Override
                public int getTotalPageCount() {
                    return movieAdapter.getTotalPages();
                }

                @Override
                public boolean isLastPage() {
                    return movieAdapter.getPageCount() >= movieAdapter.getTotalPages();
                }

                @Override
                public boolean isLoading() {
                    return false;
                }
            });
        });
    }

    public enum Filter {
        Popular, TopRated, Upcoming, NowPlaying
    }

    public enum Size {
        w92,
        w154,
        w185,
        w342,
        w500,
        w780
    }
}
