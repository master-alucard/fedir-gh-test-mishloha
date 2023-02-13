package com.fedirgithubtest.presentation.main.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fedirgithubtest.R;
import com.fedirgithubtest.data.model.cache.GHRepoDetails;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.databinding.FragmentRepositoryDetailsBinding;
import com.fedirgithubtest.presentation.base.BaseFragment;
import com.fedirgithubtest.presentation.main.SharedViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RepositoryDetailsFragment extends BaseFragment<SharedViewModel> {


    private FragmentRepositoryDetailsBinding binding;

    private GHRepoDetails repoDetails = null;

    @Inject
    public RepositoryDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentRepositoryDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(viewModel.getRepoDetailsData());
    }

    private void initData(GHRepoDetails repoIn) {
        if (repoIn != null) {
            this.repoDetails = repoIn;
            GHRepo repo = repoDetails.getRepo();
            updateFavIcon(repoDetails.isFavorite());
            if (!TextUtils.isEmpty(repo.getOwner().getAvatarUrl())
                    && URLUtil.isValidUrl(repo.getOwner().getAvatarUrl())) {
                Glide.with(binding.getRoot().getContext())
                        .load(repo.getOwner().getAvatarUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_baseline_person_24)
                        .into(binding.ivAvatar);
            }
            binding.tvRepoName.setText(repo.getOwner().getLogin());
            binding.tvRepoDescription.setText(repo.getDescription());
            binding.tvRepoStars.setText(String.valueOf(repo.getStargazersCount()));
            binding.tvRepoForks.setText(String.valueOf(repo.getForksCount()));
            if (!TextUtils.isEmpty(repo.getLanguage())) {
                binding.tvLanguageValue.setText(repo.getLanguage());
                binding.tvLanguageValue.setVisibility(View.VISIBLE);
            } else {
                binding.tvLanguageValue.setVisibility(View.GONE);
            }
            binding.tvURL.setText(repo.getHtmlUrl());

            binding.ivBack.setOnClickListener(v -> {

                NavHostFragment.findNavController(RepositoryDetailsFragment.this).navigateUp();
            });

            binding.ivFav.setOnClickListener(v -> {
                repoDetails.setFavorite(!repoDetails.isFavorite());
                viewModel.setRepoDetailsData(repoDetails);
                updateFavIcon(repoDetails.isFavorite());
            });

            binding.tvURL.setOnClickListener(v -> {
                if (URLUtil.isValidUrl(repo.getHtmlUrl())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getHtmlUrl()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private void updateFavIcon(boolean favorite) {
        if (favorite) {
            Glide.with(binding.getRoot().getContext())
                    .load(R.drawable.ic_baseline_favorite_24)
                    .into(binding.ivFav);
        } else {
            Glide.with(binding.getRoot().getContext())
                    .load(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.ivFav);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}