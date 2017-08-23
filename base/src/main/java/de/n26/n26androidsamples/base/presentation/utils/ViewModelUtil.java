package de.n26.n26androidsamples.base.presentation.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 Creates a one off view model factory for the given view model instance.
 */
public class ViewModelUtil {

    @Inject
    public ViewModelUtil() {}

    public <T extends ViewModel> ViewModelProvider.Factory createFor(@NonNull final T viewModel) {
        return new ViewModelProvider.Factory() {

            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(viewModel.getClass())) {
                    return (T) viewModel;
                }
                throw new IllegalArgumentException("unexpected viewModel class " + modelClass);
            }
        };
    }

}
