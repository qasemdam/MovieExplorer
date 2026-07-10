package com.qasem.movieexplorer.network;

import android.os.AsyncTask;

import com.qasem.movieexplorer.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchMovie extends AsyncTask<Void, Void, ArrayList<Movie>> {
    // API URLs
    private final String BASE_URL =
            "https://api.themoviedb.org/3/movie/popular";
    private final String SEARCH_URL =
            "https://api.themoviedb.org/3/search/movie";
    // TMDB API Key
    private final String API_KEY =
            "e736c96871438d26e642b00557a0a776";

    // OkHttp Client
    private OkHttpClient client = new OkHttpClient();
    // Listener
    private OnMoviesLoadedListener listener;
    // Search Query
    private String searchQuery;
    public interface OnMoviesLoadedListener {
        void onMoviesLoaded(ArrayList<Movie> movieList);
    }

    // Load Popular Movies
    public FetchMovie(OnMoviesLoadedListener listener) {
        this.listener = listener;
    }
    // Search Movies
    public FetchMovie(OnMoviesLoadedListener listener,
                      String searchQuery) {
        this.listener = listener;
        this.searchQuery = searchQuery;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        HttpUrl.Builder builder;
        if (searchQuery == null || searchQuery.isEmpty()) {
            builder = HttpUrl.parse(BASE_URL).newBuilder();
        } else {
            builder = HttpUrl.parse(SEARCH_URL).newBuilder();
        }

        builder.addQueryParameter("api_key", API_KEY)
                .addQueryParameter("language", "en-US")
                .addQueryParameter("page", "1");

        if (searchQuery != null && !searchQuery.isEmpty()) {
            builder.addQueryParameter("query", searchQuery);
        }
        String url = builder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsArray =
                    jsonObject.getJSONArray("results");
            ArrayList<Movie> movieList = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movie =
                        resultsArray.getJSONObject(i);
                movieList.add(
                        new Movie(
                                movie.getString("title"),
                                movie.getString("overview"),
                                movie.getString("release_date"),
                                movie.getString("poster_path"),
                                movie.getDouble("vote_average")
                        )
                );
            }
            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movieList) {
        super.onPostExecute(movieList);
        // Return Movies To Fragment
        if (listener != null) {
            listener.onMoviesLoaded(movieList);
        }
    }
}