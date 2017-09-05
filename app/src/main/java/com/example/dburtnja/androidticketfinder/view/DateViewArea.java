package com.example.dburtnja.androidticketfinder.view;

import android.content.Context;

/**
 * Created by denys on 9/5/17.
 */

public interface DateViewArea {
    void    setOnClickListener(Context context);
    long    getTimeStart();
    long    getTimeEnd();
}
