package de.n26.n26androidsamples.credit.presentation.dashboard;

import dagger.Subcomponent;
import de.n26.n26androidsamples.base.injection.scopes.FragmentScope;

@FragmentScope
@Subcomponent
public interface CreditDashboardComponent {

    void inject(CreditDashboardFragment fragment);

    interface CreditDashboardComponentCreator {

        CreditDashboardComponent createCreditDashboardComponent();
    }
}
