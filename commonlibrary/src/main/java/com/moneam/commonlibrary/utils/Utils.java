package com.moneam.commonlibrary.utils;

import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 5/10/2017.
 */

public final class Utils {
    public static int parseInt(String value) {
        try {
            double aDouble = Double.parseDouble(value);
            return (int) aDouble;
        } catch (NumberFormatException e) {
            Timber.e(e, "parseInt: ");
            return 0;
        } catch (Exception e) {
            Timber.e(e, "parseInt: ");
            return 0;
        }
    }
}
