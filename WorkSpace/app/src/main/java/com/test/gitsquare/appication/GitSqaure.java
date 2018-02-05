package com.test.gitsquare.appication;

import android.app.Application;
import android.content.Context;

/**
 * Created by deep on 28-Apr-17.
 */

public class GitSqaure extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();


    }

    /*
    * Provides app level context.
    * */
    public static Context getAppContext() {
        return appContext;
    }


}
