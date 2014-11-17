package com.innometrics.integration.app.ml.recommender.serve.model.request;

import javax.validation.constraints.NotNull;

/**
 * Created by andrew on 2014-11-15.
 */
public class TrainingData {
    private String namespace;
    private String user;
    private String item;
    private float preference;
    private long timestamp;

    public TrainingData() {
    }

    public TrainingData(@NotNull String namespace, @NotNull String user, @NotNull String item, float preference, long timestamp) {
        this.namespace = namespace;
        this.user = user;
        this.item = item;
        this.preference = preference;
        this.timestamp = timestamp;
    }

    @NotNull
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @NotNull
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NotNull
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
        if (timestamp > 0) {
            return timestamp;
        } else {
            return System.currentTimeMillis();
        }
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
