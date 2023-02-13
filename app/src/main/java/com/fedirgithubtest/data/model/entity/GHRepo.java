package com.fedirgithubtest.data.model.entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GHRepo {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("owner")
    private GHOwner owner;

    @SerializedName("language")
    private String language;

    @SerializedName("forks_count")
    private int forksCount;

    @SerializedName("stargazers_count")
    private int stargazersCount;

    @SerializedName("created_at")
    private Date createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public GHOwner getOwner() {
        return owner;
    }

    public void setOwner(GHOwner owner) {
        this.owner = owner;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof GHRepo))
            return false;
        else {
            GHRepo temp = (GHRepo) obj;
            return createdAt.equals(temp.createdAt);
        }
    }

    @Override
    public int hashCode() {
        return createdAt.hashCode();
    }

    public GHRepo() {
    }

}
