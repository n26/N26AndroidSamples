package de.n26.n26androidsamples;

/**
 * Created by Lucia on 05/08/2017.
 */

import javax.inject.Singleton;

import dagger.Component;
import de.n26.n26androidsamples.injection.modules.LoggingModule;

/**
 Created by luciapayo on 11/06/2017
 */
@Singleton
@Component(modules = {LoggingModule.class})
public interface ApplicationComponent {

    void inject(N26SamplesApplication app);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
    }
}
