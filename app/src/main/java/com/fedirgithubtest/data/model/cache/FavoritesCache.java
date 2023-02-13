package com.fedirgithubtest.data.model.cache;

import com.fedirgithubtest.data.model.entity.GHRepo;

import java.util.HashMap;

public class FavoritesCache {
    private HashMap<Long, GHRepo> data = new HashMap<>();

    public HashMap<Long, GHRepo> getData() {
        return data;
    }

    public void setData(HashMap<Long, GHRepo> data) {
        this.data = data;
    }
}
