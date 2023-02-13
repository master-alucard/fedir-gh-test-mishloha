package com.fedirgithubtest.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fedirgithubtest.R;
import com.fedirgithubtest.databinding.FragmentMainBinding;
import com.fedirgithubtest.presentation.main.adapter.MainPagerAdapter;
import com.fedirgithubtest.presentation.main.favorites.FavoritesFragment;
import com.fedirgithubtest.presentation.main.repositories.RepositoriesFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Inject
    RepositoriesFragment repositoriesFragment;

    @Inject
    FavoritesFragment favoritesFragment;

    @Inject
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this,
                repositoriesFragment, favoritesFragment);
        binding.pager.setAdapter(pagerAdapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.main_tab_repos)));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.main_tab_fav)));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}