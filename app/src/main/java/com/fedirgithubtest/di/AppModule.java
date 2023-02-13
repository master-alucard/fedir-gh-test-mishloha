package com.fedirgithubtest.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module(includes = {
        NetworkModule.class,
        RepositoryModule.class
})

public abstract class AppModule {

    @Provides
    static Context context(final @ApplicationContext Context context) {
        return context;
    }
}
