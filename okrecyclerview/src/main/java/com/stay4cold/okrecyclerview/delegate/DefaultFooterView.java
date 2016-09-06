package com.stay4cold.okrecyclerview.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.stay4cold.okrecyclerview.OkRecyclerView.OnLoadMoreListener;
import com.stay4cold.okrecyclerview.R;
import com.stay4cold.okrecyclerview.holder.FooterView;
import com.stay4cold.okrecyclerview.holder.OnFooterListener;
import com.stay4cold.okrecyclerview.state.FooterState;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class DefaultFooterView implements FooterView {

    private FooterState mState = FooterState.Normal;

    private View mLoadingView;
    private View mErrorView;
    private View mEndView;

    private OnLoadMoreListener mListener;

    private OnClickListener mFailListener;

    private OnFooterListener mFooterListener;

    public DefaultFooterView() {
        mFailListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFooterListener != null) {
                    mFooterListener.onFooterClickListener(mState);
                }
            }
        };
    }

    @Override
    public FooterState getState() {
        return mState;
    }

    @Override
    public void setState(FooterState state) {
        if (mState == state) {
            return;
        }
        mState = state;

        switch (state) {
            case Normal:
                showStateView(null);
                break;
            case Loading:
                showStateView(mLoadingView);
                if (mListener != null) {
                    mListener.onLoadMore();
                }
                break;
            case Error:
                showStateView(mErrorView);
                mErrorView.setOnClickListener(mFailListener);
                break;
            case TheEnd:
                showStateView(mEndView);
                mEndView.setOnClickListener(mFailListener);
                break;
            default:
                break;
        }
    }

    @Override
    public void setOnFooterListener(OnFooterListener listener) {
        this.mFooterListener = listener;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.footer_container, parent, false);
        mLoadingView = view.findViewById(R.id.more);
        mErrorView = view.findViewById(R.id.error);
        mEndView = view.findViewById(R.id.end);

        return view;
    }

    @Override
    public void onBindView(View view) {
        if (getState() == FooterState.Normal) {
            setState(FooterState.Loading);
        }
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    private void showStateView(View view) {
        mErrorView.setVisibility(mErrorView == view ? View.VISIBLE : View.INVISIBLE);
        mEndView.setVisibility(mEndView == view ? View.VISIBLE : View.INVISIBLE);
        mLoadingView.setVisibility(mLoadingView == view ? View.VISIBLE : View.INVISIBLE);
    }
}
