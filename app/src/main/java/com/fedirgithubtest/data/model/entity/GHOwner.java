package com.fedirgithubtest.data.model.entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class GHOwner {

    @SerializedName("id")
    private long id;

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof GHOwner))
            return false;
        else {
            GHOwner temp = (GHOwner) obj;
            return login.equals(temp.login);
        }
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    public GHOwner() {
    }

}
