package com.stay4cold.okrecyclerview.holder;

import android.view.View.OnClickListener;

import com.stay4cold.okrecyclerview.OkRecyclerView.OnLoadMoreListener;
import com.stay4cold.okrecyclerview.state.FooterState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public interface FooterView extends IBaseHolder {

    FooterState getState();

    void setState(FooterState state);

    void setOnLoadMoreListener(OnLoadMoreListener listener);

    void setOnStateListener(FooterState state, OnClickListener listener);
}
