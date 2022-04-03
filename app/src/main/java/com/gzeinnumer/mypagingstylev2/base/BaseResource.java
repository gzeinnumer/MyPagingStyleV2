package com.gzeinnumer.mypagingstylev2.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;

public class BaseResource<T> {

    @NonNull
    final public BaseResourceStatus status;
    @Nullable
    final public T data;
    @Nullable
    final public String title;
    @Nullable
    final public String message;
    @Nullable
    final public int total;

    public BaseResource(@NonNull BaseResourceStatus status, @Nullable T data, @Nullable String title, @Nullable String message, int total) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.title = title;
        this.total = total;
    }

    public static <T> BaseResource<T> success(@NonNull String title, @NonNull String msg, @Nullable T data, int total) {
        return new BaseResource<>(BaseResourceStatus.STATUS_1_SUCCESS, data, title, msg, total);
    }

    public static <T> BaseResource<T> error(@NonNull String title, @NonNull String msg) {
        return new BaseResource<>(BaseResourceStatus.STATUS_2_ERROR, null, title, msg, 0);
    }

    public static <T> BaseResource<T> empty(@NonNull String title, @NonNull String msg) {
        return new BaseResource<>(BaseResourceStatus.STATUS_3_EMPTY, null, title, msg, 0);
    }

    public static <T> BaseResource<T> info(@NonNull String title, @NonNull String msg) {
        return new BaseResource<>(BaseResourceStatus.STATUS_4_INFO, null, title, msg, 0);
    }

    public static <T> BaseResource<T> logout() {
        return new BaseResource<>(BaseResourceStatus.STATUS_5_LOGOUT, null, null, null, 0);
    }

    public static <T> BaseResource<T> loading() {
        return new BaseResource<>(BaseResourceStatus.STATUS_6_LOADING, null, null, null, 0);
    }

    public enum BaseResourceStatus {
        STATUS_1_SUCCESS,
        STATUS_2_ERROR,
        STATUS_3_EMPTY,
        STATUS_4_INFO,
        STATUS_5_LOGOUT,
        STATUS_6_LOADING
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, BaseResource.class);
    }
}
