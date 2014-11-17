package com.innometrics.integration.app.ml.recommender.speed.api;

import com.innometrics.integration.app.ml.recommender.serve.model.request.TrainingData;

/**
 * @author andrew, Innometrics
 */
public interface Speed {
    public void train(TrainingData data);
}
