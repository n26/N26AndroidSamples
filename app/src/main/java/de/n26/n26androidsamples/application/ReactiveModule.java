package de.n26.n26androidsamples.application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.common.rx.RxSchedulerProvider;
import de.n26.n26androidsamples.base.common.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * This module is to ensure the Inversion of control to the Reactive part in the project.
 *
 * ** This also helps us in writing the unit test for the places we use schedulers.
 */
@Module
public class ReactiveModule {

    /**
     * This helps us inject a new instance of {@link CompositeDisposable} every time a view model needs it.
     *
     * @return new instance of {@link CompositeDisposable}
     */
    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

    /**
     * There is only one scheduler needed for the whole project, so we have made it with the
     * {@link Singleton} scope.
     *
     * @return {@link Singleton} scoped object of {@link RxSchedulerProvider}
     */
    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new RxSchedulerProvider();
    }
}
