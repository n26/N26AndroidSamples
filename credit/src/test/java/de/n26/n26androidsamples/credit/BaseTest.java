package de.n26.n26androidsamples.credit;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

// FIXME: This class should be in base module but the project can't compile when is placed there.
// Move when the problem with tests and modules is solved.
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public abstract class BaseTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public final RxSchedulerOverrideRule overrideSchedulersRule = new RxSchedulerOverrideRule();
}
