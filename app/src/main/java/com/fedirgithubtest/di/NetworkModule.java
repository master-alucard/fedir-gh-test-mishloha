package com.fedirgithubtest.di;

import androidx.viewbinding.BuildConfig;

import com.fedirgithubtest.data.network.RestConst;
import com.fedirgithubtest.data.network.service.GithubService;
import com.google.gson.GsonBuilder;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public final class NetworkModule {

    @Provides
    public Converter.Factory provideGsonConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder()
                .setDateFormat(RestConst.DATE_SERVER_FORMAT) // to convert String into Date
                .disableHtmlEscaping().create());
    }

    @Provides
    public Interceptor provideProfilerInterceptor() {
            return new OkHttpProfilerInterceptor();
    }

    @Provides
    public OkHttpClient provideOkhttpClient(Interceptor profilerInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(RestConst.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(RestConst.WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(profilerInterceptor);
        }
        return builder.build();
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient,
                                    Converter.Factory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(com.fedirgithubtest.BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @Singleton
    public GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

}
