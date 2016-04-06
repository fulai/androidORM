package com.fulai.myapplication.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fulai.myapplication.R;

/**
 * Created by fulai on 2016/3/30.
 */
public class CustomLinearLayout2 extends LinearLayout {
    private String text = "";

    public CustomLinearLayout2(Context context) {
        this(context, null);
    }

    public CustomLinearLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        int resouceId = -1;
        TextView tv = new TextView(context);
        EditText et = new EditText(context);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.View2, 0, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.View2_Text:
                    resouceId = typedArray.getResourceId(index, 0);
                    text = resouceId > 0 ? typedArray.getText(resouceId).toString() : typedArray.getString(index);
                case R.styleable.View2_Oriental:
                    int index1 = typedArray.getIndex(index);
                    int hv = index1 == 1 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL;
                    this.setOrientation(hv);
            }
        }


        tv.setText(text);
        addView(tv);
        addView(et, new LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        typedArray.recycle();
    }
}
