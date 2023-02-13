package com.fedirgithubtest.common.utils;

import com.fedirgithubtest.data.model.cache.SearchPeriodType;

import org.joda.time.DateTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String IN_QUALIFIER = "in%3Aname%2Cdescription";
//    private static final String IN_QUALIFIER = "%3A>in:name,description";

    public static String formatPeriodQuery(SearchPeriodType periodType) {
        return formatPeriodSearchQuery(null, periodType);
    }

    public static String formatPeriodSearchQuery(String search, SearchPeriodType periodType) {

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String period;
        switch (periodType) {
            case LAST_DAY:
                period = dateFormat.format(new DateTime(new Date()).minusDays(1).toDate());
                break;
            case LAST_WEEK:
                period = dateFormat.format(new DateTime(new Date()).minusWeeks(1).toDate());
                break;
            case LAST_MONTH:
            default:
                period = dateFormat.format(new DateTime(new Date()).minusMonths(1).toDate());

        }
        try {
            if (search != null && !search.isEmpty()) {
                return "created%3A%3E" + period + "+" + search + IN_QUALIFIER;
//                return new URI("created%3A%3E" + period + "+" + search + IN_QUALIFIER).getPath();
            } else {
                return new URI("created%3A%3E" + period).getPath();

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }


}
