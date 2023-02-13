package com.fedirgithubtest.data.model.cache;

public class InfoMessage {
    public enum InfoType {
        NUMBER_ENTRIES,
        FAVORITE_ADD,
        FAVORITE_REMOVE,
        OTHER
    }

    public InfoMessage(InfoType infoType) {
        this.extra = null;
        this.infoType = infoType;
    }

    public InfoMessage(String extra, InfoType infoType) {
        this.extra = extra;
        this.infoType = infoType;
    }

    private String extra;
    private InfoType infoType;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public InfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(InfoType infoType) {
        this.infoType = infoType;
    }
}
