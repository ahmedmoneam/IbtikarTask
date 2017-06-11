package com.moneam.commonlibrary.utils.events;

/**
 * Created by Ahmed Abdelmoneam on 1/4/2017.
 */

public class BaseEvent<T> {
    private String key;
    private T object;

    public BaseEvent(String key, T object) {
        this.key = key;
        this.object = object;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
