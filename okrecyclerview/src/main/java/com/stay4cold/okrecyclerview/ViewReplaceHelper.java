package com.stay4cold.okrecyclerview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class ViewReplaceHelper {

    private static final String TAG = ViewReplaceHelper.class.getSimpleName();

    private View mTargetView;
    private int mTargetViewIndex;

    private ViewGroup mParent;

    private ViewGroup.LayoutParams mParams;

    public ViewReplaceHelper(@NonNull View targetView) {
        if (targetView == null) {
            throw new IllegalArgumentException(TAG + " -> target view is null!");
        }
        this.mTargetView = targetView;
    }

    public void replaceView(View view) {
        if (mParent == null) {
            init();
        }

        if (mParent.getChildAt(mTargetViewIndex) != view) {
            mParent.removeViewAt(mTargetViewIndex);
            mParent.addView(view, mTargetViewIndex, mParams);
        }
    }

    public void restore() {
        replaceView(mTargetView);
    }

    private void init() {
        mParams = mTargetView.getLayoutParams();
        if (mTargetView.getParent() != null) {
            mParent = (ViewGroup) mTargetView.getParent();
        } else {
            mParent = (ViewGroup) mTargetView.getRootView().findViewById(android.R.id.content);
        }

        int count = mParent.getChildCount();
        for (int i = 0; i < count; i++) {
            if (mTargetView == mParent.getChildAt(i)) {
                mTargetViewIndex = i;
                break;
            }
        }
    }
}
