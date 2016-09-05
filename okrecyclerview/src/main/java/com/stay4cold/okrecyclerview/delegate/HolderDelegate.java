package com.stay4cold.okrecyclerview.delegate;

import android.view.View;
import android.view.ViewGroup;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public interface HolderDelegate {
    View onCreateView(ViewGroup parent);

    void onBindView(View view);
}
