package de.n26.n26androidsamples.base.common.providers;

import javax.inject.Inject;

/**
 Class to be able to test timestamp related features. Inject this instead of using System.currentTimeMillis()
 */
public class TimestampProvider {

    @Inject
    TimestampProvider() {}

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
