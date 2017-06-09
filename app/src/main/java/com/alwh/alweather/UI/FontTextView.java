package com.alwh.alweather.UI;

import android.widget.TextView;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.alwh.alweather.R;

public class FontTextView extends TextView {
    public static Typeface FONT_NAME;


    public FontTextView(Context context) {
        super(context);
        if(FONT_NAME == null) FONT_NAME = Typeface.createFromAsset(context.getAssets(),getResources().getString(R.string.font));
        this.setTypeface(FONT_NAME);
    }
    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(FONT_NAME == null) FONT_NAME = Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.font));
        this.setTypeface(FONT_NAME);
    }
    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(FONT_NAME == null) FONT_NAME = Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.font));
        this.setTypeface(FONT_NAME);
    }
}
