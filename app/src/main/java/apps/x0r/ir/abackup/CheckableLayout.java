package apps.x0r.ir.abackup;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Oplus on 19/11/2015.
 */
public class CheckableLayout extends LinearLayout implements Checkable {
    private boolean mChecked;
    public CheckableLayout(Context context) {
        super(context);
    }

    public CheckableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("deprecation")
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundDrawable(checked ? getResources().getDrawable(
                R.drawable.blue) : null);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void toggle() {
        setChecked(!mChecked);
    }
}
