package com.thinkgood.customalertdialog;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by zaidnoordeen on 10/17/2017.
 */

public class CFPushButton extends AppCompatButton {

    public CFPushButton(Context context) {
        this(context, null, 0);
    }

    public CFPushButton(Context context, AttributeSet attrs) {
        this(context,attrs, 0);
    }

    public CFPushButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initButton(context, attrs, defStyleAttr);
    }

    void initButton(Context context, AttributeSet attributeSet, int defStyleAttr) {
        // Set centered text alignment
        setGravity(Gravity.CENTER);
        ViewUtil.addBounceEffect(this);

        setTypeface(getTypeface(), Typeface.BOLD);
    }
}
