package de.n26.n26androidsamples.home.presentation;

import android.support.annotation.NonNull;

import de.n26.n26androidsamples.application.N26SamplesApplication;
import de.n26.n26androidsamples.base.presentation.base.BaseInjectingActivity;
import de.n26.n26androidsamples.base.presentation.injection.modules.ActivityModule;

public class HomeActivity extends BaseInjectingActivity<HomeActivityComponent> {

    @Override
    protected void onInject(@NonNull final HomeActivityComponent homeActivityComponent) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @NonNull
    @Override
    protected HomeActivityComponent createComponent() {
        N26SamplesApplication app = N26SamplesApplication.class.cast(getApplication());
        ActivityModule activityModule = new ActivityModule();
        return app.getComponent().createHomeActivityComponent(activityModule);
    }
}
