package com.stay4cold.okrecyclerview.holder;

import com.stay4cold.okrecyclerview.state.FooterState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    2016-09-06
 * Description:
 */
public interface OnFooterListener {
    /**
     * Footer显示Error或者TheEnd时的点击回调
     *
     * @param state
     */
    void onFooterClickListener(FooterState state);
}
