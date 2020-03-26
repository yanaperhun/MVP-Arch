package com.qati.cats.ui;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.qati.cats.ssl.OkHttpUrlLoader;
import com.qati.cats.ssl.Tls12SocketFactoryJava;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

@GlideModule
public class MyGlideModule extends AppGlideModule {

    /**
     * Avoid following errors on pre-lollipop devices:
     *
     * class com.bumptech.glide.load.engine.GlideException: Failed to load resource
     * Cause (1 of 1): class com.bumptech.glide.load.engine.GlideException: Fetching data failed, class java.io.InputStream, REMOTE
     * Cause (1 of 1): class com.bumptech.glide.load.engine.GlideException: Fetch failed
     * Cause (1 of 1): class javax.net.ssl.SSLHandshakeException: javax.net.ssl.SSLProtocolException: SSL handshake aborted: ssl=0xb84fdb80: Failure in SSL library, usually a protocol error
     * error:1407742E:SSL routines:SSL23_GET_SERVER_HELLO:tlsv1 alert protocol version (external/openssl/ssl/s23_clnt.c:741 0x96f6f926:0x00000000)
     * W/Glide: Load failed for https://www.legalzoom.com/sites/legalzoom.com/files/uploaded/attorney/atty-258.jpg with size [100x100]
     */

    //Especially enable TLS12 on pre-lollipop devices.
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);

        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(enableTls12OnPreLollipop(clientBuilder).build());

        registry.replace(GlideUrl.class, InputStream.class, factory);
    }

    private OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT < 20) {
            try {

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
                KeyStore keyStore = null;
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }
                X509TrustManager trustManager = ((X509TrustManager) trustManagers[0]);

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, null);

                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactoryJava(sc.getSocketFactory()), trustManager);

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                ArrayList<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception e) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", e);
            }

        }

        return client;
    }
}
