package com.fedirgithubtest.data.model.response;

import com.fedirgithubtest.data.model.entity.GHRepo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GHReposResponse {
    @SerializedName("total_count")
    private int total;
    @SerializedName("items")
    private List<GHRepo> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GHRepo> getItems() {
        return items;
    }

    public void setItems(List<GHRepo> items) {
        this.items = items;
    }
}
