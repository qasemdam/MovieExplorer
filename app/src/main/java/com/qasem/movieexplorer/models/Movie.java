package com.qasem.movieexplorer.models;

public class Movie {
    // Variables
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private double rating;

    // Empty Constructor
    public Movie() {
    }

    // Constructor
    public Movie(String title,
                 String overview,
                 String releaseDate,
                 String posterPath,
                 double rating) {

        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.rating = rating;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getRating() {
        return rating;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}