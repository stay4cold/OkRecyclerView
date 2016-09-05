package com.stay4cold.okrecyclerview.delegate;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.View.OnClickListener;

import com.stay4cold.okrecyclerview.R;
import com.stay4cold.okrecyclerview.helper.ViewReplaceHelper;
import com.stay4cold.okrecyclerview.holder.LoaderView;
import com.stay4cold.okrecyclerview.state.LoadingState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class DefaultLoaderView implements LoaderView {

    private static final String TAG = DefaultLoaderView.class.getSimpleName();

    @LayoutRes
    private int mEmptyViewId;

    @LayoutRes
    private int mLoadingViewId;

    @LayoutRes
    private int mErrorViewId;

    private ViewReplaceHelper mHelper;

    private LoadingState mLoadingState = LoadingState.Normal;

    private OnClickListener mErrorListener;

    private OnClickListener mEmptyListener;

    public DefaultLoaderView(View targetView) {
        mHelper = new ViewReplaceHelper(targetView);

        mEmptyViewId = R.layout.sre_empty_container;
        mLoadingViewId = R.layout.sre_loading_container;
        mErrorViewId = R.layout.sre_error_container;
    }

    @Override
    public void setOnLoaderListener(LoadingState state, OnClickListener listener) {
        switch (state) {
            case Normal:
                break;
            case Empty:
                mEmptyListener = listener;
                break;
            case Error:
                mErrorListener = listener;
                break;
            case Loading:
                break;
            default:
                break;
        }
    }

    @Override
    public void setState(LoadingState state) {
        if (mLoadingState == state) {
            return;
        }
        mLoadingState = state;

        switch (state) {
            case Normal:
                mHelper.restore();
                break;
            case Loading:
                mHelper.showLayout(mLoadingViewId, null);
                break;
            case Error:
                mHelper.showLayout(mErrorViewId, mErrorListener);
                break;
            case Empty:
                mHelper.showLayout(mEmptyViewId, mEmptyListener);
                break;
            default:
                throw new IllegalArgumentException(TAG + " setState is illegal and state is -> " + state);
        }
    }

    @Override
    public LoadingState getState() {
        return mLoadingState;
    }
}
