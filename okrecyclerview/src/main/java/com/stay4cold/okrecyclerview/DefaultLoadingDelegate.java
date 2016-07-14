package com.stay4cold.okrecyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class DefaultLoadingDelegate implements LoadingDelegate {

    private static final String TAG = DefaultLoadingDelegate.class.getSimpleName();

    private ViewGroup mEmptyView;
    private ViewGroup mLoadingView;
    private ViewGroup mErrorView;
    private ViewReplaceHelper mHelper;
    private Context mContext;
    private View mTargetView;
    private LoadingState mLoadingState;

    public DefaultLoadingDelegate(View targetView) {
        mTargetView = targetView;
        mContext = targetView.getContext();

        mHelper = new ViewReplaceHelper(targetView);

        ViewGroup parent = (ViewGroup) targetView.getParent();

        mEmptyView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.sre_empty_container, null);
        mLoadingView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.sre_loading_container, null);
        mErrorView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.sre_error_container, null);
    }

    public void setLoadingView(LoadingState state, View view) {
        switch (state) {
            case Normal:
                break;
            case Empty:
                setEmptyView(view);
                break;
            case Error:
                setErrorView(view);
                break;
            case Loading:
                setProgressView(view);
                break;
            default:
                break;
        }
    }

    public void setLoadingView(LoadingState state, @LayoutRes int viewId) {
        switch (state) {
            case Normal:
                break;
            case Empty:
                setEmptyView(viewId);
                break;
            case Error:
                setErrorView(viewId);
                break;
            case Loading:
                setProgressView(viewId);
                break;
            default:
                break;
        }
    }

    public View getLoadingView(LoadingState state) {
        switch (state) {
            case Normal:
                return mTargetView;
            case Empty:
                return mEmptyView.getChildAt(0);
            case Error:
                return mErrorView.getChildAt(0);
            case Loading:
                return mLoadingView.getChildAt(0);
            default:
                throw new IllegalArgumentException(TAG + " Loading State is illegal");
        }
    }

    public void setEmptyView(View emptyView) {
        mEmptyView.removeAllViews();
        mEmptyView.addView(emptyView);
    }

    public void setEmptyView(@LayoutRes int emptyViewId) {
        mEmptyView.removeAllViews();
        LayoutInflater.from(mContext).inflate(emptyViewId, mEmptyView);
    }

    public void setProgressView(View progressView) {
        mLoadingView.removeAllViews();
        mLoadingView.addView(progressView);
    }

    public void setProgressView(@LayoutRes int progressViewId) {
        mLoadingView.removeAllViews();
        LayoutInflater.from(mContext).inflate(progressViewId, mLoadingView);
    }

    public void setErrorView(View errorView) {
        mErrorView.removeAllViews();
        mErrorView.addView(errorView);
    }

    public void setErrorView(@LayoutRes int errorViewId) {
        mErrorView.removeAllViews();
        LayoutInflater.from(mContext).inflate(errorViewId, mErrorView);
    }

    public void setLoadingState(LoadingState state) {
        if (mLoadingState == state) {
            return;
        }
        mLoadingState = state;

        switch (state) {
            case Normal:
                mHelper.restore();
                break;
            case Loading:
                mHelper.replaceView(mLoadingView);
                break;
            case Error:
                mHelper.replaceView(mErrorView);
                break;
            case Empty:
                mHelper.replaceView(mEmptyView);
                break;
            default:
                throw new IllegalArgumentException(TAG + " setLoadingState is illegal and state is -> " + state);
        }
    }

    public LoadingState getLoadingState() {
        return mLoadingState;
    }
}
