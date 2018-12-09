package apps.x0r.ir.abackup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Oplus on 19/11/2015.
 */
public class MultiChoiceModeListener implements GridView.MultiChoiceModeListener {
    GridView _grid ;
    List<gItem> _items;
    Context _context ;
    public MultiChoiceModeListener(Context c ,GridView gridView , List<gItem> items){
        _grid = gridView;
        _items = items;
        _context = c;
    }
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        int selectCount = _grid.getCheckedItemCount();
        switch (selectCount) {
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + selectCount + " items selected");
                break;
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle("Select Items");
        mode.setSubtitle("One item selected");
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.grid_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_btn :
                Save();
                return true;
            case R.id.send_btn :
                Send();
                return true;
            case R.id.CheckAll:
                SelectAll();
                return true;
        }
        return false;
    }
    void SelectAll(){
        for(int i = 0 ; i<_grid.getAdapter().getCount() ; i++){
            _grid.setItemChecked(i,! _grid.isItemChecked(i));
        }
    }
    private void Save(){
        SparseBooleanArray arr = _grid.getCheckedItemPositions();
        ArrayList<ResolveInfo> Infos = new ArrayList<>();
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.valueAt(i)) {
                    gItem j = _items.get(arr.keyAt(i));
                    Infos.add(j.info);
                }
            }
        }

        if(Infos.size() > 0) {
            CopierTask t = new CopierTask(_context,Infos);
            t.execute();
        }
    }
    private void Send(){
        SparseBooleanArray arr = _grid.getCheckedItemPositions();
        ArrayList<Uri> uris = new ArrayList<>();
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.valueAt(i)) {
                    gItem j = _items.get(arr.keyAt(i));
                    uris.add(packageHelper.Info2Uri(j.info));
                }
            }
        }

        if(uris.size() > 0) {
            Intent sIntent = new Intent();
            sIntent.setAction(android.content.Intent.ACTION_SEND_MULTIPLE);
            sIntent.setType("*/*");
            sIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            _context.startActivity(sIntent);
        }
    }
    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}