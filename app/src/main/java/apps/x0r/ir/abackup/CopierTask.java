package apps.x0r.ir.abackup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by Oplus on 20/11/2015.
 */
public class CopierTask extends AsyncTask<Object,Integer,Object> {
    Context _context ;
    List<ResolveInfo> _items;
    ProgressDialog dlg;
    String path;
    int success = 0 ;
    public CopierTask(Context c , List<ResolveInfo> items){
        _context = c;
        _items = items;
        path = prefHelper.GetSettings(_context,"path", Environment.getExternalStorageDirectory().toString()+"/AppBackup");
    }

    @Override
    protected void onPreExecute() {
        dlg = new ProgressDialog(_context);
        dlg.setMax(_items.size());
        dlg.setCancelable(false);
        dlg.setMessage("Copying files to " + path);
        dlg.setTitle("Copying Files.");
        dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dlg.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        dlg.dismiss();
        Toast.makeText(_context,String.valueOf(success) + " Item(s) were Copied.",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer[] values) {
        int i = values[0];
        dlg.setProgress(i);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        File dest = new File(path);
        dest.mkdirs();
        for (int i = 0 ; i < _items.size() ; i++){
            if(packageHelper.Copy(_items.get(i),dest,_context)){
                success++ ;
            }
            publishProgress(i);
        }
        return null ;
    }
}
