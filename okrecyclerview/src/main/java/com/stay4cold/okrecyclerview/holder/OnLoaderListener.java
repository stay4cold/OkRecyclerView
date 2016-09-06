package com.stay4cold.okrecyclerview.holder;

import com.stay4cold.okrecyclerview.state.LoaderState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    2016-09-06
 * Description:
 */
public interface OnLoaderListener {
    /**
     * LoaderState为Empty或者Error时的回调
     *
     * @param state 当前Loader的state
     */
    void onLoaderClick(LoaderState state);
}
