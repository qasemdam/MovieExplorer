package com.qasem.movieexplorer.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qasem.movieexplorer.Notifications.NotificationHelper;
import com.qasem.movieexplorer.activities.MainActivity;
import com.qasem.movieexplorer.adapters.MovieAdapter;
import com.qasem.movieexplorer.databinding.FragmentMovieBinding;
import com.qasem.movieexplorer.models.Movie;
import com.qasem.movieexplorer.network.FetchMovie;

import java.util.ArrayList;

public class MovieFragment extends Fragment
        implements FetchMovie.OnMoviesLoadedListener {
    // View Binding
    private FragmentMovieBinding binding;

    // RecyclerView Adapter
    private MovieAdapter adapter;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize View Binding
        binding = FragmentMovieBinding.inflate(inflater, container, false);

        // Show ProgressBar
        binding.progressBar.setVisibility(View.VISIBLE);

        // Load Movies
        new FetchMovie(this).execute();

        return binding.getRoot();
    }

    @Override
    public void onMoviesLoaded(ArrayList<Movie> movieList) {
        // Check Internet Connection
        if (movieList == null) {
            binding.progressBar.setVisibility(View.GONE);
            new NotificationHelper(requireContext())
                    .showNotification(
                            requireContext(),
                            "No Internet Connection",
                            "Please check your internet and try again."
                    );
            return;
        }

        // Show Success Notification
        new NotificationHelper(requireContext())
                .showNotification(
                        requireContext(),
                        "MovieExplorer",
                        "Movies loaded successfully."
                );
        // Initialize Adapter
        adapter = new MovieAdapter(movieList, movie ->
                ((MainActivity) requireActivity()).showDetailsTab(movie));
        // Show First Movie Details
        if (!movieList.isEmpty()) {
            ((MainActivity) requireActivity())
                    .updateDetails(movieList.get(0));
        }
        // Initialize RecyclerView
        binding.movieRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        binding.movieRecyclerView.setAdapter(adapter);
        // Hide ProgressBar
        binding.progressBar.setVisibility(View.GONE);
    }

    // Change RecyclerView Layout
    public void setLayout(boolean isGrid) {
        if (binding == null)
            return;
        if (isGrid) {
            binding.movieRecyclerView.setLayoutManager(
                    new GridLayoutManager(getContext(), 2)
            );
        } else {
            binding.movieRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext())
            );
        }
        binding.movieRecyclerView.setAdapter(adapter);
    }

    // Search Movies
    public void searchMovie(String query) {
        binding.progressBar.setVisibility(View.VISIBLE);
        new FetchMovie(this, query).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}