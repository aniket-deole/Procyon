package com.procyon.procyon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by aniket on 3/18/15.
 */
public class NavigationDrawerListAdapter extends RecyclerView.Adapter<NavigationDrawerListAdapter.NavigationDrawerRecyclerViewHolder> {

    private LayoutInflater inflator;
    List<NavigationDrawerItem> entries = Collections.emptyList();
    public NavigationDrawerListAdapter (Context context, List<NavigationDrawerItem> entries) {
        inflator = LayoutInflater.from(context);
        this.entries = entries;
    }

    @Override
    public NavigationDrawerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.navigation_drawer_entry, parent, false);
        NavigationDrawerRecyclerViewHolder navigationDrawerRecyclerViewHolder =
                new NavigationDrawerRecyclerViewHolder (view);
        return navigationDrawerRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerRecyclerViewHolder holder, int position) {
        holder.label.setText(entries.get (position).value);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    class NavigationDrawerRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        public NavigationDrawerRecyclerViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.nde_label);
        }
    }
}
