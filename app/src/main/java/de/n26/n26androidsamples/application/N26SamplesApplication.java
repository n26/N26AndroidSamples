package de.n26.n26androidsamples.application;

import android.app.Application;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import de.n26.n26androidsamples.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class N26SamplesApplication extends Application {

    private ApplicationComponent component;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        getComponent().inject(this);
        installFonts();
    }

    @NonNull
    public ApplicationComponent getComponent() {
        if (component == null) {
            component = DaggerApplicationComponent.builder().application(this).build();
        }
        return component;
    }

    private void installFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                              .setDefaultFontPath("fonts/CalibreApp-Regular.ttf")
                                              .setFontAttrId(R.attr.fontPath)
                                              .build());
    }
}
