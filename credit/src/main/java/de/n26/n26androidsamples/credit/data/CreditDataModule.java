package de.n26.n26androidsamples.credit.data;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class CreditDataModule {

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
}
