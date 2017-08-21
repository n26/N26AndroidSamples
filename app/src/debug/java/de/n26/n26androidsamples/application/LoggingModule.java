package de.n26.n26androidsamples.application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 Created by luciapayo on 11/06/2017
 */
@Module
class LoggingModule {

    @Provides
    @Singleton
    static Timber.Tree provideLoggingTree() {
        return new Timber.DebugTree();
    }
}
