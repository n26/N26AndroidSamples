package de.n26.n26androidsamples.application;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.injection.qualifiers.ForApplication;

@Module
public class ApplicationModule {

    @ForApplication
    @Provides
    Context provideApplicationContext(N26SamplesApplication app) {
        return app.getApplicationContext();
    }
}
