package com.fedirgithubtest.presentation.main.repositories;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fedirgithubtest.R;
import com.fedirgithubtest.data.model.cache.ApiErrorType;
import com.fedirgithubtest.data.model.cache.GHRepoDetails;
import com.fedirgithubtest.data.model.cache.SearchCache;
import com.fedirgithubtest.data.model.cache.SearchPeriodType;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.databinding.FragmentRepositoriesBinding;
import com.fedirgithubtest.presentation.base.BaseFragment;
import com.fedirgithubtest.presentation.base.PaginationScrollListener;
import com.fedirgithubtest.presentation.main.SharedViewModel;
import com.fedirgithubtest.presentation.main.adapter.ReposAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RepositoriesFragment extends BaseFragment<RepositoriesViewModel> {

    private FragmentRepositoriesBinding binding;

    private SharedViewModel sharedVM;

    private ReposAdapter rvAdapter;

    private boolean isNextLoading = false;

    private final Observer favDataObserver = (Observer<List<GHRepo>>) repos -> {
        onFavDataLoaded(repos);
    };

    private final Observer dataObserver = (Observer<List<GHRepo>>) repos -> {
        onDataLoaded(repos);
    };

    private final Observer searchCacheInitialObserver = (Observer<SearchCache>) cache -> {
        onConfigLoaded(cache);
    };

    @Inject
    public RepositoriesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RepositoriesViewModel.class);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentRepositoriesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        viewModel.searchCacheInitial.observe(getViewLifecycleOwner(), searchCacheInitialObserver);
        sharedVM.getFavoritesLD().observe(getViewLifecycleOwner(), favDataObserver);
        sharedVM.getFavoriteGHRepos();
        viewModel.loadCachedConfig();
    }

    private void initUI() {
        ArrayAdapter spAdapter = new ArrayAdapter(
                getContext(), android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.search_period_type_array));
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerChangePeriod.setAdapter(spAdapter);
        binding.spinnerChangePeriod.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        viewModel.changePeriod(SearchPeriodType.values()[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        initAdapters();

        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.performSearch(binding.etSearch.getText().toString().trim());
                return true;
            } else {
                return false;
            }
        });

        binding.etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.performSearch(binding.etSearch.getText().toString().trim());
                return true;
            } else {
                return false;
            }
        });
    }

    private void initAdapters() {
        rvAdapter = new ReposAdapter(new ReposAdapter.OnItemInteractionListener() {
            @Override
            public void onFavoriteClicked(boolean isFavorite, GHRepo repo) {
                sharedVM.favoriteClicked(isFavorite, repo);
            }

            @Override
            public void onItemClicked(boolean isFavorite, GHRepo repo) {
                sharedVM.setRepoDetailsData(new GHRepoDetails(repo, isFavorite));
                NavHostFragment.findNavController(RepositoriesFragment.this)
                        .navigate(R.id.navigate_to_navigation_details, null);


            }
        });
        binding.rvContainer.rvRepos.setAdapter(rvAdapter);

        binding.rvContainer.rvRepos.addOnScrollListener(
                new PaginationScrollListener(
                        (LinearLayoutManager) binding.rvContainer.rvRepos.getLayoutManager()) {
                    @Override
                    protected void loadMoreItems() {
                        isNextLoading = true;
                        viewModel.loadMore();
                    }

                    public boolean isLastPage() {
                        return viewModel.pageCurrent >= viewModel.totalPages;
                    }

                    public boolean isLoading() {
                        return isNextLoading;
                    }
                });
    }

    @Override
    protected void onError(ApiErrorType errorType) {
        super.onError(errorType);
        isNextLoading = false;
    }

    private void onDataLoaded(List<GHRepo> repos) {
        isNextLoading = false;
        if (viewModel.pageCurrent > 0) {
            rvAdapter.addData(repos);
            if (repos.size() > 0) {
                binding.rvContainer.tvEmptyList.setVisibility(View.GONE);
                binding.rvContainer.rvRepos.setVisibility(View.VISIBLE);
            } else {
                binding.rvContainer.tvEmptyList.setVisibility(View.VISIBLE);
                binding.rvContainer.rvRepos.setVisibility(View.GONE);
            }
        } else rvAdapter.setDataList(repos);
    }

    private void onFavDataLoaded(List<GHRepo> repos) {
        if (rvAdapter != null) {
            rvAdapter.setFavDataList(repos);
        }
    }

    private void onConfigLoaded(SearchCache cache) {
        viewModel.searchCacheInitial.removeObserver(searchCacheInitialObserver);
        binding.etSearch.setText(cache.getQuery());
        binding.spinnerChangePeriod.setSelection(cache.getPeriod().ordinal());
        viewModel.data.observe(getViewLifecycleOwner(), dataObserver);
    }

    @Override
    public void onDestroyView() {
        viewModel.data.removeObserver(dataObserver);
        sharedVM.getFavoritesLD().removeObserver(favDataObserver);
        super.onDestroyView();
        binding = null;
    }

}