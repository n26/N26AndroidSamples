package de.n26.n26androidsamples.base.presentation;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.support.annotation.LayoutRes;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends LifecycleActivity {

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
