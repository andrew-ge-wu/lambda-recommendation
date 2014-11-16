package com.innometrics.intergration.app.ml.recommender.serve.model;

/**
 * Created by andrew on 2014-11-15.
 */
public class TrainingData {
    private String user;
    private String item;
    private float preference;
    private long timestamp;

    public TrainingData() {
    }

    public TrainingData(String user, String item, float preference, long timestamp) {
        this.user = user;
        this.item = item;
        this.preference = preference;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getPreference() {
        return preference;
    }

    public void setPreference(float preference) {
        this.preference = preference;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
