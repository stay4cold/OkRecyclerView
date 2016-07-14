package com.stay4cold.okrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/12
 * Description:
 */
public class OkRecyclerView {

    private static final String TAG = OkRecyclerView.class.getSimpleName();

    private Context mContext;

    private RecyclerView mOriginalRv;

    private RecyclerView.Adapter mOriginalAdapter;

    private RvAdapterAgent mAdapterAgent;

    private MoreState mMoreState = MoreState.Normal;

    private LoadingDelegate mLoadDelegate;

    private FooterDelegate mFooterDelegate;

    public OkRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new IllegalArgumentException(TAG + " recyclerView can't be null");
        }
        mContext = recyclerView.getContext();

        this.mOriginalRv = recyclerView;

        mAdapterAgent = new RvAdapterAgent(mOriginalRv);

        mOriginalAdapter = mOriginalRv.getAdapter();

        mOriginalAdapter.registerAdapterDataObserver(new SDataObserver(mAdapterAgent));

        mOriginalRv.setAdapter(mAdapterAgent);

        mAdapterAgent.addFooter(getFooterDelegate());
    }

    public void setLoadDelegate(LoadingDelegate delegate) {
        mLoadDelegate = delegate;
    }

    public LoadingDelegate getLoadDelegate() {
        if (mLoadDelegate == null) {
            mLoadDelegate = new DefaultLoadingDelegate(mOriginalRv);
        }

        return mLoadDelegate;
    }

    public void setLoadState(LoadingState state) {
        getLoadDelegate().setLoadingState(state);
    }

    public LoadingState getLoadState() {
        return getLoadDelegate().getLoadingState();
    }

    public void setFooterDelegate(FooterDelegate footerDelegate) {
        mFooterDelegate = footerDelegate;
    }

    public FooterDelegate getFooterDelegate() {
        if (mFooterDelegate == null) {
            mFooterDelegate = new DefaultFooterDelegate(mContext);
        }

        return mFooterDelegate;
    }

    public MoreState getMoreState() {
        return getFooterDelegate().getMoreState();
    }

    public void setMoreState(MoreState state) {
        getFooterDelegate().setMoreState(state);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener == null) {
            return;
        }

        getFooterDelegate().setOnLoadMoreListener(listener);
    }

    public RvAdapterAgent getAdapterAgent() {
        return mAdapterAgent;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public static class SDataObserver extends RecyclerView.AdapterDataObserver {

        private RvAdapterAgent agent;
        public SDataObserver(RvAdapterAgent adapter) {
            agent = adapter;
        }

        @Override
        public void onChanged() {
            agent.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            agent.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            agent.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            agent.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            agent.notifyItemMoved(fromPosition, toPosition);
        }
    }
}
