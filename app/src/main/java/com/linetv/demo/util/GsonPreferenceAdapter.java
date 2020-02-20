package com.linetv.demo.util;

import android.content.SharedPreferences;

import com.f2prateek.rx.preferences2.Preference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonPreferenceAdapter<T> implements Preference.Adapter<T> {
    private Class<T> clazz;
    private TypeToken<T> type;

    public GsonPreferenceAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public GsonPreferenceAdapter(TypeToken<T> type) {
        this.type = type;
    }

    @Override
    public T get(String key, SharedPreferences preferences) {
        String str = preferences.getString(key, null);
        if (isNullOrEmpty(str)) {
            return null;
        }

        if (this.clazz != null) {
            return new Gson().fromJson(str, this.clazz);
        }

        if (this.type != null) {
            return new Gson().fromJson(str, type.getType());
        }

        return null;
    }

    @Override
    public void set(String key, T value, SharedPreferences.Editor editor) {
        editor.putString(key, new Gson().toJson(value)).apply();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}