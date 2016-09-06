package com.stay4cold.okrecyclerview.holder;

import com.stay4cold.okrecyclerview.state.LoaderState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public interface LoaderView {
    void setOnLoaderListener(OnLoaderListener listener);

    void setState(LoaderState state);

    LoaderState getState();
}
