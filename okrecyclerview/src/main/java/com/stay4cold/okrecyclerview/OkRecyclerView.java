package com.stay4cold.okrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.stay4cold.okrecyclerview.delegate.DefaultFooterView;
import com.stay4cold.okrecyclerview.delegate.DefaultLoaderView;
import com.stay4cold.okrecyclerview.holder.FooterView;
import com.stay4cold.okrecyclerview.holder.HeaderView;
import com.stay4cold.okrecyclerview.holder.LoaderView;
import com.stay4cold.okrecyclerview.holder.OnFooterListener;
import com.stay4cold.okrecyclerview.holder.OnLoaderListener;
import com.stay4cold.okrecyclerview.state.FooterState;
import com.stay4cold.okrecyclerview.state.LoaderState;
import java.util.ArrayList;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/12
 * Description:
 */
public class OkRecyclerView {

    private static final String TAG = OkRecyclerView.class.getSimpleName();

    private RecyclerView mOriginalRv;

    private RecyclerView.Adapter mOriginalAdapter;

    private AdapterAgent mAdapterAgent;

    private LoaderView mLoader;

    private FooterView mFooter;

    private View mLoaderTargetView;

    private OnLoaderListener mLoaderListener;

    private OnLoadMoreListener mListener;

    private OnFooterListener mFooterListener;

    private ArrayList<HeaderView> mHeaders = new ArrayList<>();

    private OkRecyclerView(Builder builder) {

        this.mOriginalRv = builder.recyclerView;
        this.mLoader = builder.loader;
        this.mFooter = builder.footer;
        this.mLoaderTargetView = builder.loaderTargerView;
        this.mHeaders = builder.headerViews;
        this.mListener = builder.listener;
        this.mFooterListener = builder.footerListener;
        this.mLoaderListener = builder.loaderListener;

        //设置默认的TargetView
        if (mLoaderTargetView == null) {
            mLoaderTargetView = mOriginalRv;
        }

        mAdapterAgent = new AdapterAgent(mOriginalRv);

        mOriginalAdapter = mOriginalRv.getAdapter();

        mOriginalAdapter.registerAdapterDataObserver(new SDataObserver(mAdapterAgent));

        mOriginalRv.setAdapter(mAdapterAgent);

        //添加Header
        for (HeaderView view : mHeaders) {
            if (view != null) {
                mAdapterAgent.addHeader(view);
            }
        }

        //添加LoaderListener
        getLoader().setOnLoaderListener(mLoaderListener);

        //添加Footer
        mAdapterAgent.addFooter(getFooter());

        //添加OnLoadMoreListener
        getFooter().setOnLoadMoreListener(mListener);

        //添加Footer listener
        getFooter().setOnFooterListener(mFooterListener);
    }

    private LoaderView getLoader() {
        if (mLoader == null) {
            mLoader = new DefaultLoaderView(mLoaderTargetView);
        }

        return mLoader;
    }

    public void setLoaderState(LoaderState state) {
        getLoader().setState(state);
    }

    public LoaderState getLoaderState() {
        return getLoader().getState();
    }

    private FooterView getFooter() {
        if (mFooter == null) {
            mFooter = new DefaultFooterView();
        }

        return mFooter;
    }

    public void setFooterState(FooterState state) {
        getFooter().setState(state);
    }

    public FooterState getFooterState() {
        return getFooter().getState();
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

    public static final class Builder {

        private RecyclerView recyclerView;
        private LoaderView loader;
        private View loaderTargerView;
        private ArrayList<HeaderView> headerViews = new ArrayList<>();
        private FooterView footer;
        private OnLoadMoreListener listener;
        private OnFooterListener footerListener;
        private OnLoaderListener loaderListener;

        public Builder(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public Builder setLoader(LoaderView loader) {
            this.loader = loader;
            return this;
        }

        /**
         * 设置loading的target view
         */
        public Builder setLoaderTargetView(View loaderTargetView) {
            this.loaderTargerView = loaderTargetView;
            return this;
        }

        /**
         * 可以添加多个Header
         */
        public Builder addHeader(HeaderView headerView) {
            headerViews.add(headerView);
            return this;
        }

        public Builder setFooter(FooterView footer) {
            this.footer = footer;
            return this;
        }

        public Builder setOnLoadmoreListener(OnLoadMoreListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setOnFooterListener(OnFooterListener listener) {
            this.footerListener = listener;
            return this;
        }

        public Builder setOnLoaderListener(OnLoaderListener listener) {
            this.loaderListener = listener;
            return this;
        }

        public OkRecyclerView build() {
            return new OkRecyclerView(this);
        }
    }
}
