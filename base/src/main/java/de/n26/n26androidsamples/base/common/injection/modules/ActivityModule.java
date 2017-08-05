package de.n26.n26androidsamples.base.common.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.common.injection.qualifiers.ForActivity;

/**
 * Created by Lucia on 14/07/2017.
 */
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
