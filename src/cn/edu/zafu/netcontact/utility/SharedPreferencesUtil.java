package cn.edu.zafu.netcontact.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {
	public static final String FLAG="NetContacts";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static boolean hasBind(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String flag = sp.getString(FLAG, "");
        if (TRUE.equalsIgnoreCase(flag)) {
            return true;
        }
        return false;
    }

    public static void setBind(Context context, boolean flag) {
        String flagStr = FALSE;
        if (flag) {
            flagStr = TRUE;
        }
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(FLAG, flagStr);
        editor.commit();
    }
    public static int getX(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String X = sp.getString("X", "");
        if ("".equalsIgnoreCase(X)) {
            return 0;
        }
        return Integer.parseInt(X);
    }
    public static int getY(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String Y = sp.getString("Y", "");
        if ("".equalsIgnoreCase(Y)) {
            return 0;
        }
        return Integer.parseInt(Y);
    }
    public static void setXY(Context context,String X,String Y) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString("X", X);
        editor.putString("Y", Y);
        editor.commit();
    }

}
