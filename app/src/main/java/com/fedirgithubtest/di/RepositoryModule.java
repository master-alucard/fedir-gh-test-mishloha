package com.fedirgithubtest.di;

import android.content.Context;

import com.fedirgithubtest.data.network.service.GithubService;
import com.fedirgithubtest.data.pref.PrefManager;
import com.fedirgithubtest.data.pref.PrefManagerImpl;
import com.fedirgithubtest.data.repository.GHRepoRepository;
import com.fedirgithubtest.data.repository.GHRepoRepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class RepositoryModule {

    @Provides
    static Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    static PrefManager providePrefManager(Context context, Gson gson) {
        return new PrefManagerImpl(context, gson);
    }

    @Singleton
    @Provides
    static GHRepoRepository provideGHRepository(PrefManager localStorage, GithubService ghService) {
        return new GHRepoRepositoryImpl(localStorage, ghService);
    }
}
