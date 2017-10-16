package com.udacitymovie.action.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by hanyuezi on 17/10/15.
 */
@JsonObject
public class HttpObject {
    @JsonField
    private int page;
    @JsonField
    private int total_results;
    @JsonField
    private int total_pages;
    @JsonField
    private List<MoviesModel> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<MoviesModel> getResults() {
        return results;
    }

    public void setResults(List<MoviesModel> results) {
        this.results = results;
    }
}
