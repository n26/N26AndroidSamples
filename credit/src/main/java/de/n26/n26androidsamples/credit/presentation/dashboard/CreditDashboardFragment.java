package de.n26.n26androidsamples.credit.presentation.dashboard;

import de.n26.n26androidsamples.base.presentation.BaseInjectingActivity;
import de.n26.n26androidsamples.base.presentation.BaseInjectingFragment;
import de.n26.n26androidsamples.credit.R;
import de.n26.n26androidsamples.credit.presentation.dashboard.CreditDashboardComponent.CreditDashboardComponentCreator;

public class CreditDashboardFragment extends BaseInjectingFragment {

    @Override
    public void onInject() {
        final BaseInjectingActivity activity = BaseInjectingActivity.class.cast(getActivity());
        final CreditDashboardComponentCreator componentCreator = CreditDashboardComponentCreator.class.cast(activity.getComponent());
        componentCreator.createCreditDashboardComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_credit_dashboard;
    }
}
