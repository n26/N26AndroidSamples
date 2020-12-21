package de.n26.n26androidsamples.credit.test_common;

import de.n26.n26androidsamples.base.common.rx.SchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler scheduler;

    public TestSchedulerProvider(TestScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Scheduler computation() {
        return scheduler;
    }

    @Override
    public Scheduler io() {
        return scheduler;
    }

    @Override
    public Scheduler ui() {
        return scheduler;
    }
}