package de.n26.n26androidsamples.base.common.rx;

import javax.inject.Singleton;

import io.reactivex.Scheduler;

@Singleton
public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}