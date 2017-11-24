package com.udacitymovie.action.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.udacitymovie.action.model.MoviesModel;

import java.util.List;

/**
 * Created by hanyuezi on 17/10/15.
 */
@JsonObject
public class MovieObject extends BaseObject{

    @JsonField
    private List<MoviesModel> results;

    public List<MoviesModel> getResults() {
        return results;
    }

    public void setResults(List<MoviesModel> results) {
        this.results = results;
    }
}
