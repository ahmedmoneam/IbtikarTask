package com.moneam.commonlibrary.utils.events;

/**
 * Created by Ahmed Abdelmoneam on 1/4/2017.
 */

public class NetworkDisableEvent extends BaseEvent<String> {
    public NetworkDisableEvent(String key, String object) {
        super(key, object);
        setKey(EventsContract.NETWORK_DISABLED);
    }
}
