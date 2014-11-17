package com.innometrics.integration.app.ml.recommender.storage.api;

import com.innometrics.integration.app.ml.recommender.serve.model.request.TrainingData;

/**
 * @author andrew, Innometrics
 */
public interface Storage {
    public void saveTrainingData(TrainingData data);
}
