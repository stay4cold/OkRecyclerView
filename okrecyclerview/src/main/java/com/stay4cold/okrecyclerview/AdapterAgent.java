package com.stay4cold.okrecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.stay4cold.okrecyclerview.delegate.FooterDelegate;
import com.stay4cold.okrecyclerview.delegate.HeaderDelegate;
import com.stay4cold.okrecyclerview.delegate.HolderDelegate;

import java.util.ArrayList;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 * 将原始的Adapter进行包装生成的新Adapter
 */
public class AdapterAgent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = AdapterAgent.class.getSimpleName();

    private ArrayList<HeaderDelegate> mHeaders = new ArrayList<>();
    private ArrayList<FooterDelegate> mFooters = new ArrayList<>();

    //原始的Recyclerview
    private RecyclerView mOriginalRv;

    //原始RecyclerView中的Adapter
    private RecyclerView.Adapter mOriginalAdapter;

    //代理原始GridLayoutManager中的Spansizelookup，其中处理Header和Footer的item
    private GridSpansizeLookup mSpansizeLookup;

    public AdapterAgent(RecyclerView recyclerView) {
        mOriginalRv = recyclerView;
        mOriginalAdapter = mOriginalRv.getAdapter();

        if (mOriginalRv.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) mOriginalRv.getLayoutManager();
            mSpansizeLookup = new GridSpansizeLookup(lm);
            lm.setSpanSizeLookup(mSpansizeLookup);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View header = getHeaderByType(parent, viewType);
        if (header != null) {
            return new FooterHolder(header);
        }

        View footer = getFooterByType(parent, viewType);
        if (footer != null) {
            return new FooterHolder(footer);
        }

        return mOriginalAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mHeaders.size() != 0 && position < mHeaders.size()) {
            mHeaders.get(position).onBindView(holder.itemView);
            return;
        }

        int index = position - mHeaders.size() - mOriginalAdapter.getItemCount();
        if (mFooters.size() != 0 && index >= 0) {
            mFooters.get(index).onBindView(holder.itemView);
            return;
        }

        mOriginalAdapter.onBindViewHolder(holder, position - mHeaders.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaders.size() != 0) {
            if (position < mHeaders.size()) {
                return mHeaders.get(position).hashCode();
            }
        }

        if (mFooters.size() != 0) {
            int index = position - mHeaders.size() - getRealItemCount();
            if (index >= 0) {
                return mFooters.get(index).hashCode();
            }
        }

        return mOriginalAdapter.getItemViewType(position - mHeaders.size());
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + mFooters.size() + mOriginalAdapter.getItemCount();
    }

    public int getRealItemCount() {
        return mOriginalAdapter.getItemCount();
    }

    private View getHeaderByType(ViewGroup parent, int viewType) {
        for (HeaderDelegate header : mHeaders) {
            if (header.hashCode() == viewType) {
                return convertLayoutParams(header.onCreateView(parent));
            }
        }
        return null;
    }

    private View getFooterByType(ViewGroup parent, int viewType) {
        for (FooterDelegate footer : mFooters) {
            if (footer.hashCode() == viewType) {
                return convertLayoutParams(footer.onCreateView(parent));
            }
        }
        return null;
    }

    /**
     * 处理StaggeredGridLayoutManager中Header和Footer的Span，使其可以占满整个Item
     *
     * @param view
     * @return
     */
    private View convertLayoutParams(View view) {
        if (view == null) {
            return null;
        }

        final RecyclerView.LayoutManager lm = mOriginalRv.getLayoutManager();

        if (lm instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams slp;
            if (view.getLayoutParams() != null) {
                slp = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
            } else {
                slp = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            slp.setFullSpan(true);
            view.setLayoutParams(slp);
        }

        return view;
    }

    public void addHeader(HeaderDelegate header) {
        if (header == null) {
            throw new IllegalArgumentException(TAG + " header can't be null");
        }
        mHeaders.add(header);
        notifyItemInserted(mHeaders.size() - 1);
    }

    public void addFooter(FooterDelegate footer) {
        if (footer == null) {
            throw new IllegalArgumentException(TAG + " footer can't be null");
        }
        mFooters.add(footer);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeHeader(RecyclerView.ViewHolder header) {
        int position = mHeaders.indexOf(header);
        mHeaders.remove(header);
        notifyItemRemoved(position);
    }

    public void removeFooter(RecyclerView.ViewHolder footer) {
        int position = getItemCount() - mFooters.size() + mFooters.indexOf(footer);
        mFooters.remove(footer);
        notifyItemRemoved(position);
    }

    public void removeAllHeaders() {
        int count = mHeaders.size();
        if (count == 0) {
            return;
        }

        mHeaders.clear();
        notifyItemRangeRemoved(0, count);
    }

    public void removeAllFooters() {
        int count = mFooters.size();
        if (count == 0) {
            return;
        }

        mFooters.clear();
        notifyItemRangeRemoved(getItemCount() - 1, count);
    }

    public int getHeadersCount() {
        return mHeaders.size();
    }

    public int getFootersCount() {
        return mFooters.size();
    }

    public HolderDelegate getHeader(int index) {
        return mHeaders.get(index);
    }

    public FooterDelegate getFooter(int index) {
        return mFooters.get(index);
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public class GridSpansizeLookup extends GridLayoutManager.SpanSizeLookup {

        private GridLayoutManager.SpanSizeLookup lookup;
        private int count;

        public GridSpansizeLookup(GridLayoutManager lm) {
            lookup = lm.getSpanSizeLookup();
            count = lm.getSpanCount();
        }

        /**
         * 返回处理后的spanSize，注意：新的adapter中position位置已经更改，所以应该为 position - mHeaders.size()
         *
         * @param position
         * @return
         */
        @Override
        public int getSpanSize(int position) {
            if (mHeaders.size() != 0 && position < mHeaders.size()) {
                return count;
            }

            if (mFooters.size() != 0 && (position - mHeaders.size() - getRealItemCount()) >= 0) {
                return count;
            }
            return lookup.getSpanSize(position - mHeaders.size());
        }
    }
}
