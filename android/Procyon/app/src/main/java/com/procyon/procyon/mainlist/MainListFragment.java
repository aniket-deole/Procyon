package com.procyon.procyon.mainlist;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.procyon.procyon.IFC;
import com.procyon.procyon.Intro;
import com.procyon.procyon.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aniket on 3/18/15.
 */
public class MainListFragment extends Fragment {
    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    private RecyclerView entries;
    private MainListAdapter mainListAdapter;
    private List<MainListEntry> data = Collections.emptyList();
    private View containerView;

    private IFC ifc;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;

    public MainListFragment() {
        super();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) getView ().findViewById(R.id.main_list_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;

        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);

        // adapter
        getDataProvider ();

        final MainListAdapter mainListAdapter = new MainListAdapter(getDataProvider());

        mAdapter = mainListAdapter;

        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(mainListAdapter);       // wrap for expanding

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(false);

        // additional decorations
        //noinspection StatementWithEmptyBody
//        if (supportsViewElevation()) {
//            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
//        } else {
//            mRecyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z1)));
//        }
//        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));

        mRecyclerViewExpandableItemManager.attachRecyclerView(mRecyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.app_bar);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        ifc = (IFC) getActivity();
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
                clickListener.onClick(childView, recyclerView.getChildPosition(childView), Math.round(e.getX()),
                        Math.round(e.getY()));
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

    public AbstractExpandableDataProvider getDataProvider() {
        return ((Intro) getActivity()).getDataProvider();
    }

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

}
