package com.moneam.commonlibrary.utils;

import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/11/2017.
 */

public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        // Log the message to Crashlytics, so we can see it in crash reports
        /*Crashlytics.log(priority, tag, message);

        // Log the exception in Crashlytics if we have one.
        if (t != null) {
            Crashlytics.logException(t);
        }

        // If this is an error or a warning, log it as a exception so we see it in Crashlytics.
        if (priority > Log.WARN) {
            Crashlytics.logException(new Throwable(message));
        }*/

        /*// Track INFO level logs as custom Answers events.
        if (priority == Log.INFO) {
            Answers.getInstance().logCustom(new CustomEvent(message));
        }*/
    }
}
