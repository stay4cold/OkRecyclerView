package com.stay4cold.okrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/13
 * Description:
 */
public class RvAdapterAgent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RvAdapterAgent.class.getSimpleName();

    private ArrayList<HeaderDelegate> mHeaders = new ArrayList<>();
    private ArrayList<FooterDelegate> mFooters = new ArrayList<>();

    private RecyclerView mOriginalRv;

    private RecyclerView.Adapter mOriginalAdapter;

    public RvAdapterAgent(RecyclerView recyclerView) {
        mOriginalRv = recyclerView;
        mOriginalAdapter= mOriginalRv.getAdapter();
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
            int index = position - mHeaders.size() - mOriginalAdapter.getItemCount();
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
                return header.onCreateView(parent);
            }
        }
        return null;
    }

    private View getFooterByType(ViewGroup parent, int viewType) {
        for (FooterDelegate footer : mFooters) {
            if (footer.hashCode() == viewType) {
                return footer.onCreateView(parent);
            }
        }
        return null;
    }

    //--------->Header And Footer function start<------------//

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

    public HeaderDelegate getHeader(int index) {
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
}
