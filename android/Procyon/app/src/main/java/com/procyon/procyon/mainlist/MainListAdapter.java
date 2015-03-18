package com.procyon.procyon.mainlist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.procyon.procyon.Intro;
import com.procyon.procyon.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by aniket on 3/18/15.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListRecyclerViewHolder> {

    private LayoutInflater inflater;
    List<MainListEntry> entries = Collections.emptyList();

    public MainListAdapter (Context context, List<MainListEntry> entries) {
        inflater = LayoutInflater.from(context);
        this.entries = entries;
    }

    @Override
    public MainListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate (R.layout.main_list_entry, parent, false);
        MainListRecyclerViewHolder mainListRecyclerViewHolder =
                new MainListRecyclerViewHolder(view);
        return mainListRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(MainListRecyclerViewHolder holder, int position) {
        holder.label.setText (entries.get(position).value);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    class MainListRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        public MainListRecyclerViewHolder (View itemView) {
            super (itemView);
            label = (TextView) itemView.findViewById(R.id.mle_label);
        }
    }


}
