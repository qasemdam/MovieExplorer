package com.qasem.movieexplorer.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qasem.movieexplorer.R;
import com.qasem.movieexplorer.databinding.ItemMovieBinding;
import com.qasem.movieexplorer.models.Movie;

import java.util.ArrayList;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    // Movie List
    private ArrayList<Movie> movieList;
    // Item Click Listener
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
    private OnMovieClickListener listener;

    // Constructor
    public MovieAdapter(ArrayList<Movie> movieList,
                        OnMovieClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {
        // Inflate Item Layout
        ItemMovieBinding binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MovieViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder,
                                 int position) {
        // Get Current Movie
        Movie movie = movieList.get(position);

        // Set Movie Data
        holder.binding.txtTitle.setText(movie.getTitle());

        holder.binding.txtReleaseDate.setText(
                "Release: " + movie.getReleaseDate());

        holder.binding.txtRating.setText(
                "★ " + movie.getRating() + " / 10");

        // Load Movie Poster
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"
                        + movie.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.binding.imgPoster);

        // Handle Item Click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    // ViewHolder
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;
        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}