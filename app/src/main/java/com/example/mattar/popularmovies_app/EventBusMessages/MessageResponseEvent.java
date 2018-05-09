package com.example.mattar.popularmovies_app.EventBusMessages;

/**
 * Created by Mattar on 5/9/2018.
 */

public class MessageResponseEvent<T> {

    public final T message;

    public MessageResponseEvent(T message) {
        this.message = message;
    }
}
