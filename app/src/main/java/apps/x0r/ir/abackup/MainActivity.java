package apps.x0r.ir.abackup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context baseContext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "Developer@8thbit.net" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "AppBackup");
                intent.putExtra(Intent.EXTRA_TEXT, "");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
        baseContext = this ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final List pkgAppsList = packageHelper.GetInstalledPackages(this);
        final List<gItem> items = new ArrayList<gItem>();
        for (Object object : pkgAppsList) {
            ResolveInfo info = packageHelper.ObjectToResolveInfo(object,this);
            if(info == null )
                continue;
            items.add(packageHelper.InfoToData(info, this));
        }
        gridview_adapter adapter = new gridview_adapter(items,this) ;
        final GridView grid = ((GridView) findViewById(R.id.gridView));
        grid.setAdapter(adapter);
        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        grid.setMultiChoiceModeListener(new MultiChoiceModeListener(this, grid, items));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                final CharSequence[] chooses = {"Save To SDCard", "Save Via ..."};
                AlertDialog.Builder builder = new AlertDialog.Builder(baseContext);
                builder.setTitle("Choose an action");
                builder.setItems(chooses, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Save(items,position);
                        } else if (item == 1) {
                            Send(items,position);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    private void Save(List<gItem> _items , int i){
        ArrayList<ResolveInfo> Infos = new ArrayList<>();
        Infos.add(_items.get(i).info);
        if(Infos.size() > 0) {
            CopierTask t = new CopierTask(this,Infos);
            t.execute();
        }
    }
    private void Send(List<gItem> _items , int i ){
        Uri uri = packageHelper.Info2Uri(_items.get(i).info);
        if(uri != null) {
            Intent sIntent = new Intent();
            sIntent.setAction(Intent.ACTION_SEND);
            sIntent.setType("*/*");
            sIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(sIntent);
        }
    }
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
