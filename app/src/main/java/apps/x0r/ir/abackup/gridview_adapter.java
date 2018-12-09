package apps.x0r.ir.abackup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Oplus on 16/11/2015.
 */
public class gridview_adapter extends BaseAdapter {
    List<gItem> _items ;
    Context _context;
    LayoutInflater inflater ;
    public gridview_adapter(List<gItem> items , Context c){
        _items = items;
        _context = c;
    }
    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int position) {
        return _items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater =(LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View v = inflater.inflate(R.layout.gridview_child,null);
        TextView text = ((TextView) v.findViewById(R.id.textView));
        ImageView image = (ImageView) v.findViewById(R.id.imageView);

        if(_items.get(position).caption != null )
            text.setText(_items.get(position).caption);
        if(_items.get(position).image != null )
            image.setImageDrawable(_items.get(position).image);
        return v;
    }
}
