package com.uny.crysdip.ui.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by rahardyan on 15/10/15.
 */
public class ExpandedListView extends ListView {

    private android.view.ViewGroup.LayoutParams params;
    private int old_count = 0;

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int indexLast = getChildCount()-1;
        if (getCount() != old_count) {
            old_count = getCount();
            params = getLayoutParams();
            if(getChildCount()>=40){
                params.height = getCount() * getChildAt(indexLast).getHeight() + (getChildAt(indexLast).getHeight());
            }else if(getChildCount()>=50){
                params.height = getCount() * getChildAt(indexLast).getHeight() + (getChildAt(indexLast).getHeight()*2);
            }else if(getChildCount()>=70){
                params.height = getCount() * getChildAt(indexLast).getHeight() + (getChildAt(indexLast).getHeight()*3);
            } else if(getChildCount()<40){
                params.height = getCount() * getChildAt(indexLast).getHeight() + (getChildAt(indexLast).getHeight()/3);
            }

            setLayoutParams(params);

        }

        super.onDraw(canvas);
    }

}
