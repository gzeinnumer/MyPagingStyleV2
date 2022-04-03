package com.gzeinnumer.mypagingstylev2.base;

public interface BaseCallBackAdapter<T> {
    void onClick(int type, int position, T data);
}