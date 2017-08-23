package de.n26.n26androidsamples.application;

import android.app.Application;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import de.n26.n26androidsamples.R;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class N26SamplesApplication extends Application {

    private ApplicationComponent component;

    @Inject
    Timber.Tree loggingTree;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        getComponent().inject(this);
        installFonts();
        initialize();
    }

    @NonNull
    public ApplicationComponent getComponent() {
        if (component == null) {
            component = DaggerApplicationComponent.builder().application(this).build();
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

    private void installFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                              .setDefaultFontPath("fonts/CalibreApp-Regular.ttf")
                                              .setFontAttrId(R.attr.fontPath)
                                              .build());
    }
}
