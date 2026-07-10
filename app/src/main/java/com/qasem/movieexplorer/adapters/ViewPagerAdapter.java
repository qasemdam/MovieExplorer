package com.qasem.movieexplorer.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qasem.movieexplorer.fragments.DetailsFragment;
import com.qasem.movieexplorer.fragments.MovieFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    // Fragments
    private MovieFragment movieFragment = new MovieFragment();
    private DetailsFragment detailsFragment = new DetailsFragment();
    // Constructor
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return movieFragment;
            case 1:
                return detailsFragment;
            default:
                return movieFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    // Return Details Fragment
    public DetailsFragment getDetailsFragment() {
        return detailsFragment;
    }

    // Return Movie Fragment
    public MovieFragment getMovieFragment() {
        return movieFragment;
    }
}