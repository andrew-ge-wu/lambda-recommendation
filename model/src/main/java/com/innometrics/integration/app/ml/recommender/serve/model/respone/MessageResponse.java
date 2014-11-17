package com.innometrics.integration.app.ml.recommender.serve.model.respone;

/**
 * @author andrew, Innometrics
 */
public class MessageResponse {
    private final int status;
    private final Object message;


    public MessageResponse(int status, Object message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public Object getMessage() {
        return message;
    }
}
