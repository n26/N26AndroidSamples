package de.n26.n26androidsamples.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = InstrumentationModule.class)
final class NetworkModule {

    private static final String API_URL = "API_URL";

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface AppInterceptor {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface NetworkInterceptor {
    }

    @Provides
    @Singleton
    static Retrofit provideN26Api(@Named(API_URL) String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .addConverterFactory(GsonConverterFactory.create(gson))
                                     .client(client)
                                     .baseUrl(baseUrl)
                                     .build();
    }

    @Provides
    @Named(API_URL)
    static String provideBaseUrl() {
        return BuildConfig.N26_FAKE_API_URL;
    }

    @Provides
    @Singleton
    static Gson provideGson(Set<TypeAdapterFactory> typeAdapters) {
        final GsonBuilder builder = new GsonBuilder();

        for (TypeAdapterFactory factory : typeAdapters) {
            builder.registerTypeAdapterFactory(factory);
        }

        return builder.create();
    }

    @Provides
    @Singleton
    static OkHttpClient provideApiOkHttpClient(@AppInterceptor Set<Interceptor> appInterceptor,
                                               @NetworkInterceptor Set<Interceptor> networkInterceptor) {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.interceptors().addAll(appInterceptor);
        okBuilder.networkInterceptors().addAll(networkInterceptor);

        return okBuilder.build();
    }
}
