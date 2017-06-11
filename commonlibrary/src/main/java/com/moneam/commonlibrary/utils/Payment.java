package com.moneam.commonlibrary.utils;/*
package com.moneam.gootaxi.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.moneam.gootaxi.R;

*/
/**
 * Created by Ahmed Abdelmoneam on 4/12/2017.
 *//*


public enum Payment {
    CASH(
            "cash",
            R.drawable.ic_cash_white,
            R.drawable.ic_cash_blue,
            R.string.label_payment_method_cash),
    SUBSCRIBER(
            "subscriber",
            R.drawable.ic_subscriber_white,
            R.drawable.ic_subscriber_blue,
            R.string.label_payment_method_subscriber),
    PAYPAL(
            "pay_pal",
            R.drawable.ic_paypal_logo_accent_color,
            R.drawable.ic_paypal_logo_primary_color,
            R.string.label_payment_method_paypal);

    private final String name;
    @DrawableRes
    private final int iconWithAccentColor;
    @DrawableRes
    private final int iconWithPrimaryColor;
    @StringRes
    private final int displayName;

    Payment(String name, @DrawableRes int iconWithAccentColor,
            @DrawableRes int iconWithPrimaryColor, @StringRes int displayName) {
        this.name = name;
        this.iconWithAccentColor = iconWithAccentColor;
        this.iconWithPrimaryColor = iconWithPrimaryColor;
        this.displayName = displayName;
    }

    @DrawableRes
    public int getIconWithAccentColor() {
        return iconWithAccentColor;
    }

    @DrawableRes
    public int getIconWithPrimaryColor() {
        return iconWithPrimaryColor;
    }

    @StringRes
    public int getDisplayName() {
        return displayName;
    }

    public Payment getPaymentByCode(@NonNull String paymentMethodCode) {
        if (Preconditions.checkisNotNullOrEmpty(paymentMethodCode)) return CASH;
        if (CASH.toString().equals(paymentMethodCode)) return CASH;
        if (SUBSCRIBER.toString().equals(paymentMethodCode)) return SUBSCRIBER;
        if (PAYPAL.toString().equals(paymentMethodCode)) return PAYPAL;
        return CASH;
    }

    @Override
    public String toString() {
        return name;
    }
}*/
