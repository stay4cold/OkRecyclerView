package com.stay4cold.okrecyclerview.holder;

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
public interface LoaderView {
    void setOnLoaderListener(LoadingState state, OnClickListener listener);

    void setState(LoadingState state);

    LoadingState getState();
}
