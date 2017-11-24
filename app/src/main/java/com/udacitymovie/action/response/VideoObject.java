package com.udacitymovie.action.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.udacitymovie.action.model.VideoModel;

import java.util.List;

/**
 * Created by hanyuezi on 17/11/23.
 */
@JsonObject
public class VideoObject {
    @JsonField
    private List<VideoModel> results;

    public List<VideoModel> getResults() {
        return results;
    }

    public void setResults(List<VideoModel> results) {
        this.results = results;
    }
}
