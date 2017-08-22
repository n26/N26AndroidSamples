package de.n26.n26androidsamples.base.presentation;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseInjectingFragment extends LifecycleFragment {

    @Override
    public void onAttach(final Context context) {
        onInject();

        super.onAttach(context);
    }

    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    public abstract void onInject();

    @LayoutRes
    protected abstract int getLayoutId();
}
