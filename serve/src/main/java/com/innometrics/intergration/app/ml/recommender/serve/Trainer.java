package com.innometrics.intergration.app.ml.recommender.serve;

import com.innometrics.intergration.app.ml.recommender.serve.model.TrainingData;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by andrew on 2014-11-15.
 */
@Path("/train")
public class Trainer {
    private final ExecutorService executorService;

    public Trainer() {
        executorService = Executors.newSingleThreadExecutor();
    }

    @POST
    public int train(TrainingData data) {
        executorService.execute(new TrainingRunner(data));
        return HttpURLConnection.HTTP_ACCEPTED;
    }


    private class TrainingRunner implements Runnable {
        private final TrainingData data;

        private TrainingRunner(TrainingData data) {
            this.data = data;
        }

        @Override
        public void run() {

        }
    }
}
