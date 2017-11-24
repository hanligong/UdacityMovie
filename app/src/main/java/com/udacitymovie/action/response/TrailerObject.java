package com.udacitymovie.action.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.udacitymovie.action.model.MoviesModel;
import com.udacitymovie.action.model.TrailerModel;

import java.util.List;

/**
 * Created by hanyuezi on 17/11/23.
 */
@JsonObject
public class TrailerObject extends BaseObject {
    @JsonField
    private List<TrailerModel> results;

    public List<TrailerModel> getResults() {
        return results;
    }

    public void setResults(List<TrailerModel> results) {
        this.results = results;
    }
}
