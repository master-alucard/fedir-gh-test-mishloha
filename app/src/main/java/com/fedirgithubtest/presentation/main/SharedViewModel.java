package com.fedirgithubtest.presentation.main;

import androidx.lifecycle.MutableLiveData;

import com.fedirgithubtest.data.model.cache.GHRepoDetails;
import com.fedirgithubtest.data.model.cache.InfoMessage;
import com.fedirgithubtest.data.model.entity.GHRepo;
import com.fedirgithubtest.data.repository.GHRepoRepository;
import com.fedirgithubtest.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SharedViewModel extends BaseViewModel {

    @Inject
    GHRepoRepository repoRepository;

    @Inject
    SharedViewModel() {
    }

    private GHRepoDetails repoDetailsData = null;


    private MutableLiveData<List<GHRepo>> favorites = new MutableLiveData<>();

    public MutableLiveData<List<GHRepo>> getFavoritesLD() {
        return favorites;
    }

    public void getFavoriteGHRepos() {
        List<GHRepo> res = repoRepository.getFavoriteGHRepositories();
        favorites.postValue(res);
    }

    public void favoriteClicked(boolean isFavorite, GHRepo dataFav) {
        if (isFavorite) {
            repoRepository.addFavoriteGHRepository(dataFav);
            info.postValue(new InfoMessage(InfoMessage.InfoType.FAVORITE_ADD));
        } else {
            repoRepository.removeFavoriteGHRepository(dataFav);
            info.postValue(new InfoMessage(InfoMessage.InfoType.FAVORITE_REMOVE));
        }
        getFavoriteGHRepos();
    }

    public GHRepoDetails getRepoDetailsData() {
        return repoDetailsData;
    }

    public void setRepoDetailsData(GHRepoDetails repoDetailsDataIn) {
        if(repoDetailsData != null) {
            favoriteClicked(repoDetailsDataIn.isFavorite(), repoDetailsDataIn.getRepo());
        }
        this.repoDetailsData = repoDetailsDataIn;
    }
}