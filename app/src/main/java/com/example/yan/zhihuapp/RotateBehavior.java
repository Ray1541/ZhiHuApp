package com.example.yan.zhihuapp;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by muyang on 2016/5/9.
 *
 */
public class RotateBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private static final String TAG = RotateBehavior.class.getSimpleName();

    public RotateBehavior() {

    }

    public RotateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        float translationY = getFabTranslationYForSnackBar(parent, child);
        float percentComplete = -translationY / dependency.getHeight();
        child.setRotation(-135 * percentComplete);
        child.setTranslationY(translationY);
        return true;
    }

    private float getFabTranslationYForSnackBar(CoordinatorLayout parent,
                                                FloatingActionButton fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                //view.getHeight()固定为144
                //ViewCompat.getTranslationY(view)从144-0，再从0-144
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());
                Log.d("TranslationY", ViewCompat.getTranslationY(view)+"");
                Log.d("Height",view.getHeight()+"");
            }
        }

        return minOffset;
    }
}