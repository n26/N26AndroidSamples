package de.n26.n26androidsamples.credit.presentation.dashboard;

import org.junit.Test;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;

import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.BEFORE_ESIGN;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;
import static org.assertj.core.api.Assertions.assertThat;

public class CreditDashboardItemComparatorTest {

    @Test
    public void sameModelsWithSameTypeAreTheSame() {
        Object model = new Object();
        DisplayableItem item1 = DisplayableItem.toDisplayableItem(model, IN_REPAYMENT);
        DisplayableItem item2 = DisplayableItem.toDisplayableItem(model, IN_REPAYMENT);

        CreditDashboardItemComparator comparator = new CreditDashboardItemComparator();
        assertThat(comparator.areItemsTheSame(item1, item2)).isTrue();
        assertThat(comparator.areContentsTheSame(item1, item2)).isTrue();
    }

    @Test
    public void sameModelsWithDifferentTypeAreNotTheSame() {
        Object o = new Object();
        DisplayableItem item1 = DisplayableItem.toDisplayableItem(o, IN_REPAYMENT);
        DisplayableItem item2 = DisplayableItem.toDisplayableItem(o, BEFORE_ESIGN);

        CreditDashboardItemComparator comparator = new CreditDashboardItemComparator();
        assertThat(comparator.areItemsTheSame(item1, item2)).isFalse();
        assertThat(comparator.areContentsTheSame(item1, item2)).isFalse();
    }

    @Test
    public void differentModelsWithSameTypeAreNotTheSame() {
        DisplayableItem item1 = DisplayableItem.toDisplayableItem(new Object(), IN_REPAYMENT);
        DisplayableItem item2 = DisplayableItem.toDisplayableItem(new Object(), IN_REPAYMENT);

        CreditDashboardItemComparator comparator = new CreditDashboardItemComparator();
        assertThat(comparator.areItemsTheSame(item1, item2)).isFalse();
        assertThat(comparator.areContentsTheSame(item1, item2)).isFalse();
    }
}
