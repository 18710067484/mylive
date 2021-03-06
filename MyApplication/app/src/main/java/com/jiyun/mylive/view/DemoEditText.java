package com.jiyun.mylive.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import com.jiyun.mylive.R;


/**
 * Created by xkazerzhang on 2017/5/24.
 */
public class DemoEditText extends android.support.v7.widget.AppCompatEditText {
    public DemoEditText(Context context) {
        super(context);
        init();
    }

    public DemoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 定制Demo中的编辑框
    private void init(){
        getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
    }
}
