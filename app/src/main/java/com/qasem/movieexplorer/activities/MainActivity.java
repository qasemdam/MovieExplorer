package com.qasem.movieexplorer.activities;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayoutMediator;
import com.qasem.movieexplorer.R;
import com.qasem.movieexplorer.adapters.ViewPagerAdapter;
import com.qasem.movieexplorer.databinding.ActivityMainBinding;
import com.qasem.movieexplorer.models.Movie;

public class MainActivity extends AppCompatActivity {
    // View Binding
    private ActivityMainBinding binding;
    // ViewPager Adapter
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Request Notification Permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    100
            );
        }
        // Set Toolbar
        setSupportActionBar(binding.toolbar);
        // Initialize ViewPager Adapter
        adapter = new ViewPagerAdapter(this);
        // Handle Back Button
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (binding.viewPager.getCurrentItem() == 1) {
                            binding.viewPager.setCurrentItem(0, true);
                        } else {
                            setEnabled(false);
                            getOnBackPressedDispatcher().onBackPressed();
                        }
                    }
                });
        // Connect ViewPager with Adapter
        binding.viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Movies");
                            break;
                        case 1:
                            tab.setText("Details");
                            break;
                    }
                }).attach();
    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Initialize SearchView
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search)
                        .getActionView();
        searchView.setQueryHint("Search movies...");
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        adapter.getMovieFragment().searchMovie(query);
                        return true;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
        return true;
    }

    // Handle Menu Clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_list) {
            adapter.getMovieFragment().setLayout(false);
            return true;
        } else if (item.getItemId() == R.id.action_grid) {
            adapter.getMovieFragment().setLayout(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Open Details Tab
    public void showDetailsTab() {
        binding.viewPager.setCurrentItem(1, true);
    }

    // Open Details Tab With Selected Movie
    public void showDetailsTab(Movie movie) {
        adapter.getDetailsFragment().updateMovie(movie);
        binding.viewPager.setCurrentItem(1, true);
    }

    // Update Movie Details
    public void updateDetails(Movie movie) {
        adapter.getDetailsFragment().updateMovie(movie);
    }
}