package com.stay4cold.okrecyclerview.delegate;

import android.support.annotation.LayoutRes;
import android.view.View;

import android.view.View.OnClickListener;
import com.stay4cold.okrecyclerview.state.LoadingState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public interface LoaderDelegate {
    void setLoaderView(LoadingState state, @LayoutRes int viewId);

    void addLoaderListener(LoadingState state, OnClickListener listener);

    void setLoaderState(LoadingState state);

    LoadingState getLoaderState();
}
