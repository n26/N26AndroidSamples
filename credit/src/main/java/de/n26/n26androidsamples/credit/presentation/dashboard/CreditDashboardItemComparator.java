package de.n26.n26androidsamples.credit.presentation.dashboard;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.base.presentation.recyclerview.ItemComparator;

final class CreditDashboardItemComparator implements ItemComparator {

    @Override
    public boolean areItemsTheSame(final DisplayableItem item1, final DisplayableItem item2) {
        return item1.equals(item2);
    }

    @Override
    public boolean areContentsTheSame(final DisplayableItem item1, final DisplayableItem item2) {
        return item1.equals(item2);
    }
}
