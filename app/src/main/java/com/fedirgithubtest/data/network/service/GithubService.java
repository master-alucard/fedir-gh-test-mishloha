package com.fedirgithubtest.data.network.service;

import com.fedirgithubtest.data.model.response.GHReposResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubService {

    @GET("/search/repositories")
    Call<GHReposResponse> getGHRepositories(
            @Query(value = "q", encoded = true) String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("per_page") int limit,
            @Query("page") int page);

}
