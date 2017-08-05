package de.n26.n26androidsamples.base.presentation.recyclerview;

/**
 * Created by Lucia on 11/07/2017.
 */
public interface ItemComparator {

    /**
     Decides whether two {@link DisplayableItem} represent the same Item.
     For example, if your items have unique ids, this method should check their id equality.
     @return True if the two items represent the same object or false if they are different.
     */
    boolean areItemsTheSame(final DisplayableItem item1, final DisplayableItem item2);

    /**
     Checks whether the visual representation of two {@link DisplayableItem}s is the same.
     <p>
     This method is called only if {@link #areItemsTheSame(DisplayableItem, DisplayableItem)}
     returns {@code true} for these items. For instance, when the item is the same with different
     state, like selected.
     @return True if the visual representation for the {@link DisplayableItem}s are the same or
     false if they are different.
     */
    boolean areContentsTheSame(final DisplayableItem item1, final DisplayableItem item2);
}
