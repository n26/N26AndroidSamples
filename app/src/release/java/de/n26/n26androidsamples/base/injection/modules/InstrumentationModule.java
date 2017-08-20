package de.n26.n26androidsamples.base.injection.modules;

import java.util.Collections;
import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import de.n26.n26androidsamples.injection.modules.NetworkModule.AppInterceptor;
import de.n26.n26androidsamples.injection.modules.NetworkModule.NetworkInterceptor;
import okhttp3.Interceptor;

/**
 * Created by Lucia on 05/08/2017.
 */
@Module
public final class InstrumentationModule {

    @Provides
    @Singleton
    @NetworkInterceptor
    @ElementsIntoSet
    static Set<Interceptor> provideNetworkInterceptors() {
        return Collections.emptySet();
    }

    @Provides
    @Singleton
    @AppInterceptor
    @ElementsIntoSet
    static Set<Interceptor> provideAppInterceptors() {
        return Collections.emptySet();
    }
}

