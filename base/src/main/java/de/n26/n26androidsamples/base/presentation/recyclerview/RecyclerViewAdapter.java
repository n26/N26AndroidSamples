package de.n26.n26androidsamples.base.presentation.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.n26.n26androidsamples.base.common.preconditions.AndroidPreconditions;
import io.reactivex.Single;

/**
 * Implementation of {@link Adapter} for {@link DisplayableItem}.
 * <p>
 * Created by Lucia on 11/07/2017.
 */
public class RecyclerViewAdapter extends Adapter {

    @NonNull
    private final List<DisplayableItem> modelItems = new ArrayList<>();

    @NonNull
    private final ItemComparator comparator;

    @NonNull
    private final Map<Integer, ViewHolderFactory> factoryMap;

    @NonNull
    private final Map<Integer, ViewHolderBinder> binderMap;

    @NonNull
    private final AndroidPreconditions androidPreconditions;

    public RecyclerViewAdapter(@NonNull final ItemComparator comparator,
                               @NonNull final Map<Integer, ViewHolderFactory> factoryMap,
                               @NonNull final Map<Integer, ViewHolderBinder> binderMap,
                               @NonNull final AndroidPreconditions androidPreconditions) {
        this.comparator = comparator;
        this.factoryMap = factoryMap;
        this.binderMap = binderMap;
        this.androidPreconditions = androidPreconditions;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return factoryMap.get(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DisplayableItem item = modelItems.get(position);
        binderMap.get(item.type()).bind(holder, item);
    }

    @Override
    public int getItemCount() {
        return modelItems.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return modelItems.get(position).type();
    }

    /**
     * Updates modelItems currently stored in adapter with the new modelItems.
     *
     * @param items collection to update the previous values
     */
    public void update(@NonNull final List<DisplayableItem> items) {
        androidPreconditions.assertUiThread();

        if (modelItems.isEmpty()) {
            updateAllItems(items);
        } else {
            updateDiffItemsOnly(items);
        }
    }

    /**
     * Only use for the first update of the adapter, whe it is still empty.
     */
    private void updateAllItems(@NonNull final List<DisplayableItem> items) {
        Single.just(items)
              .doOnSuccess(this::updateItemsInModel)
              .subscribe(__ -> notifyDataSetChanged());
    }

    /**
     * Do not use for first update of the adapter. The method {@link DiffUtil.DiffResult#dispatchUpdatesTo(Adapter)} is significantly slower than {@link
     * RecyclerViewAdapter#notifyDataSetChanged()} when it comes to update all the items in the adapter.
     */
    private void updateDiffItemsOnly(@NonNull final List<DisplayableItem> items) {
        // IMPROVEMENT: The diff calculation should happen in the background
        Single.fromCallable(() -> calculateDiff(items))
              .doOnSuccess(__ -> updateItemsInModel(items))
              .subscribe(this::updateAdapterWithDiffResult);
    }

    private DiffUtil.DiffResult calculateDiff(@NonNull final List<DisplayableItem> newItems) {
        return DiffUtil.calculateDiff(new DiffUtilCallback(modelItems, newItems, comparator));
    }

    private void updateItemsInModel(@NonNull final List<DisplayableItem> items) {
        modelItems.clear();
        modelItems.addAll(items);
    }

    private void updateAdapterWithDiffResult(@NonNull final DiffUtil.DiffResult result) {
        result.dispatchUpdatesTo(this);
    }
}
