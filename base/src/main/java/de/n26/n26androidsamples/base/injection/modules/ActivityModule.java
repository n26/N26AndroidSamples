package de.n26.n26androidsamples.base.injection.modules;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.injection.qualifiers.ForActivity;

@Module
public final class ActivityModule {

    @NonNull
    private final LifecycleActivity activity;

    public ActivityModule(@NonNull final LifecycleActivity activity) {
        this.activity = activity;
    }

    @ForActivity
    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager(@NonNull final AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
