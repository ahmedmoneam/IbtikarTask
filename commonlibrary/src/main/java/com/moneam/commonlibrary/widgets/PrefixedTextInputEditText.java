package com.moneam.commonlibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

/**
 * Created by ahmon on 11/17/2016.
 */

public class PrefixedTextInputEditText extends TextInputEditText {
    private String mPrefix; // can be hardcoded for demo purposes
    private Rect mPrefixRect = new Rect(); // actual prefix size

    public PrefixedTextInputEditText(Context context) {
        super(context);
    }

    public PrefixedTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrefixedTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mPrefix != null && !mPrefix.isEmpty()) {
            getPaint().getTextBounds(mPrefix, 0, mPrefix.length(), mPrefixRect);
            mPrefixRect.right += getPaint().measureText("  "); // add some offset
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPrefix != null && !mPrefix.isEmpty()) {
            canvas.drawText(mPrefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
        }
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + mPrefixRect.width();
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String mPrefix) {
        this.mPrefix = mPrefix;
        invalidate();
    }
}
