package com.example.bipl.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

/**
 * Created by fahad on 5/23/2017.
 */

public class FontHelper {
    public static float fontGenerator(Activity activity,float percent){
           /*Formula for FontSize
        *
        * px(percent)=Required px/ScreenWidth
        *
        * */
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getSize(size);
        }
        int screenWidth = size.x;
        Log.e("ScreenWidth>>>>>>>>>>", String.valueOf(screenWidth));
        Log.e("textSize>>>>>>>>>", "textSize is:  " + (screenWidth*percent)/100);
        return ((screenWidth*percent)/100);
    }
}
