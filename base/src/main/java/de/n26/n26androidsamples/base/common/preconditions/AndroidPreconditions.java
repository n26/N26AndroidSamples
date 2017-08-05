package de.n26.n26androidsamples.base.common.preconditions;

import android.os.Looper;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by Lucia on 11/07/2017.
 */
public class AndroidPreconditions {

    @Inject
    AndroidPreconditions() {}

    /**
     Asserts that the current thread is a worker thread.
     */
    public void assertWorkerThread() {
        if (isMainThread()) {
            throw new IllegalStateException(
                "This task must be run on a worker thread and not on the Main thread.");
        }
    }

    /**
     Asserts that the current thread is the Main Thread.
     */
    public void assertUiThread() {
        if (!isMainThread()) {
            throw new IllegalStateException(
                "This task must be run on the Main thread and not on a worker thread.");
        }
    }

    /**
     Returns whether the current thread is the Android main thread
     @return true if the current thread is the main thread, otherwise; false.
     */
    public boolean isMainThread() {
        return Objects.equals(Looper.getMainLooper().getThread(), Thread.currentThread());
    }
}
