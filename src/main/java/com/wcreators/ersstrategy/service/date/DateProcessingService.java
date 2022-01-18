package com.wcreators.ersstrategy.service.date;

public interface DateProcessingService<T> {
    T now();

    String dateToString(T date);

    int getMinutes(T date);
}
