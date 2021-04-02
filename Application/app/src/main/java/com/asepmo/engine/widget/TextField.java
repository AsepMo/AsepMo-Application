package com.asepmo.engine.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.asepmo.R;

public class TextField extends TextView {

    public TextField(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.attr.textFieldStyle);
    }

}
