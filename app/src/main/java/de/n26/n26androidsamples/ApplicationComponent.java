package de.n26.n26androidsamples;

import javax.inject.Singleton;

import dagger.Component;
import de.n26.n26androidsamples.base.injection.modules.LoggingModule;
import de.n26.n26androidsamples.injection.modules.FeaturesModule;
import de.n26.n26androidsamples.injection.modules.NetworkModule;

@Singleton
@Component(modules = {NetworkModule.class, LoggingModule.class, FeaturesModule.class})
public interface ApplicationComponent {

    void inject(N26SamplesApplication app);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
