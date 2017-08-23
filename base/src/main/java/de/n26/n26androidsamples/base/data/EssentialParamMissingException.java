package de.n26.n26androidsamples.base.data;


import android.support.annotation.NonNull;

/**
 * Exception thrown when an essential parameter is missing in the backend/network response.
 */
public class EssentialParamMissingException extends RuntimeException {

    public EssentialParamMissingException(@NonNull final String missingParam, @NonNull final Object rawObject) {
        super("The params: " + missingParam + " are missing in received object: " + rawObject);
    }
}
