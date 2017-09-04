package de.n26.n26androidsamples.application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import de.n26.n26androidsamples.base.injection.modules.ActivityModule;
import de.n26.n26androidsamples.home.presentation.HomeActivityComponent;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, DataModule.class, ViewModelModule.class})
public interface ApplicationComponent {

    void inject(N26SamplesApplication app);

    HomeActivityComponent createHomeActivityComponent(ActivityModule module);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(N26SamplesApplication app);

        ApplicationComponent build();
    }
}
