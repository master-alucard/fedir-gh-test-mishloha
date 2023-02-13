package com.fedirgithubtest.data.repository;

import com.fedirgithubtest.data.model.cache.SearchCache;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.data.model.response.GHReposResponse;

import java.util.List;

import retrofit2.Call;

public interface GHRepoRepository {

    Call<GHReposResponse> getGHRepositories(String query, int page);

    List<GHRepo> getFavoriteGHRepositories();

    void addFavoriteGHRepository(GHRepo data);

    void removeFavoriteGHRepository(GHRepo data);

    void saveCachedConfig(SearchCache searchCache);

    SearchCache getCachedConfig();
}
