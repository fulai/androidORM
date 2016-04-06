package com.fulai.myapplication.customView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fulai.myapplication.R;

/**
 * Created by fulai on 2016/3/30.
 */
public class CustomLinearLayout extends LinearLayout {
    private String text = "";

    public CustomLinearLayout(Context context) {
        this(context, null);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        int resouceId = -1;
        TextView tv = new TextView(context);
        EditText et = new EditText(context);
        this.setOrientation(LinearLayout.VERTICAL);
        resouceId = attrs.getAttributeResourceValue(null, "Test", 0);
        if (resouceId > 0) {
            text = context.getResources().getText(resouceId).toString();
            context.getResources().getString(R.string.app_name);
        } else {
            text = "";
        }
        tv.setText(text);
        addView(tv);
        addView(et, new LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
