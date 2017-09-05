package de.n26.n26androidsamples.credit.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.n26.n26androidsamples.credit.R;
import io.reactivex.Single;

class MockCreditService implements CreditService {

    private static final String TAG = MockCreditService.class.getSimpleName();
    @NonNull
    private final Gson gson;

    @NonNull
    private final Context context;

    MockCreditService(@NonNull final Gson gson, @NonNull final Context context) {
        this.gson = gson;
        this.context = context;
    }

    @Override
    public Single<List<CreditDraftRaw>> getCreditDrafts() {
        Type listType = new TypeToken<ArrayList<CreditDraftRaw>>() {}.getType();
        try {
            return Single.just(gson.fromJson(new InputStreamReader(context.getResources().openRawResource(R.raw.credit_drafts), "UTF-8"),
                                             listType));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(TAG + ": Error parsing from file credit_drafts.json");
        }
    }
}
