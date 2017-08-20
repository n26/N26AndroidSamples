package de.n26.n26androidsamples.base.domain;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import polanski.option.Option;

/**
 Interfaces for Interactors. This interfaces represent use cases (this means any use case in the application should implement this contract).
 */
public interface ReactiveInteractor {

    /**
     The push interactor is used to push some changes made in presentation layer to data layer. The response for the push operation comes as onNext
     event in the returned observable.

     @param <Response> the type of the push response.
     @param <Params>   required parameters for the push.
     */
    interface PushInteractor<Params, Response> extends ReactiveInteractor {

        @NonNull
        Single<Response> getPushSingle(@NonNull final Option<Params> params);
    }

    /**
     The get interactor is used to get infinite number of updates for the specified type of data. This stream will never complete but it can error if
     there is any problems performing the required actions to serve the data.

     @param <Result> the type of the returned data.
     @param <Params> required parameters for the get.
     */
    interface GetInteractor<Params, Result> extends ReactiveInteractor {

        @NonNull
        Flowable<Result> getBehaviorStream(@NonNull final Option<Params> params);
    }

    /**
     The request interactor is used to request some result once. The returned observable is a single, emits once and then completes or errors.

     @param <Params> the type of the returned data.
     @param <Result> required parameters for the request.
     */
    interface RequestInteractor<Params, Result> extends ReactiveInteractor {

        @NonNull
        Single<Result> getRequestSingle(@NonNull final Option<Params> params);
    }

    /**
     The delete interactor is used to delete entities from data layer. The response for the delete operation comes as onNext
     event in the returned observable.

     @param <Response> the type of the delete response.
     @param <Params>   required parameters for the delete.
     */
    interface DeleteInteractor<Params, Response> extends ReactiveInteractor {

        @NonNull
        Single<Response> getDeleteSingle(@NonNull final Option<Params> params);
    }

    /**
     The refresh interactor is used to refresh the reactive store with new data. Typically calling this interactor will trigger events in its
     get interactor counterpart. The returned observable will complete when the refresh is finished or error if there was any problem in the process.

     @param <Params> required parameters for the refresh.
     */
    interface RefreshInteractor<Params> extends ReactiveInteractor {

        @NonNull
        Completable getRefreshSingle(@NonNull final Option<Params> params);
    }

}

