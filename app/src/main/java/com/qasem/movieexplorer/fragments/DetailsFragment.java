package com.qasem.movieexplorer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.qasem.movieexplorer.R;
import com.qasem.movieexplorer.databinding.FragmentDetailsBinding;
import com.qasem.movieexplorer.models.Movie;
public class DetailsFragment extends Fragment {

    // View Binding
    private FragmentDetailsBinding binding;

    // Current Selected Movie
    private Movie currentMovie;

    public DetailsFragment() {
        // Required empty public constructor
    }
    // Update Selected Movie
    public void updateMovie(Movie movie) {
        currentMovie = movie;
        if (binding == null)
            return;
        showMovie();
    }
    // Display Movie Details
    private void showMovie() {
        if (currentMovie == null)
            return;
        binding.txtTitle.setText(currentMovie.getTitle());
        binding.txtReleaseDate.setText(
                "Release: " + currentMovie.getReleaseDate());
        binding.txtRating.setText(
                "★ " + currentMovie.getRating() + " / 10");
        binding.txtOverview.setText(currentMovie.getOverview());
        Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500"
                        + currentMovie.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.imgPoster);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize View Binding
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        // Show Current Movie
        showMovie();
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}