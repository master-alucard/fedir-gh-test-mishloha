package com.fedirgithubtest.presentation.main.repositories;

import androidx.lifecycle.MutableLiveData;

import com.fedirgithubtest.common.utils.DataUtils;
import com.fedirgithubtest.data.model.cache.ApiErrorType;
import com.fedirgithubtest.data.model.cache.InfoMessage;
import com.fedirgithubtest.data.model.cache.SearchCache;
import com.fedirgithubtest.data.model.cache.SearchPeriodType;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.data.model.response.GHReposResponse;
import com.fedirgithubtest.data.network.RestConst;
import com.fedirgithubtest.data.repository.GHRepoRepository;
import com.fedirgithubtest.presentation.base.BaseViewModel;

import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class RepositoriesViewModel extends BaseViewModel {

    @Inject
    GHRepoRepository repoRepository;

    @Inject
    RepositoriesViewModel() {
    }

    MutableLiveData<List<GHRepo>> data = new MutableLiveData<>();
    MutableLiveData<SearchCache> searchCacheInitial = new MutableLiveData<>();
    SearchCache searchCacheCurrent = null;
    int totalCount = 0;
    int totalPages = 0;
    int pageCurrent = 0;

    public void changePeriod(SearchPeriodType period) {
        if (period == searchCacheCurrent.getPeriod()) return;
        this.searchCacheCurrent.setPeriod(period);
        clearTmp();
        getGHRepos();
    }

    public void performSearch(String searchQuery) {
        this.searchCacheCurrent.setQuery(searchQuery);
        clearTmp();
        getGHRepos();
    }

    public void loadMore() {
        if (pageCurrent + 1 > totalPages) return;
        pageCurrent++;
        getGHRepos();
    }

    private void getGHRepos() {
        repoRepository.saveCachedConfig(searchCacheCurrent);
        repoRepository.getGHRepositories(
                        DataUtils.formatPeriodSearchQuery(
                                searchCacheCurrent.getQuery(),
                                searchCacheCurrent.getPeriod()),
                        pageCurrent)
                .enqueue(new Callback<GHReposResponse>() {
                    @Override
                    public void onResponse(Call<GHReposResponse> call, Response<GHReposResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (pageCurrent == 0) {
                                totalCount = response.body().getTotal();
                                info.postValue(new InfoMessage(String.valueOf(totalCount),
                                        InfoMessage.InfoType.NUMBER_ENTRIES));
                                totalPages = totalCount / RestConst.DATA_COUNT_LIMIT;
                            }
                            data.postValue(response.body().getItems());
                        } else {
                            totalPages = 0;
                            totalCount = 0;
                        }
                    }

                    @Override
                    public void onFailure(Call<GHReposResponse> call, Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            // No internet
                            error.postValue(ApiErrorType.INTERNET_CONNECTION);
                        } else {
                            // Request limit reached
                            error.postValue(ApiErrorType.REQUESTS_LIMIT);
                        }
                    }
                });
    }

    private void clearTmp() {
        pageCurrent = 0;
        totalCount = 0;
        totalPages = 0;
    }

    public void loadCachedConfig() {
        searchCacheCurrent = repoRepository.getCachedConfig();
        searchCacheInitial.postValue(searchCacheCurrent);
        getGHRepos();
    }
}