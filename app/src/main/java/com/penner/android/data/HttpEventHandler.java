package com.penner.android.data;

/**
 * Created by PennerYu on 15/10/13.
 */
public abstract class HttpEventHandler<T> {

    public abstract void HttpSucessHandler(T result);

    public abstract void HttpFailHandler(Object error);
}
