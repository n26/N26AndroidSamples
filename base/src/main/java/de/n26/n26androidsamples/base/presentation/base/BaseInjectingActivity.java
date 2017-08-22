package de.n26.n26androidsamples.base.presentation.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.n26.n26androidsamples.base.common.preconditions.Preconditions;

public abstract class BaseInjectingActivity<Component> extends BaseActivity {

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

    @NonNull
    protected abstract Component createComponent();
}
