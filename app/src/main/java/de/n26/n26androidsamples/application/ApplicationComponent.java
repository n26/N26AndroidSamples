package de.n26.n26androidsamples.application;

import javax.inject.Singleton;

import dagger.Component;
import de.n26.n26androidsamples.base.presentation.injection.modules.ActivityModule;
import de.n26.n26androidsamples.home.presentation.HomeActivityComponent;

@Singleton
@Component(modules = {NetworkModule.class, LoggingModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(N26SamplesApplication app);

    HomeActivityComponent createHomeActivityComponent(ActivityModule module);

    @Component.Builder
    interface Builder {

        ApplicationComponent build();
    }
}
