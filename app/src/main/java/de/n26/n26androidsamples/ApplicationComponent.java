package de.n26.n26androidsamples;

import javax.inject.Singleton;

import dagger.Component;
import de.n26.n26androidsamples.base.common.injection.modules.NetworkModule;
import de.n26.n26androidsamples.base.injection.modules.LoggingModule;

/**
 Created by luciapayo on 11/06/2017
 */
@Singleton
@Component(modules = {NetworkModule.class, LoggingModule.class})
public interface ApplicationComponent {

    void inject(N26SamplesApplication app);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
