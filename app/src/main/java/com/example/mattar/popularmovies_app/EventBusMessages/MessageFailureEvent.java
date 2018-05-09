package com.example.mattar.popularmovies_app.EventBusMessages;

/**
 * Created by Mattar on 5/9/2018.
 */

public class MessageFailureEvent <T> {

    public final T message;

    public MessageFailureEvent(T message) {
        this.message = message;
    }

}
