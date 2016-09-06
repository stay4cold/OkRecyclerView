package com.stay4cold.okrecyclerview.delegate;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.View.OnClickListener;
import com.stay4cold.okrecyclerview.R;
import com.stay4cold.okrecyclerview.helper.ViewReplaceHelper;
import com.stay4cold.okrecyclerview.holder.LoaderView;
import com.stay4cold.okrecyclerview.holder.OnLoaderListener;
import com.stay4cold.okrecyclerview.state.LoaderState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class DefaultLoaderView implements LoaderView {

    private static final String TAG = DefaultLoaderView.class.getSimpleName();

    @LayoutRes private int mEmptyViewId;

    @LayoutRes private int mLoadingViewId;

    @LayoutRes private int mErrorViewId;

    private ViewReplaceHelper mHelper;

    private LoaderState mLoadingState = LoaderState.Normal;

    private OnClickListener mListener;

    private OnLoaderListener mLoaderListener;

    public DefaultLoaderView(View targetView) {
        mHelper = new ViewReplaceHelper(targetView);

        mEmptyViewId = R.layout.sre_empty_container;
        mLoadingViewId = R.layout.sre_loading_container;
        mErrorViewId = R.layout.sre_error_container;

        mListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoaderListener != null) {
                    mLoaderListener.onLoaderClick(mLoadingState);
                }
            }
        };
    }

    @Override
    public void setOnLoaderListener(OnLoaderListener listener) {
        mLoaderListener = listener;
    }

    @Override
    public void setState(LoaderState state) {
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
                mHelper.showLayout(mErrorViewId, mListener);
                break;
            case Empty:
                mHelper.showLayout(mEmptyViewId, mListener);
                break;
            default:
                throw new IllegalArgumentException(
                    TAG + " setState is illegal and state is -> " + state);
        }
    }

    @Override
    public LoaderState getState() {
        return mLoadingState;
    }
}
