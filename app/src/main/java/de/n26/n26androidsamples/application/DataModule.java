package de.n26.n26androidsamples.application;

import dagger.Module;
import de.n26.n26androidsamples.credit.data.CreditDataModule;

@Module(includes = {CreditDataModule.class})
final class DataModule {
}
