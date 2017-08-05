package de.n26.n26androidsamples.base.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Lucia on 11/07/2017.
 */
final class DiffUtilCallback extends DiffUtil.Callback {

    @NonNull
    private final List<DisplayableItem> oldItems;

    @NonNull
    private final List<DisplayableItem> newItems;

    @NonNull
    private final ItemComparator comparator;

    DiffUtilCallback(@NonNull final List<DisplayableItem> oldItems,
                     @NonNull final List<DisplayableItem> newItems,
                     @NonNull final ItemComparator comparator) {
        this.oldItems = oldItems;
        this.newItems = newItems;
        this.comparator = comparator;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        return comparator.areItemsTheSame(oldItems.get(oldItemPosition),
            newItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        return comparator.areContentsTheSame(oldItems.get(oldItemPosition),
            newItems.get(newItemPosition));
    }
}
