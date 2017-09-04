package de.n26.n26androidsamples.credit.data;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import de.n26.n26androidsamples.base.common.providers.TimestampProvider;
import de.n26.n26androidsamples.base.data.cache.Cache;
import de.n26.n26androidsamples.base.data.store.MemoryReactiveStore;
import de.n26.n26androidsamples.base.data.store.ReactiveStore;
import de.n26.n26androidsamples.base.data.store.Store;

@Module
public class CreditDataModule {

    private static long CACHE_MAX_AGE = 5 * 60 * 1000; // 5 minutes

    @Provides
    @IntoSet
    TypeAdapterFactory provideTypeAdapterFactory() {
        return CreditTypeAdapterFactory.create();
    }

//    @Provides
//    CreditService provideCreditService(Retrofit retrofit) {
//        return retrofit.create(CreditService.class);
//    }

    @Provides
    CreditService provideMockCreditService(Gson gson) {
        return new MockCreditService(gson);
    }

    @Provides
    @Singleton
    Store.MemoryStore<String, CreditDraft> provideCache(TimestampProvider timestampProvider) {
        return new Cache<>(CreditDraft::id, timestampProvider, CACHE_MAX_AGE);
    }

    @Provides
    @Singleton
    ReactiveStore<String, CreditDraft> provideReactiveStore(Store.MemoryStore<String, CreditDraft> cache) {
        return new MemoryReactiveStore<>(CreditDraft::id, cache);
    }
}
