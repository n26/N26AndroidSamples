package de.n26.n26androidsamples.base.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.injection.qualifiers.ForActivity;

@Module
public final class ActivityModule {

    @ForActivity
    @Provides
    Context provideContext(@NonNull final AppCompatActivity activity) {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager(@NonNull final AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
