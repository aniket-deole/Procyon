package com.procyon.procyon.mainlist;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.procyon.procyon.IFC;
import com.procyon.procyon.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aniket on 3/18/15.
 */
public class MainListFragment extends Fragment {

    private RecyclerView entries;
    private MainListAdapter mainListAdapter;
    private List<MainListEntry> data = Collections.emptyList();
    private View containerView;

    private IFC ifc;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.app_bar);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        ifc = (IFC) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main_list, container, false);
        entries = (RecyclerView) layout.findViewById(R.id.main_list_view);
        mainListAdapter = new MainListAdapter(getActivity(), getData());
        containerView = getActivity().findViewById(R.id.main_list_view);
        entries.setAdapter(mainListAdapter);
        entries.setLayoutManager(new LinearLayoutManager(getActivity()));
        entries.addOnItemTouchListener(new EntriesOnTouchListener(getActivity(), entries, new ClickListener() {
            @Override
            public void onClick(View view, int position, int cx, int cy) {
                ifc.sendMessage("The Text at " + position + " is " +
                        data.get(position).value, cx, cy);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        data.add(new MainListEntry("There"));
        data.add(new MainListEntry("is"));
        data.add(new MainListEntry("no"));
        data.add(new MainListEntry("pain"));
        data.add(new MainListEntry("you're"));
        data.add(new MainListEntry("receding."));
        data.add(new MainListEntry("A"));
        data.add(new MainListEntry("distant"));
        data.add(new MainListEntry("ship"));
        data.add(new MainListEntry("smoke"));
        data.add(new MainListEntry("on"));
        data.add(new MainListEntry("the"));
        data.add(new MainListEntry("horizon."));
        data.add(new MainListEntry("You"));
        data.add(new MainListEntry("are"));
        data.add(new MainListEntry("only"));
        data.add(new MainListEntry("coming"));
        data.add(new MainListEntry("through"));
        data.add(new MainListEntry("in"));
        data.add(new MainListEntry("veins."));
        data.add(new MainListEntry("Your"));
        data.add(new MainListEntry("lips"));
        data.add(new MainListEntry("move"));
        data.add(new MainListEntry("but"));
        data.add(new MainListEntry("I"));
        data.add(new MainListEntry("can't"));
        data.add(new MainListEntry("hear"));
        data.add(new MainListEntry("what"));
        data.add(new MainListEntry("you"));
        data.add(new MainListEntry("saying."));
    }

    public List<MainListEntry> getData() {
        return data;
    }

    class EntriesOnTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public EntriesOnTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && clickListener != null) {
                        clickListener.onLongClick(childView, recyclerView.getChildPosition(childView));
                    }
                }

            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(childView, recyclerView.getChildPosition(childView), Math.round (e.getX()),
                        Math.round (e.getY()));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position, int cx, int cy);

        public void onLongClick(View view, int position);
    }

}
