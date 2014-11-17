package com.innometrics.integration.app.ml.recommender.serve.resource;

import com.innometrics.integration.app.ml.recommender.serve.model.request.TrainingData;
import com.innometrics.integration.app.ml.recommender.serve.model.respone.MessageResponse;
import com.innometrics.integration.app.ml.recommender.speed.api.Speed;
import com.innometrics.integration.app.ml.recommender.storage.api.Storage;
import com.innometrics.integration.app.ml.recommender.utils.BeanValidationUtils;
import com.innometrics.integration.app.ml.recommender.utils.JacksonUtil;
import com.innometrics.integration.app.ml.recommender.utils.Singletons;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public Response train(String dataString) {
        if (StringUtils.isEmpty(dataString)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MessageResponse(Response.Status.BAD_REQUEST.getStatusCode(), "Body can not be empty")).build();
        } else {
            try {
                TrainingData data = JacksonUtil.getObjectMapper().readValue(dataString, TrainingData.class);
                Set<ConstraintViolation<Object>> result = BeanValidationUtils.validate(data);
                if (result.isEmpty()) {
                    executorService.execute(new TrainingRunner(data));
                    return Response.status(Response.Status.ACCEPTED)
                            .entity(new MessageResponse(Response.Status.OK.getStatusCode(), data)).build();
                } else {
                    List<String> messages = new ArrayList<>();
                    for (ConstraintViolation eachViolation : result) {
                        messages.add(eachViolation.getPropertyPath().toString() + " " + eachViolation.getMessage());
                    }
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new MessageResponse(Response.Status.BAD_REQUEST.getStatusCode(), messages)).build();

                }
            } catch (Throwable e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MessageResponse(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage())).build();
            }
        }
    }


    private class TrainingRunner implements Runnable {
        private final TrainingData data;
        private final Speed speedProvider;
        private final Storage storageProvider;

        private TrainingRunner(TrainingData data) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
            this.data = data;
            String className = this.getClass().getCanonicalName();
            this.speedProvider = Singletons.getObjectSingletonByConfig(className + ".speed.impl", Speed.class);
            this.storageProvider = Singletons.getObjectSingletonByConfig(className + ".storage.impl", Storage.class);
        }

        @Override
        public void run() {
            speedProvider.train(data);
            storageProvider.saveTrainingData(data);
        }
    }
}
