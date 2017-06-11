package com.moneam.commonlibrary.mvp.model.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.moneam.commonlibrary.utils.Preconditions;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Ahmed Abdelmoneam on 12/4/2016.
 */

@ApplicationScope
public class SharedPreferencesUtils {

    private SharedPreferences pref;
    private Gson gson;
    private RxSharedPreferences rxSharedPreferences;

    @Inject
    public SharedPreferencesUtils(Context context, Gson gson) {
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
        this.gson = gson;
        rxSharedPreferences = RxSharedPreferences.create(pref);
    }

    public SharedPreferences getPref() {
        return pref;
    }

    // Primitive

    public void putInt(String key, int value) {
        pref.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int def) {
        return pref.getInt(key, def);
    }

    public void putLong(String key, long value) {
        pref.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long def) {
        return pref.getLong(key, def);
    }

    public void putFloat(String key, float value) {
        pref.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float def) {
        return pref.getFloat(key, def);
    }

    public void putBoolean(String key, boolean value) {
        pref.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return pref.getBoolean(key, def);
    }

    public void putString(String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    public String getString(String key, String def) {
        return pref.getString(key, def);
    }

    public Set<String> getStringSet(String key, HashSet<String> def) {
        return pref.getStringSet(key, def);
    }

    private void putStringSet(String key, HashSet<String> value) {
        pref.edit().putStringSet(key, value).apply();
    }

    // Date

    public void putDate(String key, Date date) {
        pref.edit().putLong(key, date.getTime()).apply();
    }

    public Date getDate(String key) {
        return new Date(pref.getLong(key, 0));
    }

    // Gson

    public <T> void putObject(String key, T t) {
        pref.edit().putString(key, gson.toJson(t)).apply();
    }

    public <T> T getObject(String key, TypeToken<T> typeToken) {
        String objectString = pref.getString(key, null);
        if (objectString != null) {
            return gson.fromJson(objectString, typeToken.getType());
        }
        return null;
    }

    public void clearData() {
        pref.edit().clear().apply();
    }

    public boolean isLoggedUser() {
        return Preconditions.checkisNotNullOrEmpty(getUserName());
    }

    public String getUserName() {
        return getString(PreferencesContract.USER_NAME, "");
    }

    public void putUserId(long userId) {
        putLong(PreferencesContract.USER_ID, userId);
    }

    public void putUserName(String userName) {
        putString(PreferencesContract.USER_NAME, userName);
    }

    public void putUserAuthToken(String token) {
        putObject(PreferencesContract.USER_TOKEN, token);
    }

    public void putUserAuthSecret(String secret) {
        putObject(PreferencesContract.USER_SECRET, secret);
    }

    public static final class PreferencesContract {
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String USER_TOKEN = "user_token";
        public static final String USER_SECRET = "user_secret";
    }
}
