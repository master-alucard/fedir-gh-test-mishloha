package com.fedirgithubtest.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.fedirgithubtest.data.model.cache.FavoritesCache;
import com.fedirgithubtest.data.model.cache.SearchCache;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefManagerImpl implements PrefManager {

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private static final String PREF_NAME = "cache_pref";
    private static final String PREF_GH_REPO = "pref_gh_repo";
    private static final String PREF_SEARCH = "pref_search";

    @Inject
    public PrefManagerImpl(final Context context, final Gson gson) {
        this.gson = gson;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void clearData() {
        sharedPreferences.edit().clear().apply();
    }

    @Override
    public void saveFavoriteGHRepos(FavoritesCache repos) {
        sharedPreferences.edit().putString(PREF_GH_REPO, gson.toJson(repos)).apply();
    }

    @Override
    public FavoritesCache getFavoriteGHRepos() {
        String stringGson = sharedPreferences.getString(PREF_GH_REPO, null);
        if (stringGson != null) return gson.fromJson(stringGson, FavoritesCache.class);
        else return new FavoritesCache();
    }

    @Override
    public SearchCache getCachedConfig() {
        String stringGson = sharedPreferences.getString(PREF_SEARCH, null);
        if (stringGson != null) return gson.fromJson(stringGson, SearchCache.class);
        else return new SearchCache();
    }

    @Override
    public void saveCachedConfig(SearchCache searchCache) {
        sharedPreferences.edit().putString(PREF_SEARCH, gson.toJson(searchCache)).apply();
    }


}
