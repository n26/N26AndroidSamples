package de.n26.n26androidsamples;

import android.app.Application;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Lucia on 05/08/2017.
 */
public class N26SamplesApplication extends Application {
    private ApplicationComponent component;

    @Inject
    Timber.Tree loggingTree;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        getComponent().inject(this);
        initialize();
    }

    @NonNull
    public ApplicationComponent getComponent() {
        if(component == null) {
            component = DaggerApplicationComponent.builder().build();
        }
        return component;
    }

    private void initialize() {
        initLogging();
    }

    private void initLogging() {
        Timber.uprootAll();
        Timber.plant(loggingTree);
    }
}
