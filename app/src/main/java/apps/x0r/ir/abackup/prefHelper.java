package apps.x0r.ir.abackup;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Oplus on 20/11/2015.
 */
public class prefHelper {
    public static  void SetSettings(Context c,String key , String value){
        SharedPreferences pref = c.getSharedPreferences("settings",Context.MODE_PRIVATE);
        pref.edit().putString(key,value).apply();
    }
    public static String GetSettings(Context c , String key , String DefaultValue){
        SharedPreferences pref = c.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return pref.getString(key,DefaultValue) ;
    }
}
