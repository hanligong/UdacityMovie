package com.udacitymovie.action.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by hanyuezi on 17/11/22.
 */
@JsonObject
public class VideoModel{
    @JsonField
    private String id;
    @JsonField
    private String iso_639_1;
    @JsonField
    private String iso_3166_1;
    @JsonField
    private String key;
    @JsonField
    private String name;
    @JsonField
    private String site;
    @JsonField
    private int size;
    @JsonField
    private String type;

//    "id":"58f2bf699251413d95006445",
//            "iso_639_1":"en",
//            "iso_3166_1":"US",
//            "key":"FnCdOQsX5kc",
//            "name":"Official Teaser Trailer",
//            "site":"YouTube",
//            "size":1080,
//            "type":"Teaser"


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
