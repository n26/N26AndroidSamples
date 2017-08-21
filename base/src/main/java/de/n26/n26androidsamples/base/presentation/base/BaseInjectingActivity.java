package de.n26.n26androidsamples.base.presentation.base;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.n26.n26androidsamples.base.common.preconditions.Preconditions;

public abstract class BaseInjectingActivity<Component> extends LifecycleActivity {

    @Nullable
    private Component component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        component = createComponent();
        onInject(component);
        
        super.onCreate(savedInstanceState);
    }

    @NonNull
    public Component getComponent() {
        return Preconditions.get(component);
    }

    protected abstract void onInject(@NonNull final Component component);

    @LayoutRes
    protected abstract int getLayoutId();

    @NonNull
    protected abstract Component createComponent();
}
