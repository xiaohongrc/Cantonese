package hong.cantonese.utils;

import android.util.Log;

/**
 * Created by Xiaohong on 2018/6/15.
 * desc:
 */

public class LogUtil {

    final static boolean willLog = true;

    public static void i(Object object, String message) {
        if (willLog) {
            Log.i(object.getClass().getSimpleName(), message);
        }
    }

    public static void v(Object object, String message) {
        if (willLog) {
            Log.i(object.getClass().getSimpleName(), message);
        }
    }

    public static void d(Object object, String message) {
        if (willLog) {
            Log.i(object.getClass().getSimpleName(), message);
        }
    }

    public static void e(Object object, String message) {
        if (willLog) {
            Log.i(object.getClass().getSimpleName(), message);
        }
    }

}
