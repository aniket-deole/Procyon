package com.procyon.procyon.article;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.procyon.procyon.R;

/**
 * Created by aniket on 3/19/15.
 */
public class ArticleFragment extends Fragment {

    private TextView textView;
    String data;
    View layout;
    int cx, cy;
    public ArticleFragment () {
        super ();
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        layout = inflater.inflate (R.layout.fragment_article, container, false);
        textView = (TextView) layout.findViewById(R.id.article_body);
        textView.setText(data);
        // Create a reveal {@link Animator} that starts clipping the view from
        // the top left corner until the whole view is covered.


        return layout;
    }

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
       @TargetApi(Build.VERSION_CODES.LOLLIPOP)
       public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);// previously invisible view
                int finalRadius = Math.max(layout.getWidth(), layout.getHeight());
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(layout, cx, cy, 0, finalRadius);
                anim.setInterpolator(new DecelerateInterpolator());
                
                layout.setVisibility(View.VISIBLE);

                anim.start();
            } else {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            // get width and height of the view
        }
    };
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        layout.setVisibility(View.VISIBLE);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setTextView (String data,int cx, int cy)
    {
        this.data = data;
        this.cx = cx;
        this.cy = cy;
    }
}
