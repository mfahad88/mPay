package com.example.bipl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fahad on 9/14/2017.
 */

public class Utility {
    public static String getStan() throws Exception {
        Date dt=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
        String stan=sdf.format(dt);
        System.out.println("Stan is "+stan);
        return stan;
    }

}
