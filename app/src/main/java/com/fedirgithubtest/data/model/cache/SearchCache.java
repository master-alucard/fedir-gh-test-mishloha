package com.fedirgithubtest.data.model.cache;

import android.text.TextUtils;

public class SearchCache {
    private String query;
    private SearchPeriodType period;

    public SearchCache(){
        query = "";
        period = SearchPeriodType.LAST_MONTH;
    }

    public boolean hasQuery() {
        return !TextUtils.isEmpty(query);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchPeriodType getPeriod() {
        return period;
    }

    public void setPeriod(SearchPeriodType period) {
        this.period = period;
    }
}
