package com.fedirgithubtest.presentation.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fedirgithubtest.presentation.main.favorites.FavoritesFragment;
import com.fedirgithubtest.presentation.main.repositories.RepositoriesFragment;

import javax.inject.Inject;

public class MainPagerAdapter extends FragmentStateAdapter {

    RepositoriesFragment repositoriesFragment = new RepositoriesFragment();
    FavoritesFragment favoritesFragment = new FavoritesFragment();

    public MainPagerAdapter(@NonNull Fragment fragment,
                            @NonNull RepositoriesFragment repositoriesFragment,
                            @NonNull FavoritesFragment favoritesFragment) {
        super(fragment);
//        this.repositoriesFragment = repositoriesFragment;
//        this.favoritesFragment = favoritesFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return favoritesFragment;
        } else {
            return repositoriesFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
