package com.gzeinnumer.mypagingstylev2.base;

//File and Code Template -> BaseCallBackAdapter
public interface BaseCallBackAdapter<T> {
    void onClick(int type, int position, T data);
}