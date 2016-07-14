package com.stay4cold.okrecyclerview.delegate;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.stay4cold.okrecyclerview.state.LoadingState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public interface LoadingDelegate {
    void setLoadingView(LoadingState state, View view);

    void setLoadingView(LoadingState state, @LayoutRes int viewId);

    View getLoadingView(LoadingState state);

    void setEmptyView(View emptyView);

    void setEmptyView(@LayoutRes int emptyViewId);

    void setProgressView(View progressView);

    void setProgressView(@LayoutRes int progressViewId);

    void setErrorView(View errorView);

    void setErrorView(@LayoutRes int errorViewId);

    void setLoadingState(LoadingState state);

    LoadingState getLoadingState();
}
