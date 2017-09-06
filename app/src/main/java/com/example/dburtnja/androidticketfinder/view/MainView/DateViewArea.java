package com.example.dburtnja.androidticketfinder.view.MainView;

import android.content.Context;

/**
 * Created by denys on 9/5/17.
 */

public interface DateViewArea {
    void    setOnClickListeners(Context context);
    void    notifyValuesHasChanged(DateView changed);
    long    getTimeStart();
    long    getTimeEnd();
}
