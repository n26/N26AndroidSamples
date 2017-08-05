package de.n26.n26androidsamples.base.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.n26.n26androidsamples.base.common.preconditions.Preconditions;

/**
 * Created by Lucia on 14/07/2017.
 */
public abstract class BaseInjectingActivity<Component> extends AppCompatActivity {

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
