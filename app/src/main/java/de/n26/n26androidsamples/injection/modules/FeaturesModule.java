package de.n26.n26androidsamples.injection.modules;

import dagger.Module;
import de.n26.n26androidsamples.credit.CreditModule;

@Module(includes = {CreditModule.class})
public final class FeaturesModule {
}
