package de.n26.n26androidsamples;

import dagger.Module;
import de.n26.n26androidsamples.credit.CreditModule;

@Module(includes = {CreditModule.class})
public class FeaturesModule {
}
