package com.stay4cold.okrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class DefaultFooterDelegate implements FooterDelegate {

    private MoreState mState = MoreState.Normal;

    private Context mContext;

    private ViewGroup mContainer;

    private View mLoadingView, mErrorView, mEndView;

    private OkRecyclerView.OnLoadMoreListener mListener;

    public DefaultFooterDelegate(Context context) {
        mContext = context;
        initContainer();
    }

    private void initContainer() {
        if (mContainer != null) {
            return;
        }
        mContainer = new FrameLayout(mContext);
        mContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContainer.setMinimumHeight(1);
    }

    @Override
    public void setMoreContainer(ViewGroup viewGroup) {
        mContainer = viewGroup;
    }

    @Override
    public ViewGroup getMoreContainer() {
        return mContainer;
    }

    @Override
    public void setMoreLoadingView(View moreLoadingView) {
        mLoadingView = moreLoadingView;
    }

    @Override
    public void setMoreErrorView(View moreErrorView) {
        mErrorView = moreErrorView;
    }

    @Override
    public void setMoreEndView(View moreEndView) {
        mEndView = moreEndView;
    }

    @Override
    public View getMoreStateView(MoreState state) {
        switch (state) {
            case Normal:
                return mContainer;
            case TheEnd:
                return mEndView;
            case Loading:
                return mLoadingView;
            case Error:
                return mErrorView;
        }
        return null;
    }

    @Override
    public MoreState getMoreState() {
        return mState;
    }

    @Override
    public void setMoreState(MoreState state) {
        if (mState == state) {
            return;
        }
        mState = state;

        switch (mState) {
            case Normal:
                showNormal();
                break;
            case Error:
                showError();
                break;
            case TheEnd:
                showEnd();
                break;
            case Loading:
                showLoading();
                break;
            default:
                showNormal();
                break;
        }
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return mContainer;
    }

    @Override
    public void onBindView(View view) {
        if (getMoreState() == MoreState.Normal) {
            setMoreState(MoreState.Loading);
        }
    }

    @Override
    public void setOnLoadMoreListener(OkRecyclerView.OnLoadMoreListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    private void showNormal() {
        initContainer();

        for (int index = 0; index < mContainer.getChildCount(); index++) {
            mContainer.getChildAt(index).setVisibility(View.INVISIBLE);
        }
    }

    private void showError() {
        initContainer();
        if (mErrorView == null) {
            mErrorView = LayoutInflater.from(mContext).inflate(R.layout.footer_error, null);
        }

        if (mErrorView.getParent() == null) {
            mContainer.addView(mErrorView);
        }

        for (int index = 0; index < mContainer.getChildCount(); index++) {
            if (mContainer.getChildAt(index) == mErrorView) {
                mErrorView.setVisibility(View.VISIBLE);
            } else {
                mContainer.getChildAt(index).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showEnd() {
        initContainer();
        if (mEndView == null) {
            mEndView = LayoutInflater.from(mContext).inflate(R.layout.footer_end, null);
        }

        if (mEndView.getParent() == null) {
            mContainer.addView(mEndView);
        }

        for (int index = 0; index < mContainer.getChildCount(); index++) {
            if (mContainer.getChildAt(index) == mEndView) {
                mEndView.setVisibility(View.VISIBLE);
            } else {
                mContainer.getChildAt(index).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showLoading() {
        initContainer();

        if (mLoadingView == null) {
            mLoadingView = LayoutInflater.from(mContext)
                    .inflate(R.layout.footer_more, null);
        }

        if (mLoadingView.getParent() == null) {
            mContainer.addView(mLoadingView);
        }


        for (int index = 0; index < mContainer.getChildCount(); index++) {
            if (mContainer.getChildAt(index) == mLoadingView) {
                mLoadingView.setVisibility(View.VISIBLE);
            } else {
                mContainer.getChildAt(index).setVisibility(View.INVISIBLE);
            }
        }
        if (mListener != null) {
            mListener.onLoadMore();
        }
    }
}
