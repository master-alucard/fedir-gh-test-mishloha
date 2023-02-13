package com.fedirgithubtest.presentation.main.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.fedirgithubtest.R;
import com.fedirgithubtest.data.model.cache.GHRepoDetails;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.databinding.FragmentFavoritesBinding;
import com.fedirgithubtest.presentation.base.BaseFragment;
import com.fedirgithubtest.presentation.main.SharedViewModel;
import com.fedirgithubtest.presentation.main.adapter.ReposAdapter;
import com.fedirgithubtest.presentation.main.repositories.RepositoriesFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoritesFragment extends BaseFragment<SharedViewModel> {

    private FragmentFavoritesBinding binding;
    private ReposAdapter rvAdapter;

    private final Observer dataObserver = (Observer<List<GHRepo>>) repos -> {
        onDataLoaded(repos);
    };

    @Inject
    public FavoritesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapters();
        viewModel.getFavoritesLD().observe(getViewLifecycleOwner(), dataObserver);
        viewModel.getFavoriteGHRepos();
    }

    private void initAdapters() {
        rvAdapter = new ReposAdapter(new ReposAdapter.OnItemInteractionListener() {
            @Override
            public void onFavoriteClicked(boolean isFavorite, GHRepo repo) {
                viewModel.favoriteClicked(isFavorite, repo);
            }

            @Override
            public void onItemClicked(boolean isFavorite, GHRepo repo) {
                viewModel.setRepoDetailsData(new GHRepoDetails(repo, isFavorite));
                NavHostFragment.findNavController(FavoritesFragment.this)
                        .navigate(R.id.navigate_to_navigation_details, null);
            }
        }, true);
        binding.rvContainer.rvRepos.setAdapter(rvAdapter);
    }

    private void onDataLoaded(List<GHRepo> repos) {
        rvAdapter.setDataList(repos);
        if (repos.size() > 0) {
            binding.rvContainer.tvEmptyList.setVisibility(View.GONE);
            binding.rvContainer.rvRepos.setVisibility(View.VISIBLE);
        } else {
            binding.rvContainer.tvEmptyList.setVisibility(View.VISIBLE);
            binding.rvContainer.rvRepos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        viewModel.getFavoritesLD().removeObserver(dataObserver);
        super.onDestroyView();
        binding = null;
    }

}