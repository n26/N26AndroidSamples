package de.n26.n26androidsamples.base.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Populates a {@link ViewHolder} with the model details.
 *
 * Created by Lucia on 11/07/2017.
 */
public interface ViewHolderBinder {

    /**
     Populates the passed {@link ViewHolder} with the details of the passed {@link DisplayableItem}.
     */
    void bind(@NonNull final ViewHolder viewHolder, @NonNull final DisplayableItem item);
}
