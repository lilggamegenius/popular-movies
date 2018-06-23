package net.lilggamegenius.popularmovies.TMDB;


import com.fasterxml.jackson.annotation.JsonCreator;

import net.lilggamegenius.popularmovies.BuildConfig;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class ApiUrl {
    static final byte API_VERSION = 3;
    static final String API_URL = "https://api.themoviedb.org/" + API_VERSION;
    static final String api_key = BuildConfig.TMDBAPIKey;

    String apiPath;

    Map<String, String> map = new HashMap<>();

    public ApiUrl(String apiPath) {
        this.apiPath = apiPath;
    }

    public ApiUrl(String apiPath, short page) {
        this.apiPath = apiPath;
        map.put("page", Short.toString(page));
    }

    public ApiUrl(String apiPath, short page, String language, String region, Sort sort_by, boolean order_asc, boolean include_adult) {
        this.apiPath = apiPath;
        map.put("page", Short.toString(page));
        map.put("language", language);
        map.put("region", region);
        map.put("sort_by", sort_by.toString(order_asc));
        map.put("include_adult", Boolean.toString(include_adult));
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public int getPage() {
        String val = "page";
        if (!map.containsKey(val)) return 0;
        return Integer.parseInt(map.get(val));
    }

    public void setPage(int page) {
        map.put("page", Integer.toString(page));
    }

    public String getLanguage() {
        return map.get("language");
    }

    public void setLanguage(String language) {
        map.put("language", language);
    }

    public String getRegion() {
        return map.get("region");
    }

    public void setRegion(String region) {
        map.put("region", region);
    }

    public Sort getSort_by() {
        return Sort.forValue(map.get("sort_by"));
    }

    public void setSort_by(Sort sort_by) {
        map.put("sort_by", sort_by.toString());
    }

    public void setSort_by(Sort sort_by, boolean asc) {
        map.put("sort_by", sort_by.toString(asc));
    }

    public boolean isInclude_adult() {
        String val = "include_adult";
        return map.containsKey(val) && Boolean.getBoolean(map.get(val));
    }

    public void setInclude_adult(boolean include_adult) {
        map.put("include_adult", Boolean.toString(include_adult));
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(API_URL);
        builder.append(apiPath).append('?').append("api_key=").append(api_key).append('&');
        boolean seen = false;
        StringBuilder acc = null;
        for (Map.Entry<String, String> p : map.entrySet()) {
            String s = URLEncoder.encode(p.getKey()) + "=" + URLEncoder.encode(p.getValue());
            if (!seen) {
                seen = true;
                acc = new StringBuilder(s);
            } else {
                acc.append("&").append(s);
            }
        }
        if (acc != null) builder.append(acc);
        return builder.toString();
    }

    public enum Sort {
        popularity,
        release_date,
        revenue,
        primary_release_date,
        original_title,
        vote_average,
        vote_count;

        @JsonCreator
        public static Sort forValue(String value) {
            String[] parts = value.split(".");
            return Sort.valueOf(parts[0]);
        }

        @Override
        public String toString() {
            return toString(true);
        }

        public String toString(boolean order) {
            return super.toString() + (order ? ".asc" : ".dsc");
        }
    }
}
