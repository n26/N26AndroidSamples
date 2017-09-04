package de.n26.n26androidsamples.credit.presentation.dashboard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.BaseTest;
import de.n26.n26androidsamples.credit.data.CreditDraft;

import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreditDisplayableItemMapperTest extends BaseTest {

    @Mock
    private InRepaymentCardViewEntityMapper viewEntityMapper;

    private CreditDisplayableItemMapper displayableItemMapper;

    @Before
    public void setUp() {
        displayableItemMapper = new CreditDisplayableItemMapper(viewEntityMapper);
    }

    @Test
    public void creditDraftsAreMappedToViewEntities() throws Exception {
        CreditDraft creditDraft = Mockito.mock(CreditDraft.class);
        new ArrangeBuilder().withMappedViewEntity(Mockito.mock(InRepaymentCardViewEntity.class));

        displayableItemMapper.apply(Collections.singletonList(creditDraft));

        Mockito.verify(viewEntityMapper).apply(creditDraft);
    }

    @Test
    public void viewEntityIsWrappedInDisplayableItem() throws Exception {
        CreditDraft creditDraft = Mockito.mock(CreditDraft.class);
        InRepaymentCardViewEntity viewEntity = Mockito.mock(InRepaymentCardViewEntity.class);
        new ArrangeBuilder().withMappedViewEntity(viewEntity);

        List<DisplayableItem> items = displayableItemMapper.apply(Collections.singletonList(creditDraft));

        List<DisplayableItem> expected = Collections.singletonList(DisplayableItem.toDisplayableItem(viewEntity, IN_REPAYMENT));
        assertThat(items).isEqualTo(expected);
    }

    private class ArrangeBuilder {

        private ArrangeBuilder withMappedViewEntity(InRepaymentCardViewEntity viewEntity) throws Exception {
            Mockito.when(viewEntityMapper.apply(Mockito.any(CreditDraft.class))).thenReturn(viewEntity);
            return this;
        }
    }
}