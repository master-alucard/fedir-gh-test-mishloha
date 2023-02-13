package com.fedirgithubtest.presentation.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fedirgithubtest.common.utils.DataUtils;
import com.fedirgithubtest.data.model.cache.ApiErrorType;
import com.fedirgithubtest.data.model.cache.InfoMessage;
import com.fedirgithubtest.data.model.cache.SearchCache;
import com.fedirgithubtest.data.model.cache.SearchPeriodType;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.data.model.response.GHReposResponse;
import com.fedirgithubtest.data.network.RestConst;
import com.fedirgithubtest.data.repository.GHRepoRepository;

import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseViewModel extends ViewModel {

    public MutableLiveData<ApiErrorType> error = new MutableLiveData<>();
    public MutableLiveData<InfoMessage> info = new MutableLiveData<>();

}