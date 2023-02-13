package com.fedirgithubtest.data.model.cache;

import com.fedirgithubtest.data.model.entity.GHRepo;

public class GHRepoDetails {

    private GHRepo repo;

    private boolean favorite;

    public GHRepoDetails(GHRepo repo, boolean favorite) {
        this.repo = repo;
        this.favorite = favorite;
    }

    public GHRepo getRepo() {
        return repo;
    }

    public void setRepo(GHRepo repo) {
        this.repo = repo;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;


    }

}