package com.stay4cold.okrecyclerview.delegate;

import android.view.View;
import android.view.ViewGroup;

import com.stay4cold.okrecyclerview.OkRecyclerView;
import com.stay4cold.okrecyclerview.state.MoreState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public interface FooterDelegate extends HolderDelegate {

    void setMoreContainer(ViewGroup viewGroup);

    ViewGroup getMoreContainer();

    void setMoreLoadingView(View moreLoadingView);

    void setMoreErrorView(View moreErrorView);

    void setMoreEndView(View moreEndView);

    View getMoreStateView(MoreState state);

    MoreState getMoreState();

    void setMoreState(MoreState state);

    void setOnLoadMoreListener(OkRecyclerView.OnLoadMoreListener listener);
}
