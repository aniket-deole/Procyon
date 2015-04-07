package com.procyon.procyon.mainlist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.procyon.procyon.Intro;
import com.procyon.procyon.R;
import com.procyon.procyon.utils.ViewUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by aniket on 3/18/15.
 */
public class MainListAdapter
        extends AbstractExpandableItemAdapter<MainListAdapter.MyGroupViewHolder, MainListAdapter.MyChildViewHolder> {

    private LayoutInflater inflater;
    List<MainListEntry> entries = Collections.emptyList();

    private static final String TAG = "MainListAdapter";

    private AbstractExpandableDataProvider mProvider;


    public MainListAdapter(Context context, List<MainListEntry> entries) {
        inflater = LayoutInflater.from(context);
        this.entries = entries;
    }

    public MainListAdapter(AbstractExpandableDataProvider dataProvider) {
        mProvider = dataProvider;

        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
    }
    @Override
    public int getGroupCount() {
        return mProvider.getGroupCount();
    }

    @Override
    public int getChildCount(int i) {
        return mProvider.getChildCount(i);
    }

    @Override
    public long getGroupId(int i) {
        return mProvider.getGroupItem(i).getGroupId();
    }

    @Override
    public long getChildId(int i, int i2) {
        return mProvider.getChildItem(i, i2).getChildId();
    }

    @Override
    public int getGroupItemViewType(int i) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int i, int i2) {
        return 0;
    }

    @Override
    public MainListAdapter.MyGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item_category, parent, false);
        return new MainListAdapter.MyGroupViewHolder(v);
    }

    @Override
    public MainListAdapter.MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int i) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item_article_entry, parent, false);
        return new MyChildViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(MainListAdapter.MyGroupViewHolder holder, int groupPosition, int viewType) {
        // child item
        final AbstractExpandableDataProvider.BaseData item = mProvider.getGroupItem(groupPosition);

        // set text
        holder.mTextView.setText(item.getText());

        // mark as clickable
        holder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final int expandState = holder.getExpandStateFlags();

        if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_UPDATED) != 0) {
            int bgResId;

            if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_EXPANDED) != 0) {
                bgResId = R.drawable.bg_list_item_category_expanded_state;
            } else {
                bgResId = R.drawable.bg_list_item_category_normal_state;
            }

            holder.mContainer.setBackgroundResource(bgResId);
        }
    }

    @Override
    public void onBindChildViewHolder(MainListAdapter.MyChildViewHolder holder, int groupPosition, int childPosition,
                                      int viewType) {
        // group item
        final AbstractExpandableDataProvider.ChildData item = mProvider.getChildItem(groupPosition, childPosition);

        // set text
        holder.mTextView.setText(item.getText());

        // set background resource (target view ID: container)
        int bgResId;
        bgResId = R.drawable.bg_list_item_article_entry_normal_state;
        holder.mContainer.setBackgroundResource(bgResId);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(MainListAdapter.MyGroupViewHolder holder,
                                                   int groupPosition, int x, int y, boolean expand) {
        // check the item is *not* pinned
        if (mProvider.getGroupItem(groupPosition).isPinnedToSwipeLeft()) {
            // return false to raise View.OnClickListener#onClick() event
            return false;
        }

        // check is enabled
        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
            return false;
        }

        final View containerView = holder.mContainer;

        final int offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        final int offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);


        return true;
    }

    abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public ViewGroup mContainer;
        public TextView mTextView;

        public MyBaseViewHolder(View v) {
            super(v);
            mContainer = (ViewGroup) v.findViewById(R.id.container);
            mTextView = (TextView) v.findViewById(android.R.id.text1);
        }
    }

    class MyGroupViewHolder extends MyBaseViewHolder {
        public MyGroupViewHolder(View v) {
            super(v);
        }
    }

    class MyChildViewHolder extends MyBaseViewHolder {
        public MyChildViewHolder(View v) {
            super(v);
        }
    }
}
