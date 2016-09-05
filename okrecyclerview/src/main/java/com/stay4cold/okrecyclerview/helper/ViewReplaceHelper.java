package com.stay4cold.okrecyclerview.helper;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    2016-09-05
 * Description: 动态替换布局view，主要用于页面加载状态的替换
 */
public class ViewReplaceHelper {
    private static final String TAG = ViewReplaceHelper.class.getSimpleName();

    //  需要进行替换的原始targetView
    private View mTargetView;

    private ViewGroup mParentView;
    private ViewGroup.LayoutParams mTargetParams;

    private int mTargetViewIndex;

    public ViewReplaceHelper(@NonNull View targetView) {
        if (targetView == null) {
            throw new IllegalArgumentException(TAG + " -> target view is null!");
        }
        this.mTargetView = targetView;
    }

    /**
     * 恢复到原始状态
     */
    public void restore() {
        showLayout(mTargetView, null);
    }

    /**
     * 将targetView替换为当前的view
     */
    public void showLayout(View view, View.OnClickListener listener) {
        if (mParentView == null) {
            init();
        }

        if (mParentView.getChildAt(mTargetViewIndex) != view) {
            mParentView.removeViewAt(mTargetViewIndex);
            mParentView.addView(view, mTargetViewIndex, mTargetParams);
            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void showLayout(@LayoutRes int layoutId, View.OnClickListener listener) {
        showLayout(inflate(layoutId), listener);
    }

    public View inflate(int layoutId) {
        return LayoutInflater.from(mTargetView.getContext()).inflate(layoutId, mParentView, false);
    }

    private void init() {
        mTargetParams = mTargetView.getLayoutParams();
        if (mTargetView.getParent() != null) {
            mParentView = (ViewGroup) mTargetView.getParent();
        } else {
            mParentView = (ViewGroup) mTargetView.getRootView().findViewById(android.R.id.content);
        }

        int count = mParentView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (mTargetView == mParentView.getChildAt(i)) {
                mTargetViewIndex = i;
                break;
            }
        }
    }
}
