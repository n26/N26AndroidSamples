package de.n26.n26androidsamples.credit;

import dagger.Module;
import de.n26.n26androidsamples.credit.data.CreditDataModule;

@Module(includes = {CreditDataModule.class})
public class CreditModule {
}
