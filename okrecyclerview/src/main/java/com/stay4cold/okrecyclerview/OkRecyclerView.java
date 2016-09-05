package com.stay4cold.okrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.stay4cold.okrecyclerview.delegate.DefaultFooterView;
import com.stay4cold.okrecyclerview.delegate.DefaultLoaderView;
import com.stay4cold.okrecyclerview.holder.FooterView;
import com.stay4cold.okrecyclerview.holder.HeaderView;
import com.stay4cold.okrecyclerview.holder.LoaderView;
import com.stay4cold.okrecyclerview.state.FooterState;

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

    private AdapterAgent mAdapterAgent;

    private LoaderView mLoadDelegate;

    private FooterView mFooterDelegate;

    private View mLoadTargetView;

    public OkRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new IllegalArgumentException(TAG + " recyclerView can't be null");
        }
        mContext = recyclerView.getContext();

        this.mOriginalRv = recyclerView;

        mAdapterAgent = new AdapterAgent(mOriginalRv);

        mOriginalAdapter = mOriginalRv.getAdapter();

        mOriginalAdapter.registerAdapterDataObserver(new SDataObserver(mAdapterAgent));

        mOriginalRv.setAdapter(mAdapterAgent);

        mAdapterAgent.addFooter(getFooter());
    }

    public AdapterAgent getAdapterAgent() {
        return mAdapterAgent;
    }

    public void setLoader(LoaderView delegate) {
        mLoadDelegate = delegate;
    }

    public LoaderView getLoader() {
        if (mLoadDelegate == null) {
            mLoadDelegate = new DefaultLoaderView(getLoadTargetView());
        }

        return mLoadDelegate;
    }

    /**
     * 设置loading的target view
     * @param view
     */
    public void setLoadTargetView(View view) {
        mLoadTargetView = view;
    }

    /**
     * 获取loading的target view，如果没有设置则返回原始的RecyclerView
     * @return
     */
    public View getLoadTargetView() {
        if (mLoadTargetView == null) {
            mLoadTargetView = mOriginalRv;
        }
        return mLoadTargetView;
    }

    /**
     * 添加一个Header，可以添加多个Header
     * @param header
     */
    public void addHeader(HeaderView header) {
        if (header != null) {
            mAdapterAgent.addHeader(header);
        }
    }

    public void setFooter(FooterView footerDelegate) {
        mFooterDelegate = footerDelegate;
    }

    public FooterView getFooter() {
        if (mFooterDelegate == null) {
            mFooterDelegate = new DefaultFooterView(mContext);
        }

        return mFooterDelegate;
    }

    public void setFooterState(FooterState state) {
        getFooter().setState(state);
    }

    public FooterState getFooterState() {
        return getFooter().getState();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener == null) {
            return;
        }

        getFooter().setOnLoadMoreListener(listener);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * todo 后期添加一些状态的自动更新，比如自动显示“Empty视图”等
     */
    public static class SDataObserver extends RecyclerView.AdapterDataObserver {

        private AdapterAgent agent;

        public SDataObserver(AdapterAgent adapter) {
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
