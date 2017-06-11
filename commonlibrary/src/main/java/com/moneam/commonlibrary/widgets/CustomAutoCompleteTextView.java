package com.moneam.commonlibrary.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Ahmed Abdelmoneam on 3/13/2017.
 */

public class CustomAutoCompleteTextView
        extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean enoughToFilter() {
//        return super.enoughToFilter();
        return true;
    }
}
