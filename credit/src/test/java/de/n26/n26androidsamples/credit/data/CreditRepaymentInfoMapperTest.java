package de.n26.n26androidsamples.credit.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.n26.n26androidsamples.base.data.EssentialParamMissingException;
import de.n26.n26androidsamples.credit.test_common.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 Created by luciapayo on 04/09/2017
 */
public class CreditRepaymentInfoMapperTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void map() {
        CreditRepaymentInfoRaw raw = CreditDataTestUtils.creditRepaymentInfoRawTestBuilder().build();

        CreditRepaymentInfo mapped = CreditRepaymentInfoMapper.processRaw(raw);

        assertThat(mapped.disbursedDate()).isEqualTo(raw.disbursedDate());
        assertThat(mapped.nextPaymentDate()).isEqualTo(raw.nextPaymentDate());
        assertThat(mapped.nextPayment()).isEqualTo(raw.nextPayment());
        assertThat(mapped.totalPaid()).isEqualTo(raw.totalPaid());
    }

    @Test
    public void exceptionThrownWhenEssentialParamsMissing() {
        thrown.expect(EssentialParamMissingException.class);
        thrown.expectMessage("disbursedDate nextPaymentDate");

        CreditRepaymentInfoRaw raw = CreditDataTestUtils.creditRepaymentInfoRawTestBuilder()
                                                        .disbursedDate(null)
                                                        .nextPaymentDate(null)
                                                        .build();

        CreditRepaymentInfoMapper.processRaw(raw);
    }

}