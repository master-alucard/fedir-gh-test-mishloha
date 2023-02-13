package com.fedirgithubtest.data.pref;

import com.fedirgithubtest.data.model.cache.FavoritesCache;
import com.fedirgithubtest.data.model.cache.SearchCache;
import com.fedirgithubtest.data.model.entity.GHRepo;

import java.util.List;
import java.util.Map;

public interface PrefManager {

    void clearData();

    void saveFavoriteGHRepos(FavoritesCache favorites);

    FavoritesCache getFavoriteGHRepos();

    SearchCache getCachedConfig();

    void saveCachedConfig(SearchCache searchCache);
}
