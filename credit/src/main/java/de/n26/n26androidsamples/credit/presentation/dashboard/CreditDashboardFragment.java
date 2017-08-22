package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.n26.n26androidsamples.base.presentation.BaseInjectingActivity;
import de.n26.n26androidsamples.base.presentation.BaseInjectingFragment;
import de.n26.n26androidsamples.credit.R;
import de.n26.n26androidsamples.credit.presentation.dashboard.CreditDashboardComponent.CreditDashboardComponentCreator;

public class CreditDashboardFragment extends BaseInjectingFragment {

    private RecyclerView draftSummaryList;
    private View emptyDashboardLayout;

    @Override
    public void onInject() {
        final BaseInjectingActivity activity = BaseInjectingActivity.class.cast(getActivity());
        final CreditDashboardComponentCreator componentCreator = CreditDashboardComponentCreator.class.cast(activity.getComponent());
        componentCreator.createCreditDashboardComponent().inject(this);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        draftSummaryList = view.findViewById(R.id.list_credit_dashboard_draft);
        emptyDashboardLayout = view.findViewById(R.id.layout_credit_dashboard_empty);
        configureRecyclerView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_credit_dashboard;
    }

    private void configureRecyclerView() {
        draftSummaryList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //        draftSummaryList.setAdapter(adapter);
    }
}
