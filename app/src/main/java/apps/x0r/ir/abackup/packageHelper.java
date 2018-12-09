package apps.x0r.ir.abackup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Oplus on 16/11/2015.
 */
public class packageHelper {
    public static ResolveInfo ObjectToResolveInfo(Object object , Context c){
        try{
            ResolveInfo info = (ResolveInfo) object;
            return info;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    public static List GetInstalledPackages(Context c){
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = c.getPackageManager().queryIntentActivities( mainIntent, 0);
        return pkgAppsList;
    }
    public static boolean Copy(ResolveInfo info , File destination , Context context){
        File source =new File( info.activityInfo.applicationInfo.publicSourceDir);
        try{
            destination.mkdirs();
            destination = new File(destination.getPath() + "/" + info.loadLabel(context.getPackageManager()).toString() + ".apk");
            destination.createNewFile();
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(destination);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
        }catch (FileNotFoundException ex){
            Log.d("ABackup",ex.getMessage());
            return false;
        }catch (IOException ex){
            Log.d("ABackup",ex.getMessage());
            return false;
        }
        return true;
    }
    public static gItem InfoToData(ResolveInfo info , Context context){
        try {
            PackageManager pm = context.getPackageManager();
            gItem item = new gItem();
            item.image = info.activityInfo.loadIcon(pm);
            item.caption = info.loadLabel(pm).toString();
            item.info = info;
            return item;
        }catch (Exception e){
            Log.d("ABackup", "InfoToData() called with: " + "info = [" + info + "], context = [" + context + "] , e=[" +e.getMessage() + "]");
            return null;
        }
    }
    public static String getPackageInfo(ResolveInfo info){
        return info.resolvePackageName;
    }
    public static Uri Info2Uri(ResolveInfo info){
        File f = new File( info.activityInfo.applicationInfo.publicSourceDir);
        return Uri.fromFile(f);
    }

}
