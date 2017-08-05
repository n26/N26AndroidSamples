package de.n26.n26androidsamples.base.common.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

// TODO: Write tests for this
public final class ListUtils {

    /**
     * Returns a new list containing the second list appended to the
     * first list.
     *
     * @param list1  the first list
     * @param list2  the second list
     * @return  a new list containing the union of those lists
     */
    @NonNull
    public static <T> List<T> union(@NonNull final List<T> list1, @NonNull final List<T> list2) {
        return new ArrayList<T>() {{
            addAll(list1);
            addAll(list2);
        }};
    }

    public static <T> boolean isNotEmpty(@NonNull final List<T> list) {
        return !list.isEmpty();
    }
}
