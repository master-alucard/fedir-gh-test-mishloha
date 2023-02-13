package com.fedirgithubtest.data.network;

public abstract class RestConst {

    public static final int READ_TIMEOUT = 120;  //sec
    public static final int WRITE_TIMEOUT = 60; //sec

    public static final int DATA_COUNT_LIMIT = 100;
    public static final String DATA_SORT_DEFAULT = "stars";
    public static final String DATA_SORT_ORDER_DEFAULT = "desc";

    public static final String DATE_SERVER_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
