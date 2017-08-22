package de.n26.n26androidsamples.home.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.n26.n26androidsamples.R;
import de.n26.n26androidsamples.application.N26SamplesApplication;
import de.n26.n26androidsamples.base.injection.modules.ActivityModule;
import de.n26.n26androidsamples.base.presentation.BaseInjectingActivity;
import de.n26.n26androidsamples.credit.presentation.dashboard.CreditDashboardFragment;
import polanski.option.Option;

public class HomeActivity extends BaseInjectingActivity<HomeActivityComponent> {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Option.ofObj(savedInstanceState).ifNone(this::showCreditFragment);
    }

    @Override
    protected void onInject(@NonNull final HomeActivityComponent homeActivityComponent) {
        homeActivityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @NonNull
    @Override
    protected HomeActivityComponent createComponent() {
        N26SamplesApplication app = N26SamplesApplication.class.cast(getApplication());
        ActivityModule activityModule = new ActivityModule();
        return app.getComponent().createHomeActivityComponent(activityModule);
    }

    private void showCreditFragment() {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.frame, new CreditDashboardFragment())
                                   .commit();
    }
}
