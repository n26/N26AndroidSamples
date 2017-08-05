package de.n26.n26androidsamples.base.injection.modules;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import de.n26.n26androidsamples.base.common.injection.modules.NetworkModule;
import de.n26.n26androidsamples.base.common.injection.modules.NetworkModule.AppInterceptor;
import de.n26.n26androidsamples.base.common.injection.modules.NetworkModule.NetworkInterceptor;
import de.n26.n26androidsamples.base.common.injection.qualifiers.ForApplication;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Lucia on 05/08/2017.
 */
@Module
public class InstrumentationModule {

    @Provides
    @NetworkInterceptor
    @IntoSet
    @Singleton
    static HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message))
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @NetworkInterceptor
    @IntoSet
    @Singleton
    static StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @AppInterceptor
    @IntoSet
    @Singleton
    static ChuckInterceptor provideChuckInterceptor(@ForApplication Context context) {
        return new ChuckInterceptor(context);
    }
}
