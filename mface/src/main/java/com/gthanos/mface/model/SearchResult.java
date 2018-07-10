package com.gthanos.mface.model;


public class SearchResult {
    private long id;
    private String name;
    private float score;
    private String avatar;

    public long getId() {
        return id;
    }

    public SearchResult setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SearchResult setName(String name) {
        this.name = name;
        return this;
    }

    public float getScore() {
        return score;
    }

    public SearchResult setScore(float score) {
        this.score = score;
        return this;
    }


    public String getAvatar() {
        return avatar;
    }

    public SearchResult setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
