package de.n26.n26androidsamples.credit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

// FIXME: This class should be in base module but the project can't compile when is placed there.
// Move when the problem with tests and modules is solved.
public class RxSchedulerOverrideRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                }
            }
        };
    }
}
