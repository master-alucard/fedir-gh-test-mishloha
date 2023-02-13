package com.fedirgithubtest.data.repository;

import androidx.lifecycle.LiveData;

import com.fedirgithubtest.data.model.cache.FavoritesCache;
import com.fedirgithubtest.data.model.cache.SearchCache;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.data.model.response.GHReposResponse;
import com.fedirgithubtest.data.network.RestConst;
import com.fedirgithubtest.data.network.service.GithubService;
import com.fedirgithubtest.data.pref.PrefManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class GHRepoRepositoryImpl implements GHRepoRepository {

    GithubService ghService;

    PrefManager localStorage;

    @Inject
    public GHRepoRepositoryImpl(PrefManager localStorage, GithubService ghService) {
        this.ghService = ghService;
        this.localStorage = localStorage;
    }

    // keep the list of all results received
    private List<GHRepo> inMemoryDataCache = new ArrayList<GHRepo>();

    @Override
    public Call<GHReposResponse> getGHRepositories(String query, int page) {
        return ghService.getGHRepositories(query,
                RestConst.DATA_SORT_DEFAULT,
                RestConst.DATA_SORT_ORDER_DEFAULT,
                RestConst.DATA_COUNT_LIMIT,
                page);
    }

    @Override
    public List<GHRepo> getFavoriteGHRepositories() {
        return new ArrayList(localStorage.getFavoriteGHRepos().getData().values());
    }

    @Override
    public void addFavoriteGHRepository(GHRepo data) {
        FavoritesCache favRepos = localStorage.getFavoriteGHRepos();
        favRepos.getData().put(data.getId(), data);
        localStorage.saveFavoriteGHRepos(favRepos);
    }

    @Override
    public void removeFavoriteGHRepository(GHRepo data) {
        FavoritesCache favRepos = localStorage.getFavoriteGHRepos();
        favRepos.getData().remove(data.getId());
        localStorage.saveFavoriteGHRepos(favRepos);
    }

    @Override
    public void saveCachedConfig(SearchCache searchCache) {
        localStorage.saveCachedConfig(searchCache);
    }

    @Override
    public SearchCache getCachedConfig() {
        return localStorage.getCachedConfig();
    }
}
