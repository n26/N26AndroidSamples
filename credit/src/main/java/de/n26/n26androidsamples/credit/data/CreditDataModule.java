package de.n26.n26androidsamples.credit.data;

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
}
