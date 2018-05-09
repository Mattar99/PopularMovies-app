package com.example.mattar.popularmovies_app.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Mattar on 5/10/2018.
 */

public class Misc {

    public static boolean isNetworkAvailable(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean state = false ;
        if(connectivityManager!=null){
            state = connectivityManager.getActiveNetworkInfo().isConnected();
        }

        return  state;

    }
}
