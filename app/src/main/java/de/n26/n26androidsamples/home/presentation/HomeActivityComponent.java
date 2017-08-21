package de.n26.n26androidsamples.home.presentation;

import dagger.Subcomponent;
import de.n26.n26androidsamples.base.presentation.injection.modules.ActivityModule;
import de.n26.n26androidsamples.base.presentation.injection.scopes.ActivityScope;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface HomeActivityComponent {

    void inject(HomeActivity homeActivity);
}
